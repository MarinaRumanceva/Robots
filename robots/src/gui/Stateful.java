package gui;

import java.util.Map;

public interface Stateful {
    void saveState(Map<String, String> state);
    void restoreState(Map<String, String> state);
}
