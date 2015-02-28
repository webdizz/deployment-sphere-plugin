package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import static java.lang.String.format;

import org.skife.jdbi.v2.Handle;
import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.query.BuildQuery;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.util.DateFormatUtil;

@Log
public class BuildMetaDataDao extends GenericDao {

    public void save(final BuildMetaData buildMetaData) {
        Build mappedBuild = getModelMapper().map(buildMetaData, Build.class);
        mappedBuild.setBuiltAt(DateFormatUtil.toDate(buildMetaData.getBuiltAt()));

        try (Handle handle = database().open()) {
            BuildQuery query = handle.attach(BuildQuery.class);
            query.save(mappedBuild);
            log.fine(format("Build '%s' was saved", mappedBuild));
        }
    }

    public BuildMetaData find(final String applicationName, final String buildVersion) {
        try (Handle handle = database().open()) {
            BuildQuery query = handle.attach(BuildQuery.class);
            Build foundBuild = query.find(applicationName, buildVersion);
            log.fine(format("Here is a build found '%s'", foundBuild));
            return getModelMapper().map(foundBuild, BuildMetaData.class);
        }
    }
}
