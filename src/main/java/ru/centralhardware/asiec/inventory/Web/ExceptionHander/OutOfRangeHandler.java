package ru.centralhardware.asiec.inventory.Web.ExceptionHander;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.centralhardware.asiec.inventory.Web.ExceptionHander.Exception.ApiError;
import ru.centralhardware.asiec.inventory.Web.ExceptionHander.Exception.OutOfRangeException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class OutOfRangeHandler {

    @ExceptionHandler(OutOfRangeException.class)
    public ApiError handle(HttpServletRequest req){
        return new ApiError(400, "Вы пытаетесь добавить число вне разрешенного диапазона" , req.getRequestURI());
    }

}
