package main.java.org.ce.ap.client.services;

public interface SceneHandler {
    public void changeScene(String fxml);

    public void changeScene(String fxml, Object data);

    public void newWindow(String fxml, String title);

    public void newWindow(String fxml, String title, Object data);

    public void toggleDarkTheme();

    public void toggleFullScreen();
}
