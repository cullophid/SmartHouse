package config;
import java.io.*;
import java.util.Scanner;
public class Config{
    private static int depth = 1;
    private static int dayIntervals = 1;

       public static void loadConfig(){

        try{
            File f = new File("kiiib.properties");
            if(!f.exists()){
                System.out.println("could not find preferences file, generating a new one");
                f.createNewFile();
                FileWriter fstream = new FileWriter(f);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write("#automatically generated preferences file\n#delete to return to default settings\n");
                out.write("depth 1\n");
                out.write("dayIntervals 1\n");
                out.close();

            }
            else{
                Scanner scan = new Scanner(f);
                String token;
                while(scan.hasNextLine()){
                    token = scan.next(); 
                    if(token.equals("depth")){
                        depth = Integer.parseInt(scan.next());
                        scan.nextLine();
                    }
                    else if(token.equals("dayIntervals")){
                        dayIntervals = Integer.parseInt(scan.next());
                        scan.nextLine();
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
    public static int getDepth(){
        return depth;
    }
    public static int getDayIntervals(){
        return dayIntervals;
    }
         
}
