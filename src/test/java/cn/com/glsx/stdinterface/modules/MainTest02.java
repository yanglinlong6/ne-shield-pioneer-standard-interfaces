package cn.com.glsx.stdinterface.modules;

import cn.com.glsx.stdinterface.modules.util.DateUtils;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class MainTest02 {

    public static void main(String[] args) {
//        boolean b = compareTime(DateUtil.formatDate(new Date()), new Date(), DateUtil.formatDate(new Date()));
//        System.out.println("b = " + b);

        if ("y".equalsIgnoreCase("Y")) {
            System.out.println("b ===");
        }
    }

    private static boolean compareTime(String startTime, Date alarmTime, String endTime) {
        Date statDate = DateUtil.parseDateTime(startTime + " 00:00:00");
        System.out.println("statDate = " + statDate);
        Date endDate = DateUtil.parseDateTime(endTime + " 23:59:59");
        System.out.println("endDate = " + endDate);
        if (DateUtils.compareDate(statDate, endDate)) {
            endDate = DateUtils.getOffsetDayDate(endDate, -1);
        }
        return DateUtils.compareDate(alarmTime, statDate) && DateUtils.compareDate(endDate, alarmTime);
    }
}
