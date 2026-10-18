package renderingtest;
public class Vertex {
    public double x;
    public double y;
    public double z;

    public Vertex() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vertex(double x_position, double y_position, double z_position) {
        x = x_position;
        y = y_position;
        z = z_position;
    }

    public void setPos(double x_position, double y_position, double z_position) {
        x = x_position;
        y = y_position;
        z = z_position;
    }

    public void changePos(double x_change, double y_change, double z_change) {
        x += x_change;
        y += y_change;
        z += z_change;
    }

    public Vertex add(Vertex v1) {
        x += v1.x;
        y += v1.y;
        z += v1.z;
        return this;
    }

    public Vertex multiply(double factor) {
        x *= factor;
        y *= factor;
        z *= factor;
        return this;
    }

    @Override 
    public String toString() {
        return "("+x+", "+y+", "+z+")";
    }

    public static void main(String[] args) {
        Vertex v1 = new Vertex(1.245,2.356,1224);
        Vertex v2 = new Vertex();
        Vertex v3 = new Vertex(1,5,3);
        Vertex[] arr = {v1, v2, v3};
        System.out.println(v1);
        for (Vertex v : arr) {
            System.out.println(v);
        }
    }
}