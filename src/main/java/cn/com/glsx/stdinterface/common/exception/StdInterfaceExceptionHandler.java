package cn.com.glsx.stdinterface.common.exception;

import com.glsx.plat.core.web.R;
import com.glsx.plat.exception.ExceptionLevel;
import com.glsx.plat.exception.GlobalExceptionHandler;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author payu
 */
@Slf4j
@RestControllerAdvice
public class StdInterfaceExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(StdInterfaceException.class)
    public R handleAdminException(StdInterfaceException e) {
        return R.error(e.getErrorCode(), e.getMessage()).data(e.getData());
    }

    /**
     * 引入Feign服务的话
     *
     * @param e
     * @return
     */
    @ExceptionHandler(FeignException.class)
    public R handleFeignException(FeignException e) {
        return R.error(e.status(), e.getMessage());
    }

    /**
     * Mybatis BindingException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindingException.class)
    public R handleBindingException(BindingException e) {
        return R.error(e.getMessage());
    }

    @Override
    protected void saveException(Exception e, ExceptionLevel level) {
        super.saveException(e, level);
    }
}
