import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SE {
    //Initializing the Scanner
    private static final Scanner scanner = new Scanner(System.in);
    private static int consoleLength;
    static String folderPath;


    //Main class
    public static void main(String[] args) throws Exception {


        File f = new File("./data/index.txt");
        if (f.exists() && !f.isDirectory()) {
            folderPath = "./data/";
        } else {
            folderPath = "../data/";
        }

        try {
            consoleLength = Integer.parseInt(args[0]);
        } catch (Exception e) {
            consoleLength = 150;
        }


        int option;
        do {
            //Choosing the function
            System.out.println("\nFollowing are the functionalities in the code, choose anyone:\n" +
                    "1.Generate Keys\n" +
                    "2.Generate Inverted Index and Encrypt Files\n" +
                    "6.Exit\n" +
                    "Please choose the value in between 1,2,3,4,5 or 6 ");

            int funcSel = promptChoice(6);

            subMain(funcSel);

            System.out.println("\nDo you want to re-run the program?");
            System.out.println("Please choose from the following.\n1. Yes\n2. No");
            option = promptChoice(2);
        } while (option == 1);

        System.out.println("You chose to terminate the program. Thank you.");
    }


    //Print a line on console
    private static void printLine() {
        for (int i = 1; i < consoleLength; i++) {
            System.out.print("-");
        }
        System.out.println();
    }


    //Function to get input from the user to select the functions.
    private static int promptChoice(int max) {
        for (; ; ) {
            if (!scanner.hasNextInt()) {
                System.out.println("You must enter a number");
            } else {
                int option = scanner.nextInt();
                if (option >= 1 && option <= max) {
                    return option;
                } else {
                    System.out.println("You must enter a number between 1 and " + max);
                }
            }
            scanner.nextLine();
        }
    }

    private static void subMain(int option) throws Exception {
        long duration, endTime, startTime;
        //Calling Generate Keys function
        if (option == 1) {
            System.out.println();
            printLine();
            System.out.println("Selected Function is Generate Keys.\n Please enter the value of lambda, suggested values:128,192,256");
            int lambda = promptChoice(256);
            if(lambda%64 ==0) {
                startTime = System.nanoTime();
                String aesKey, prfKey;
                aesKey = EncDecFunction.keyGen(lambda);
                prfKey = EncDecFunction.keyGen(lambda);
                FileReadWrite.WriteFile("skaes.txt", aesKey);
                FileReadWrite.WriteFile("skprf.txt", prfKey);
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                double seconds = (double) duration / 1000000000.0;
                System.out.println("The function took " + seconds + " seconds to execute.");
                System.out.println("AES Key: " + aesKey);
                System.out.println("PRF Key: " + prfKey);
                printLine();
            }
            else {
                System.out.println("Cannot generate keys with given lambda value.");
            }
        }
        //Generate index and encrypt Files
        if (option == 2) {
            System.out.println();
            printLine();
            System.out.println("Selected Function is Generate Inverted Index and Encrypt Files");
                startTime = System.nanoTime();
                String aesKey, prfKey;
                HashMap<String, ArrayList<String>> plainIndex = new HashMap<>();
                plainIndex = ReadFiles.readFiles();
                String dataToWrite="";
                for (String keyWords:plainIndex.keySet())
                {
                    String  hashedIndex = DatatypeConverter.printHexBinary(EncDecFunction.encryptECB(keyWords,"skprf.txt"));
                    for (int i = 0; i < plainIndex.get(keyWords).size(); i++) {
                        hashedIndex = hashedIndex + " " + plainIndex.get(keyWords).get(i).replace("f","c");
                    }
                    dataToWrite = dataToWrite + hashedIndex+"\n";
                }
                FileReadWrite.WriteFile("index.txt",dataToWrite);
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                double seconds = (double) duration / 1000000000.0;
                System.out.println("The function took " + seconds + " seconds to execute.");
                printLine();

        }
/*
        //Some function
        if (option == 3) {
            System.out.println();
            printLine();
            System.out.println("Selected Function is Generate Inverted Index and Encrypt Files");
            startTime = System.nanoTime();
            String aesKey, prfKey;

            endTime = System.nanoTime();
            duration = (endTime - startTime);
            double seconds = (double) duration / 1000000000.0;
            System.out.println("The function took " + seconds + " seconds to execute.");
            printLine();

        }
*/
        //Exit the Program
        else if (option == 6) {
            System.out.println("Terminating the program.");
            System.exit(0);
        }
    }
}