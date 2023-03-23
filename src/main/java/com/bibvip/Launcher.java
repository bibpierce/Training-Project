package com.bibvip;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Launcher {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Actions ins = new Actions();
    static Actions view = new Actions();
    static Actions del = new Actions();
    static Actions exp = new Actions();
    static Actions se = new Actions();
    static MainMessage mm = new MainMessage();

    public void launchProgram() throws Exception {
        String option;
        do {
            mm.getMessage();
            option = br.readLine();
            System.out.println("=============================================================");


            switch (option.toUpperCase()) {
                case "A":
                    view.viewUser();
                    break;

                case "B":
                    ins.insertData();
                    break;

                case "C":
                    ins.selectUser();
                    break;

                case "D":
                    del.deleteData();
                    break;

                case "E":
                    exp.exportToExcel();
                    break;

                case "F":
                    se.singleExport();
                    break;

                case "G":
                    System.out.println("**************************THANK YOU**************************");
                    System.exit(2);
                    break;

                default:
                    System.out.println("Invalid Input! Please enter again");
                    break;
            }

        } while (!option.equals("F"));

    }
}
