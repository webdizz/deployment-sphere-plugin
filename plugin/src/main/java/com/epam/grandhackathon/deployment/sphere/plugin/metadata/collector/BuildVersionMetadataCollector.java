package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector;

import static java.lang.String.format;
import hudson.model.AbstractBuild;
import hudson.scm.ChangeLogSet;
import lombok.extern.java.Log;

import org.joda.time.DateTime;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;

@Log
public class BuildVersionMetadataCollector implements Collector<BuildMetaData> {

    @Override
    public BuildMetaData collect(AbstractBuild<?, ?> build) {
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
        return buildMetaData;
    }
}
