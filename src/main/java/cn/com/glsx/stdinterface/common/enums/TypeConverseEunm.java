package cn.com.glsx.stdinterface.common.enums;

/**
 * 参数转换枚举类
 *
 * @author Yanglinlong
 * @date 2021/12/20 14:36
 */
public class TypeConverseEunm {
    /**
     * 报警类型枚举类
     */
    public enum FenceAlarmTypeEnum {
        DRIVE_IN(1, "驶入报警"),
        DRIVE_OUT(2, "驶出报警"),
        DRIVE_IN_OUT(3, "驶入驶出报警");

        private final Integer alarmType;
        private final String alarmTypeStr;

        FenceAlarmTypeEnum(Integer alarmType, String alarmTypeStr) {
            this.alarmType = alarmType;
            this.alarmTypeStr = alarmTypeStr;
        }

        public static String getStrByAlarmType(Integer alarmType) {
            for (FenceAlarmTypeEnum alarmTypeEnum : FenceAlarmTypeEnum.values()) {
                if (alarmTypeEnum.getAlarmType().equals(alarmType)) {
                    return alarmTypeEnum.getAlarmTypeStr();
                }
            }
            return null;
        }

        public Integer getAlarmType() {
            return alarmType;
        }

        public String getAlarmTypeStr() {
            return alarmTypeStr;
        }
    }

    /**
     * 报警类型枚举类
     */
    public enum TriggerTypeEnum {
        TRIGGER_IN(1, "驶入状态"),
        TRIGGER_OUT(2, "驶出状态");

        private final Integer triggerType;
        private final String triggerTypeStr;

        TriggerTypeEnum(Integer triggerType, String triggerTypeStr) {
            this.triggerType = triggerType;
            this.triggerTypeStr = triggerTypeStr;
        }

        public static String getStrByAlarmType(Integer alarmType) {
            for (TriggerTypeEnum triggerTypeEnum : TriggerTypeEnum.values()) {
                if (triggerTypeEnum.getTriggerType().equals(alarmType)) {
                    return triggerTypeEnum.getTriggerTypeStr();
                }
            }
            return null;
        }

        public Integer getTriggerType() {
            return triggerType;
        }

        public String getTriggerTypeStr() {
            return triggerTypeStr;
        }
    }

    /**
     * 是否需要安装类型枚举类
     */
    public enum NeedInstallTypeEnum {
        NEED_INSTALL("Y", "需要安装"),
        UN_NEED_INSTALL("N", "不需要安装");

        private final String needInstallType;
        private final String needInstallTypeStr;

        NeedInstallTypeEnum(String needInstallType, String needInstallTypeStr) {
            this.needInstallType = needInstallType;
            this.needInstallTypeStr = needInstallTypeStr;
        }

        public static String getStrByAlarmType(Integer alarmType) {
            for (NeedInstallTypeEnum needInstallTypeEnum : NeedInstallTypeEnum.values()) {
                if (needInstallTypeEnum.getNeedInstallType().equals(alarmType)) {
                    return needInstallTypeEnum.getNeedInstallTypeStr();
                }
            }
            return null;
        }

        public String getNeedInstallType() {
            return needInstallType;
        }

        public String getNeedInstallTypeStr() {
            return needInstallTypeStr;
        }
    }

    /**
     * 围栏报警类型和警情的枚举对应关系
     */
    public enum SmsTemplateWithAlarmTypeEnum {
        DRIVE_IN(1, "SMS_TEMPLATE_ENTER_FENCE"),
        DRIVE_OUT(2, "SMS_TEMPLATE_LEAVE_FENCE"),
        DRIVE_IN_OUT(3, "SMS_TEMPLATE_ENTER_LEAVE_FENCE");

        private final Integer alarmType;
        private final String smsTemplate;

        SmsTemplateWithAlarmTypeEnum(Integer alarmType, String smsTemplate) {
            this.alarmType = alarmType;
            this.smsTemplate = smsTemplate;
        }

        public static String getTemplateByAlarmType(Integer alarmType) {
            for (SmsTemplateWithAlarmTypeEnum smsTemplateWithAlarmTypeEnum : SmsTemplateWithAlarmTypeEnum.values()) {
                if (smsTemplateWithAlarmTypeEnum.getAlarmType().equals(alarmType)) {
                    return smsTemplateWithAlarmTypeEnum.getSmsTemplate();
                }
            }
            return null;
        }

        public Integer getAlarmType() {
            return alarmType;
        }

        public String getSmsTemplate() {
            return smsTemplate;
        }
    }

    /**
     * 围栏是否已经关闭
     */
    public enum EnableStatusEnum {
        UN_ENABLE(0, "关闭"),
        ENABLE(1, "开启");

        private final Integer enableStatus;
        private final String enableStatusStr;

        EnableStatusEnum(Integer enableStatus, String enableStatusStr) {
            this.enableStatus = enableStatus;
            this.enableStatusStr = enableStatusStr;
        }

        public static String getStrByEnableStatus(Integer enableStatus) {
            for (EnableStatusEnum enableStatusEnum : EnableStatusEnum.values()) {
                if (enableStatusEnum.getEnableStatus().equals(enableStatus)) {
                    return enableStatusEnum.getEnableStatusStr();
                }
            }
            return null;
        }

        public Integer getEnableStatus() {
            return enableStatus;
        }

        public String getEnableStatusStr() {
            return enableStatusStr;
        }
    }
}
