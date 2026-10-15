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
            g.drawLine(l.projectedLine[0].x + PANEL_MID_X, -l.projectedLine[0].y + PANEL_MID_Y, l.projectedLine[1].x + PANEL_MID_X, -l.projectedLine[1].y + PANEL_MID_Y);
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
        if (keys[KeyEvent.VK_A]) cam.cameraPosition.x -= cam.CAMERA_SPEED;
        if (keys[KeyEvent.VK_D]) cam.cameraPosition.x += cam.CAMERA_SPEED;
        if (keys[KeyEvent.VK_W]) cam.cameraPosition.z += cam.CAMERA_SPEED;
        if (keys[KeyEvent.VK_S]) cam.cameraPosition.z -= cam.CAMERA_SPEED;
        if (keys[KeyEvent.VK_SPACE]) cam.cameraPosition.y += cam.CAMERA_SPEED;
        if (keys[KeyEvent.VK_SHIFT]) cam.cameraPosition.y -= cam.CAMERA_SPEED;

        if (keys[KeyEvent.VK_LEFT]) cam.xrot -= cam.CAMERA_ROTATE_SPEED;
        if (keys[KeyEvent.VK_RIGHT]) cam.xrot += cam.CAMERA_ROTATE_SPEED;
        if (keys[KeyEvent.VK_UP]) cam.yrot += cam.CAMERA_ROTATE_SPEED;
        if (keys[KeyEvent.VK_DOWN]) cam.yrot -= cam.CAMERA_ROTATE_SPEED;
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

