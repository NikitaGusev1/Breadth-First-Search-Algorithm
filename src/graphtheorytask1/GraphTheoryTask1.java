/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtheorytask1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Nikita
 */
public class GraphTheoryTask1 {
    static class Graph
    {
        int V; //number of vertices
        int r; //randomized number of neighbours
        ArrayList<Integer> vertices[];
        Graph(int V) //constructor for manual graph
        {
            this.V = V;
            vertices = new ArrayList[V];
            for(int i = 0; i < V; ++i)
            {
                vertices[i] = new ArrayList<>(); //initialized vertices as lists
            }
        }
//        Graph(int V, int r) //for random
//        {
//            this.V = V;
//            this.r = r;
//            vertices = new ArrayList[V];
//            for(int i = 0; i < V; ++i)
//            {
//                vertices[i] = new ArrayList<>(r);
//            }
//        }
    }
    class Edge implements Comparable<Edge> 
    { 
        int src, dest, weight; 
  
        // Comparator function used for sorting edges  
        // based on their weight 
        public int compareTo(Edge compareEdge) 
        { 
            return this.weight-compareEdge.weight; 
        } 
    };
    class subset 
    { 
        int parent, rank; 
    }; 
    static void addEdge(Graph graph, Edge edge)
    {
        graph.vertices[edge.src].add(edge.dest);
        graph.vertices[edge.dest].add(edge.src);
        
    }
    static void printGraph(Graph graph) 
    {        
        for(int v = 0; v < graph.V; v++) 
        { 
            System.out.println("Adjacency list of vertex "+ v); 
            System.out.print("head"); 
            for(Integer u: graph.vertices[v]){ 
                System.out.print(" -> "+u); 
            } 
            System.out.println("\n"); 
        } 
    }
    static boolean IsCyclic(Graph graph,int source,boolean visited[])
    {
        int parent[] = new int [graph.V];
        Arrays.fill(parent, -1); //set parent vertex for every vertex
        Queue<Integer> queue = new LinkedList<>(); // BFS starts
        visited[source] = true;
        queue.add(source);
        while(!queue.isEmpty())
        {
            int deq = queue.poll();
            for(int i=0; i<graph.vertices[deq].size();++i)
            {
                int v =graph.vertices[deq].get(i); //get every adjacent vertex and mark it as visited
                if(!visited[v])
                {
                    visited[v]= true;
                    queue.add(v);
                    parent[v] = deq;
                }
                else if(parent[deq] != v)
                    return true;
            }
        }
        return false;
    }
    static boolean NonCyclic(Graph graph)
    {
        boolean visited[] = new boolean[graph.V];
        Arrays.fill(visited,false);
        for(int i=0;i<graph.V;++i)
        {
            if(!visited[i] && IsCyclic(graph,i,visited))
            {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("To choose random graph generation enter r, for manual enter m");
        switch (sc.nextLine()) {
            case "m":
                {
                    System.out.println("Enter a number of vertices");
                    int V = sc.nextInt();
                    System.out.println("Enter a number of edges");
                    int E = sc.nextInt();
                    int count =0;
                    Graph graph = new Graph(V);
                    System.out.println("Enter edges");
                    while(count<E)
                    {
                        addEdge(graph,sc.nextInt(),sc.nextInt());
                        count = count+1;
                    }           
                    printGraph(graph);
                    //System.out.println("Next is cycle detection");
                    if(NonCyclic(graph))
                        System.out.println("Graph has cycles");
                    else System.out.println("Graph is a tree");
                    break;
                }
            case "r":
                {
                    System.out.println("Enter a number of vertices");
                    int V = sc.nextInt();
                    System.out.println("Enter a number of edges");
                    int E = sc.nextInt();
                    //int count =0;
                    System.out.println("Enter min number of neighbours");
                    int Kmin = sc.nextInt();
                    System.out.println("Enter max number of neighbours");
                    int Kmax = sc.nextInt();
                    //int r = ThreadLocalRandom.current().nextInt(Kmin,Kmax+1);
                    Graph randomGraph = new Graph(V);
                    for(int i=0; i < E; ++i)
                    {
                        for(int j=0; j<randomGraph.V; ++j)
                        {
                        if(randomGraph.vertices[j].size()==Kmin)
                        {
                            break;
                        }
                        int src = ThreadLocalRandom.current().nextInt(0,randomGraph.vertices.length);
                        int dest = ThreadLocalRandom.current().nextInt(0,randomGraph.vertices.length);
                        addEdge(randomGraph,src,dest);
                        //count=count+1;
                    }
                    }
                    printGraph(randomGraph);
                    if(NonCyclic(randomGraph))
                        System.out.println("Graph has cycles");
                    else System.out.println("Graph is a tree");
                    break;
                }
                
            default:
                System.out.println("Something is wrong, try again");
                break;
        }
    
}
}
