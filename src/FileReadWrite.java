/*
* This class is implemented to read and write files to
* */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FileReadWrite {

    private static String folderPath;


    static  List<String>  ReadFile(String fileName)
    {
        folderPath = SE.folderPath;
        String filePath = folderPath+fileName;
        List<String> readData=null;

        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            readData = stream.collect(Collectors.toList());
        }
        catch (IOException e)
        {
            System.out.println("File does not exist.");
        }
        return readData;

    }

    static void WriteFile(String fileName, String DataToWrite)
    {
        folderPath = SE.folderPath;
        String filePath = folderPath+fileName;
        try
        {
            Path file = Paths.get(filePath);
            Files.write(file, DataToWrite.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
