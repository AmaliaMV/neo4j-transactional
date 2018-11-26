package com.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationEvent;

import org.grails.datastore.mapping.core.Datastore;
import org.grails.datastore.mapping.engine.EntityAccess;
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent;
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEventListener;
import org.grails.datastore.mapping.engine.event.PreInsertEvent;
import org.grails.datastore.mapping.engine.event.PreUpdateEvent;
import org.grails.datastore.mapping.model.MappingContext;
import org.grails.datastore.mapping.model.PersistentEntity;

public class AutoUserstampEventListener extends AbstractPersistenceEventListener implements MappingContext.Listener {

    private static final Log log = LogFactory.getLog(AutoUserstampEventListener.class);

    private SecurityService securityService;

    public static final String CREATED_BY_PROPERTY = "createdBy";

    private Map<PersistentEntity, Boolean> entitiesWithCreatedBy = new ConcurrentHashMap<>();

    public AutoUserstampEventListener(final Datastore datastore) {
        super(datastore);

        for (PersistentEntity persistentEntity : datastore.getMappingContext().getPersistentEntities()) {
            storeCreatedByInfo(persistentEntity);
        }

        datastore.getMappingContext().addMappingContextListener(this);
    }

    @Override
    protected void onPersistenceEvent(final AbstractPersistenceEvent event) {
        log.debug("AutoUserstampEventListener .... ");
        if (event instanceof PreInsertEvent) {
            beforeInsert(event.getEntity(), event.getEntityAccess());
        }
    }

    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return PreInsertEvent.class.isAssignableFrom(eventType) || PreUpdateEvent.class.isAssignableFrom(eventType);
    }

    public boolean beforeInsert(PersistentEntity entity, EntityAccess ea) {
        if (hasCreatedBy(entity)) {
            setProperty(entity, ea, CREATED_BY_PROPERTY);
        }
        return true;
    }

    private void setProperty(PersistentEntity entity, EntityAccess ea, String propertyName) {
        log.info("[doing call] getSecurityService().getCurrentLoggedInUser()");
        final User user = getSecurityService().getCurrentUser();
        log.info("[finishing call] getSecurityService().getCurrentLoggedInUser()");
        if (user != null) {
            //do something
        }
    }

    private boolean hasCreatedBy(PersistentEntity entity) {
        return entitiesWithCreatedBy.containsKey(entity) && entitiesWithCreatedBy.get(entity);
    }

    private void storeCreatedByInfo(PersistentEntity persistentEntity) {
        entitiesWithCreatedBy.put(persistentEntity,
                                  persistentEntity.hasProperty(CREATED_BY_PROPERTY, User.class));
    }

    public void persistentEntityAdded(PersistentEntity entity) {
        storeCreatedByInfo(entity);
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }


}
