package ru.centralhardware.asiec.inventory;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import ru.centralhardware.asiec.inventory.Entity.Deletable;

import java.io.Serializable;

public class FilterDeleteHibernateInterceptor extends EmptyInterceptor {

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof Deletable){
            if (state[7] instanceof Boolean){
                if ((boolean)state[7]) throw new UserAlreadyDeleted();
            }
        }
        return super.onLoad(entity, id, state, propertyNames, types);
    }
}
