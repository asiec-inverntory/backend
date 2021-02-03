package ru.centralhardware.asiec.inventory.Security;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Log4j
@Component
public class Ip {

    @Autowired
    private HttpServletRequest request;

    /**
     * get request ip address
     * @return String with ip address
     */
    public String getClientIP(){
        log.info("get ip ");
        var xHeader = request.getHeader("X-Forwarded-For");
        if (xHeader == null){
            return request.getRemoteAddr();
        }
        return xHeader.split(",")[0];
    }

}
