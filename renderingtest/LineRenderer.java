package renderingtest;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LineRenderer extends JPanel {
    private final int PANEL_WIDTH = 1000;
    private final int PANEL_HEIGHT = 1000;
    private final int PANEL_MID_X = PANEL_WIDTH/2;
    private final int PANEL_MID_Y = PANEL_HEIGHT/2;
    private ArrayList<Line> lines = Line.getLines();
    private Camera cam = new Camera();

    private final boolean[] keys = new boolean[256];
    private final int DELAY = 16;
    private Timer engineTimer;


    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        ArrayList<ProjectedLine> projectedLines = cam.getProjectedLines(lines, PANEL_WIDTH, PANEL_HEIGHT);
        for (ProjectedLine l : projectedLines) {
            g.drawLine(l.projectedLine[0].x, l.projectedLine[0].y, l.projectedLine[1].x, l.projectedLine[1].y);
        }
        //g.drawLine(0,0,getWidth(),getHeight());
    }

    public LineRenderer() {
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode < keys.length)
                    keys[keyCode] = true;
            }
            @Override 
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode < keys.length)
                    keys[keyCode] = false;
            }
        });
        
        engineTimer = new Timer(DELAY, e -> {
            stateChange();
            repaint();
        });
        engineTimer.start();
    }

    private void stateChange() {

        /* CAMERA CONTROLS:

        ** MOVEMENT: **
        W: MOVE DIRECTLY FORWARD (ROTATION DEPENDENT)
        S: MOVE DIRECTLY BACKWARD (ROTATION DEPENDENT)
        A: MOVE DIRECTLY LEFT (ROTATION DEPENDENT)
        D: MOVE DIRECTLY RIGHT (ROTATION DEPENDENT)

        SPACE: MOVE DIRECTLY UP (ROTATION INDEPENDENT)
        SHIFT: MOVE DIRECTLY DOWN (ROTATION INDEPENDENT)
        

        ** ROTATION: **

        ARROW KEYS: 
        LEFT/RIGHT: YAW
        UP/DOWN: PITCH

        (I DIDN'T IMPLEMENT ROLL BECAUSE IT IS NON-TYPICAL IN FIRST-PERSON CONTROLS)

        */

        double yaw = Math.toRadians(cam.yrot);
        double pitch = Math.toRadians(cam.xrot);

        Vertex changeV = new Vertex(
            Math.sin(yaw) * Math.cos(pitch),
            -Math.sin(pitch),
            Math.cos(yaw) * Math.cos(pitch)
        );
        changeV.multiply(cam.CAMERA_SPEED);

        if (keys[KeyEvent.VK_W]) {
            cam.cameraPosition.add(changeV);
        }
        if (keys[KeyEvent.VK_S]) {
            cam.cameraPosition.add(changeV.multiply(-1));
        }

        if (keys[KeyEvent.VK_D]) {
            cam.cameraPosition.x += Math.cos(yaw) * cam.CAMERA_SPEED;
            cam.cameraPosition.z += -Math.sin(yaw) * cam.CAMERA_SPEED;
        }
        if (keys[KeyEvent.VK_A]) {
            cam.cameraPosition.x -= Math.cos(yaw) * cam.CAMERA_SPEED;
            cam.cameraPosition.z -= -Math.sin(yaw) * cam.CAMERA_SPEED;
        }


        if (keys[KeyEvent.VK_SPACE]) cam.cameraPosition.y += cam.CAMERA_SPEED;
        if (keys[KeyEvent.VK_SHIFT]) cam.cameraPosition.y -= cam.CAMERA_SPEED;

        if (keys[KeyEvent.VK_UP]) cam.xrot -= cam.CAMERA_ROTATE_SPEED;
        if (keys[KeyEvent.VK_DOWN]) cam.xrot += cam.CAMERA_ROTATE_SPEED;
        if (keys[KeyEvent.VK_RIGHT]) cam.yrot += cam.CAMERA_ROTATE_SPEED;
        if (keys[KeyEvent.VK_LEFT]) cam.yrot -= cam.CAMERA_ROTATE_SPEED;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("line renderer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 1000);

            Vertex v1 = new Vertex(-2, 0, 2);
            Vertex v2 = new Vertex(2, 0, 2);
            Vertex v3 = new Vertex(0, 2, 2);
            Vertex v4 = new Vertex(-2, 0, 8);
            Vertex v5 = new Vertex(2, 0, 8);
            Vertex v6 = new Vertex(0, 2, 8);

            ArrayList<Line> lines = new ArrayList<>();
            lines.add(new Line(v1,v2));
            lines.add(new Line(v2,v3));
            lines.add(new Line(v3,v1));
            lines.add(new Line(v1,v4));
            lines.add(new Line(v2,v5));
            lines.add(new Line(v3,v6));
            lines.add(new Line(v4,v5));
            lines.add(new Line(v5,v6));
            lines.add(new Line(v6,v4));

            
            // 2. Instantiate and add your custom drawing panel to the JFrame
            LineRenderer drawingPanel = new LineRenderer();
            frame.add(drawingPanel);
            frame.setVisible(true);
        });
    }
}

