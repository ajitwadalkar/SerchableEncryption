import javax.xml.bind.DatatypeConverter;
import java.io.File;
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
                    "2.Print Inverted Index\n" +
                    "3.Generate Encrypted Inverted Index and Encrypt Files\n" +
                    "4.Token Generation\n" +
                    "5.Search token\n" +
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
        //Printing plaintext Inverted Index
        if (option == 2) {
            System.out.println();
            printLine();
            System.out.println("Invertedindex for plaintext:");
            Indexing.printPlainIndex();
            printLine();
        }

        //Generate index and encrypt Files
        if (option == 3) {
            System.out.println();
            printLine();
            System.out.println("Selected Function is Generate Encrypted Inverted Index and Encrypt Files");
                startTime = System.nanoTime();
                Indexing.GenerateEncryptedIndex();
                EncryptFiles.EncFiles();
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                double seconds = (double) duration / 1000000000.0;
                System.out.println("The function took " + seconds + " seconds to execute.");
                System.out.println("Generated Inverted Index is:");
                Indexing.printEncIndex();

            printLine();

        }

        //token generation function
        //Some function
        if (option == 4) {
            System.out.println();
            printLine();
            System.out.println("Selected Function is Token Generation Function.\nPlease enter word you want to search.");
            String tokenWord = scanner.next();
            startTime = System.nanoTime();
            String  hashedToken = DatatypeConverter.printHexBinary(EncDecFunction.encryptECB(tokenWord,"skprf.txt"));
            System.out.println("Saved token is: "+hashedToken);
            FileReadWrite.WriteFile("token.txt",hashedToken);
            endTime = System.nanoTime();
            duration = (endTime - startTime);
            double seconds = (double) duration / 1000000000.0;
            System.out.println("The function took " + seconds + " seconds to execute.");
            printLine();
        }

        //Searchfunction
        if (option == 5) {
            System.out.println();
            printLine();
            System.out.println("Selected Function is Search Token");
            startTime = System.nanoTime();
            SearchToken.search();
            endTime = System.nanoTime();
            duration = (endTime - startTime);
            double seconds = (double) duration / 1000000000.0;
            System.out.println("The function took " + seconds + " seconds to execute.");
            printLine();

        }

        //Exit the Program
        else if (option == 6) {
            System.out.println("Terminating the program.");
            System.exit(0);
        }
    }
}