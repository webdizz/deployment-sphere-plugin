package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import java.io.IOException;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jenkinsci.plugins.database.jpa.PersistenceService;
import org.modelmapper.ModelMapper;
import jenkins.model.Jenkins;

public class GenericDao {
    @Inject
    private PersistenceService persistenceService;

    private EntityManager entityManager;

    private ModelMapper modelMapper = new ModelMapper();

    public GenericDao() {
        initializeEntityManager();
    }

    private void initializeEntityManager() {
        Jenkins.getInstance().getInjector().injectMembers(this);
        try {
            entityManager = persistenceService.getGlobalEntityManagerFactory().createEntityManager();
        } catch (SQLException | IOException exc) {
            throw new IllegalStateException("Unable to instantiate EntityManager for BuildMetadataDao", exc);
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }
}
