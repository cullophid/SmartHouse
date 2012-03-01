package config;
import java.io.*;
import java.util.Scanner;

/**
 * @author Andreas
 */
public class Config{
    
    /**
     * database 
     */
    public static String DB = "jdbc:mysql://localhost/kiiib_dev?user=KIIIB&password=42";
    /**
     * pattern length for markov chains
     */
    public static int patternLength = 2;
    /**
     * maximum time interval in ms, for events to count as a pattern
     */
    public static int patternInterval = 10*1000;
    /**
     * maximum time interval in ms, for events to count as a zone event
     */
    public static int zoneInterval = 500; 
    /**
     * the interval after an on event, that sensor events is considered to be correlated to the switch
     */
    public static int correlationInterval = 7*1000;
    /**
     * minimum correlation probability for a sensor to extend the timeout of a switch 
     */
    public static float probabilityThreshold = .5f;
    /**
     * should the system detect zone events
     */
    public static boolean useZones = true;
    /**
     * base timeout for all switches in ms
     */
    public static int defaultOnTime = 5000;
    /**
     * the interval after a switch is turned off based on timeout, that the system considers a on event a punishment
     */
    public static int punishmentTimeout = 10*1000;
    /**
     * the correlation correction when the system is punished
     */
    public static float correlationCorrectionStep = .1f;
    /**
     * flag for when the system is in debug mode
     * used toggle debug output
     * also toggles simulator logging motion and switch event to database (doesn't log in debug mode)
     */
    public static boolean debug = false;
    //hej David
    
    public static void main(String[] args) {
        Config.loadConfig();
    }
   
    public static void loadConfig(){
        System.out.println("Loading Configurations");
        try{
            File f = new File("kiiib.settings");
            if(!f.exists()){
                System.out.println("could not find preferences file, generating a new one");
                f.createNewFile();
                FileWriter fstream = new FileWriter(f);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write("#automatically generated preferences file\n#delete to return to default settings\n");
                out.write("pattern_interval "+patternInterval+"\n");
                out.write("pattern_length "+patternLength+"\n");
                out.write("probability_threshold "+probabilityThreshold+"\n");
                out.write("use_zones "+useZones+"\n");
                out.write("zone_interval "+zoneInterval+"\n");
                out.write("correlation_interval " + correlationInterval+"\n");
                out.close();

            }
            else{
                Scanner scan = new Scanner(f);
                String token;
                while(scan.hasNextLine()){
                    token = scan.next(); 
                    if(token.equals("pattern_length")){
                        patternLength = Integer.parseInt(scan.next());
                        scan.nextLine();
                        System.out.println("pattern_length = "+patternLength);
                    }
                    else if(token.equals("pattern_interval")){
                        patternInterval = Integer.parseInt(scan.next());
                        scan.nextLine();
                        System.out.println("pattern_interval = "+patternInterval);
                    }
                    else if(token.equals("probability_threshold")){
                        probabilityThreshold = Float.parseFloat(scan.next());
                        scan.nextLine();
                        System.out.println("probablility_threshold = "+probabilityThreshold);
                    }
                    else if(token.equals("use_zones")){
                        useZones = Boolean.parseBoolean(scan.next());
                        scan.nextLine();
                        System.out.println("use_zones = "+useZones);
                    }
                    else if(token.equals("zone_interval")){
                        zoneInterval = Integer.parseInt(scan.next());
                        scan.nextLine();
                        System.out.println("zone_interval = "+zoneInterval);
                    }
                    else {
                        scan.nextLine();
                    }

                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(Exception e){
            System.out.println("could not read preferences file... using default settings");
        }
    } 
    }
