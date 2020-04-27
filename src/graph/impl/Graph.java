package graph.impl;

import java.util.*;

import graph.IGraph;
import graph.INode;
import graph.NodeVisitor;

/**
 * A basic representation of a graph that can perform BFS, DFS, Dijkstra,
 * and Prim-Jarnik's algorithm for a minimum spanning tree.
 * 
 * @author jspacco
 *
 */
public class Graph implements IGraph
{
    private Map<String, INode> nodes = new HashMap();

    public Graph() {
    }
    /**
     * Return the {@link Node} with the given name.
     * 
     * If no {@link Node} with the given name exists, create
     * a new node with the given name and return it. Subsequent
     * calls to this method with the same name should
     * then return the node just created.
     * 
     * @param name
     * @return
     */
    public INode getOrCreateNode(String name) {
        if (this.nodes.containsKey(name)) {
            return this.nodes.get(name);
        } else {
            INode n = new Node(name);
            this.nodes.put(name, n);
            return n;
        }
    }
    /**
     * Return true if the graph contains a node with the given name,
     * and false otherwise.
     * 
     * @param name
     * @return
     */
    public boolean containsNode(String name) {
        return this.nodes.containsKey(name);
    }

    /**
     * Return a collection of all of the nodes in the graph.
     * 
     * @return
     */
    public Collection<INode> getAllNodes() {
        return this.nodes.values();
    }
    
    /**
     * Perform a breadth-first search on the graph, starting at the node
     * with the given name. The visit method of the {@link NodeVisitor} should
     * be called on each node the first time we visit the node.
     * 
     * 
     * @param startNodeName
     * @param v
     */
    public void breadthFirstSearch(String startNodeName, NodeVisitor v)
    {
        // TODO: Implement this method
        Set<INode> visited= new HashSet<>();
        Queue<INode> tovisit = new LinkedList<>();
        tovisit.add(getOrCreateNode(startNodeName));
        while(!tovisit.isEmpty()){
            INode x = tovisit.remove();
            if(visited.contains(x)) continue;
            v.visit(x);
            visited.add(x);
            for(INode n : x.getNeighbors()){
                if(!visited.contains(n)){
                    tovisit.add(n);
                }
            }
        }

    }

    /**
     * Perform a depth-first search on the graph, starting at the node
     * with the given name. The visit method of the {@link NodeVisitor} should
     * be called on each node the first time we visit the node.
     * 
     * 
     * @param startNodeName
     * @param v
     */
    public void depthFirstSearch(String startNodeName, NodeVisitor v)
    {
        // TODO: implement this method
        Set<INode> visited= new HashSet<>();
        Stack<INode> tovisit = new Stack<>();
        tovisit.push(getOrCreateNode(startNodeName));
        while(!tovisit.isEmpty()){
            INode x = tovisit.pop();
            if(visited.contains(x)) continue;
            v.visit(x);
            visited.add(x);
            for(INode n : x.getNeighbors()){
                if(!visited.contains(n)){
                    tovisit.push(n);
                }
            }
        }
    }

    /**
     * Perform Dijkstra's algorithm for computing the cost of the shortest path
     * to every node in the graph starting at the node with the given name.
     * Return a mapping from every node in the graph to the total minimum cost of reaching
     * that node from the given start node.
     * 
     * <b>Hint:</b> Creating a helper class called Path, which stores a destination
     * (String) and a cost (Integer), and making it implement Comparable, can be
     * helpful. Well, either than or repeated linear scans.
     * 
     * @param startName
     * @return
     */
    public Map<INode,Integer> dijkstra(String startName) {
        // TODO: Implement this method
        Map<INode,Integer> result = new HashMap<>();
        PriorityQueue<Path> todo = new PriorityQueue<>();
        todo.add(new Path(startName,0));
        while(result.size() < this.getAllNodes().size()){
            Path nextpath = todo.poll();
            INode node = this.getOrCreateNode(nextpath.getDst());
            if (result.containsKey(node)) continue;
            int cost = nextpath.getCost();
            result.put(node,cost);
            for(INode n : node.getNeighbors()){
                todo.add(new Path(n.getName(),cost + node.getWeight(n)));
            }
        }
        return result;
    }
    
    /**
     * Perform Prim-Jarnik's algorithm to compute a Minimum Spanning Tree (MST).
     * 
     * The MST is itself a graph containing the same nodes and a subset of the edges 
     * from the original graph.
     * 
     * @return
     */
    public IGraph primJarnik() {
        //TODO Implement this method
        IGraph res = new Graph();
        Set<INode> visited = new HashSet<>();
        PriorityQueue<Path> todo = new PriorityQueue<>();
        INode curr = this.nodes.values().iterator().next();
        res.getOrCreateNode(curr.getName()); //curr belongs to this
        INode test = (INode) res.getAllNodes().toArray()[0];
        System.out.println(test.getName());

        for(INode n : curr.getNeighbors()){
            if(curr.hasEdge(n))
                todo.add(new Path(curr.getName(),n.getName(),curr.getWeight(n)));
        }
        visited.add(curr); //visited nodes
        while(visited.size() < this.getAllNodes().size()){
            Path nextpath = todo.poll(); //take out lowest cost path
            INode node = this.getOrCreateNode(nextpath.getDst()); //get destination
            INode start = this.getOrCreateNode(nextpath.getStart());

            if (visited.contains(node)) continue; //if nodes already visited, pass
            int cost = nextpath.getCost();
            visited.add(node);

            INode resNode = res.getOrCreateNode(node.getName()); //add node to des
            INode resStart = res.getOrCreateNode(start.getName()); //get the node that belongs to res
            resNode.addUndirectedEdgeToNode(resStart,cost); //add an edge

            curr = node;
            for(INode n : node.getNeighbors()){
                if(curr.hasEdge(n))
                    todo.add(new Path(curr.getName(),n.getName(),curr.getWeight(n)));
            }
        }
        return res;

    }
}