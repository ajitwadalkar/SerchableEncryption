import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ReadFiles {
    static String folderPath;

    public static HashMap<String, ArrayList<String>> readFiles(){
        HashMap<String, ArrayList<String>> fileIndex = new HashMap<>();

        String filesPath = SE.folderPath + "files/";
        File folder = new File(filesPath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File fnm : listOfFiles) {
                String[] fileData = FileReadWrite.ReadFile("files/" + fnm.getName()).split(" ");
                for (String keywords : fileData) {
                    if (fileIndex.containsKey(keywords)) {
                        fileIndex.get(keywords).add(fnm.getName());
                    } else {
                        fileIndex.put(keywords, new ArrayList(Arrays.asList(new String[]{fnm.getName()})));
                    }
                }
            }

         /*
                //To print the files
                for (String hashKey : fileIndex.keySet()) {
                System.out.print(hashKey);
                for (int i = 0; i < fileIndex.get(hashKey).size(); i++) {
                    System.out.print(" " + fileIndex.get(hashKey).get(i));
                }
                System.out.println();
            }
*/
        }

        return fileIndex;
    }
}
