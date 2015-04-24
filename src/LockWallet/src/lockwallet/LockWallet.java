/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lockwallet;

import external.PasswordField;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giovanni
 */
public class LockWallet {

    public static PwdDB database;

    public static void Banner() {
        System.out.println();
        System.out.println("     ******************************************************");
        System.out.println("     *                                                    *");
        System.out.println("     *            LOCKWALLET 0.1 (LENNYNERO)              *");
        System.out.println("     *                        BY                          *");
        System.out.println("     *          GIOVANNI SANTOSTEFANO @LCYBERSPAZIO       *");
        System.out.println("     *                                                    *");
        System.out.println("     ******************************************************");

        System.out.println("");
    }

    //work on password database
    public static void DbWorking(String dbname) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int choice = 0;
        String enckey;

        enckey = new String(PasswordField.getPassword(System.in, "ENTER ENCRYPTION KEY: "));
        /*OLD PASSWORD INSERTION UNSECURE
         System.out.print("ENTER ENCRYPTION KEY: ");
         enckey = br.readLine();*/
        for (int i = 0; i < 2000; i++) {
            System.out.println();
        }

        if (dbname == null) {
            return;
        }

        while (true) {
            System.out.println("DATABASE: " + dbname);
            System.out.println();
            System.out.println("1. INSERT ITEM");
            System.out.println("2. DELETE ITEM");
            System.out.println("3. QUERY BY ID");
            System.out.println("4. PRINT DATABASE");
            System.out.println("5. EXIT");
            System.out.print(": ");

            try {
                choice = Integer.parseInt(br.readLine());
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
            }

            if (choice == 1) { //insert a new item

                PwdItem item = new PwdItem();
                String input;
                System.out.println("\n*** NEW PASSWORD ***");

                System.out.print("DOMAIN [EG. GMAIL]: ");
                input = br.readLine();
                item.setDomain(input);

                System.out.print("USERNAME: ");
                input = br.readLine();
                item.setUsername(input);

                //System.out.print("PASSWORD: ");
                input = new String(PasswordField.getPassword(System.in, "PASSWORD: "));
                item.setPassword(input);

                System.out.print("COMMENT: ");
                input = br.readLine();
                item.setComment(input);

                /*hide password
                 for (int i = 0; i < 2000; i++) {
                 System.out.println();
                 }*/
                System.out.println();
                System.out.println("******************************************************");
                System.out.println("DOMAIN: " + item.getDomain());
                System.out.println("USERNAME: " + item.getUsername());
                System.out.println("PASSWORD: " + item.getPasswordHint());
                System.out.println("COMMENT: " + item.getComment());
                System.out.println("******************************************************");
                System.out.println();
                System.out.print("IS THIS CORRECT? ENTER 1 TO CONTINUE: ");
                try {
                    int command = Integer.parseInt(br.readLine());
                    if (command == 1) { //put the password into the database
                        item.EncryptPassword(enckey);
                        database.InsertPWD(item);
                        System.out.println("\nITEM ADDED. PRESS ENTER TO CONTINUE");
                        br.readLine();
                    }
                } catch (NumberFormatException nfe) {
                    System.err.println("Invalid Format!");
                }

            } else if (choice == 2) { //delete item by id
                int delid = -1;
                System.out.print("ID ITEM TO DELETE: ");
                try {
                    delid = Integer.parseInt(br.readLine());
                    System.out.println("\nDELETED. PRESS ENTER TO CONTINUE");
                    br.readLine();
                } catch (NumberFormatException nfe) {
                    System.err.println("Invalid Format!");
                }
                database.DeletePWD(delid);

            } else if (choice == 3) { //query database by id
                int queryid = -1;
                System.out.print("ID OF THE ITEM: ");
                try {
                    queryid = Integer.parseInt(br.readLine());
                } catch (NumberFormatException nfe) {
                    System.err.println("Invalid Format!");
                }

                //get the item and, if exists print info and
                //give the user the possibility to put the password in
                //the clipboard
                PwdItem item = database.GetItem(queryid);
                if (item != null) {
                    item.DecryptPassword(enckey);
                    System.out.println();
                    System.out.println("******************************************************");
                    System.out.println("ID: " + item.getId());
                    System.out.println("DOMAIN: " + item.getDomain());
                    System.out.println("USERNAME: " + item.getUsername());
                    System.out.println("COMMENT: " + item.getComment());
                    System.out.println("******************************************************");
                    System.out.println();
                    System.out.println("1. COPY PASSWORD IN THE CLIPBOARD");
                    System.out.println("2. SHOW PASSWORD HINT");
                    System.out.println("3. EXIT");
                    System.out.print(": ");

                    try {
                        int command = Integer.parseInt(br.readLine());
                        if (command == 1) {
                            StringSelection stringSelection = new StringSelection(item.getPassword());
                            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clpbrd.setContents(stringSelection, null);
                            System.out.println("\nPASSWORD IN CLIPBOARD. PRESS ENTER TO CONTINUE");
                            br.readLine();
                        } else if (command == 2) {
                            System.out.println("PASSWORD: " + item.getPasswordHint());
                            System.out.println("\nPRESS ENTER TO CONTINUE");
                            br.readLine();
                        }
                    } catch (NumberFormatException nfe) {
                        System.err.println("Invalid Format!");
                    }
                }

            } else if (choice == 4) { //print entire database

                List<PwdItem> ilist = database.GetItems();

                for (int i = 0; i < ilist.size(); i++) {
                    PwdItem item = ilist.get(i);
                    item.DecryptPassword(enckey);
                    System.out.println();
                    System.out.println("******************************************************");
                    System.out.println("ID: " + item.getId());
                    System.out.println("DOMAIN: " + item.getDomain());
                    System.out.println("USERNAME: " + item.getUsername());
                    System.out.println("COMMENT: " + item.getComment());
                    System.out.println("******************************************************");
                    System.out.println();

                    if (i + 1 % 4 == 0) {
                        System.out.println("\nPRESS ENTER TO CONTINUE");
                        br.readLine();
                    }
                }

                System.out.println("\nPRESS ENTER TO CONTINUE");
                br.readLine();

            } else {
                return;
            }
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int choice = 0;
        database = new PwdDB();

        Banner();
        System.out.print("\nDATABASE FILENAME [even new one]: ");
        String dbname = br.readLine();
        database.OpenDB(dbname);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        while (true) {
            //hello
            Banner();
            System.out.println("\n\n\n\n\n\n\n\n");

            System.out.println("1. INITIALIZE DATABASE");
            System.out.println("2. WORK ON PASSWORD DATABASE");
            System.out.println("3. CLEAR CLIPBOARD AND EXIT");
            System.out.println("4. EXIT");
            System.out.print(": ");
            try {
                choice = Integer.parseInt(br.readLine());
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
            }

            if (choice == 1) {
                database.CreateDB();
                System.out.println("DATABASE INITIALIZED");
                System.out.print("PRESS ANY KEY TO CONTINUE ");
                br.readLine();
            } else if (choice == 2) {
                DbWorking(dbname);
            } else if (choice == 3) {
                StringSelection stringSelection = new StringSelection("e il naufragar m'è dolce in questo mare");
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
                System.out.println("e il naufragar m'è dolce in questo mare");
                database.CloseDB();
                System.exit(0);
            } else {
                database.CloseDB();
                System.exit(0);
            }

        }

        //PwdItem item = new PwdItem("libero.it", "gio", "q", "lol");
        //System.out.println(item.getPasswordHint() + "\n");
    }

}
