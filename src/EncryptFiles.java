import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class EncryptFiles {
    public static void main(String[] args) throws Exception {
        EncFiles();
    }
    public static void EncFiles() throws Exception {

        String filesPath = SE.folderPath + "files/";
        File folder = new File(filesPath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File fnm : listOfFiles) {
                String fileData = FileReadWrite.ReadFile("files/" + fnm.getName()).get(0);
                byte[] encData = EncDecFunction.encryptCBC(fileData,"skaes.txt");
                FileReadWrite.WriteFile("ciphertextfiles/"+fnm.getName().replace("f","c"),DatatypeConverter.printHexBinary(encData));
            }
        }


    }
}
