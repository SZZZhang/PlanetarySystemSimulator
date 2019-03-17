/* Name: Shirley Zhang
 * Course code: ICS3U
 * Date: Jan 22, 2019
 * Instructor: Jeff Radulovic
 * Assignment name: Culminating activity
 * Description: This class stores all planets and the star of the planetary system
 */

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Sphere;
import javafx.util.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PlanetarySystem {

    //loads in the images that cover the 3D spheres
    //star image
    public static Image starExteriorMap = new Image("Images/Sun.jpg");

    //planetary images
    public static ArrayList<Image> planetExteriorMaps = new ArrayList<Image>();
    static Image mercury = new Image("Images/Mercury.jpg");
    static Image venus = new Image("Images/Venus.jpg");
    static Image earth = new Image("Images/Earth.jpg");
    static Image mars = new Image("Images/Mars.jpg");
    static Image jupiter = new Image("Images/Jupiter.jpg");
    static Image saturn = new Image("Images/Saturn.jpg");
    static Image uranus = new Image("Images/Uranus.jpg");
    static Image neptune = new Image("Images/Neptune.jpg");

    public static ImagePattern milkyWayBackground = new ImagePattern(new Image("Images/Stars_Milky_Way.jpg"));

    //star and planets part of the system
    public static Star star;
    public static ArrayList<Planet> planets = new ArrayList<>();
    public static ArrayList<Sphere> renderedPlanets = new ArrayList<>();

    private static ArrayList<Planet> sortedPlanets; // planets sorted by distance
    private static ArrayList<Double> orbitAngles = new ArrayList<>();
    private static ArrayList<Double> orbitAngleIncreases = new ArrayList<>();
    private static ArrayList<Integer> orbitRadii = new ArrayList<>();
    private static ArrayList<Pair<Integer, Integer>> translatePlanets; // orbit x and y translations per day

    public int outerOrbit = 450;

    int p; // iterator variable

    //animation
    AnimationTimer timer; // timer for animation
    long curTime = 0, lastTime = 0, deltaTime = 0;
    int refreshRate = 60;

    public Image getStarExteriorMap() {
        return starExteriorMap;
    }

    public static void initiatePlanetTextures() {
        if(planetExteriorMaps.size() > 0) return;
        planetExteriorMaps.addAll(Arrays.asList(mercury, venus, earth, mars,
                jupiter, saturn, uranus, neptune));
    }

    //creates planetary system Group from the file
    public Group renderPlanetarySystem(File file) throws FileNotFoundException {
        planets.clear();
        renderedPlanets.clear();

        Group planetarySystem = new Group();

        Scanner scan = new Scanner(file);

        //gets rid of fst line of file
        String fstLine = scan.nextLine();

        //takes in information for the star and renders it
        this.star = Star.readStar(scan);
        planetarySystem.getChildren().add(Star.renderStar(this.star));//renders star

        //gets rid of third line of file
        String thrdLine = scan.nextLine();

        initiatePlanetTextures();
        while (scan.hasNextLine()) {
            Planet planet = Planet.readPlanet(scan);
            planets.add(planet);
        }

        //sorts planets by increasing distance from star
        Comparator<Planet> planetComparator = new Comparator<Planet>() {
            @Override
            public int compare(Planet a, Planet b) {
                if (a.getDistanceFromStar() < b.getDistanceFromStar()) return -1;
                if (a.getDistanceFromStar() > b.getDistanceFromStar()) return 1;
                return 0;
            }
        };
        sortedPlanets = (ArrayList<Planet>) planets.clone();
        Collections.sort(sortedPlanets, planetComparator);

        //offset to ensure that the planets are not inside the star
        int radiusOffset = (int) star.getRadius() + 30;

        //adds rendered planets to group
        for (p = 0; p < sortedPlanets.size(); p++) {
            //used for the orbit animation
            orbitAngleIncreases.add((2 * Math.PI) / (planets.get(p).getOrbitalPeriod() * refreshRate));
            orbitRadii.add(outerOrbit/sortedPlanets.size() * (p) + radiusOffset);
            orbitAngles.add(90.0);
            //System.out.println(2 * Math.PI +  " " + planets.get(p).getOrbitalPeriod());

            final Sphere rendered = Planet.renderPlanet(sortedPlanets.get(p));
            //rendered.setLayoutX(outerOrbit / sortedPlanets.size() * (p) + radiusOffset);
            renderedPlanets.add(rendered);
            planetarySystem.getChildren().add(rendered);
        }

        //animation
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();

        return planetarySystem;
    }

    public static void update(){
        for(int p = 0; p < renderedPlanets.size(); p++){
            double radius = orbitRadii.get(p);
            double angle = orbitAngles.get(p) + orbitAngleIncreases.get(p);
            renderedPlanets.get(p).setTranslateZ(radius * Math.cos(angle));
            renderedPlanets.get(p).setTranslateX(radius * Math.sin(angle));
            orbitAngles.set(p, angle);
        }
    }
}
