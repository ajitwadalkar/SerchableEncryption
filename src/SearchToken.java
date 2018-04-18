import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchToken {

     public static void search()
    {
        List <String> list = FileReadWrite.ReadFile("index.txt");
        String token = FileReadWrite.ReadFile("token.txt").get(0);
        List<String> fileList = new ArrayList<String >();
        for(String s:list){
            String[] lineArray = s.split(" ");
            if(lineArray[0].equals(token))
            {
                for(int i=1;i<lineArray.length;i++){
                    fileList.add(lineArray[i]);
                }
            }
        }

        if(fileList.size()>0)
        {
            for (String fileName:fileList) System.out.print(fileName+" ");
            System.out.println();
            for (String fileName:fileList)
            {
                String  cipherText = FileReadWrite.ReadFile("ciphertextfiles/"+fileName).get(0);
                String decryptedText = EncDecFunction.decryptCBC(DatatypeConverter.parseHexBinary(cipherText),"skaes.txt");
                System.out.println(fileName+" "+decryptedText);
            }
        }
        else{
            System.out.println("No match found.");
        }
    }
}
