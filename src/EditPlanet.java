/* Name: Shirley Zhang
 * Course code: ICS3U
 * Date: Jan 22, 2019
 * Instructor: Jeff Radulovic
 * Assignment name: Culminating activity
 * Description: VBox with textfields allowing for the editing of planet attributes
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import java.util.Random;

public class EditPlanet {

    private VBox box = new VBox();
    private TextField nameField;
    private TextField radiusField;
    private TextField distField;
    private TextField periodField;
    private TextField tiltField;
    private TextField rotationalField;
    private int image;

    private String defaultName = "PlanetName";
    private int minRadius = 2000;
    private int maxRadius = 90000;
    private int defaultDist = 700;
    private int defaultPeriod = 365;
    private int defaultTilt = 0;
    private int defaultRotation = 1;

    Sphere demoPlanet;

    public EditPlanet(Planet planet) {

        Label header = new Label("Planet");

        GridPane pane = new GridPane();

        image = planet.getImage();

        //name
        Label name = new Label("Name: ");
        nameField = new TextField(planet.getName());
        //the second and third parameters represent the column and the row number
        pane.add(name, 0, 0); //column 0, row 0
        pane.add(nameField, 1, 0); //column 1, row 0

        //radius
        Label radius = new Label("Radius: ");
        radiusField = new TextField(Double.toString(planet.getRealRadius()));
        Label radiusUnit = new Label(" km");
        pane.add(radius, 0, 1);
        pane.add(radiusField, 1, 1);
        pane.add(radiusUnit, 2, 1);

        //distance from star
        Label distance = new Label("Distance from star: ");
        distField = new TextField(Long.toString(planet.getRealDistanceFromStar()));
        Label distUnit = new Label(" km");
        pane.add(distance, 0, 2);
        pane.add(distField, 1, 2);
        pane.add(distUnit, 2, 2);

        //orbital period
        Label orbitalPeriod = new Label("Orbital period: ");
        periodField = new TextField(Double.toString(planet.getOrbitalPeriod()));
        Label periodUnit = new Label(" days");
        pane.add(orbitalPeriod, 0, 3);
        pane.add(periodField, 1, 3);
        pane.add(periodUnit, 2, 3);

        //axis tilt
        Label axisTilt = new Label("Axis tilt: ");
        tiltField = new TextField(Double.toString(planet.getAxisTilt()));
        Label tiltUnit = new Label("\u00b0");
        pane.add(axisTilt, 0, 4);
        pane.add(tiltField, 1, 4);
        pane.add(tiltUnit, 2, 4);

        //rotational period
        Label rotationalPeriod = new Label("Rotational period: ");
        rotationalField = new TextField(Double.toString(planet.getRotationalPeriod()));
        Label rotationalUnit = new Label(" days");
        pane.add(rotationalPeriod, 0, 5);
        pane.add(rotationalField, 1, 5);
        pane.add(rotationalUnit, 2, 5);

        //sample planet
        demoPlanet = renderPlanetPreview(planet);

        Button changeImage = new Button("Change image");
        changeImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage changeImage = changeImageWindow();
                changeImage.show();
            }
        });

        box.getChildren().addAll(header, demoPlanet, changeImage, pane);
    }

    //renders a 3D sphere of the planet to be shown in the "edit menu"
    private Sphere renderPlanetPreview(Planet planet) {
        int radius = 20;
        Sphere sphere = new Sphere(radius);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(PlanetarySystem.planetExteriorMaps.get(planet.getImage()));
        sphere.setMaterial(material);

        return sphere;
    }

    //renders 3D sphere with an image mask, shown in the "change image" window;
    private Sphere renderImagePreview(int image, int radius) {
        Sphere sphere = new Sphere(radius);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(PlanetarySystem.planetExteriorMaps.get(image));
        sphere.setMaterial(material);

        return sphere;
    }

    //inside the edit menu, there is the option to change the appearance of a planet,
    //this is the popup window that enables this change
    private Stage changeImageWindow() {
        int WINDOWWIDTH = 300;
        int WINDOWHEIGHT = 300;
        int buttonBoxSpacing = 5;
        int radius = 30;
        int padding = 10;

        GridPane pane = new GridPane();
        ColumnConstraints constraints = new ColumnConstraints(WINDOWWIDTH / 2);
        pane.getColumnConstraints().addAll(constraints, constraints);

        Stage stage = new Stage();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(pane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Scene scene = new Scene(scrollPane, WINDOWWIDTH, WINDOWHEIGHT);

        for (int row = 0, cnt = 0; cnt < PlanetarySystem.planetExteriorMaps.size(); cnt++) {
            VBox buttonBox = new VBox();
            buttonBox.setPadding(new Insets(padding, padding, padding, padding));
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setSpacing(buttonBoxSpacing);
            Button button = new Button("Set image");

            final int imageInd = cnt;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    image = imageInd;
                    stage.close();
                }
            });

            Sphere sphere = renderImagePreview(cnt, radius);
            buttonBox.getChildren().addAll(sphere, button);
            pane.add(buttonBox, cnt % 2, row);

            if (cnt % 2 != 0) row++;
        }

        stage.setTitle("Change image");
        stage.setScene(scene);
        return stage;
    }

    //setter for changing the image of a planet
    public void setImage(int image) {
        this.image = image;
    }

    public VBox getEditPlanetBox() {
        return box;
    }

    //get updated data from textfields
    public String getName() {
        if (nameField.getText().length() == 0)
            return defaultName;
        return nameField.getText();
    }

    public String getRadius() {
        //randomly generates radius if user input is invalid
        Random random = new Random();
        int defaultRadius = random.nextInt((maxRadius - minRadius) + 1);

        try {
            if (Double.parseDouble(radiusField.getText()) < minRadius ||
                    Double.parseDouble(radiusField.getText()) > maxRadius)
                return Double.toString(defaultRadius);
            return radiusField.getText();

        } catch (Exception e) {
            //if the text entered contains string characters,
            //an exception occurs, when that happens, the default value is returned
            return Double.toString(defaultRadius);
        }
    }

    public int getImage() {
        return this.image;
    }

    public String getDistance() {
        try {
            if (Double.parseDouble(distField.getText()) < 0) {
                return Double.toString(defaultDist);
            }
            return distField.getText();
        } catch (Exception e) {
            return Double.toString(defaultDist);
        }
    }

    public String getPeriod() {
        try {
            if (Double.parseDouble(periodField.getText()) < 0) {
                return Double.toString(defaultPeriod);
            }
            return periodField.getText();
        } catch (Exception e) {
            return Double.toString(defaultPeriod);
        }
    }

    public String getTilt() {
        try {
            if (Double.parseDouble(tiltField.getText()) < 0) {
                return Double.toString(defaultTilt);
            }
            return tiltField.getText();
        } catch (Exception e) {
            return Double.toString(defaultTilt);
        }
    }

    public String getRotationalPeriod() {
        try {
            if (Double.parseDouble(rotationalField.getText()) < 0) {
                return Double.toString(defaultRotation);
            }
            return rotationalField.getText();
        } catch (Exception e) {
            return Double.toString(defaultRotation);
        }
    }
}
