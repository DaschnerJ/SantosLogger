/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santoslogger.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author justdasc
 */
public class CommandThread implements Runnable{
 
    //Option to display command exit codes or not.
    private Boolean exitCode = false;
    /**
     * Commands are run as separate threads. This allows multiple
     * commands to be run simultaneously.
     */
    private Thread t;
    //The actual command being ran in the thread.
    private final String command;
    //This is the log passed off to the thread.
    private final Log l;
    
    /**
     * Sets the initial requirements of the command thread so it may be ran.
     * Note there is no thread killing or cancel because the commands are
     * expected to finish by themselves and automatically close and clean up.
     * @param command The command to execute.
     * @param l The logger to log the results to (including errors).
     */
    public CommandThread(String command, Log l)
    {
        this.command = command;
        this.l = l;
    }
    /**
     * Sets the initial requirements of the command thread so it may be ran.
     * @param command The command to execute.
     * @param l The logger to log the results to (including errors).
     * @param exitCode Extra option to print out the exit code result.
     */
    public CommandThread(String command, Log l, Boolean exitCode)
    {
        this.command = command;
        this.l = l;
        this.exitCode = exitCode;
    }

    /**
     * To actually execute the thread and run the command.
     * This is separated for security and thread stability reasons.
     * Do not put this in the CommandThread constructor.
     */
    @Override
    public void run() {
        runCommand();
    }
    
    /**
     * This starts the new thread so it may run the command.
     */
    public void start()
    {
        if(t == null)
        {
            t = new Thread(this);
            t.start();
        }
    }
    
    /**
     * This method executes the command.
     */
    public void runCommand()
    {
        try {
            /**
             * We create a new command console process.
             */
            Process p = Runtime.getRuntime().exec(command);
            /**
             * Then we create a buffered reader in order receive information
             * back from the console.
             */
            BufferedReader br;
            /**
             * We try to create an input stream however there may be exceptions
             * within the console that prevent us from accessing the console.
             * So if the command fails the command will not be ran.
             */
            try (InputStream is = p.getInputStream()) {
                //Creates a new reader from the results from the console.
                br = new BufferedReader(new InputStreamReader(is));
                //Each line outputted by the console.
                String line;
                //As long as there is a next line to read, keep reading.
                while ((line = br.readLine()) != null) {
                    //Add the results to the log.
                    l.addInfo(line);
                }   
                /**
                 * We wait for the process to finish. Even if the console is
                 * no longer outputting information, the process may still
                 * be connected and being used by the system so we do not
                 * want to kill the process early when the thread finishes
                 * grabbing the input so we must wait.
                 */
                p.waitFor();
                //If we are interested in the exit codes, print the exit code now.
                if(exitCode)
                    l.addInfo("Command exited with code: " + String.valueOf(p.exitValue()) + ".");
                /**
                 * If we reached here then there is no other command to be ran
                 * and we can destroy the thread to ensure the thread
                 * gets cleaned up by Java Garbage Collection.
                 */
                if(p.isAlive())
                {
                    p.destroy();
                }
                /**
                 * We close the stream as well incase the stream doesn't
                 * get destroyed as well.
                 */
                is.close();
            }
            /**
             * We close the stream as well incase the stream doesn't
             * get destroyed as well.
             */
            br.close();  
        }
        catch (IOException | InterruptedException e) {
            /**
             * If one of the previously mentioned exceptions do happen, we 
             * log the exceptions with the logger provided.
             */
            l.addSevere(e.getMessage());
        }
    }
    
}
