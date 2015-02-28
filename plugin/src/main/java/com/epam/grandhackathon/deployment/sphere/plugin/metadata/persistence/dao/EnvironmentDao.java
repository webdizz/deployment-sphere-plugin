package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;


import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Environment;
import com.google.common.collect.Lists;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.database.jpa.PersistenceService;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class EnvironmentDao {

    @Inject
    private PersistenceService persistenceService;

    private EntityManager entityManager;

    private ModelMapper modelMapper = new ModelMapper();


    public EnvironmentDao () {
        Jenkins.getInstance().getInjector().injectMembers(this);
        try {
            entityManager = persistenceService.getGlobalEntityManagerFactory().createEntityManager();
        } catch (SQLException | IOException exc) {
            throw new IllegalStateException("Unable to instantiate EntityManager for BuildMetadataDao", exc);
        }
    }

    public Collection<EnvironmentMetaData> findAll () {

        Query query = entityManager.createQuery("SELECT e FROM Environment e", Environment.class);

        final Collection<Environment> environments = (Collection<Environment>) query.getResultList();
        List<EnvironmentMetaData> environmentMetaDatas = Lists.newArrayList();
        for (Environment environment : environments) {
            modelMapper.map(environment, EnvironmentMetaData.class);
        }

        return environmentMetaDatas;
    }


}
