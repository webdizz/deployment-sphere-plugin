package com.epam.grandhackathon.deployment.sphere.plugin;

import java.util.logging.Level;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import jenkins.YesNoMaybe;
import lombok.extern.java.Log;

@Log
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class BuildVersionMetaDataCollectorDescriptor extends BuildStepDescriptor<Publisher> {

    public BuildVersionMetaDataCollectorDescriptor() {
        super(BuildVersionMetaDataPublisher.class);
        load();
    }

    @Override
    public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
        log.log(Level.FINE, String.format("Current project is of type %s", jobType));
        return true;
    }


    public String getDisplayName() {
        return "Collect Build Metadata";
    }
}