/*Created by Plamen Donev
 *Licensed to the Apache Software Foundation (ASF) under one
 *or more contributor license agreements.  See the NOTICE file
 *distributed with this work for additional information
 *regarding copyright ownership.  The ASF licenses this file
 *to you under the Apache License, Version 2.0 (the
 *"License"); you may not use this file except in compliance
 *with the License.  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *Unless required by applicable law or agreed to in writing,
 *software distributed under the License is distributed on an
 *"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *KIND, either express or implied.  See the License for the
 *specific language governing permissions and limitations
 *under the License.
*/
package com.passoftheday.gui;

import javax.swing.JOptionPane;

/**
 *
 * @author horadrim
 */
public class Dialogs {
    
    public static String Message="";
    
    public static String getMessage (String args){
           
           try {
           switch (args) {
            case "err1":
                Message = "Invalid dates. I need dates that span a year at most.";
                break;
            case "err2":
                Message = "Seed input can not be empty!";
                break;
            case "err3":
                Message = "Invalid seed length.";
                break;
            case "looks":
                Message = "Substance failed to initialize";
                break;
            }
           } catch (IllegalArgumentException e){
               Message = e.toString();
           }
           return Message;
    }
    
    
    public static void errMessage(String args){
        String err = getMessage(args);
        JOptionPane.showMessageDialog(null, err, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
     
    public static void errException(String args){
        JOptionPane.showMessageDialog(null, args, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void errException(String arg1, String arg2){
        String err = getMessage(arg2);
        JOptionPane.showMessageDialog(null, arg1+err, "Error", JOptionPane.ERROR_MESSAGE);
    }
     
    public static int infoMessage(String args){
        String inf = getMessage(args);
        int res = 0;
        int response = JOptionPane.showConfirmDialog(null, inf, "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION){
            res = 1;
        }
        if (response == JOptionPane.NO_OPTION){
            res = 0;
        }
        return res;
    }
     
}
