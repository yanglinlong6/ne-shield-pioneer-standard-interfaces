package cn.com.glsx.stdinterface.common.exception;

import cn.com.glsx.stdinterface.common.enums.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author payu
 */
@Setter
@Getter
public class StdInterfaceException extends RuntimeException {

    private String errorCode;

    private String message;

    private Object data;

    public StdInterfaceException() {
    }

    public StdInterfaceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public StdInterfaceException(String errorCode, String message, Object data) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public StdInterfaceException(ResultEnum resultEnum) {
        this.errorCode = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public StdInterfaceException(Throwable cause) {
        super(cause);
    }

    public StdInterfaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
