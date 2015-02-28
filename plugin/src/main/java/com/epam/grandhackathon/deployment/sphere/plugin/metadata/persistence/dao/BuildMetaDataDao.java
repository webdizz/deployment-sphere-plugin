package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;

@Log
public class BuildMetaDataDao extends GenericDao {

    public void save(final BuildMetaData buildMetaData) {


        Build mappedBuild = getModelMapper().map(buildMetaData, Build.class);

    }

    public BuildMetaData find(final String applicationName, final String buildVersion) {
//        Build foundBuild = getEntityManager().find(Build.class, new BuildPk(applicationName, buildVersion));
//        return getModelMapper().map(foundBuild, BuildMetaData.class);
        return null;
    }
}
