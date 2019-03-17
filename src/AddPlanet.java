import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddPlanet {
    int WINDOW_HEIGHT = 300;
    int WINDOW_WIDTH = 300;

    private int defaultRadius = 20000;
    private int defaultDist = 700;
    private int defaultImage = 0;
    private int defaultPeriod = 365;
    private int defaultTilt = 0;
    private int defaultRotation = 1;

    public Stage createAddPlanetMenu(){
        VBox box = new VBox();
        Planet planet = new Planet("NewPlanet", defaultRadius, defaultImage,
                defaultDist, defaultPeriod, defaultTilt, defaultRotation);


        Scene scene = new Scene(box, WINDOW_WIDTH, WINDOW_HEIGHT);
        Stage menu = new Stage();
        menu.setScene(scene);
        return menu;
    }
}
