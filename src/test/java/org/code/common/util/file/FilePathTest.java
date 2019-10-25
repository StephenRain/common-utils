package org.code.common.util.file;

import org.junit.Test;

import static org.junit.Assert.*;

public class FilePathTest {

    @Test
    public void getCurrentPath() {

        String currentPath = FilePath.getCurrentPath(FilePath.class);
        System.out.println("currentPath = " + currentPath);
    }
}