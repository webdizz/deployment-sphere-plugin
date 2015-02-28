package com.epam.grandhackathon.deployment.sphere.plugin.collector;

import java.util.logging.Level;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import jenkins.YesNoMaybe;
import lombok.extern.java.Log;

@Log
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class BuildVersionMetadataCollectorDescriptor extends BuildStepDescriptor<Publisher> {

    public BuildVersionMetadataCollectorDescriptor() {
        super(BuildVersionMetadataPublisher.class);
        load();
    }

    @Override
    public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
        log.log(Level.FINE, "Current project is of type %s", jobType);
        return true;
    }


    public String getDisplayName() {
        return "Collect Build Metadata";
    }
}