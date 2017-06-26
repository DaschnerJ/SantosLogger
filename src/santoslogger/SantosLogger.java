/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santoslogger;

import santoslogger.gui.Log;

/**
 *
 * @author justdasc
 */
public class SantosLogger {
    
    /**
     * We create a log here so Santos Logger can log itself
     * if needed.
     */
    private final Log l = new Log("logger_log");
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /**
         * This is empty because we only need to create instantaneous classes
         * from this jar.
         */
    }
    
}
