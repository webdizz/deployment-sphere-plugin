package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import javax.inject.Inject;

import org.modelmapper.ModelMapper;
import org.skife.jdbi.v2.DBI;
import jenkins.model.Jenkins;

public class GenericDao {
//    @Inject
//    private PersistenceService persistenceService;
//
//    private EntityManager entityManager;

    private ModelMapper modelMapper = new ModelMapper();

    @Inject
    private DatabaseProvider databaseProvider;

    public GenericDao() {
        Jenkins.getInstance().getInjector().injectMembers(this);
    }

//    private void initializeEntityManager() {
//        try {
//            entityManager = persistenceService.getGlobalEntityManagerFactory().createEntityManager();
//        } catch (SQLException | IOException exc) {
//            throw new IllegalStateException("Unable to instantiate EntityManager for BuildMetadataDao", exc);
//        }
//    }

//    public EntityManager getEntityManager() {
//        return entityManager;
//    }

    public DBI database() {
        return databaseProvider.database();
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }
}
