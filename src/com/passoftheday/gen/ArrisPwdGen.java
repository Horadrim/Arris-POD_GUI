/*Created by Plamen Donev
 *The code below is modification of Raúl Pedro Santos` (a.k.a. borfast)
 *code, which could be obtained from here:
 *
 *	 github.com/borfast/arrispwgen
 *
 *Licensed to the Apache Software Foundation (ASF) under one
 *or more contributor license agreements.  See the NOTICE file
 *distributed with this work for additional information
 *regarding copyright ownership.  The ASF licenses this file
 *to you under the Apache License, Version 2.0 (the
 *"License"); you may not use this file except in compliance
 *with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *   
 *Unless required by applicable law or agreed to in writing,
 *software distributed under the License is distributed on an
 *"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *KIND, either express or implied.  See the License for the
 *specific language governing permissions and limitations
 *under the License.
*/
package com.passoftheday.gen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.passoftheday.gui.Dialogs;


public class ArrisPwdGen {
    protected static final int[][] table1 = {
            {15, 15, 24, 20, 24},
            {13, 14, 27, 32, 10},
            {29, 14, 32, 29, 24},
            {23, 32, 24, 29, 29},
            {14, 29, 10, 21, 29},
            {34, 27, 16, 23, 30},
            {14, 22, 24, 17, 13}
            };

    private static final int[][] table2 = {
            {0, 1, 2, 9, 3, 4, 5, 6, 7, 8},
            {1, 4, 3, 9, 0, 7, 8, 2, 5, 6},
            {7, 2, 8, 9, 4, 1, 6, 0, 3, 5},
            {6, 3, 5, 9, 1, 8, 2, 7, 4, 0},
            {4, 7, 0, 9, 5, 2, 3, 1, 8, 6},
            {5, 6, 1, 9, 8, 0, 4, 3, 2, 7}
    };

    private static final char[] alphanum = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    
    public static HashMap<String, String> GenArrisPasswords(Date startDate, Date endDate, String seed) {
        if (startDate == null) {
            startDate = new Date();
        }
        if (endDate == null) {
            endDate = new Date (startDate.getTime());
        }
        int password_count = 0;
        long one_day_in_milliseconds = 24 * 60 * 60 * 1000; // 1 day in milliseconds

        // Check how many passwords we're going to generate.
        password_count = (int)Math.ceil((endDate.getTime() - startDate.getTime() + 1) / one_day_in_milliseconds) + 1;

        // See if we have a valid number of passwords
        if ((password_count < 1) || (password_count > 365)) {
                Dialogs.errMessage("err1");
                
        }

	String seedeight = seed.substring(0, 8);
	String seedten   = seed;

        int[] list1 = new int[8];
        int[] list2 = new int[8];
        int[] list3 = new int[10];
        int[] list4 = new int[10];
        int[] list5 = new int[10];
        int year  = 0;
        int month = 0;
        int day_of_month = 0;
        int day_of_week  = 0;
        int i;
        long iter;
        Date date = null;

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        HashMap<String, String> password_list = new HashMap<String, String>();
        for (iter = 0; iter < password_count; iter++) {
                // For each iteration advance the date one day
                date = new Date(startDate.getTime() + (iter * one_day_in_milliseconds));

                // Last two digits of the year
                sdf = new SimpleDateFormat("yy");
                year = Integer.parseInt(sdf.format(date), 10);

                // Number of the month (no leading zero; January == 0)
                sdf = new SimpleDateFormat("M");
                month = Integer.parseInt(sdf.format(date), 10);

                // Day of the month
                sdf = new SimpleDateFormat("d");
                day_of_month = Integer.parseInt(sdf.format(date), 10);

                // Day of the week. Normally 0 would be Sunday but we need it to be Monday.
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
                if (day_of_week < 0) {
                        day_of_week = 6;
                }

                // Now build the lists that will be used by each other.

                // list1
                for (i = 0; i <= 4; i++) {
                        list1[i] = table1[day_of_week][i];
                }
                list1[5] = day_of_month;
                if (((year + month) - day_of_month) < 0) {
                        list1[6] = (((year + month) - day_of_month) + 36) % 36;
                } else {
                        list1[6] = ((year + month) - day_of_month) % 36;
                }
                list1[7] = (((3 + ((year + month) % 12)) * day_of_month) % 37) % 36;

                // list2
                for (i = 0; i <= 7; i++) {
                        list2[i] = (seedeight.substring(i, i+1).codePointAt(0)) % 36;
                }

                // list3
                for (i = 0; i <= 7; i++) {
                        list3[i] = (((list1[i] + list2[i])) % 36);
                }
                list3[8] = (list3[0] + list3[1] + list3[2] + list3[3] + list3[4] +
                list3[5] + list3[6] + list3[7]) % 36;
                int num8 = (list3[8] % 6);
                list3[9] = (int)Math.round(Math.pow(num8, 2));

                // list4
                for (i = 0; i <= 9; i++) {
                        list4[i] = list3[table2[num8][i]];
                }

                // list5
                for (i = 0; i <= 9; i++) {
                        list5[i] = ((seedten.substring(i, i+1).codePointAt(0)) + list4[i]) % 36;
                }


                // Finally, build the password of the day.
                String password_of_the_day = new String();
                int len = list5.length;
                for (i = 0; i < len; i++) {
                        password_of_the_day += alphanum[list5[i]];
                }
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                password_list.put(sdf.format(date), password_of_the_day);
        }

        return password_list;
    }

 

}
