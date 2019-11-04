package appCore;

import dropBoxPackage.DropBoxCore;
import interfacePac.SpecInterface;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class appCoreTryTwo {

    private SpecInterface dropBoxObj;

    public static void main(String[] args) {
        new appCoreTryTwo().runApp();
    }

    private void runApp(){
        //
        dropBoxObj = new DropBoxCore();
        //
        Scanner s = new Scanner(System.in);
        String currPath;
        String username;
        String password;
        do{
            System.out.println("Enter root file name.");
            currPath = s.nextLine();
        }while (!dropBoxObj.create(currPath));

        do{
            System.out.println("Success. Now enter username.");
            username = s.nextLine();
        }while (username.length() == 0 || username.length() > 32);

        do{
            System.out.println("Now enter password.");
            password = s.nextLine();
        }while (!(password.length() < 32));

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            password = "";
            for(int i = 0; i < hash.length; i++){
                password += (char)hash[i];
            }
            password = password.replaceAll("\\n", "");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        dropBoxObj.logIn(username, password, currPath);

        System.out.println("Enter 'comm' to list all comands.");

        String consoleRead;

        while(true){

            consoleRead = s.nextLine();

            if(consoleRead.equals("comm")) {
                System.out.println("1 - logout \n2 - makeFile \n3 " +                          // Lists all functions
                        "- deleteFile \n4 - makeUser\n5 - givePermission");
            }
            if(consoleRead.equals("1")) {
                break;                                                                         // Basically stops the program
            }
            if(consoleRead.equals("4")) {
                System.out.println("Enter new username for new user.");
                consoleRead = s.nextLine();
                while(!dropBoxObj.createUser(consoleRead)){                                    // Loops until valid username is entered
                    System.out.println("User already exists, please choose another username.");
                    consoleRead = s.nextLine();
                }
            }
            if(consoleRead.equals("5")){
                System.out.println("Enter username of user whose credentials you want to change.");
                consoleRead = s.nextLine();
                dropBoxObj.changeUserCredentials(consoleRead);
            }


        }
    }
}
