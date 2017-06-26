/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santoslogger.gui;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JFrame;
import santosutils.file.FileManager;

/**
 *
 * @author justdasc
 */
public class Log {
    
    //The extension to be used for the log files.
    private final String extension = ".satlog";

    //Path location of where to put the file.
    private String location;
    /**
     * The name of the file. This is separate in order to be able
     * to create the directory and the file if they do not exist.
     */
    private String name;
    /**
     * The java logger used to print the information to the log
     * file. This may be replaced at a later date with a new logger
     * in order to be able to have more control over the format and
     * headers with in the log file.
     */
    private Logger logger;
    /**
     * The frame for GUI for the logger.
     */
    private final JFrame g;
    
    //Optional setting to see exit codes displayed.
    private Boolean exitCode = false;
    
    //File manager from Santos Utils to assist in file and directory creation.
    private final FileManager f = new FileManager();

    /**
     * Creates a file with the given name in the default logs directory.
     * @param name Name of the file.
     */
    public Log(String name) {
        this.location = f.GetExecutionPath() +"logs\\";
        this.name = name;
        
        createLog();
        g = new GUI(this);
        g.setTitle(name);
    }
    
    /**
     * Creates a file with the given name in the default logs directory.
     * @param name Name of the file.
     * @param exitCode Optional parameter to display exit code.
     */
    public Log(String name, Boolean exitCode) {
        this.location = f.GetExecutionPath() +"logs\\";
        this.name = name;
        this.exitCode = exitCode;
        
        createLog();
        g = new GUI(this);
        g.setTitle(name);
    }
    
    /**
     * Creates a log file at the specified location with the specified name.
     * @param name Name of the log file.
     * @param location Directory location of the file.
     */
    public Log(String name, String location) {
        this.location = location;
        this.name = name;
        
        createLog();
        g = new GUI(this);
        g.setTitle(name);
    }
    
    /**
     * Creates a log file at the specified location with the specified name.
     * @param name Name of the log file.
     * @param location Directory location of the file.
     * @param exitCode Optional parameter to display exit codes.
     */
    public Log(String name, String location, Boolean exitCode)
    {
        this.location = location;
        this.name = name;
        this.exitCode = exitCode;
        
        createLog();
        g = new GUI(this);
        g.setTitle(name);
    }
    
    /**
     * Allows for toggling the logging of exit codes on and off.
     */
    public void toggleExitCode()
    {
        exitCode = !exitCode;
        if(exitCode)
            addInfo("Now displaying command exit codes.");
        else
            addInfo("Command exit codes will no longer be displayed.");
    }

    /**
     * Changes the file name to start logging to. This method is a setter
     * and is not meant to be used.
     * @param name Name of the new file. 
     */
    @Deprecated
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * Changes the log file location. This method is a setter and is not
     * meant to be used.
     * @param location The new location of the file.
     */
    @Deprecated
    public void changeLocaiton(String location) {
        this.location = location;
    }

    /**
     * Adds a normal info entry to the Santos Log.
     * @param text The text to be inputed into the entry.
     */
    public void addInfo(String text) {
        logger.info(text);
        if (GUI.console.getText().equals("") || GUI.console == null) {
            GUI.console.setText(text);
        } else {
            GUI.console.setText(GUI.console.getText() + "\n" + text);
        }
    }

    /**
     * Adds a warning entry to the Santos Log.
     * @param text The text to be inputed into the entry.
     */
    public void addWarning(String text) {
        logger.warning(text);
        if (GUI.console.getText().equals("") || GUI.console == null) {
            GUI.console.setText(text);
        } else {
            GUI.console.setText(GUI.console.getText() + "\n" + text);
        }
    }

    /**
     * Adds a severe entry to the Santos Log.
     * @param text The text to be inputed into the entry.
     */
    public void addSevere(String text) {
        logger.severe(text);
        if (GUI.console.getText().equals("") || GUI.console == null) {
            GUI.console.setText(text);
        } else {
            GUI.console.setText(GUI.console.getText() + "\n" + text);
        }
    }

    /**
     * Adds a fine entry to the Santos Log.
     * @param text The text to be inputed into the entry.
     */
    public void addFine(String text) {
        logger.fine(text);
        if (GUI.console.getText().equals("") || GUI.console == null) {
            GUI.console.setText(text);
        } else {
            GUI.console.setText(GUI.console.getText() + "\n" + text);
        }
    }

    /**
     * Adds a finest entry to the Santos Log.
     * @param text The text to be inputed into the entry.
     */
    public void addFinest(String text) {
        logger.finest(text);
        if (GUI.console.getText().equals("") || GUI.console == null) {
            GUI.console.setText(text);
        } else {
            GUI.console.setText(GUI.console.getText() + "\n" + text);
        }
    }

    /**
     * Makes the log GUI visible or invisible to the user.
     */
    public void showLog() {
        if (g.isVisible()) {
            g.setVisible(false);
        } else {
            g.setVisible(true);
        }
    }

    /**
     * Creates the initial file to be able to log to.
     */
    private void createLog() {
        FileHandler fh;
        logger = Logger.getLogger(name);
        try {
            /**
             * Creates initial directory and log as noted in the Santos Utils
             * create.
             */
            f.createDirectory(location);
            f.createFile(location + name + extension);
            
            /**
             * Here we use a file handler to be able to create a new log
             * to start appending to. We use a simple formatter to enable
             * the log to be read as HTML. The reason for this is to allow
             * color text and special formatting with the log for later
             * updates.
             */
            fh = new FileHandler(location + name + extension);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            //Log that the log has been created for reference. 
            logger.info("Santos Log has been created.");

        } catch (SecurityException | IOException e) {
            //Print out that the log failed to create.
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Executes the command in a new thread and prints the results to the
     * Santos Logger log.
     * @param command The command to be executed.
     */
    public void executeCommand(String command) {
        /**
         * Creates a CommandThread to execute the command. The reason for
         * threading the commands is to allow simultaneous commands to
         * be ran at once and the program will not be held up by waiting
         * for a single command to be executed.
         */
        CommandThread cmd = new CommandThread(command, this, exitCode);
        /**
         * This is separate for security and thread stability reasons.
         */
        cmd.start();
    }
}
