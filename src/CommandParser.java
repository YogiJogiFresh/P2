import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Handles parsing the commands to send to the database
 * @author Alan Kai (yogijogi)
 * @version 1.0
 *
 */
public class CommandParser {
    //Max number of input parameters
    private static final int INPUT_PARAM = 6;
    //spaces to split all input parameters
    private static final String PARAM_SEPARATOR = "\\s++";
    
    private final int cmmdPos = 0; //Command Position
    private final int namePos = 1; //Name position
    
    //Position to make insert/remove parsing easier
    private final int insertXPos = 2;
    private final int removeXPos = 1;
    
    //Rectangle name
    private String name;
    //Coordinate and Dimensions
    private int x;
    private int y;
    private int w;
    private int h;
    
    private Point r;
    
    private Database db;
    /**
     * Parses the commands
     * @param fileName Filename
     * @throws IOException Error
     */
    public CommandParser(String fileName) throws IOException {
        db = new Database();
        //Temporary line for file scanner
        String line = null;
        String[] input = new String[INPUT_PARAM];
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            //Reads every line in the command file while there is a line
            while ((line = bufferedReader.readLine()) != null) {
                input = new String[6];
                
                //Checks for empty lines & stores command name and params
                if (!line.trim().isEmpty()) {
                    input = line.trim().split(PARAM_SEPARATOR);
                }
                //Sends commands to database
                if (input[0] != null) {
                    commandProcess(input);
                }
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException errorMsg) {
            System.out.println("Unable to open file: " + fileName);
        }
    }
    /**
     * Processes commands
     * @param input String in
     */
    public void commandProcess(String[] input) {
        switch (input[cmmdPos].toLowerCase()) {
            case "insert":
                name = input[namePos];
                parseIntegers(input);
                r = new Point(name, x, y, w, h);
                db.insert(r);
                break;
            case "remove":
                if (input.length == 2) {
                    name = input[namePos];
                    db.removeByName(name);
                }
                else {
                    parseIntegers(input);
                    r = new Point(x, y, w, h);
                    db.removeByCoord(r);
                }
                break;
            case "regionsearch":
                parseIntegers(input);
                int[] region = {x, y, w, h};
                db.regionsearch(region);
                break;
            //case "intersections":
                //db.intersections();
                //break;
            case "search":
                name = input[namePos];
                db.search(name, db.searchFound(name));
                break;
            case "dump":
                db.dump();
                break;
            case "duplicates":
                db.duplicates();
                break;
        }
    }
    /**
     * Parses the string to integer
     * @param input input string array
     */
    public void parseIntegers(String[] input) {
        switch (input[0].toLowerCase()) {
            case "insert":
                x = Integer.parseInt(input[insertXPos]);
                y = Integer.parseInt(input[insertXPos + 1]);
                //w = Integer.parseInt(input[insertXPos + 2]);
                //h = Integer.parseInt(input[insertXPos + 3]);
                break;
            case "remove":
                x = Integer.parseInt(input[removeXPos]);
                y = Integer.parseInt(input[removeXPos + 1]);
                break;
            case "regionsearch":
                x = Integer.parseInt(input[removeXPos]);
                y = Integer.parseInt(input[removeXPos + 1]);
                w = Integer.parseInt(input[removeXPos + 2]);
                h = Integer.parseInt(input[removeXPos + 3]);
                break;
        }
    }
}
