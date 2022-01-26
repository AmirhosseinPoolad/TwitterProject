package main.java.org.ce.ap.client.services;

public interface SceneHandler {
    /**
     * changes scene to fxml file
     *
     * @param fxml address of fxml file
     */
    public void changeScene(String fxml);

    /**
     * changes scene to fxml file and passes the data to it.
     *
     * @param fxml address of fxml file
     * @param data data to be passed
     */
    public void changeScene(String fxml, Object data);

    /**
     * opens new window with fxml file and title
     *
     * @param fxml  address of fxml file
     * @param title title of new window
     */
    public void newWindow(String fxml, String title);

    /**
     * opens new window with fxml file and title and passes data to it
     *
     * @param fxml  address of fxml file
     * @param title title of new window
     * @param data  data to be passed
     */
    public void newWindow(String fxml, String title, Object data);

    /**
     * toggles dark theme
     */
    public void toggleDarkTheme();

    /**
     * toggles fullscreen
     */
    public void toggleFullScreen();
}
