import Enums.Level;
import Enums.LogTemplateParameters;
import Enums.TemplateState;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Logger {

    private String name;
    private volatile Configuration config;
    private Level level;
    private TemplateState template;
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
        this.template = TemplateState.DETAILED;
    }
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
    private void log (String message, Level level){
        setLevel(level);
        try {
            writeToFile(templateToMessage(message));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void off(String message) {
       log(message, Level.OFF);
    }
    public void fatal(String message) {
        log(message, Level.FATAL);
    }

    public void error(String message) {
        log(message, Level.ERROR);
    }

    public void warning(String message) {
        log(message, Level.WARN);
    }

    public void info(String message) {
        log(message, Level.INFO);
    }

    public void debugging(String message) {
        log(message, Level.DEBUG);
    }

    public void trace(String message) {
        log(message, Level.TRACE);
    }

    public void setTemplate(TemplateState template) {
        this.template = template;
    }

    public TemplateState getTemplate() {
        return template;
    }
    private String templateToMessage(String message) throws UnknownHostException {
        StringBuilder write = new StringBuilder();
        for (LogTemplateParameters param : template.getTemplate()) {
            switch (param) {
                case DATE -> write.append(LocalDate.now());
                case TIME -> write.append(LocalTime.now());
                case THREADNR -> write.append(Thread.currentThread().getId());
                case LOGTYPE -> write.append(level.name());
                case IP -> write.append(InetAddress.getLocalHost());
                case MESSAGE -> write.append(message);
            }
            write.append("\t");
        }
        return write.toString();
    }

    private void writeToFile(String message) {
        File f = new File(this.fileFullName);
        if (!f.exists() && !f.isFile()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred while creating a new log file.");
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
