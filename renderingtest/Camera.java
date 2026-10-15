package renderingtest;

import java.util.ArrayList;

public class Camera {
    public Vertex cameraPosition;

    public double xrot;
    public double yrot;
    public double zrot;

    public double CAMERA_SPEED = 0.1;
    public double CAMERA_ROTATE_SPEED = 2;

    public int fov;


    public Camera() {
        cameraPosition = new Vertex();
        xrot = 0;
        yrot = 0;
        fov = 70;
    }

    public void rotateX(double xChange) {
        xrot += xChange;
        this.normalizeRotation();
    }

    public void rotateY(double yChange) {
        yrot += yChange;
        this.normalizeRotation();
    }

    public void normalizeRotation() {
        xrot = xrot % 360;
        yrot = yrot % 360;
        if (xrot < 0) {
            xrot += 360;
        }
        if (yrot < 0) {
            yrot += 360;
        }
    }

    public double getProjectedX(Vertex v) {
        return Math.toDegrees(Math.atan((v.x-cameraPosition.x)/(v.z-cameraPosition.z))) - xrot;
    }

    public double getProjectedY(Vertex v) {
        return Math.toDegrees(Math.atan((v.y-cameraPosition.y)/(v.z-cameraPosition.z))) - yrot;
    }

    public ArrayList<ProjectedLine> getProjectedLines(ArrayList<Line> lineList, int cameraSizeX, int cameraSizeY) {
        ArrayList<ProjectedLine> ret = new ArrayList<>();
        for (Line l : lineList) {
            ProjectedVertex v1 = new ProjectedVertex((int)(getProjectedX(l.v1)/fov/2*cameraSizeX), 
                                                     (int)(getProjectedY(l.v1)/fov/2*cameraSizeY));
            ProjectedVertex v2 = new ProjectedVertex((int)(getProjectedX(l.v2)/fov/2*cameraSizeX), 
                                                     (int)(getProjectedY(l.v2)/fov/2*cameraSizeY));
            ProjectedLine newL = new ProjectedLine(v1, v2);
            ret.add(newL);
        }
        return ret;
    }

    public static void main(String[] args) {
        Vertex testPos = new Vertex(-5, 17, 5);
        Camera camera = new Camera();

        System.out.println(camera.getProjectedX(testPos)+", "+camera.getProjectedY(testPos));

        Vertex v1 = new Vertex(-2, 0, 6);
        Vertex v2 = new Vertex(-2, 0, 8);
        Vertex v3 = new Vertex(2, 0, 8);
        Vertex v4 = new Vertex(2, 0, 6);
        Vertex v5 = new Vertex(0, 4, 5);

        ArrayList<Line> lines = new ArrayList<>();
        lines.add(new Line(v1,v2));
        lines.add(new Line(v2, v3));
        lines.add(new Line(v3,v4));
        lines.add(new Line(v4, v1));

        lines.add(new Line(v1, v5));
        lines.add(new Line(v2, v5));
        lines.add(new Line(v3, v5));
        lines.add(new Line(v4, v5));

        for (ProjectedLine l : camera.getProjectedLines(lines, 1000,1000)) {
            System.out.println(l);
        }
        
    }

}
