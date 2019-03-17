/* Name: Shirley Zhang
 * Course code: ICS3U
 * Date: Jan 22, 2019
 * Instructor: Jeff Radulovic
 * Assignment name: Culminating activity
 * Description: This is the main menu
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class MainMenu {

    //variables that need to be accessed from other classes
    static File file;
    static Scene simulationScene;

    public static VBox getMainMenu(Stage primaryStage){
        //main menu
        VBox mainMenuBox = new VBox();
        VBox mainMenuTitleBox = new VBox();
        VBox mainMenuButtonsBox = new VBox();

        //adds stylesheets
        mainMenuBox.getStylesheets().add("StyleSheets/Main_Menu.css");
        mainMenuTitleBox.getStylesheets().add("StyleSheets/Main_Menu_Title.css");
        mainMenuButtonsBox.getStylesheets().add("StyleSheets/Main_Menu_Buttons.css");

        //create elements/nodes that go in the main menu
        Label titleText = new Label("Make-Your-Own\nPlanetary System");
        Button openExistingButton = new Button("Open existing");
        Button createNewButton = new Button("Create new");
        Button randomlyGenerateButton = new Button("Randomly generate");

        openExistingButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent arg0) {
                FileChooser openFile = new FileChooser();
                openFile.setTitle("Select a csv file");

                openFile.setInitialDirectory(new File(
                        System.getProperty("user.dir")
                                + "/Planet System Files"));
                file = openFile.showOpenDialog(primaryStage);
                if (file != null) {
                    GUI gui = new GUI();
                    simulationScene = gui.simulationScene(file, primaryStage);

                    if (simulationScene == null) {
                        System.out.println("Enter a legit file");
                    }
                    primaryStage.setScene(simulationScene);

                }
            }
        });

        randomlyGenerateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GenerateRandomFile randomFileGenerator = new GenerateRandomFile();
                GUI gui = new GUI();
                file = randomFileGenerator.generateFile();
                simulationScene = gui.simulationScene(file, primaryStage);
                primaryStage.setScene(simulationScene);
            }
        });

        //adds icons to buttons using css
        openExistingButton.setId("open-existing-button");
        createNewButton.setId("create-new-button");
        randomlyGenerateButton.setId("randomly-generate-button");

        //adds elements to main menu
        mainMenuTitleBox.getChildren().addAll(titleText);
        mainMenuButtonsBox.getChildren().addAll(openExistingButton, createNewButton,
                randomlyGenerateButton);
        mainMenuBox.getChildren().addAll(mainMenuTitleBox, mainMenuButtonsBox);
        return mainMenuBox;
    }
}
