package gui;

import java.util.Observable;

public class RobotModel extends Observable {
    private volatile double robotPositionX = 100;
    private volatile double robotPositionY = 100;
    private volatile double robotDirection = 0;

    public double getRobotPositionX() {
        return robotPositionX;
    }

    public double getRobotPositionY() {
        return robotPositionY;
    }

    public double getRobotDirection() {
        return robotDirection;
    }

    public void updatePosition(double x, double y, double direction) {
        this.robotPositionX = x;
        this.robotPositionY = y;
        this.robotDirection = direction;
        setChanged();
        notifyObservers();
    }
}
