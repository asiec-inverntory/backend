package ru.centralhardware.asiec.inventory;

import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.centralhardware.asiec.inventory.Web.ApiError;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class DuplicatedKeyHandler {

    @ExceptionHandler(PSQLException.class)
    public ApiError handle(PSQLException exception, HttpServletRequest req){
        return new ApiError(400, exception.getLocalizedMessage(), req.getRequestURI());
    }


}
