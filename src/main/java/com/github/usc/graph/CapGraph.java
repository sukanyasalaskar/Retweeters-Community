/**
 * 
 */
package com.github.usc.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

import com.github.usc.util.GraphLoader;

/**
 * @author Sukanya Salaskar.
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {

	private HashMap<Integer, HashSet<Integer>> nodes = new HashMap<>(); 
	
	public CapGraph() {
		nodes = new HashMap<>();
	}
	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		nodes.put(num, new HashSet<Integer>());
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		HashSet<Integer> edges = nodes.get(from);
		edges.add(to);
		nodes.put(from, edges);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		Graph g = new CapGraph();
		//System.out.println(center+" "+nodes.size());
		if (!nodes.containsKey(center))	return g;
		
		g.addVertex(center);
		HashSet<Integer> set = nodes.get(center);
		
		for (Integer i : set) {
			g.addVertex(i);
			g.addEdge(center, i);
		}
		
		for (Integer i : set) {
			HashSet<Integer> subSet = nodes.get(i);
			for (Integer n : subSet) {
				//System.out.println(n+" "+i);
				if (set.contains(n))	g.addEdge(i, n);
			}
		}
		
		return g;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		
		List<Graph> retList = new ArrayList<>();
		Stack<Integer> v = new Stack<>();
		
		for (int key : nodes.keySet())	v.push(key);
		Stack<Integer> finished = dfs(nodes, v);
		Graph transpose = getTranspose(nodes);
		List<List<Integer>> graphList = dfsTranspose(transpose.exportGraph(), finished);
		
		for (List<Integer> lst : graphList) {
			Graph g = buildGraph(nodes, lst);
			retList.add(g);
		}
		return retList;
	}
	
	private Graph buildGraph(HashMap<Integer, HashSet<Integer>> n, List<Integer> lst) {
		Graph g = new CapGraph();
		
		for (int i : lst) {
			g.addVertex(i);
		}
		
		for (int key : n.keySet()) {
			if(lst.contains(key)) {
				for (int i : n.get(key)) {
					if (lst.contains(i))	g.addEdge(key, i);
				}
			}
		}
		
		return g;
	}
	
	private List<List<Integer>> dfsTranspose(HashMap<Integer, HashSet<Integer>> n, Stack<Integer> v) {
		Stack<Integer> finished = new Stack<>();
		HashSet<Integer> visited = new HashSet<>();
		List<List<Integer>> retList = new ArrayList<>();
		
		while (!v.isEmpty()) {
			int v1 = v.pop();
			List<Integer> lst = new ArrayList<>();
			if (!visited.contains(v1)) {
				dfsTransposeVisit(n, v1, visited, finished, lst);
				retList.add(lst);
			}
		}
		
		return retList;
	}
	
	private void dfsTransposeVisit(HashMap<Integer, HashSet<Integer>> n, int v, 
			HashSet<Integer> visited, Stack<Integer> finished, List<Integer> lst) {
		visited.add(v);
		lst.add(v);
		for (int neighbor : n.get(v)) {
			if (!visited.contains(neighbor))
				dfsTransposeVisit(n, neighbor, visited, finished, lst);
		}
		finished.add(v);
	}
	
	private Stack<Integer> dfs(HashMap<Integer, HashSet<Integer>> n, Stack<Integer> v) {
		Stack<Integer> finished = new Stack<>();
		HashSet<Integer> visited = new HashSet<>();
		//System.out.println(v.size()+" "+visited.size());
		
		while (!v.isEmpty()) {
			int v1 = v.pop();
			if (!visited.contains(v1))	dfsVisit(n, v1, visited, finished);
		}
		
		return finished;
	}
	
	private void dfsVisit(HashMap<Integer, HashSet<Integer>> n, int v, 
			HashSet<Integer> visited, Stack<Integer> finished) {
		visited.add(v);
		//System.out.println("v "+v+" size "+n.get(v).size());
		for (int neighbor : n.get(v)) {
			//System.out.println(v+" "+neighbor);
			if (!visited.contains(neighbor))
				dfsVisit(n, neighbor, visited, finished);
		}
		finished.add(v);
	}
	
	private Graph getTranspose(HashMap<Integer, HashSet<Integer>> n) {
		Graph newGraph = new CapGraph();
		for (int key : n.keySet()) {
			newGraph.addVertex(key);
		}
		for (int to : n.keySet()) {
			for (int from : n.get(to))	newGraph.addEdge(from, to);
		}
		return newGraph;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		return nodes;
	}
	
	public static void main(String[] args) {
		Graph thegraph = new CapGraph();
		GraphLoader.loadGraph(thegraph, "data/scc/test_2.txt");
		
		List<Graph> lst = thegraph.getSCCs();
		System.out.println(lst.size());
		for (Graph g : lst) {
			HashMap<Integer, HashSet<Integer>> getGraph = g.exportGraph();
			for (int i : getGraph.keySet()) {
				HashSet<Integer> set = getGraph.get(i);
				System.out.print(i+" -> ");
				for (int id : set) {
					System.out.print(id+", ");
				}
				System.out.println();
			}
			System.out.println();
		}
		/*Graph subGraph = thegraph.getEgonet(23);
		HashMap<Integer, HashSet<Integer>> getGraph = subGraph.exportGraph();
		for (int i : getGraph.keySet()) {
			HashSet<Integer> set = getGraph.get(i);
			System.out.print(i+" -> ");
			for (int id : set) {
				System.out.print(id+", ");
			}
			System.out.println();
		}*/
	}

}
