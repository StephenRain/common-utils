package org.code.common.util.file;

import org.code.common.util.number.UniqueNumber;
import org.junit.Test;

public class FileUtilTest {

    @Test
    public void updateFileToAnother() {

        UniqueNumber uniqueNumber = new UniqueNumber(10000, new long[]{2L,1L,3L});
//        FileUtil.updateFileToAnother("D:\\文档\\58到家.txt",
//                "D:\\文档\\58到家_updated.txt", (s) -> {
//                    String uniqueNum = uniqueNumber.getUniqueNumStr();
//                    return uniqueNum + "\t" + s + "\r\n";
//                });
        FileUtil.updateFileToAnother("D:\\文档\\58到家_家政人员.txt",
                "D:\\文档\\58到家_家政人员_updated.txt", (s) -> {
                    String uniqueNum = uniqueNumber.getUniqueNumStr();
                    return uniqueNum + "\t" + s + "\r\n";
                });

    }
}