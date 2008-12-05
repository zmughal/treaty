/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.vocabulary.junit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * A simple test runner for junit test cases.
 * Supports constructor injection. I.e., test cases have a constructor 
 * that is used to set instances of implementation classes. Using the constructor
 * to inject dependencies makes sense here as this happens before setup is called.
 * This makes it possible to write test cases that only reference 
 * interfaces. 
 * @author Jens Dietrich
 */

public class TestRunner {
	
	public boolean run(Class test,Class... tested) throws Exception {
		Method before = null;
		Method after = null;
		List<Method> tests = new ArrayList<Method>();
		List<Long> timeouts = new ArrayList<Long>();
		
		// collect tests 
		for (Method method:test.getMethods()) {
			if (method.getParameterTypes().length==0 && Modifier.isPublic(method.getModifiers())) {
				for (Annotation annotation:method.getAnnotations()) {
					String aName = annotation.annotationType().getName();
					if (before==null&&aName.equals("org.junit.Before")) {
						before=method;
						//System.out.println("Junit before detected: " + before);
					}
					else if (after==null&&aName.equals("org.junit.After")) {
						after=method;
						//System.out.println("Junit after detected: " + after);
					}
					else if (aName.equals("org.junit.Test")) {
						tests.add(method);
						org.junit.Test testAnnotation = (org.junit.Test)annotation;
						long timeout = testAnnotation.timeout();
						timeouts.add(timeout);
					}
				}
			}
		}
		
		boolean result = true;
		
		// check constructor
		Constructor constructor = null;
		for (Constructor c:test.getConstructors()) {
			if (c.getParameterTypes().length==tested.length) {
				boolean ok = true;
				for (int i=0;i<tested.length;i++) {
					ok = ok&&c.getParameterTypes()[i].isAssignableFrom(tested[i]);
				}
				if (ok) {
					constructor = c;
					break;
				}
			}
		}
		
		// test single test case
		if (constructor!=null) {
			for (int i=0;i<tests.size();i++) {
				Method testCase = tests.get(i);
				long timeout = timeouts.get(i);
				// if one test fails other tests will not be evaluated!
				result = result && runSingleTest(constructor,tested,testCase,before,after,timeout);
			}
			return result;
		}
		
		else {
			return false;
		}
	}

	private boolean runSingleTest(Constructor constructor, Class[] tested,final Method testCase, Method before, Method after,long timeout) {
		try {
			ExecutorService ES = Executors.newSingleThreadExecutor();
			Object[] params = new Object[tested.length];
			for (int i=0;i<params.length;i++) {
				params[i] = tested[i].newInstance();
			}
			final Object test = constructor.newInstance(params);
			
			// before
			if (before!=null) before.invoke(test,new Object[]{});
			// test
			if (timeout==0) {
				testCase.invoke(test,new Object[]{});
			}
			else {
				// run with timeout
				Callable<Object> task = new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						testCase.invoke(test,new Object[]{});
						return null;
					}
				};
				Collection<Callable<Object>> tasks = new ArrayList<Callable<Object>>(); 
				tasks.add(task);
				ES.invokeAny(tasks,timeout,TimeUnit.MILLISECONDS);
			}
			// after
			if (after!=null) after.invoke(test,new Object[]{});
			
			return true;
		}
		catch (Exception x) {			
			Logger.info("Exception in test case, this might be ok",x);
			return false;
		}
		catch (AssertionError x) {
			Logger.info("Test case failed",x);
			return false;
		}
	}

}
