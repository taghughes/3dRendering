package renderingtest;

import java.util.ArrayList;

public class Line {
    public Vertex v1;
    public Vertex v2;
    private double length;
    private static ArrayList<Line> lines = new ArrayList<>();

    public Line(Vertex vertex1, Vertex vertex2) {
        v1 = vertex1;
        v2 = vertex2;
        length = Math.sqrt((v1.z-v2.z)*(v1.z-v2.z)+
                          ((v1.x-v2.x)*(v1.x-v2.x)
                          +(v1.y-v2.y)*(v1.y-v2.y)));
        lines.add(this);
    }

    @Override 
    public String toString() {
        return v1+" -> "+v2+"; length: "+length;
    }

    public static ArrayList<Line> getLines() {
        return lines;
    }

    public static void main(String[] args) {
        Vertex v1 = new Vertex(0, 2, 3);
        Vertex v2 = new Vertex(12, 3.5, 3);

        Line line = new Line(v1,v2);
        System.out.println(line);
    }

}