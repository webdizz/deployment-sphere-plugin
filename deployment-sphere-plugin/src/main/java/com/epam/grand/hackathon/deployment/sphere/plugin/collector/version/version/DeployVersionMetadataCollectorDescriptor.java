package com.epam.grand.hackathon.deployment.sphere.plugin.collector.version.version;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import jenkins.YesNoMaybe;

@Extension(dynamicLoadable = YesNoMaybe.YES)
public class DeployVersionMetadataCollectorDescriptor extends BuildStepDescriptor<Publisher> {

    public DeployVersionMetadataCollectorDescriptor() {
        super(BuildVersionMetadataPublisher.class);
    }

    @Override
    public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
        return true;
    }

    public String getDisplayName() {
        return "Collect Deploy Metadata";
    }
}