package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector;

import hudson.model.AbstractBuild;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeployMetaData;

public final class DeployVersionMetadataCollector implements Collector<DeployMetaData> {

    @Override
    public DeployMetaData collect(final AbstractBuild<?, ?> build) {
        DeployMetaData metaData = new DeployMetaData();
        return metaData;
    }

}
