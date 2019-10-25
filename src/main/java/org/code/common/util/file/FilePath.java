package org.code.common.util.file;

import java.io.File;

/**
 * @author yaotianchi
 * @date 2019/10/22
 */
public class FilePath {


    /**
     * 获取当前class父目录
     *
     * @param cls
     * @return 当前class父目录 URL
     */
    public static String getCurrentPath(Class<?> cls) {
        String path = cls.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.replaceFirst("file:/", "");
        path = path.replaceAll("!/", "");
        if (path.lastIndexOf(File.separator) >= 0) {
            path = path.substring(0, path.lastIndexOf(File.separator));
        }
        if (path.substring(0, 1).equalsIgnoreCase("/")) {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("window")) {
                path = path.substring(1);
            }
        }
        if (path.endsWith("jar") || path.endsWith("war")) {
            path = path.substring(0, path.lastIndexOf(File.separator) + 1);
        }
        return path;
    }
}
