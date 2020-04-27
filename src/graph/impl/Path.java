package graph.impl;

public class Path implements Comparable<Path>
{
    private String start;
    private String dst;
    private int cost;

    public Path(String dst, int cost){
        this.dst = dst;
        this.cost = cost;
    }
    public Path(String start, String dst, int cost){
        this.dst = dst;
        this.start = start;
        this.cost = cost;
    }

    public int compareTo(Path other){
        return this.cost - other.cost;
    }

    public String toString(){
        return this.dst + " with cost "+this.cost;
    }

    public String getStart() {
        return start;
    }

    public String getDst(){
        return dst;
    }
    public int getCost(){
        return cost;
    }
}