package net.java.treaty.eclipse;

import org.jdom.Element;

import net.java.treaty.InstantiationContext;
/**
 * Instantiation context. This is a wrapper around an node in plugin.xml,
 * this will be used as a context node when evaluating xpath expressions
 * defining resource references.
 * @author Jens Dietrich
 *
 */
public class EclipseInstantiationContext implements InstantiationContext {
	private Element contextNode = null;
	private String path = null;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public EclipseInstantiationContext(Element contextNode,String path) {
		super();
		this.contextNode = contextNode;
		this.path = path;
	}

	public Element getContextNode() {
		return contextNode;
	}

	public void setContextNode(Element contextNode) {
		this.contextNode = contextNode;
	}
	
	public String toString() {
		return super.toString()+'('+path+')';
	}

}
