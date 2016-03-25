import java.io.IOException;
/**
 * The class containing the main method, the entry point of the application.
 *
 * @author {Alan Kai}
 * @version {1.0}
 */
public class Point2
{
    private static CommandParser cp;

    /**
     * The entry point of the application.
     *
     * @param args
     *            The name of the command file passed in as a command line
     *            argument.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        String fileName = args[0];
        cp = new CommandParser(fileName);
    }
}
