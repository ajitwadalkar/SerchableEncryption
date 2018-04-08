import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Indexing {
    public static void GenerateEncryptedIndex() throws Exception {
        HashMap<String, ArrayList<String>> plainIndex = new HashMap<>();
        plainIndex = ReadFiles.readFiles("files/");
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
    }
    public static void printEncIndex(){
        List<String> list = FileReadWrite.ReadFile("index.txt");
        for(String s:list) System.out.println(s);
    }

    public static void printPlainIndex(){
        HashMap<String, ArrayList<String>> plainIndex = new HashMap<>();
        plainIndex = ReadFiles.readFiles("files/");
        for (String hashKey : plainIndex.keySet()) {
            System.out.print(hashKey);
            for (int i = 0; i < plainIndex.get(hashKey).size(); i++) {
                System.out.print(" " + plainIndex.get(hashKey).get(i));
            }
            System.out.println();
        }
    }
}
