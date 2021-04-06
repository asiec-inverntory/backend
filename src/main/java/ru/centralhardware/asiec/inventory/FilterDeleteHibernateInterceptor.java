package ru.centralhardware.asiec.inventory;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import ru.centralhardware.asiec.inventory.Entity.Deletable;

import java.io.Serializable;

public class FilterDeleteHibernateInterceptor extends EmptyInterceptor {

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof Deletable){
            Deletable deletable = (Deletable) entity;
            if (deletable.isDeleted()) throw new EntityAlreadyDeleted();
        }
        return super.onLoad(entity, id, state, propertyNames, types);
    }
}
