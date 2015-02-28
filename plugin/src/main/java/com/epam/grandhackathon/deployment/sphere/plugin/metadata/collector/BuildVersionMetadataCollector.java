package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector;

import static java.lang.String.format;

import javax.inject.Inject;

import org.joda.time.DateTime;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import hudson.scm.ChangeLogSet;
import jenkins.model.Jenkins;
import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetadataDao;

@Log
public class BuildVersionMetadataCollector implements Collector<BuildMetaData> {

    @Inject
    private BuildMetadataDao metadataDao;

    public BuildVersionMetadataCollector() {
        Jenkins.getInstance().getInjector().injectMembers(this);
    }

    @Override
    public BuildMetaData collect(AbstractBuild<?, ?> build, final TaskListener taskListener) {
        int buildNumber = build.getNumber();

        String buildId = build.getId();
        ChangeLogSet<? extends hudson.scm.ChangeLogSet.Entry> changeSet = build.getChangeSet();
        log.fine(format("Resolved build number: %s, build id: %s, changeSet emptiness: %s", buildNumber, buildId,
                changeSet.isEmptySet()));
        BuildMetaData buildMetaData = new BuildMetaData();
        buildMetaData.setNumber(buildNumber);
        buildMetaData.setApplicationName("Some app name");
        buildMetaData.setJobName(build.getDisplayName());
        buildMetaData.setBuiltAt(new DateTime(build.due()));
        buildMetaData.setBuildVersion(String.format("0.0.%s", buildNumber));
        metadataDao.save(buildMetaData);
        return buildMetaData;
    }
}
