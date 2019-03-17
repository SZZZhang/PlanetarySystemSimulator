/* Name: Shirley Zhang
 * Course code: ICS3U
 * Date: Jan 22, 2019
 * Instructor: Jeff Radulovic
 * Assignment name: Culminating activity
 * Description: Creates toolbar and handles tool actions
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Arrays;

public class ToolHandler {
    //dimensions
    private int TOOLBAR_WIDTH = 1200;
    private int TOOLBAR_HEIGHT = 50;

    //number of tools
    public static int numOfTools = 5;
    public static int numOfAnimateTools = 3;

    //check whether or not a tool is selected
    public static boolean tools[] = new boolean[numOfTools];
    boolean animateTools[] = new boolean[numOfAnimateTools];
    boolean editInd = false;

    //indexes and the tool it represents:
    static final int selectInd = 0;
    static final int rotateInd = 1;
    static final int dragInd = 2;
    static final int zoomInInd = 3;
    static final int zoomOutInd = 4;

    //animation has yet to be implemented
    int playInd = 0;
    int pauseInd = 1;
    int fastForwardInd = 2;

    int zoomFactor = 70;

    //buttons
    Button select = new Button(" ");
    Button rotate = new Button(" ");
    Button drag = new Button(" ");
    Button zoomIn = new Button(" ");
    Button zoomOut = new Button(" ");
    Button play = new Button(" ");
    Button pause = new Button(" ");
    Button fastForward = new Button(" ");
    Button edit = new Button("        Edit Planetary System");

    public SubScene getToolbar(Scene scene) {

        VBox toolBarHolder = new VBox();
        HBox toolBar = new HBox();

        SubScene toolBarSubScene = new SubScene(toolBarHolder, TOOLBAR_WIDTH, TOOLBAR_HEIGHT, false,
                SceneAntialiasing.BALANCED);

        //styling
        toolBarHolder.setBackground(null);
        toolBarSubScene.widthProperty().bind(toolBarHolder.widthProperty());
        toolBarSubScene.heightProperty().bind(toolBarHolder.heightProperty());
        toolBar.getStylesheets().add("StyleSheets/PlanetarySystem.css");
        select.setId("select");
        rotate.setId("rotate");
        drag.setId("drag");
        zoomIn.setId("zoom-in");
        zoomOut.setId("zoom-out");
        play.setId("play");
        pause.setId("pause");
        fastForward.setId("fast-forward");
        edit.setId("edit");

        toolBar.getChildren().addAll(select, rotate, drag, zoomIn, zoomOut,
                play, fastForward, pause, edit);

        toolBarHolder.getChildren().addAll(toolBar);

        return toolBarSubScene;
    }

    //handles tool actions
    public void handleToolActions(Scene scene){
        select.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Arrays.fill(tools, false);
                tools[selectInd] = true;
            }
        });
        zoomIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Arrays.fill(tools, false);
                tools[zoomInInd] = true;
                handleEvents(scene);
            }
        });
        zoomOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Arrays.fill(tools, false);
                tools[zoomOutInd] = true;
                handleEvents(scene);
            }
        });

        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EditMenu editMenu = new EditMenu();
                editMenu.createEditMenu().show();
            }
        });

    }

    //handles events
    public void handleEvents(Scene scene){
        if(tools[zoomInInd]){
            zoomInMethod(scene);
        }
        else if(tools[zoomOutInd]){
            zoomOutMethod(scene);
        }
    }

    //zoom in and zoom out methods, moves camera back and forth
    private void zoomInMethod(Scene scene){
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(tools[zoomInInd])
                    GUI.camera.translateZProperty().set(GUI.camera.getTranslateZ() + zoomFactor);
            }
        });
    }
    private void zoomOutMethod(Scene scene){
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(tools[zoomOutInd])
                    GUI.camera.translateZProperty().set(GUI.camera.getTranslateZ() - zoomFactor);
            }
        });
    }
}
