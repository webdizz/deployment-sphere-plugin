package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector;

import static java.lang.String.format;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;
import hudson.scm.ChangeLogSet;
import lombok.extern.java.Log;

import org.joda.time.DateTime;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeployMetaData;

@Log
public final class DeployVersionMetadataCollector implements Collector<DeployMetaData> {

    @Override
    public DeployMetaData collect(final AbstractBuild<?, ?> build, final TaskListener taskListener) {
        int buildNumber = build.getNumber();
        String buildId = build.getId();

        ChangeLogSet<? extends hudson.scm.ChangeLogSet.Entry> changeSet = build.getChangeSet();
        log.fine(format("Resolved build number: %s, build id: %s, changeSet emptiness: %s", buildNumber, buildId,
                changeSet.isEmptySet()));

        DeployMetaData metaData = new DeployMetaData();
        metaData.setNumber(build.getNumber());
        metaData.setApplicationName("Some app name");
        metaData.setJobName(build.getDisplayName());
        metaData.setDeployedAt(new DateTime(build.due()));
        metaData.setDeployedVersion(String.format("0.0.%s", buildNumber));
        return metaData;
    }

}
