/* Name: Shirley Zhang
 * Course code: ICS3U
 * Date: Jan 22, 2019
 * Instructor: Jeff Radulovic
 * Assignment name: Culminating activity
 * Description: Generates a random planetary system file
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class GenerateRandomFile {

    private String[] planetNames = {
            "Earth", "Mercury", "Venus", "Saturn", "Mars", "Jupiter", "Uranus", "Neptune",
            "MUMU", "Pluto", "Titan", "Moon"
    };

    //bounds of the random attributes
    private int minNumOfPlanets = 3;
    private int maxNumOfPlanets = 9;
    private int minRadius = 2000;
    private int maxRadius = 70000;
    private int minDist = 200000;
    private int maxDist = 2_000_000_000;
    private int minPeriod = 40;
    private int maxPeriod = 1000;
    private int maxTilt = 180;
    private int minRotatePeriod = 1;
    private int maxRotatePeriod = 100;

    //writes updated star data
    private void writeStar(PrintWriter writer) {
        writer.print("StarName" + ',');
        writer.print("700000" + ',');
        writer.print("G2V" + ',');
        writer.print("3.828 *10^26" + ',');
        writer.println("15000000" + ',');
    }

    //writes random planet data
    private void writePlanets(PrintWriter writer) {
        PlanetarySystem.initiatePlanetTextures();
        Random random = new Random();
        int numOfPlanets = random.nextInt(maxNumOfPlanets - minNumOfPlanets + 1) + minNumOfPlanets;

        for (int i = 0; i < numOfPlanets; i++) {
            writer.print(planetNames[random.nextInt(planetNames.length)] + ",");
            writer.print(random.nextInt(maxRadius - minRadius) + minRadius + ",");
            writer.print(random.nextInt(PlanetarySystem.planetExteriorMaps.size()) + ",");
            writer.print(random.nextInt(maxDist - minDist) + minDist + ",");
            writer.print(random.nextInt(maxPeriod - minPeriod) + minPeriod + ",");
            writer.print(random.nextInt(maxTilt) + ",");
            writer.println(random.nextInt(maxRotatePeriod - minRotatePeriod) + ",");
        }
    }

    //generates random file
    public File generateFile() {
        String fstLine = "Name,Radius,SpectralClass,Luminosity,Temperature,,";
        String thrdLine = "Name,Radius,Image,Distance from star,Orbital Period,Axis tilt,Rotation Period,";

        File file = new File(System.getProperty("user.dir")
                + "/Planet System Files/RandomFile.csv");
        try {
            //if file was not created yet
            if(file.createNewFile()){
                //writes to file
                PrintWriter writer = new PrintWriter(file);
                writer.println(fstLine);
                writeStar(writer);
                writer.println(thrdLine);
                writePlanets(writer);

                writer.flush();
                writer.close();
            }
            //if file was already created
            else{
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
            }
            return file;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("File not found");
            return null;

        }
    }
}
