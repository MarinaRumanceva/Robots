package gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import java.util.Observable;
import java.util.Observer;
import java.util.Map;

public class RobotCoordinatesWindow extends JInternalFrame implements Observer, Stateful {
    private final JLabel coordinatesLabel;
    private final RobotModel model;

    public RobotCoordinatesWindow(RobotModel model) {
        super("Координаты робота", true, true, true, true);
        this.model = model;
        this.model.addObserver(this);

        coordinatesLabel = new JLabel("X: 0, Y: 0, Direction: 0");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(coordinatesLabel, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

        updateCoordinates();
    }

    private void updateCoordinates() {
        coordinatesLabel.setText(String.format("X: %.2f, Y: %.2f, Direction: %.2f",
                model.getRobotPositionX(),
                model.getRobotPositionY(),
                model.getRobotDirection()));
    }

    @Override
    public void update(Observable o, Object arg) {
        updateCoordinates();
    }

    @Override
    public void saveState(Map<String, String> state) {
        PrefixedStateMap coordState = new PrefixedStateMap(state, "coord.");
        coordState.put("x", Integer.toString(getX()));
        coordState.put("y", Integer.toString(getY()));
        coordState.put("width", Integer.toString(getWidth()));
        coordState.put("height", Integer.toString(getHeight()));
        coordState.put("isMaximized", Boolean.toString(isMaximum()));
    }

    @Override
    public void restoreState(Map<String, String> state) {
        PrefixedStateMap coordState = new PrefixedStateMap(state, "coord.");
        if (!coordState.isEmpty()) {
            setBounds(
                    Integer.parseInt(coordState.getOrDefault("x", "700")),
                    Integer.parseInt(coordState.getOrDefault("y", "10")),
                    Integer.parseInt(coordState.getOrDefault("width", "200")),
                    Integer.parseInt(coordState.getOrDefault("height", "100"))
            );
            try {
                setMaximum(Boolean.parseBoolean(coordState.get("isMaximized")));
            } catch (Exception ignored) {}
        }
    }
}
