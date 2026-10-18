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
    public int screenWidth;
    public int screenHeight;
    public double focalLength;
    public int screenCenterX;
    public int screenCenterY;


    public Camera() {
        cameraPosition = new Vertex();
        xrot = 0;
        yrot = 0;
        fov = 70;
        screenWidth = 1000;
        screenHeight = 1000;
        double fov2 = Math.toRadians(fov);
        focalLength = (screenHeight/2.0)/(Math.tan(fov2/2.0));
        screenCenterX = screenWidth/2;
        screenCenterY = screenHeight/2;
    }

    public Camera(int fieldOfView, int sw, int sh) {
        cameraPosition = new Vertex();
        xrot = 0;
        yrot = 0;
        fov = fieldOfView;
        screenWidth = sw;
        screenHeight = sh;
        double fov2 = Math.toRadians(fov);
        focalLength = (screenHeight/2.0)/(Math.tan(fov2/2.0));
        screenCenterX = screenWidth/2;
        screenCenterY = screenHeight/2;
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

    public Double getProjectedX(Vertex v) {
        Vertex v1 = getCameraSpace(v);
        //IF VERTEX IS BEHIND CAMERA RETURN NULL
        if (v1.z <= 0.01) {
            return null;
        }
        return screenCenterX + focalLength*(v1.x/v1.z);
    }

    public Double getProjectedY(Vertex v) {
        Vertex v1 = getCameraSpace(v);
        //IF VERTEX IS BEHIND CAMERA RETURN NULL
        if (v1.z <= 0.01) {
            return null;
        }
        return screenCenterY - focalLength*(v1.y/v1.z);
    }

    public Vertex getCameraSpace(Vertex v) {
        double yrot2 = Math.toRadians(yrot);
        double xrot2 = Math.toRadians(xrot);

        double dx = v.x - cameraPosition.x;
        double dy = v.y - cameraPosition.y;
        double dz = v.z - cameraPosition.z;

        double x1 = Math.cos(yrot2)*dx - Math.sin(yrot2)*dz;
        double y1 = dy;
        double z1 = Math.sin(yrot2)*dx + Math.cos(yrot2)*dz;

        double xc = x1;
        double yc = Math.cos(xrot2)*y1 + Math.sin(xrot2)*z1;
        double zc = -Math.sin(xrot2)*y1 + Math.cos(xrot2)*z1;

        return new Vertex(xc, yc, zc);
    }

    public ArrayList<ProjectedLine> getProjectedLines(ArrayList<Line> lineList, int cameraSizeX, int cameraSizeY) {
        ArrayList<ProjectedLine> ret = new ArrayList<>();
        for (Line l : lineList) {
            Double pv1x = getProjectedX(l.v1);
            Double pv1y = getProjectedY(l.v1);

            Double pv2x = getProjectedX(l.v2);
            Double pv2y = getProjectedY(l.v2);

            //ONLY DRAW LINE IF BOTH VERTICES ARE IN FRONT OF CAMERA
            if (pv1x != null && pv1y != null && pv2x != null && pv2y != null) {
                ProjectedVertex v1 = new ProjectedVertex(pv1x.intValue(), 
                                                         pv1y.intValue());
                ProjectedVertex v2 = new ProjectedVertex(pv2x.intValue(), 
                                                         pv2y.intValue());
                ProjectedLine newL = new ProjectedLine(v1, v2);
                ret.add(newL);
            }            
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
