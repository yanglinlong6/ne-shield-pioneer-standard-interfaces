package cn.com.glsx.stdinterface.modules.util;

import cn.com.glsx.stdinterface.common.constants.Constants;
import cn.com.glsx.stdinterface.dto.WorkOrderDeviceAddressItem;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

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

    /**
     * 解析设备在线状态
     *
     * @param addressItem
     * @param source
     * @param speedValue
     * @return
     */
    public static Integer resolutionDeviceOnlineStatus(WorkOrderDeviceAddressItem addressItem, Integer source, String speedValue) {
        if (StringUtils.isBlank(addressItem.getGpsTime())) {
            return Constants.DeviceOnline.OFFLINE;
        }
        int currentSource = (source == null) ? Constants.DeviceSource.WIRELESS : source;
        int speed = StringUtils.isEmpty(speedValue) ? 0 : Integer.parseInt(speedValue);

        Date gpsDate = DateUtils.toDate(addressItem.getGpsTime(), DateUtils.pattern19);
        Date bsDate = DateUtils.toDate(addressItem.getBsTime(), DateUtils.pattern19);
        long rtTrckTime = (gpsDate != null) ? gpsDate.getTime() : 0l;
        long gprsTime = (bsDate != null) ? bsDate.getTime() : 0l;

        int status = 0;
        long now = System.currentTimeMillis();
        if (gprsTime == 0 && rtTrckTime == 0) {
            //离线
            status = Constants.DeviceOnline.OFFLINE;
        } else if (gprsTime == 0 && rtTrckTime != 0) {
            status = commonResolutionOnline(now, rtTrckTime, speed, currentSource);
        } else if (gprsTime != 0 && rtTrckTime == 0) {
            status = commonResolutionOnline(now, gprsTime, speed, currentSource);
        } else {
            long time = 0;
            time = gprsTime >= rtTrckTime ? gprsTime : rtTrckTime;
            status = commonResolutionOnline(now, time, speed, currentSource);
        }
        return status;
    }

    /**
     * 根据当前时间判断设备的行驶状态
     *
     * @param now
     * @param rtTrckTime
     * @param speed
     * @param currentSource
     * @return
     */
    private static int commonResolutionOnline(long now, long rtTrckTime, int speed, int currentSource) {
        int status = Constants.DeviceOnline.DRIVING;
        if (now - rtTrckTime > Constants.DeviceOnline.TIME_TWENTY_HOURS) {
            status = Constants.DeviceOnline.OFFLINE;
        } else {
            if (currentSource == Constants.DeviceSource.WIRELESS) {
                status = Constants.DeviceOnline.ONLINE;
            } else {
                if (now - rtTrckTime > Constants.DeviceOnline.TIME_SIX_MINITES) {
                    //大于6分钟  有源在线
                    status = Constants.DeviceOnline.ONLINE;
                } else {
                    if (speed > 0) {
                        //行驶状态
                        status = Constants.DeviceOnline.DRIVING;
                    } else {
                        status = Constants.DeviceOnline.STANDSTILL;
                    }
                }
            }
        }
        return status;
    }
}
