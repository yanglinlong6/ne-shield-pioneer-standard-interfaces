package cn.com.glsx.stdinterface.common.constants;

/**
 * 公共的常量类
 */
public final class Constants {
    /**
     * 车辆价格临界值 100 万
     */
    public final static int VEHICLE_PRICE_LIMIT = Integer.MAX_VALUE;

    /**
     * 每次调用远程接口的尝试次数
     */
    public final static int TRY_COUNT = 3;

    /**
     * 每次尝试的等待时间
     */
    public final static int TRY_WAIT_TIME = 1000;

    /**
     * 时间格式
     */
    public final static String TIME_FORMAT = "HH:mm:ss";

    /**
     * 日期格式
     */
    public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 投保的公司 GL:新人保保单2020 本地处理
     */
    public final static String INSURANCE_COMPANY_GL = "GL";
    public final static String[] parsePatterns = new String[]{"yyyy-MM-dd", "yyyy/MM/dd"};
    // 特约长度
    public final static int SPE_BR_LEN = 166;
    /**
     * 保单的状态 0 等待投保
     */
    public static final int INSURANCE_WAIT_SEND_STATUS = 0;
    /**
     * 保单的状态 1 投保成功
     */
    public static final int INSURANCE_SUCCESS_SEND_STATUS = 1;
    /**
     * 保单的状态 2 投保失败
     */
    public static final int INSURANCE_FAIL_SEND_STATUS = 2;
    /**
     * 保单的状态 3 退保
     */
    public static final int INSURANCE_CANCEL_SEND_STATUS = 3;
    /**
     * 保单的状态 4 草稿
     */
    public static final int INSURANCE_DRAFT_STATUS = 4;
    /**
     * 保单的状态 5 待激活
     */
    public static final int INSURANCE_WAIT_ACTIVE_STATUS = 5;
    /**
     * 保单的状态 6 删除
     */
    public static final int INSURANCE_DELETE_STATUS = 6;
    public static String PICC_SUCC = "0";

    /**
     * xlsx 文件拓展名
     */
    public static final String XLSX_FILE_EXTENSION = "xlsx";

    /**
     * 断电报警
     **/
    public final static int GPS_ALARM_BLACKOUT = 1;
    /**
     * 光感报警 99
     */
    public static final int ALARM_REC_LIGHT = 99;

    /**
     * 位置有效性验证
     */
    public static class Verification {

        /**
         * 有源设备定位有效时间4h内;单位毫秒
         */
        public static final long WIRED_TIME = 14400000;

        /**
         * 无源源设备定位有效时间24h10min内;单位毫秒
         */
        public static final long WIRELESS_TIME = 87000000;

        /**
         * 设备定位状态 0有效定位
         */
        public static final int SN_VALID = 0;

        /**
         * 设备定位状态 1无效定位
         */
        public static final int SN_INVALID = 1;
    }

    /**
     * 设备有源无源状态
     */
    public static class DeviceSource {

        /**
         * 有源设备
         */
        public static final int WIRED = 1;

        /**
         * 无源设备
         */
        public static final int WIRELESS = 0;

    }

    /**
     * 设备车辆行驶状态
     */
    public static class DeviceOnline {

        /**
         * 离线时间：三天（单位毫秒)
         */
        public static final long TIME_TWENTY_HOURS = 259200000;

        /**
         * 离线时间：六分钟;单位毫秒
         */
        public static final long TIME_SIX_MINITES = 360000;

        /**
         * 行驶
         */
        public static final int DRIVING = 0;

        /**
         * 静止
         */
        public static final int STANDSTILL = 1;

        /**
         * 离线
         */
        public static final int OFFLINE = 2;

        /**
         * 在线
         */
        public static final int ONLINE = 3;

    }

    public static class DeviceActived {

        /**
         * 设备未激活（0=未激活 1=激活）
         */
        public static final int UN_ACTIVED = 0;

        /**
         * 设备已激活（0=未激活 1=激活）
         */
        public static final int ACTIVED = 1;
    }

    public static class LocationType {

        /**
         * 定位类型(0、GPS,1、基站)
         */
        public static final int GPS = 0;

        /**
         * 定位类型(0、GPS,1、基站)
         */
        public static final int BS = 1;
    }

    /**
     * 厂商ID-博实结
     */
    public static final String BSJ_FACTORY_ID = "BSJ";

    /**
     * 响应状态
     */
    public static final int STATUS_RESPONSE = 2;


    /**
     * 生效状态
     */
    public static final int STATUS_ABLE = 1;


    /**
     * 失效状态
     */
    public static final int STATUS_UNABLE = 0;


    /**
     * 发送中
     */
    public static final int STATUS_RESPONSE_SENDING = 1;

    /**
     * 永久有效
     */
    public static final int FREQ_DURATION_PERMANENT = -1;
}
