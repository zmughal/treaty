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

	public EclipseInstantiationContext(Element contextNode) {
		super();
		this.contextNode = contextNode;
	}

	public Element getContextNode() {
		return contextNode;
	}

	public void setContextNode(Element contextNode) {
		this.contextNode = contextNode;
	}
	
	public String toString() {
		return super.toString()+'('+getPath(contextNode)+')';
	}

	private String getPath(Element n) {
		if (n==null) {
			return "";
		}
		Element p = n.getParentElement();
		return this.getPath(p)+'/'+n.getName();
	}
}
