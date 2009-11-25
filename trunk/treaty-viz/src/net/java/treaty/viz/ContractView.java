/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.viz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import net.java.treaty.AbstractCondition;
import net.java.treaty.ComplexCondition;
import net.java.treaty.Conjunction;
import net.java.treaty.Connector;
import net.java.treaty.Contract;
import net.java.treaty.Disjunction;
import net.java.treaty.ExistsCondition;
import net.java.treaty.Negation;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.XDisjunction;

/**
 * Swing component to visualise contracts.
 * @author jens dietrich
 */
public class ContractView extends JPanel {
	public ContractView() {
		super();
		init();
	}

	public ContractView(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		init();
	}

	public ContractView(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		init();
	}

	public ContractView(LayoutManager layout) {
		super(layout);
		init();
	}

	private void init() {
		this.setLayout(new BorderLayout(0, 0));
	}

	private boolean mergeEqualNodes = false;
	private boolean removeBinConnectivesWithOneChild = true;

	public boolean isRemoveBinConnectivesWithOneChild() {
		return removeBinConnectivesWithOneChild;
	}

	public void setRemoveBinConnectivesWithOneChild(boolean removeBinConnectivesWithOneChild) {
		this.removeBinConnectivesWithOneChild = removeBinConnectivesWithOneChild;
		this.buildGraph();
		this.updateGraphView();
	}

	public boolean isMergeEqualNodes() {
		return mergeEqualNodes;
	}

	public void setMergeEqualNodes(boolean mergeEqualNodes) {
		this.mergeEqualNodes = mergeEqualNodes;
		this.buildGraph();
		this.updateGraphView();
	}

	enum EndNodeType {
		SUPPLIER, CONSUMER
	}

	class Node {
	}

	class EndNode extends Node {
		public EndNode(EndNodeType type, Connector connector) {
			super();
			this.type = type;
			this.connector = connector;
		}

		EndNodeType type = EndNodeType.SUPPLIER;
		Connector connector = null;

		public String toString() {
			return "[connector:" + type + "]";
		}
	}

	enum CompositionNodeType {
		AND, OR, XOR, NOT
	}

	class CompositionNode extends Node {
		public CompositionNode(CompositionNodeType type) {
			super();
			this.type = type;
		}

		CompositionNodeType type = CompositionNodeType.AND;

		public String toString() {
			return "[" + type + "]";
		}
		public boolean isBinary() {
			return type!=CompositionNodeType.NOT;
		}
	}
	class ConditionNode extends Node {}
	class RelationshipConditionNode extends ConditionNode {
		public RelationshipConditionNode(RelationshipCondition condition) {
			super();
			this.condition = condition;
		}
		RelationshipCondition condition = null;
		public String toString() {
			return "[" + condition.getRelationship() + "]";
		}
	}

	class ExistsConditionNode extends ConditionNode {
		public ExistsConditionNode(ExistsCondition condition) {
			super();
			this.condition = condition;
		}

		ExistsCondition condition = null;
		public String toString() {
			return "[must exist]";
		}
	}
	class PropertyConditionNode extends ConditionNode {
		public PropertyConditionNode(PropertyCondition condition) {
			super();
			this.condition = condition;
		}

		PropertyCondition condition = null;
		public String toString() {
			return "["+condition+"]";
		}
	}

	class Edge {
	}

	class ResourceNode extends Node {
		Resource resource = null;
		EndNodeType type = null;

		public ResourceNode(Resource resource, EndNodeType type) {
			super();
			this.resource = resource;
			this.type = type;
		}

		public ResourceNode(EndNodeType type) {
			super();
			this.type = type;
		}

		public String toString() {
			return resource == null ? "-" : "[resource:" + resource + "]";
		}

		public boolean isVirtual() {
			return resource == null;
		}
	}

	private Contract model = null;
	private DirectedGraph<Node, Edge> graph = null;
	private Collection conditionNodes = new LinkedHashSet(); // store separetly
																// to retain
																// order
	private EndNode consumerNode = null;
	private EndNode supplierNode = null;

	private boolean showConditionURINS = false;

	public int getLeftOffset() {
		return leftOffset;
	}

	public void setLeftOffset(int leftOffset) {
		this.leftOffset = leftOffset;
		this.updateGraphView();
	}

	public int getTopOffset() {
		return topOffset;
	}

	public void setTopOffset(int topOffset) {
		this.topOffset = topOffset;
		this.updateGraphView();
	}

