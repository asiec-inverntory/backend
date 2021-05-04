package ru.centralhardware.asiec.inventory.Web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class NumberFormatHandler {

    @ExceptionHandler(NumberFormatException.class)
    public ApiError handle(NumberFormatException exception, HttpServletRequest req){
        return new ApiError(400, "Вы пытаетесь добавить не число в числовой тип атрибута" , req.getRequestURI());
    }

}
