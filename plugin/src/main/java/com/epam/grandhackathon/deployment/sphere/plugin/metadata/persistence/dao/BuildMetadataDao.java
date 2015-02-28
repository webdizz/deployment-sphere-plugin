package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import java.io.IOException;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jenkinsci.plugins.database.jpa.PersistenceService;
import org.modelmapper.ModelMapper;
import jenkins.model.Jenkins;
import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.BuildPk;

@Log
public class BuildMetadataDao {

    @Inject
    private PersistenceService persistenceService;

    private EntityManager entityManager;

    private ModelMapper modelMapper = new ModelMapper();

    public BuildMetadataDao() {
        Jenkins.getInstance().getInjector().injectMembers(this);
        try {
            entityManager = persistenceService.getGlobalEntityManagerFactory().createEntityManager();
        } catch (SQLException | IOException exc) {
            throw new IllegalStateException("Unable to instantiate EntityManager for BuildMetadataDao", exc);
        }
    }

    public void save(final BuildMetaData buildMetaData) {
        Build mappedBuild = modelMapper.map(buildMetaData, Build.class);
        entityManager.persist(mappedBuild);
    }

    public BuildMetaData find(final String jobName, final Long number) {
        Build foundBuild = entityManager.find(Build.class, new BuildPk(number, jobName));
        return modelMapper.map(foundBuild, BuildMetaData.class);
    }
}
