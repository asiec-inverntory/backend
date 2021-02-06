package ru.centralhardware.asiec.inventory;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;
import ru.centralhardware.asiec.inventory.Entity.Deletable;

import java.io.Serializable;

@Component
public class CustomInterceptorImpl extends EmptyInterceptor {

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof Deletable){
            Deletable deletable = (Deletable) entity;
            if (deletable.isDeleted()){
                return false;
            } else {
                return super.onLoad(entity, id, state, propertyNames, types);
            }
        }
        return false;
    }
}
