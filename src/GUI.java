/* Name: Shirley Zhang
 * Course code: ICS3U
 * Date: Jan 22, 2019
 * Instructor: Jeff Radulovic
 * Assignment name: Culminating activity
 * Description: This is the main class of my simulation program
 */

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;

public class GUI extends Application {

    //window dimensions
    private static int WINDOW_WIDTH = 1400;
    private static int WINDOW_HEIGHT = 800;
    private int MAIN_MENU_WIDTH = 800;
    private int MAIN_MENU_HEIGHT = 700;

    //camera for the 3D simulation
    public static Camera camera = new PerspectiveCamera(true);
    private static int nearCip = 1;
    private static int farClip = 2000;

    //group that holds the 3D planetary system
    public static Group system;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //gets main menu
        VBox mainMenuBox = MainMenu.getMainMenu(primaryStage);

        //sets scene
        Scene mainMenuScene = new Scene(mainMenuBox, MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT);
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    //About SubScenes: SubScenes are a feature of Javafx 3D that allows the user to have 3D and 2D
    //components in their program. A SubScene is used to render the 3D planetary system and another
    //SubScene is used to render the 2D toolbar.

    //creates the main scene for the simulation with two SubScenes: a toolbar and the planetary system
    public static Scene simulationScene(File file, Stage stage) {
        //main vbox for this scene
        VBox root = new VBox();
        root.setBackground(null); // gets rid of gray background

        //initiates scene
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        //Planetary system SubScene
        SubScene planetarySystemGroup = getPlanetarySystem(file);
        planetarySystemGroup.heightProperty().bind(root.heightProperty());
        planetarySystemGroup.widthProperty().bind(root.widthProperty());

        //tools SubScene
        ToolHandler toolHandler = new ToolHandler();
        SubScene toolBar = toolHandler.getToolbar(scene);
        ToolHandler.tools[ToolHandler.selectInd] = true;
        toolHandler.handleToolActions(scene);

        root.getChildren().addAll(toolBar, planetarySystemGroup);
        scene.setFill(PlanetarySystem.milkyWayBackground);

        return scene;
    }

    //renders the 3D planetary system SubScene from a file
    public static SubScene getPlanetarySystem(File file) {
        try {
            //creates new SubScene for the planetary system
            PlanetarySystem planetarySystem = new PlanetarySystem();
            system = planetarySystem.renderPlanetarySystem(file);
            SubScene systemSubScene = new SubScene(system, WINDOW_WIDTH, WINDOW_HEIGHT, true,
                    SceneAntialiasing.BALANCED);

            //sets up camera, cameras are used to view the 3D world
            camera.setNearClip(nearCip);
            camera.setFarClip(farClip);
            camera.translateYProperty().set(farClip/2);
            //camera.translateZProperty().set(-farClip/2);
            camera.setRotationAxis(Rotate.X_AXIS);
            camera.setRotate(90);

            systemSubScene.setCamera(camera);

            //adds dummy light that gets rid of the default lighting
            system.getChildren().add(new PointLight());
            return systemSubScene;

        } catch (FileNotFoundException e) {
            System.out.println("Please enter an appropriate file");
            return null;
        }
    }

    //removes planetary system from the group.
    //this is done so that an updated planetary system may be added
    public static void clearSystemGroup(){
        system.getChildren().clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
