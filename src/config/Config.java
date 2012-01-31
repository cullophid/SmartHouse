package config;
import java.io.*;
import java.util.Scanner;
public class Config{
    public static int patternLength = 7;
    public static int patternInterval = 10*1000;
    public static int zoneInterval = 1000; 
    public static int correlationInterval = 7*1000;
    public static float probabilityThreshold = .5f;
    public static boolean useSensorZones = true;
    public static int defaultOnTime = 5000;
    public static int punishmentTimeout = 10*1000;
    public static float correlationCorrectionStep = .1f;
    public static boolean debug = true;
    
    public static void main(String[] args) {
        Config.loadConfig();
    }
   
    public static void loadConfig(){
        System.out.println("Loading Configurations");
        try{
            File f = new File("kiiib.properties");
            if(!f.exists()){
                System.out.println("could not find preferences file, generating a new one");
                f.createNewFile();
                FileWriter fstream = new FileWriter(f);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write("#automatically generated preferences file\n#delete to return to default settings\n");
                out.write("pattern_interval "+patternInterval+"\n");
                out.write("pattern_length "+patternLength+"\n");
                out.write("probability_threshold "+probabilityThreshold+"\n");
                out.write("use_sensor_zones "+useSensorZones+"\n");
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
                    else if(token.equals("use_sensor_zones")){
                        useSensorZones = Boolean.parseBoolean(scan.next());
                        scan.nextLine();
                        System.out.println("use_sensor_zones = "+useSensorZones);
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
