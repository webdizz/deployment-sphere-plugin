package com.epam.grandhackathon.deployment.sphere.plugin.collector;

import java.io.IOException;
import java.util.logging.Level;

import org.kohsuke.stapler.DataBoundConstructor;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepMonitor;
import lombok.extern.java.Log;

@Log
public class BuildVersionMetadataPublisher extends hudson.tasks.Notifier {

    @DataBoundConstructor
    public BuildVersionMetadataPublisher() {
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    @Override
    public boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher, final BuildListener listener) throws InterruptedException, IOException {
        log.log(Level.FINE, "Is about to collect build metadata");
        return true;
    }
}
