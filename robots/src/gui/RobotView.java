package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class RobotView extends JPanel {
    private final RobotModel model;
    private int targetPositionX = 150;
    private int targetPositionY = 100;

    public RobotView(RobotModel model) {
        this.model = model;
    }

    public void setTargetPosition(int x, int y) {
        targetPositionX = x;
        targetPositionY = y;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        drawRobot(g2d);
        drawTarget(g2d);
    }

    private void drawRobot(Graphics2D g) {
        int robotCenterX = (int)Math.round(model.getRobotPositionX());
        int robotCenterY = (int)Math.round(model.getRobotPositionY());

        AffineTransform t = AffineTransform.getRotateInstance(
                model.getRobotDirection(), robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, targetPositionX, targetPositionY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, targetPositionX, targetPositionY, 5, 5);
    }

    private static void fillOval(Graphics g, int x, int y, int width, int height) {
        g.fillOval(x - width/2, y - height/2, width, height);
    }

    private static void drawOval(Graphics g, int x, int y, int width, int height) {
        g.drawOval(x - width/2, y - height/2, width, height);
    }
}