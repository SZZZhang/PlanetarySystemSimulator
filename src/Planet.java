/* Name: Shirley Zhang
 * Course code: ICS3U
 * Date: Jan 22, 2019
 * Instructor: Jeff Radulovic
 * Assignment name: Culminating activity
 * Description: Planet class
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

public class Planet {
    private String name;
    private double radius;
    private int image;
    private double distanceFromStar;
    private double orbitalPeriod;
    private double axisTilt;
    private double rotationalPeriod;

    //constructor
    public Planet(String name, double radius, int image, double distanceFromStar,
                  double orbitalPeriod, double axisTilt, double rotationalPeriod) {
        this.name = name;
        this.radius = radius;
        this.image = image;

        this.distanceFromStar = distanceFromStar;
        this.orbitalPeriod = orbitalPeriod;
        this.axisTilt = axisTilt;
        this.rotationalPeriod = rotationalPeriod;
    }

    //getters
    public String getName() {
        return name;
    }

    public double getRadius() {
        //scales the radius of the planet with a quadratic function
        return (radius - radius * radius / 100000) / 1000;
    }

    public double getRealRadius() {
        return radius;
    }

    public int getImage() {
        return image;
    }

    public double getDistanceFromStar() {
        return distanceFromStar / 1_000_000;
    }

    public long getRealDistanceFromStar() {
        return (long) distanceFromStar;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public double getAxisTilt() {
        return axisTilt;
    }

    public double getRotationalPeriod() {
        return rotationalPeriod;
    }

    //reads planet attributes from a file and creates a Planet object
    public static Planet readPlanet(Scanner scan) {
        String line[] = scan.nextLine().split(",");
        Planet planet = new Planet(line[0], Double.parseDouble(line[1]), Integer.parseInt(line[2]),
                Double.parseDouble(line[3]), Double.parseDouble(line[4]),
                Double.parseDouble(line[5]), Double.parseDouble(line[6]));
        return planet;
    }

    //renders the 3D planet
    public static Sphere renderPlanet(Planet planet) {
        Sphere sphere = new Sphere(planet.getRadius());

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(PlanetarySystem.planetExteriorMaps.get(planet.getImage()));
        sphere.setMaterial(material);

        sphere.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ToolHandler.tools[ToolHandler.selectInd]) {
                    Stage info = renderInformation(planet);
                    info.show();
                }
            }
        });
        return sphere;
    }

    //creates popup window with all information regarding the planet
    private static Stage renderInformation(Planet planet) {
        int INFO_WIDTH = 250, INFO_HEIGHT = 150;
        Stage stage = new Stage();

        VBox infoBox = new VBox();
        HBox header = new HBox();
        Scene infoScene = new Scene(infoBox, INFO_WIDTH, INFO_HEIGHT);

        infoBox.getStylesheets().add("StyleSheets/InformationPopup.css");

        Label name = new Label(planet.getName());
        header.getChildren().add(name);

        Label radius = new Label("Radius: " + planet.getRealRadius() + " km");
        Label distance = new Label("Distance from " +
                PlanetarySystem.star.getName() + ": "
                + planet.getRealDistanceFromStar() + " km");
        Label orbitalPeriod = new Label("Orbital period: "
                + planet.getOrbitalPeriod() + " days");
        Label axisTilt = new Label("Axis tilt: "
                + planet.getAxisTilt() + "\u00b0");
        Label rotationalPeriod = new Label("Rotational period: " +
                planet.getRotationalPeriod() + " days");

        infoBox.getChildren().addAll(header, radius, distance, orbitalPeriod,
                axisTilt, rotationalPeriod);

        stage.setScene(infoScene);
        return stage;
    }

}
