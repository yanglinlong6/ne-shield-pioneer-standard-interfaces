package cn.com.glsx.stdinterface.common.enums;

import com.glsx.plat.exception.SystemMessage;

/**
 * 试驾服务错误码
 *
 * @author zhouhaibao
 */
public enum ResultEnum {

    PARAM_ERROR("1001", "参数错误"),
    DECRYPT_ENCRYPT_ERROR("6021", "加解密失败[Sm4]"),
    ;

    private final String code;
    private final String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultEnum(SystemMessage systemMessage) {
        this.code = String.valueOf(systemMessage.getCode());
        this.message = systemMessage.getMsg();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
