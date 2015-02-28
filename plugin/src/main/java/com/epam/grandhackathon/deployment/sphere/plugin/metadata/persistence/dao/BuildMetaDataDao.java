package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.BuildPk;

@Log
public class BuildMetaDataDao extends GenericDao {

    public void save(final BuildMetaData buildMetaData) {
        Build mappedBuild = getModelMapper().map(buildMetaData, Build.class);
        getEntityManager().persist(mappedBuild);
    }

    public BuildMetaData find(final String applicationName, final Long number) {
        Build foundBuild = getEntityManager().find(Build.class, new BuildPk(applicationName, number));
        return getModelMapper().map(foundBuild, BuildMetaData.class);
    }
}
