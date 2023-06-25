import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;
//import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import java.awt.Point;

public class DrawingProgram extends JFrame implements MouseMotionListener, MouseListener, ChangeListener {
    private Point mousePnt = new Point();
    public static Color penColor = new Color(0, 0, 0);
    private JSlider penSize = new JSlider(JSlider.HORIZONTAL, 1, 30, 4);
    public static int pen = 4;
    int currentAction = 1;

    JButton buttonLine, buttonRect, buttonCircle, buttonTri;

    public DrawingProgram() {
        super("Painter");
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel jp = new JPanel();
        Box box = Box.createHorizontalBox();

        buttonLine = makeButtons("pencil.png", 1);
        buttonRect = makeButtons("rectangle.png", 2);
        buttonCircle = makeButtons("circle.png", 3);
        buttonTri = makeButtons("triangle.png", 4);

        box.add(buttonLine);
        box.add(buttonRect);
        box.add(buttonCircle);
        box.add(buttonTri);

        toolbar.add(new Label("Drag mouse to draw"));
        toolbar.add(penSize);

        this.add(toolbar, BorderLayout.SOUTH);
        this.add(jp, BorderLayout.CENTER);
        jp.add(box);
        jp.addMouseMotionListener(this);
        jp.addMouseListener(this);
        penSize.addChangeListener(this);
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JButton makeButtons(String iconFile, final int actionNum) {

        JButton button = new JButton();
        Icon icon = new ImageIcon(iconFile);
        button.setIcon(icon);

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                currentAction = actionNum;
                if (e.getSource() == buttonRect) {

                    JFrame f = new JFrame("Draw Rectangle");
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.add(new DrawRectangle());
                    f.pack();
                    f.setVisible(true);
                }

                else if (e.getSource() == buttonCircle) {

                    JFrame c = new JFrame("Draw Circle");
                    c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    c.add(new DrawCircle());
                    c.pack();
                    c.setVisible(true);
                }

                else if (e.getSource() == buttonTri) {

                    JFrame c = new JFrame("Draw Triangle");
                    c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    c.add(new DrawTriangle());
                    c.pack();
                    c.setVisible(true);
                }
            }
        });
        return button;
    }

    public void mouseMoved(MouseEvent me) {

    }

    public void mouseDragged(MouseEvent me) {
        mousePnt = me.getPoint();
        repaint();
    }

    public void mouseClicked(MouseEvent me) {
        if (me.getModifiers() == MouseEvent.BUTTON3_MASK) {
            penColor = JColorChooser.showDialog(null, "Change pen color", penColor);
        }
    }

    public void mouseEntered(MouseEvent me) {

    }

    public void mouseExited(MouseEvent me) {

    }

    public void mousePressed(MouseEvent me) {

    }

    public void mouseReleased(MouseEvent me) {

    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            pen = (int) source.getValue();
        }
    }

    public void paint(Graphics g) {
        g.setColor(penColor);
        g.fillOval(mousePnt.x, mousePnt.y, pen, pen);
    }

    public static void main(String[] a) {
        new DrawingProgram();
    }
}

class DrawRectangle extends JPanel {

    MouseHandler mouseHandler = new MouseHandler();
    Point p1 = new Point(0, 0);
    Point p2 = new Point(0, 0);
    boolean drawing;


    public DrawRectangle() {

        this.addMouseListener(mouseHandler);
    }

    public Dimension getPreferredSize() {
        return new Dimension(500, 300);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(p1.x, p1.y, p2.x, p2.y);
        // g.drawOval(p1.x, p1.y, p2.x, p2.y);
        // g.drawRoundRect(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
    }

    private class MouseHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            drawing = true;
            p1 = e.getPoint();
            p2 = e.getPoint();
            // p3 = e.getPoint();
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
        drawing = false;
        p2 = e.getLocationOnScreen();
        // p3=e.getLocationOnScreen();
        repaint();
    }
}

class DrawTriangle extends JPanel {

    MyMouseListener ml = new MyMouseListener();
    Point p1 = new Point(0, 0);
    Point p2 = new Point(0, 0);
    Point p3 = new Point(0, 0);

    boolean drawing = false;
    boolean extra = false;
    
    public DrawTriangle() {
        
        this.addMouseListener(ml);
        this.addMouseMotionListener(ml);
    }

    protected void paintComponent(Graphics g) {
        
        if ((p3.x != 0) && (p3.y != 0))
        {
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
            g.drawLine(p2.x, p2.y, p3.x, p3.y);
            g.drawLine(p1.x, p1.y, p3.x, p3.y);
            
            p1.x = 0;
            p1.y = 0;
            p2.x = 0;
            p2.y = 0;
            p3.x = 0;
            p3.y = 0;
        }
    }


    public Dimension getPreferredSize() {
        return new Dimension(500, 300);
    }


        class MyMouseListener extends MouseInputAdapter{
            Point point1;
            Point point2;
            Point point3;

			public void mousePressed(MouseEvent e){
                if(drawing==false){
                    p1 = e.getPoint();
                }else{
                    p3 = e.getPoint();
                    repaint();
                    drawing = false;
                    extra = true;
                }
            }
			// public void mouseDragged(MouseEvent e){
            //     point2 = e.getPoint();
            //     p2=point2;
            //     repaint();
			// }

			public void mouseReleased(MouseEvent e){
                if((drawing == false) && (extra == false)){
                    p2 = e.getPoint();
                    repaint();
                    drawing = true;
                }else{
                    extra = false;
                }
			}
		}


    /* private class MouseHandler extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            drawing = true;
            p1 = e.getPoint();
            p2 = e.getPoint();
            p3 = e.getPoint();
            repaint();
        }
    }

    public void mouseDragged(MouseEvent e) {
        drawing = false;
        p1 = e.getLocationOnScreen();
        p2 = e.getLocationOnScreen();
        p3 = e.getLocationOnScreen();

        repaint();
    } */

}

class DrawCircle extends JPanel {

    MouseHandler mouseHandler = new MouseHandler();
    Point p1 = new Point(0, 0);
    Point p2 = new Point(0, 0);
    boolean drawing;

    public Dimension getPreferredSize() {
        return new Dimension(500, 300);
    }

    public DrawCircle() {

        this.addMouseListener(mouseHandler);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(p1.x, p1.y, p2.x, p2.y);
        // g.drawRoundRect(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
    }

    private class MouseHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            drawing = true;
            p1 = e.getPoint();
            p2 = e.getPoint();
            // p3 = e.getPoint();
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
        drawing = false;
        p2 = e.getLocationOnScreen();
        // p3=e.getLocationOnScreen();
        repaint();
    }
}
