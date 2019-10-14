package org.code.common.util.number;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author yaotianchi
 * @date 2019/10/14
 */
public class UniqueNumber {

    private final long[] randomSteps;

    private long nowNum;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");

    Random r = new Random();


    public UniqueNumber(long startNum, long[] randomSteps) {
        this.randomSteps = randomSteps;
        this.nowNum = startNum;
    }

    /**
     * 前面添加十位格式化后的时间字符串
     *
     * @return
     */
    public String getUniqueNumStr() {
        String time = dateFormat.format(new Date());
        nowNum += randomSteps[r.nextInt(randomSteps.length)];
        return time + nowNum;
    }


}
