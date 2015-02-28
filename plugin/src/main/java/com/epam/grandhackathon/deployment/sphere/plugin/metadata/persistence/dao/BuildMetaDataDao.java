package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import static java.lang.String.format;

import java.util.List;

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
            Build savedBuild = query.find(mappedBuild.getApplicationName(), mappedBuild.getBuildVersion());
            log.fine(format("Here is a build found '%s'", savedBuild));
            List<Build> builds = query.all();
            log.fine(format("There are builds buildNumber in database '%s'", builds.size()));
        }
    }

    public BuildMetaData find(final String applicationName, final String buildVersion) {
//        Build foundBuild = getEntityManager().find(Build.class, new BuildPk(applicationName, buildVersion));
//        return getModelMapper().map(foundBuild, BuildMetaData.class);
        return null;
    }
}
