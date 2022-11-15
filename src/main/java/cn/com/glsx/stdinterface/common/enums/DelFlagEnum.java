package cn.com.glsx.stdinterface.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 进出围栏状态枚举类
 *
 * @author yangll
 */
@Getter
@AllArgsConstructor
public enum DelFlagEnum {

    DEL(1, "删除状态"),
    NOT_IN_DEL(0, "未删除状态");

    private Integer status;
    private String desc;

    public static String getDescByStatus(Integer status) {
        for (DelFlagEnum delFlagEnum : DelFlagEnum.values()) {
            if (delFlagEnum.getStatus().equals(status)) {
                return delFlagEnum.getDesc();
            }
        }
        return "";
    }
}
