package gui;

import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.util.Map;

public class GameWindow extends JInternalFrame implements Stateful {
    private final RobotView view;

    public GameWindow(RobotModel model) {
        super("Игровое поле", true, true, true, true);
        this.view = new RobotView(model);
        new RobotController(model, view);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(view, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void saveState(Map<String, String> state) {
        PrefixedStateMap gameState = new PrefixedStateMap(state, "game.");
        gameState.put("x", Integer.toString(getX()));
        gameState.put("y", Integer.toString(getY()));
        gameState.put("width", Integer.toString(getWidth()));
        gameState.put("height", Integer.toString(getHeight()));
        gameState.put("isMaximized", Boolean.toString(isMaximum()));
    }

    @Override
    public void restoreState(Map<String, String> state) {
        PrefixedStateMap gameState = new PrefixedStateMap(state, "game.");
        if (!gameState.isEmpty()) {
            setBounds(
                    Integer.parseInt(gameState.getOrDefault("x", "100")),
                    Integer.parseInt(gameState.getOrDefault("y", "100")),
                    Integer.parseInt(gameState.getOrDefault("width", "400")),
                    Integer.parseInt(gameState.getOrDefault("height", "400"))
            );
            try {
                setMaximum(Boolean.parseBoolean(gameState.get("isMaximized")));
            } catch (Exception ignored) {}
        }
    }
}