package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData
import hudson.model.AbstractBuild
import hudson.scm.ChangeLogSet
import lombok.extern.java.Log
import org.joda.time.DateTime

import static java.lang.String.format

@Log
public class BuildVersionMetadataCollector {

    BuildMetaData collect(final AbstractBuild<?, ?> build) {
        int buildNumber = build.getNumber()
        String buildId = build.getId()
        ChangeLogSet<? extends ChangeLogSet.Entry> changeSet = build.getChangeSet()
        log.fine(format("Resolved build number: %s, build id: %s, changeSet emptiness: %s", buildNumber, buildId, changeSet.isEmptySet()))
        return new BuildMetaData(number: buildNumber, name: build.displayName, builtAt: new DateTime(build.time.time))
    }
}
