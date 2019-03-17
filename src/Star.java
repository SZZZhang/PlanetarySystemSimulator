/* Name: Shirley Zhang
 * Course code: ICS3U
 * Date: Jan 22, 2019
 * Instructor: Jeff Radulovic
 * Assignment name: Culminating activity
 * Description: Star class, representing stars
 */

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import java.util.Scanner;

public class Star {
    //default values
    private String name = "";
    private double radius = 300; // in KM
    private String spectralClass = "G2V";
    private String luminosity = "3.828×1026";
    private String temperature = "15000000";

    public Star(String name, double radius, String spectralClass, String luminosity, String temperature) {
        this.name = name;
        this.radius = radius;
        this.spectralClass = spectralClass;
        this.luminosity = luminosity;
        this.temperature = temperature;
    }

    //getters
    public String getName() {
        return this.name;
    }

    public double getRadius() {
        //scales down radius
        return this.radius / 10000.0;
    }

    public long getRealRadius() {
        return (long) this.radius;
    }

    public String getSpectralClass() {
        return this.spectralClass;
    }

    public String getLuminosity() {
        return this.luminosity;
    }

    public String getTemperature() {
        return this.temperature;
    }

    //creates Star object from line in csv file
    public static Star readStar(Scanner scan) {
        String line[] = scan.nextLine().split(",");
        Star star = new Star(line[0], Double.parseDouble(line[1]),
                line[2], line[3], line[4]);
        return star;
    }

    //takes in a Star object and returns a rendered 3D star
    public static Sphere renderStar(Star star) {

        Sphere sphere = new Sphere(star.getRadius());

        //sets the mesh (exterior) of the sphere
        PhongMaterial material = new PhongMaterial();
        material.setSelfIlluminationMap(new
                PlanetarySystem().getStarExteriorMap());
        sphere.setMaterial(material);

        //when the user has selected a planet while using the selection tool
        sphere.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(ToolHandler.tools[ToolHandler.selectInd]){
                    Stage info = renderInformation(star);
                    info.show();
                }
            }
        });

        return sphere;
    }

    //creates popup window with all information regarding the star
    private static Stage renderInformation(Star star) {
        int INFO_WIDTH = 250, INFO_HEIGHT = 125;
        Stage stage = new Stage();

        VBox infoBox = new VBox();
        HBox header = new HBox();
        Scene infoScene = new Scene(infoBox, INFO_WIDTH, INFO_HEIGHT);

        infoBox.getStylesheets().add("StyleSheets/InformationPopup.css");

        Label name = new Label(star.getName());
        header.getChildren().add(name);

        Label radius = new Label("Radius: " + star.getRealRadius() + " km");
        Label spectralClass = new Label("Spectral class: " +
                star.getSpectralClass());
        Label luminosity = new Label("Luminosity: "
                + star.getLuminosity());
        Label temperature = new Label("Temperature: "
                + star.getTemperature() + "\u00b0");

        infoBox.getChildren().addAll(header, radius, spectralClass, luminosity,
                temperature);

        stage.setScene(infoScene);
        return stage;
    }
}
