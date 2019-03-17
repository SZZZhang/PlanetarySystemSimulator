/* Name: Shirley Zhang
 * Course code: ICS3U
 * Date: Jan 22, 2019
 * Instructor: Jeff Radulovic
 * Assignment name: Culminating activity
 * Description: Edit menu
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class EditMenu {
    static int EDIT_MENU_WIDTH = 800;
    static int EDIT_MENU_HEIGHT = 400;
    static int COLUMN_WIDTH = 400;

    static ArrayList<EditPlanet> editPlanets = new ArrayList<>();
    static EditStar editStar;

    //creates edit menu window
    public Stage createEditMenu() {
        VBox menuBox = new VBox();

        //buttons
        HBox buttons = new HBox();
        Button addPlanet = new Button("Add Planet");
        Button cancel = new Button("Cancel");
        Button ok = new Button("OK");

        ScrollPane scrollPane = new ScrollPane();
        GridPane holder = new GridPane();
        menuBox.getStylesheets().add("StyleSheets/EditMenu.css");

        //holder.setGridLinesVisible(true);
        ColumnConstraints constraints = new ColumnConstraints(COLUMN_WIDTH);
        holder.getColumnConstraints().addAll(constraints, constraints);

        editStar = new EditStar(PlanetarySystem.star);
        holder.add(editStar.getBox(), 0, 0);

        for (int p = 0, row = 0; p < PlanetarySystem.planets.size(); p++) {
            EditPlanet editPlanetMenu = new EditPlanet(PlanetarySystem.planets.get(p));
            editPlanets.add(editPlanetMenu);
            VBox editPlanetBox = editPlanetMenu.getEditPlanetBox();
            //boxes go to alternating columns
            if (p % 2 != 0) {
                holder.add(editPlanetBox, 0, row);
            }else{
                holder.add(editPlanetBox, 1, row);
                row++;
            }
        }

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(holder);
        menuBox.getChildren().addAll(scrollPane, buttons);
        Scene editScene = new Scene(menuBox, EDIT_MENU_WIDTH, EDIT_MENU_HEIGHT);

        Stage menu = new Stage();
        menu.setScene(editScene);

        //button event handlers
        buttons.getChildren().addAll(addPlanet, cancel, ok);
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menu.close();
            }
        });
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                writeToFile(MainMenu.file);
                menu.close();
                editPlanets.clear();
                GUI.clearSystemGroup();
                PlanetarySystem planetarySystem = new PlanetarySystem();
                try{
                    GUI.system.getChildren().add(planetarySystem.renderPlanetarySystem(MainMenu.file));
                }catch (FileNotFoundException e){
                    System.out.println("File not found");
                }
            }
        });
        return menu;
    }

    //writes updated star data
    private void writeStar(PrintWriter writer){
        writer.print(editStar.getName() + ',');
        writer.print(editStar.getRadiusField() + ',');
        writer.print(editStar.getSpectClass() + ',');
        writer.print(editStar.getLuminosity() + ',');
        writer.println(editStar.getTemperature() + ',');
    }

    //writes updated planet data
    private void writePlanets(PrintWriter writer){
        for(EditPlanet editPlanet: editPlanets){
            writer.print(editPlanet.getName() + ',');
            writer.print(editPlanet.getRadius() + ',');
            writer.print(editPlanet.getImage() + ",");
            writer.print(editPlanet.getDistance() + ',');
            writer.print(editPlanet.getPeriod() + ',');
            writer.print(editPlanet.getTilt() + ',');
            writer.println(editPlanet.getRotationalPeriod() + ',');
        }
    }
    //updates csv file with updated data
    public void writeToFile(File file) {
        String fstLine = "Name,Radius,SpectralClass,Luminosity,Temperature,,";
        String thrdLine = "Name,Radius,Image,Distance from star,Orbital Period,Axis tilt,Rotation Period,";
        try{
            //clears file
            FileOutputStream writerClear = new FileOutputStream(file);
            writerClear.write(("").getBytes());
            writerClear.close();

            //writes to file
            PrintWriter writer = new PrintWriter(file);
            writer.println(fstLine);
            writeStar(writer);
            writer.println(thrdLine);
            writePlanets(writer);

            writer.flush();
            writer.close();

        }catch (Exception e){
            System.out.println("File Not Found :(");
        }
    }
}
