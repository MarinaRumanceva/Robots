package gui;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ApplicationState {
    private static final String CONFIG_FILE = System.getProperty("user.home") + "/robots_config.properties";

    public static void saveState(Map<String, String> state) {
        Properties props = new Properties();
        props.putAll(state);

        try (OutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Robots Application Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> loadState() {
        Properties props = new Properties();
        Path configPath = Paths.get(CONFIG_FILE);

        if (Files.exists(configPath)) {
            try (InputStream in = new FileInputStream(CONFIG_FILE)) {
                props.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Map<String, String> state = new HashMap<>();
        for (String key : props.stringPropertyNames()) {
            state.put(key, props.getProperty(key));
        }
        return state;
    }
}
