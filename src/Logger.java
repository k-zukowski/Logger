import java.io.*;
import java.nio.file.Files;

public class Logger {

    private String name;
    private volatile Configuration config;
    private Level level;
    private String template;
    private final String fileName;
    private final String fileExtension;
    private final String fileFullName;

    private static final class Configuration {
        public Configuration() {
        }

    }

    public Logger(String name) {
        this.name = name;
        config = new Configuration();
        this.fileName = "log";
        this.fileExtension = "txt";
        this.fileFullName = String.format("%s.%s",fileName,fileExtension);
    }

    public void off(String message) {
        //take into consideration template
        //write to file
        //message = template....//maybe template should be a Class
        this.level = Level.OFF;
        configureTemplate();
        writeToFile(message);
    }

    public void fatal(String message) {

    }

    public void error(String message) {

    }

    public void warning(String message) {

    }

    public void info(String message) {

    }

    public void debugging(String message) {

    }

    public void trace(String message) {

    }

    private void configureTemplate() {
        switch (level) {
            case OFF -> template = "off";/// time-thread number-message-sth else useful
            case FATAL -> template = "fatal";// -||-
            case ERROR -> template = "error";
            case WARN -> template = "warning";
            case INFO -> template = "info";
            case DEBUG -> template = "debugging";
            case TRACE -> template = "trace";

        }
    }

    private void writeToFile(String message) {
        //check if the config file already exists
        //skip file creation
        File f = new File(this.fileFullName);
        if (!f.exists() && !f.isFile()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        try(FileWriter fw = new FileWriter("log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(message);

        } catch (IOException ignore) {

        }

    }
}
