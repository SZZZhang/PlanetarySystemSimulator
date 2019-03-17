/* Name: Shirley Zhang
 * Course code: ICS3U
 * Date: Jan 22, 2019
 * Instructor: Jeff Radulovic
 * Assignment name: Culminating activity
 * Description: VBox with textfields allowing for the editing of star attributes
 */

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class EditStar {

    private VBox box = new VBox();
    private TextField nameField;
    private TextField radiusField;
    private TextField spectClassField;
    private TextField luminosityField;
    private TextField temperatureField;

    //constructor
    public EditStar(Star star) {

        Label header = new Label("Star");
        Sphere previewStar = renderPreviewStar();
        GridPane pane = new GridPane();

        Label name = new Label("Name: ");
        nameField = new TextField(star.getName());
        //the second and third parameters represent the column and the row number
        pane.add(name, 0, 0); //column 0, row 0
        pane.add(nameField, 1, 0); //column 1, row 0

        Label radius = new Label("Radius: ");
        radiusField = new TextField(Long.toString(star.getRealRadius()));
        Label radiusUnit = new Label(" km");
        pane.add(radius, 0, 1);
        pane.add(radiusField, 1, 1);
        pane.add(radiusUnit, 2, 1);

        Label spectClass = new Label("Spectral class: ");
        spectClassField = new TextField(star.getSpectralClass());
        pane.add(spectClass, 0, 2);
        pane.add(spectClassField, 1, 2);

        Label luminosity = new Label("Luminosity: ");
        luminosityField = new TextField(star.getLuminosity());
        pane.add(luminosity, 0, 3);
        pane.add(luminosityField, 1, 3);

        Label temperature = new Label("Temperature: ");
        temperatureField = new TextField(star.getTemperature());
        Label temperatureUnit = new Label("\u00b0");
        pane.add(temperature, 0, 4);
        pane.add(temperatureField, 1, 4);
        pane.add(temperatureUnit, 2, 4);

        box.getChildren().addAll(header, previewStar, pane);
    }

    //renders a preview star
    private Sphere renderPreviewStar() {
        int radius = 20;
        Sphere sphere = new Sphere(radius);

        PhongMaterial material = new PhongMaterial();
        material.setSelfIlluminationMap(PlanetarySystem.starExteriorMap);
        sphere.setMaterial(material);
        return sphere;
    }

    //gets updated data from textfields
    public VBox getBox() {
        return box;
    }

    public String getName() {
        return nameField.getText();
    }

    public String getRadiusField() {
        int minRadius = 100000;
        int maxRadius = 1000000;
        int defaultRadius = 696392;
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

    public String getSpectClass() {
        return spectClassField.getText();
    }

    public String getLuminosity() {
        return luminosityField.getText();
    }

    public String getTemperature() {
        return temperatureField.getText();
    }
}
