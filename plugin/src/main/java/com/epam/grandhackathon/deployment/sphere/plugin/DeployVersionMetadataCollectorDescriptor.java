package com.epam.grandhackathon.deployment.sphere.plugin;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;

import java.util.logging.Level;

import jenkins.YesNoMaybe;
import lombok.extern.java.Log;

@Log
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class DeployVersionMetadataCollectorDescriptor extends BuildStepDescriptor<Publisher> {
    private static final String DISPLAY_NAME = "Collect Deploy Metadata";

    public DeployVersionMetadataCollectorDescriptor() {
        super(DeployVersionMetadataPublisher.class);
        load();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
        log.log(Level.FINE, String.format("Current project is of type %s", jobType));
        return true;
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }
}