package cn.com.glsx.stdinterface.modules.util;

import cn.com.glsx.stdinterface.common.constants.Constants;
import org.apache.commons.lang.StringUtils;

/**
 * 设备位置信息 - 工具类
 */
public class DeviceLocationUtil {

    /**
     * 解析设备位置状态：有效，无效
     *
     * @param gpsTime
     * @param bsTime
     * @param source
     * @return
     */
    public static Integer resolutionGpsType(String gpsTime, String bsTime, Integer source) {
        if (StringUtils.isBlank(gpsTime) && StringUtils.isEmpty(bsTime)) {
            return Constants.Verification.SN_INVALID;
        }

        // 初始化设备有源无源状态：默认无源
        int currentSource = (source == null) ? Constants.DeviceSource.WIRELESS : source;
        long differBase = (currentSource == Constants.DeviceSource.WIRED) ? Constants.Verification.WIRED_TIME : Constants.Verification.WIRELESS_TIME;

        if (StringUtils.isBlank(gpsTime)) {
            return Constants.Verification.SN_INVALID;
        }

        long timeDiff = DateUtils.getTimeDifference(gpsTime);
        if (currentSource == Constants.DeviceSource.WIRELESS) {
            long gpsDiffer = DateUtils.getTimeDifference(gpsTime);
            long bsDiffer = DateUtils.getTimeDifference(bsTime);
            timeDiff = (gpsDiffer > bsDiffer) ? bsDiffer : gpsDiffer;
        }
        return timeDiff > differBase ? Constants.Verification.SN_INVALID : Constants.Verification.SN_VALID;
    }
}
