package ru.centralhardware.asiec.inventory.Web.Exceptionhander;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class OutOfRangeHandler {

    @ExceptionHandler(OutOfRangeException.class)
    public ApiError handle(HttpServletRequest req){
        return new ApiError(400, "Вы пытаетесь добавить число вне разрешенного диапазона" , req.getRequestURI());
    }

}