	public int getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
		this.updateGraphView();
	}

	public int getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
		this.updateGraphView();
	}

	private int leftOffset = 50;
	private int topOffset = 50;
	private int columnWidth = 80;
	private int rowHeight = 60;

	// settings

	public Contract getModel() {
		return model;
	}

	public void setModel(Contract model) {
		this.model = model;
		buildGraph();
		updateGraphView();
	}

	private void updateGraphView() {

		class Updater extends SwingWorker<VisualizationViewer, Object> {
			@Override
			public VisualizationViewer doInBackground() {
				Layout<Node, Edge> layout = new ContractLayout(graph,
						ContractView.this.getSize());
				final VisualizationViewer<Node, Edge> vv = new VisualizationViewer<Node, Edge>(
						layout);

				configureRenderer(vv.getRenderContext());

				// configureRenderer(vv.getRenderContext(),instance);
				vv.getRenderer().getVertexLabelRenderer().setPosition(
						Position.CNTR);
				vv.setPreferredSize(ContractView.this.getSize()); // Sets the
																	// viewing
																	// area size
				vv.setBackground(Color.white);

				DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
				gm.setMode(edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode.PICKING);
				vv.setGraphMouse(gm);

				vv.setVertexToolTipTransformer(new Transformer<Node, String>() {
					@Override
					public String transform(Node v) {
						if (v instanceof ResourceNode) {
							ResourceNode rn = (ResourceNode) v;
							return getToolTip(rn.resource);
						}
						else if (v instanceof CompositionNode) {
							CompositionNode rn = (CompositionNode) v;
							return getToolTip(rn.type);
						} 
						else if (v instanceof EndNode) {
							EndNode rn = (EndNode) v;
							return getToolTip(rn.connector);
						} 
						else if (v instanceof RelationshipConditionNode) {
							RelationshipConditionNode rn = (RelationshipConditionNode) v;
							return getToolTip(rn.condition);
						} 
						else if (v instanceof ExistsConditionNode) {
							ExistsConditionNode rn = (ExistsConditionNode) v;
							return getToolTip(rn.condition);
						} 
						else if (v instanceof PropertyConditionNode) {
							PropertyConditionNode rn = (PropertyConditionNode) v;
							return getToolTip(rn.condition);
						} 
						
						else
							return null;
					}
				});
				return vv;
			}

			@Override
			protected void done() {
				try {
					VisualizationViewer vv = get();
					ContractView.this.removeAll();
					ContractView.this.add(vv, BorderLayout.CENTER);
					ContractView.this.revalidate();
					ContractView.this.repaint();
					ContractView.this.invalidate();
				} catch (Exception ignore) {
					ignore.printStackTrace();
				}
			}

		}
		;

		new Updater().execute();
		ContractView.this.revalidate();
		ContractView.this.repaint();
		ContractView.this.invalidate();

	}

	private void buildGraph() {

		System.out.println("building graph");

		consumerNode = null;
		supplierNode = null;
		conditionNodes = new LinkedHashSet();

		graph = new DirectedSparseMultigraph<Node, Edge>();

		if (model != null) {
			Connector consumer = model.getConsumer();
			Connector supplier = model.getSupplier();

			consumerNode = new EndNode(EndNodeType.CONSUMER, consumer);
			supplierNode = new EndNode(EndNodeType.SUPPLIER, supplier);

			graph.addVertex(consumerNode);
			graph.addVertex(supplierNode);

			buildGraph(consumerNode, supplierNode, model.getConstraints());
		}

	}

	private void buildGraph(EndNode consumerNode, EndNode supplierNode,
			List<AbstractCondition> constraints) {
		for (AbstractCondition constraint : constraints) {
			buildGraph(consumerNode, supplierNode, constraint);
		}
		// optional: identifier nodes representing same resource in the same
		// context
		// context: logical connective grouping conditions
		if (mergeEqualNodes) {
			compact(consumerNode);
			compact(supplierNode);
		}
		
		if (removeBinConnectivesWithOneChild) {
			while (removeBinConnectivesWithOneChild()) {}
		}
		
	}
	
	private boolean removeBinConnectivesWithOneChild() {
		for (Node n:graph.getVertices()) {
			if (n instanceof CompositionNode && ((CompositionNode)n).isBinary()) {
				CompositionNode c = (CompositionNode)n;
				Collection<Node> visibleSuccessors = new ArrayList<Node>();
				for (Node next:graph.getSuccessors(c)) {
					if (!isVirtualNode(next)) visibleSuccessors.add(next);
				}
				if (visibleSuccessors.size()==1) {
					Node child = visibleSuccessors.iterator().next();
					for (Node n3:graph.getPredecessors(n)) {
						graph.addEdge(new Edge(),n3,child); // rewire
						System.out.println("rewiring " + n3 + " -> " + child);
					}							
					
					System.out.println("removing " + n);
					graph.removeVertex(n);
					return true;
				}
			}
		}
		return false;
	}
	

	private void compact(Node parent) {
		Collection<Node> nodes1 = graph.getSuccessors(parent);
		Collection<Node> nodes2 = graph.getSuccessors(parent);

		for (Node node1 : nodes1) {
			for (Node node2 : nodes2) {
				if (node1 instanceof ResourceNode
						&& node2 instanceof ResourceNode) {
					ResourceNode res1 = (ResourceNode) node1;
					ResourceNode res2 = (ResourceNode) node2;
					if ((res1.isVirtual() && res2.isVirtual())
							|| (!res1.isVirtual() && !res2.isVirtual()
									&& res1 != res2 && res1.resource
									.equals(res2.resource))) {
						System.out
								.println("merging " + node1 + " and " + node2);
						// merge - take over old relationships
						if (graph.getSuccessors(node2) != null) {
							for (Node t : graph.getSuccessors(node2)) {
								graph.addEdge(new Edge(), node1, t);
							}
						}
						// merge - remove old graph
						graph.removeVertex(node2);
						graph.addEdge(new Edge(), parent, node1);

					}
				}
			}
		}

		for (Node node : graph.getSuccessors(parent)) {
			compact(node);
		}
	}

	private void buildGraph(Node consumerSideNode, Node supplierSideNode,
			AbstractCondition c) {
		if (c instanceof ComplexCondition) {
			ComplexCondition cplxCond = (ComplexCondition) c;
			CompositionNodeType type = null;
			if (cplxCond instanceof Conjunction)
				type = CompositionNodeType.AND;
			else if (cplxCond instanceof Disjunction)
				type = CompositionNodeType.OR;
			else if (cplxCond instanceof XDisjunction)
				type = CompositionNodeType.XOR;
			CompositionNode comp1 = new CompositionNode(type);
			graph.addVertex(comp1);
			graph.addEdge(new Edge(), consumerSideNode, comp1);
			CompositionNode comp2 = new CompositionNode(type);
			graph.addVertex(comp2);
			graph.addEdge(new Edge(), supplierSideNode, comp2);
			for (AbstractCondition part : cplxCond.getParts()) {
				buildGraph(comp1, comp2, part);
			}
		}

		else if (c instanceof Negation) {
			Negation neg = (Negation) c;
			CompositionNodeType type = CompositionNodeType.NOT;
			/*
			 * CompositionNode comp = new CompositionNode(type);
			 * graph.addVertex(comp); graph.addEdge(new Edge(),
			 * consumerSideNode, comp);
			 */

			CompositionNode comp1 = new CompositionNode(type);
			graph.addVertex(comp1);
			graph.addEdge(new Edge(), consumerSideNode, comp1);
			CompositionNode comp2 = new CompositionNode(type);
			graph.addVertex(comp2);
			graph.addEdge(new Edge(), supplierSideNode, comp2);
			buildGraph(comp1, comp2, neg.getNegatedCondition());
		}

		else if (c instanceof RelationshipCondition) {
			RelationshipCondition rel = (RelationshipCondition) c;
			Resource r1 = ((RelationshipCondition) c).getResource1();
			Resource r2 = ((RelationshipCondition) c).getResource2();
			ResourceNode n1 = new ResourceNode(r1, EndNodeType.SUPPLIER);
			ResourceNode n2 = new ResourceNode(r2, EndNodeType.CONSUMER);
			graph.addVertex(n1);
			graph.addVertex(n2);
			graph.addEdge(new Edge(), consumerSideNode, n2);
			graph.addEdge(new Edge(), supplierSideNode, n1);

			// glue together
			RelationshipConditionNode relN = new RelationshipConditionNode(rel);
			graph.addVertex(relN);
			graph.addEdge(new Edge(), n1, relN);
			graph.addEdge(new Edge(), n2, relN);
			conditionNodes.add(relN);
		}

		else if (c instanceof ExistsCondition) {
			ExistsCondition x = (ExistsCondition) c;
			Resource r = x.getResource();
			ResourceNode n1 = new ResourceNode(EndNodeType.CONSUMER);
			ResourceNode n2 = new ResourceNode(r, EndNodeType.SUPPLIER);
			graph.addVertex(n1);
			graph.addVertex(n2);
			graph.addEdge(new Edge(), consumerSideNode, n1);
			graph.addEdge(new Edge(), supplierSideNode, n2);

			// glue together
			ExistsConditionNode relN = new ExistsConditionNode(x);
			graph.addVertex(relN);
			graph.addEdge(new Edge(), n1, relN);
			graph.addEdge(new Edge(), n2, relN);

			conditionNodes.add(relN);
		}
		else if (c instanceof PropertyCondition) {
			PropertyCondition p = (PropertyCondition) c;
			Resource r = p.getResource();
			ResourceNode n1 = new ResourceNode(EndNodeType.CONSUMER);
			ResourceNode n2 = new ResourceNode(r, EndNodeType.SUPPLIER);
			graph.addVertex(n1);
			graph.addVertex(n2);
			graph.addEdge(new Edge(), consumerSideNode, n1);
			graph.addEdge(new Edge(), supplierSideNode, n2);

			// glue together
			PropertyConditionNode relN = new PropertyConditionNode(p);
			graph.addVertex(relN);
			graph.addEdge(new Edge(), n1, relN);
			graph.addEdge(new Edge(), n2, relN);

			conditionNodes.add(relN);
		}

	}

	private void configureRenderer(RenderContext context) {
		context.setVertexLabelTransformer(new Transformer<Object, String>() {
			@Override
			public String transform(Object v) {
				if (v instanceof CompositionNode) {
					CompositionNode c = (CompositionNode)v;
					return asHTMLLabel(c.type.toString(),3);
				} else if (v instanceof EndNode) {
					EndNode en = (EndNode) v;
					return asHTMLLabel(en.type.toString(),4);
				} else if (v instanceof ResourceNode) {
					ResourceNode en = (ResourceNode) v;
					if (en.isVirtual())
						return "<virtual node>";
					Resource r = en.resource;
					return asHTMLLabel(r.getId(), 2);
				} else if (v instanceof RelationshipConditionNode) {
					RelationshipConditionNode en = (RelationshipConditionNode) v;
					String uri = en.condition.getRelationship().toString();
					String l = null;
					if (showConditionURINS) {
						l = uri;
					} else {
						int pos = uri.indexOf("#");
						l = pos > -1 ? uri.substring(pos + 1) : uri;
					}
					return asHTMLLabel(l, 4);
				} 
				else if (v instanceof ExistsConditionNode) {
					return asHTMLLabel("must exist", 4);
				}
				else if (v instanceof PropertyConditionNode) {
					PropertyConditionNode p = (PropertyConditionNode)v;
					String uri = p.condition.getOperator().toString();
					String l = null;
					if (showConditionURINS) {
						l = uri;
					} else {
						int pos = uri.indexOf("#");
						l = pos > -1 ? uri.substring(pos + 1) : uri;
					}
					return asHTMLLabel(l+" "+ p.condition.getValue(), 4);
				}
				else
					return v.getClass().getName();

			}

			private String asHTMLLabel(String s, int l) {
				StringBuffer b = new StringBuffer();
				b.append("<html>");
				for (int i = 0; i < l; i++) {
					b.append("<br/>");
				}
				b.append(s);
				b.append("</html>");
				return b.toString();
			}

			private String asHTMLLabel(String s) {
				return asHTMLLabel(s, 1);
			}
		});
		context.setVertexIconTransformer(new Transformer<Object, Icon>() {

			@Override
			public Icon transform(Object v) {
				if (v instanceof CompositionNode) {
					CompositionNode n = (CompositionNode) v;
					return getIcon(n.type);
				} else if (v instanceof EndNode) {
					EndNode en = (EndNode) v;
					return getIcon(en.connector,en.type);
				} else if (v instanceof ResourceNode) {
					ResourceNode en = (ResourceNode) v;
					if (en.isVirtual()) return null;
					return getIcon(en.resource);
				} else if (v instanceof RelationshipConditionNode) {
					return getIcon(((RelationshipConditionNode)v).condition);
				} else if (v instanceof PropertyConditionNode) {
					return getIcon(((PropertyConditionNode)v).condition);
				} else if (v instanceof ExistsConditionNode) {
					return getIcon(((ExistsConditionNode)v).condition);
				}
				return null;
			}
		});

		context.setEdgeIncludePredicate(new Predicate<Context<Object, Edge>>() {
			@Override
			public boolean evaluate(Context<Object, Edge> edge) {
				return !isVirtualEdge(edge.element);
			}
		});
		context.setVertexIncludePredicate(new Predicate<Context>() {

			@Override
			public boolean evaluate(Context vertex) {
				Object element = vertex.element;
				if (element instanceof Node) {
					return !isVirtualNode((Node)element);
				}
				return true;
			}

		});
		
		context.setEdgeArrowPredicate(new Predicate() {

			@Override
			public boolean evaluate(Object o) {
				return false;
			}
			
			}
		);


		// context.setEdgeShapeTransformer(new EdgeShape.Wedge<Object,Edge>(1));
		context.setEdgeShapeTransformer(new EdgeShape.Line<Object, Edge>());
	}

	// collect resource nodes that can be reached from a node
	private Collection<ResourceNode> getAccessibleResources(Node node,
			boolean allowDuplicates) {
		Collection<ResourceNode> coll = allowDuplicates ? new ArrayList<ResourceNode>()
				: new HashSet<ResourceNode>();
		for (Node successor : graph.getSuccessors(node)) {
			if (successor instanceof ResourceNode) {
				coll.add((ResourceNode) successor);
			} else {
				coll.addAll(getAccessibleResources(successor, allowDuplicates));
			}
		}
		return coll;
	}

	// find the longest path to a resource
	private int getMaxPath2Resource(Node node) {
		if (node instanceof ResourceNode)
			return 0;
		int l = 0;
		for (Node successor : graph.getSuccessors(node)) {
			l = Math.max(l, getMaxPath2Resource(successor));
		}
		return l + 1;
	}

	class ContractLayout extends AbstractLayout<Node, Edge> {
		Map<Node, Point> coordinates = new HashMap<Node, Point>();

		protected ContractLayout(Graph<Node, Edge> graph, Dimension size) {
			super(graph, size);
			initialize();
		}

		@Override
		public void initialize() {
			if (consumerNode == null || supplierNode == null)
				return;

			Collection<ResourceNode> consumerRes = getAccessibleResources(consumerNode, true);
			Collection<ResourceNode> supplierRes = getAccessibleResources(supplierNode, true);

			// associate vertices with abstract positions in a grid
			// int W = getMaxPath2Resource(consumerNode) +
			// getMaxPath2Resource(supplierNode) + 2;
			// int H = Math.max(consumerRes.size(),supplierRes.size());

			addLayer(coordinates, conditionNodes,
					getMaxPath2Resource(consumerNode) + 1);
		}

		private void addLayer(Map<Node, Point> coordinates,Collection<Node> nodes, int col) {
			int i = -1;
			for (Node vertex : nodes) {
				i = i + 1;
				coordinates.put(vertex, new Point(leftOffset+(col*columnWidth),topOffset+(i*rowHeight)));
				//System.out.println("computing coordinates: "+vertex+" -> "+col+","+i);
			}
			addNextLayer(coordinates, nodes, col - 1, true);
			addNextLayer(coordinates, nodes, col + 1, false);
		}

		private void addNextLayer(Map<Node, Point> coordinates,Collection<Node> nodes, int col, boolean b) {
			Collection<Node> next = new LinkedHashSet<Node>();
			for (Node vertex : nodes) {
				next.addAll(getPredecessors(vertex, b));
			}
			if (next.size() > 0) {
				for (Node n : next) {
					// relevant predecessor nodes
					int predCount = 0;
					int predSum = 0;
					for (Node n2 : graph.getSuccessors(n)) {
						// if (nodes.contains(n2)) {
						Point p = coordinates.get(n2);
						if (p!=null && !isVirtualNode(n2)) {
							predSum = predSum + p.y;
							predCount = predCount + 1;
						}
					}
					int y = predCount == 0 ? rowHeight : (predSum / predCount);
					coordinates.put(n, new Point(leftOffset
							+ (col * columnWidth), y));
				}
				addNextLayer(coordinates, next, b ? col - 1 : col + 1, b);
			}

		}

		private Collection<Node> getPredecessors(Node vertex,
				boolean consumerSide) {
			Collection<Node> coll = new LinkedHashSet<Node>();
			if (vertex instanceof ConditionNode) {
				for (Node pre : graph.getPredecessors(vertex)) {
					if (pre instanceof ResourceNode) {
						if (consumerSide
								&& ((ResourceNode) pre).type == EndNodeType.CONSUMER) {
							coll.add(pre);
						} else if (!consumerSide
								&& ((ResourceNode) pre).type == EndNodeType.SUPPLIER) {
							coll.add(pre);
						}
					}
				}

			} else {
				coll.addAll(graph.getPredecessors(vertex));
			}
			return coll;
		}

		@Override
		public void reset() {

		}

		@Override
		public Point2D transform(Node v) {
			Point p = coordinates.get(v);
			// return p==null?new Point(75,75):new
			// Point(p.x*120,(p.y*80)+(p.x*15));
			return p == null ? new Point(75, 75) : p;
		}

	}

	protected String getToolTip(Resource r) {
		Map<String,Object> properties = new LinkedHashMap<String,Object>();
		properties.put("id",r.getId());
		properties.put("name",r.getName()==null?"-":r.getName());	
		properties.put("ref",r.getRef()==null?"-":r.getRef());
		properties.put("type",r.getType());
		return this.asHTMLTable(properties);
	}

	protected String getToolTip(CompositionNodeType r) {
		return r.name();
	}
	protected String getToolTip(Connector c) {
		return null;
	}
	protected String getToolTip(RelationshipCondition r) {
		Map<String,Object> properties = new LinkedHashMap<String,Object>();
		properties.put("id",r.getRelationship());
		return this.asHTMLTable(properties);
	}
	protected String getToolTip(ExistsCondition r) {
		Map<String,Object> properties = new LinkedHashMap<String,Object>();
		properties.put("resource",r.getResource().getId());
		return this.asHTMLTable(properties);
	}
	protected String getToolTip(PropertyCondition r) {
		Map<String,Object> properties = new LinkedHashMap<String,Object>();
		properties.put("operator",r.getOperator());
		properties.put("value",r.getValue());
		properties.put("value type",r.getValue()==null?"null":r.getValue().getClass());
		return this.asHTMLTable(properties);
	}
	
	protected Icon getIcon(Resource r) {
		return loadIcon(r.getName()==null?"constant.png":"variable.png");
	}

	protected Icon getIcon(CompositionNodeType r) {
		switch (r) {
			case AND:return loadIcon("and.png");
			case OR:return loadIcon("or.png");
			case XOR:return loadIcon("xor.png");
			case NOT:return loadIcon("not.png");
			default:return null;
		}
	}
	protected Icon getIcon(Connector c,EndNodeType t) {
		switch (t) {
			case CONSUMER:return loadIcon("consumer.png");
			case SUPPLIER:return loadIcon("provider.png");
			default:return null;
		}
	}
	protected Icon getIcon(RelationshipCondition r) {
		return loadIcon("relationship.png");
	}
	protected Icon getIcon(ExistsCondition r) {
		return loadIcon("exists.png");
	}
	protected Icon getIcon(PropertyCondition r) {
		return loadIcon("property.png");
	}
	
	
	private String asHTMLTable(Map<String,Object> values) {
		StringBuffer b = new StringBuffer();
		b.append("<html><table>\n");
		for (Entry<String,Object> e:values.entrySet()) {
			b.append("<tr><td align=\"right\"><i>");
			b.append(e.getKey());
			b.append(":</i></td><td>");
			b.append(e.getValue()==null?null:e.getValue());
			b.append("</td></tr>");
		}
		b.append("</table></html>");
		return b.toString();
	}

	private boolean isVirtualNode(Node vertex) {
		if (vertex==null) return true;
		if (vertex instanceof ConditionNode) return false;
		else if (vertex instanceof EndNode) return false;
		if (vertex instanceof ResourceNode) {
			return ((ResourceNode) vertex).isVirtual();
		}
		// recursive 
		Collection<Node> next = graph.getSuccessors(vertex);
		if (next==null) return false; // may return null!
		for (Node node:next) {
			if (node==vertex) throw new IllegalArgumentException("graph should not be circular");
			if (!isVirtualNode(node)) {
				return false;
			}
		}
		return true;
	}

	private boolean isVirtualEdge(Edge edge) {
		return isVirtualNode(graph.getSource(edge))	|| isVirtualNode(graph.getDest(edge));
	}
	
	private Icon loadIcon(String name) {
		return new ImageIcon("icons/"+ name);
	}
}
