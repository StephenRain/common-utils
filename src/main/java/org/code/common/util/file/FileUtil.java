package org.code.common.util.file;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.function.Function;

/**
 * @author yaotianchi
 * @date 2019/10/14
 */
public class FileUtil {


    @SneakyThrows
    public static void updateFileToAnother(String originFilePath, String targetFilePath, Function<String,String> lineProcess){
        List<String> allLines = Files.readAllLines(Paths.get(originFilePath));
        for (String line : allLines) {
            String updatedStr = lineProcess.apply(line);
            Files.write(Paths.get(targetFilePath),updatedStr.getBytes(),
                    StandardOpenOption.WRITE,StandardOpenOption.APPEND,StandardOpenOption.CREATE);
        }

    }


}
