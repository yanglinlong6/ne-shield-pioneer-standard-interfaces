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
public enum FakeOfficeEnum {

    IN_FENCE(1, "在围栏内"),
    NOT_IN_FENCE(2, "不在围栏内");

    private Integer status;
    private String desc;

    public static String getDescByStatus(Integer status) {
        for (FakeOfficeEnum fakeOfficeEnum : FakeOfficeEnum.values()) {
            if (fakeOfficeEnum.getStatus().equals(status)) {
                return fakeOfficeEnum.getDesc();
            }
        }
        return "";
    }
}
