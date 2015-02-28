package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector;

import static java.lang.String.format;
import static com.epam.grandhackathon.deployment.sphere.plugin.TempConstants.APP_NAME;

import javax.inject.Inject;

import org.joda.time.DateTime;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import hudson.scm.ChangeLogSet;
import jenkins.model.Jenkins;
import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDao;

@Log
public class BuildVersionMetaDataCollector implements Collector<BuildMetaData> {

    @Inject
    private BuildMetaDataDao metadataDao;

    public BuildVersionMetaDataCollector() {
        Jenkins.getInstance().getInjector().injectMembers(this);
    }

    @Override
    public BuildMetaData collect(AbstractBuild<?, ?> build, final TaskListener taskListener) {
        Long buildNumber = (long) build.getNumber();
        String buildId = build.getId();
        ChangeLogSet<? extends hudson.scm.ChangeLogSet.Entry> changeSet = build.getChangeSet();
        log.fine(format("Resolved build number: %s, build id: %s, changeSet emptiness: %s", buildNumber, buildId,
                changeSet.isEmptySet()));

        BuildMetaData buildMetaData = new BuildMetaData();
        buildMetaData.setNumber(buildNumber);
        buildMetaData.setBuildVersion(String.format("0.0.%s", buildNumber));
        buildMetaData.setApplicationName(APP_NAME);
        buildMetaData.setJobName(build.getDisplayName());
        buildMetaData.setBuiltAt(new DateTime(build.due()));

        metadataDao.save(buildMetaData);
        return buildMetaData;
    }
}
