package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import java.io.IOException;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jenkinsci.plugins.database.jpa.PersistenceService;
import jenkins.model.Jenkins;
import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;

@Log
public class BuildMetadataDao {

    @Inject
    private PersistenceService persistenceService;

    private EntityManager entityManager;

    public BuildMetadataDao() {
        Jenkins.getInstance().getInjector().injectMembers(this);
        try {
            entityManager = persistenceService.getGlobalEntityManagerFactory().createEntityManager();
        } catch (SQLException | IOException exc) {
            throw new IllegalStateException("Unable to instantiate EntityManager for BuildMetadataDao", exc);
        }
    }

    public void save(final BuildMetaData buildMetaData) {
        Build build = new Build();
        build.setJobName(buildMetaData.getJobName());
        build.setNumber(buildMetaData.getNumber());
        entityManager.persist(build);
    }
}
