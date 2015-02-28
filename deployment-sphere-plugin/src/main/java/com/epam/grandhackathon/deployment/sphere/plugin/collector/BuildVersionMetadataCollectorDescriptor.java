package com.epam.grandhackathon.deployment.sphere.plugin.collector;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import jenkins.YesNoMaybe;

@Extension(dynamicLoadable = YesNoMaybe.YES)
public class BuildVersionMetadataCollectorDescriptor extends BuildStepDescriptor<Publisher> {

    public BuildVersionMetadataCollectorDescriptor() {
        super(BuildVersionMetadataPublisher.class);
    }

    @Override
    public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
        return true;
    }

    public String getDisplayName() {
        return "Collect Build Metadata";
    }
}