/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santoslogger.gui;

import java.io.File;
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

    //
    private String location;
    private String name;
    private Logger logger;
    private final JFrame g;
    private Boolean exitCode = false;
    
    private final FileManager f = new FileManager();

    public Log(String name) {
        this.location = f.GetExecutionPath() +"logs\\";
        this.name = name;
        
        createLog();
        g = new GUI(this);
        g.setTitle(name);
    }
    
    public Log(String name, Boolean exitCode) {
        this.location = f.GetExecutionPath() +"logs\\";
        this.name = name;
        this.exitCode = exitCode;
        
        createLog();
        g = new GUI(this);
        g.setTitle(name);
    }
    
    public Log(String name, String location) {
        this.location = location;
        this.name = name;
        
        createLog();
        g = new GUI(this);
        g.setTitle(name);
    }
    
    public Log(String name, String location, Boolean exitCode)
    {
        this.location = location;
        this.name = name;
        this.exitCode = exitCode;
        
        createLog();
        g = new GUI(this);
        g.setTitle(name);
    }
    
    public void toggleExitCode()
    {
        exitCode = !exitCode;
        if(exitCode)
            addInfo("Now displaying command exit codes.");
        else
            addInfo("Command exit codes will no longer be displayed.");
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeLocaiton(String location) {
        this.location = location;
    }

    public void addInfo(String text) {
        logger.info(text);
        if (GUI.console.getText().equals("") || GUI.console == null) {
            GUI.console.setText(text);
        } else {
            GUI.console.setText(GUI.console.getText() + "\n" + text);
        }
    }

    public void addWarning(String text) {
        logger.warning(text);
        if (GUI.console.getText().equals("") || GUI.console == null) {
            GUI.console.setText(text);
        } else {
            GUI.console.setText(GUI.console.getText() + "\n" + text);
        }
    }

    public void addSevere(String text) {
        logger.severe(text);
        if (GUI.console.getText().equals("") || GUI.console == null) {
            GUI.console.setText(text);
        } else {
            GUI.console.setText(GUI.console.getText() + "\n" + text);
        }
    }

    public void addFine(String text) {
        logger.fine(text);
        if (GUI.console.getText().equals("") || GUI.console == null) {
            GUI.console.setText(text);
        } else {
            GUI.console.setText(GUI.console.getText() + "\n" + text);
        }
    }

    public void addFinest(String text) {
        logger.finest(text);
        if (GUI.console.getText().equals("") || GUI.console == null) {
            GUI.console.setText(text);
        } else {
            GUI.console.setText(GUI.console.getText() + "\n" + text);
        }
    }

    public void showLog() {
        if (g.isVisible()) {
            g.setVisible(false);
        } else {
            g.setVisible(true);
        }
    }

    private void createLog() {
        FileHandler fh;
        logger = Logger.getLogger(name);
        try {
            f.createDirectory(location);
            f.createFile(location + name + extension);
            File f = new File(location + name + extension);
            // This block configure the logger with handler and formatter  
            fh = new FileHandler(location + name + extension);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages  
            logger.info("Santos Log has been created.");

        } catch (SecurityException | IOException e) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void executeCommand(String command) {
        CommandThread cmd = new CommandThread(command, this, exitCode);
        cmd.start();
    }
}
