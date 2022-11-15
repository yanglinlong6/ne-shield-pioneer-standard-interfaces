package cn.com.glsx.stdinterface.modules.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期处理
 *
 * @author yll
 */
@Slf4j
public class DateUtils extends com.glsx.plat.common.utils.DateUtils {

    public final static String[] pattern = {"yyyy-MM-dd HH:mm:ss"};

    public final static long defaultTimeDiff = -1l;

    public static String pattern19 = "yyyy-MM-dd HH:mm:ss";
    public static String pattern14 = "yyyyMMddHHmmss";
    public static String pattern10 = "yyyy-MM-dd";
    public static String pattern8 = "yyyyMMdd";
    public static String pattern6 = "HHmmss";

    private static final Map<String, ThreadLocal<SimpleDateFormat>> pool
            = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static final Object lock = new Object();

    public static SimpleDateFormat getDateFormat(String pattern) {
        ThreadLocal<SimpleDateFormat> tl = pool.get(pattern);
        if (tl == null) {
            synchronized (lock) {
                tl = pool.get(pattern);
                if (tl == null) {
                    final String p = pattern;
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(p);
                        }
                    };
                    pool.put(p, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * 时间字符串转日期
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date toDate(String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            return getDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            log.error("dateUtil parseException", e);
        }
        return null;
    }

    /**
     * 日期转时间字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String toString(Date date, String pattern) {
        return getDateFormat(pattern).format(date);
    }

    public static String nowString(String pattern) {
        return getDateFormat(pattern).format(new Date());
    }

    /**
     * 格式化传的时间为 X天X小时X分
     *
     * @param durationTime 时间戳
     * @return 天 小时 分
     */
    public static String formatTimeInSec2(long durationTime) {
        if (durationTime > 0) {
            int s = (int) durationTime;
            int day = s / (3600 * 24);
            s = s % (3600 * 24);
            int hour = s / 3600;
            s = s % 3600;
            int min = s / 60;
            s = s % 60;
            int sec = s;
            return day + "天" + hour + "小时" + min + "分";
        } else {
            return "0";
        }
    }

    /**
     * 计算当前时间与指定时间的时间差
     *
     * @param time
     * @return
     * @author lidh
     */
    @SuppressWarnings("null")
    public static long getTimeDifference(String time) {
        if (StringUtils.isBlank(time)) {
            return defaultTimeDiff;
        }

        try {
            Date parseDate = DateUtils.parseDate(time, pattern);
            long difference = System.currentTimeMillis() - parseDate.getTime();
            return difference;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return defaultTimeDiff;
    }

    /**
     * 比较时间戳大小
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 时间1大于时间2 返回true
     */
    public static boolean compareDate(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return true;
        } else if (date1.getTime() < date2.getTime()) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）
     *
     * @param protoDate  原来的时间（java.util.Date）
     * @param dateOffset （向前移正数，向后移负数）
     * @return 时间（java.util.Date）
     */
    public static Date getOffsetDayDate(Date protoDate, int dateOffset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - dateOffset);
        return cal.getTime();
    }
}
