package com.epam.grandhackathon.deployment.sphere.plugin;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;

import java.io.IOException;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.kohsuke.stapler.DataBoundConstructor;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector.BuildVersionMetadataCollector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector.Collector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;

@Log
public class BuildVersionMetadataPublisher extends Notifier {

    @DataBoundConstructor
    public BuildVersionMetadataPublisher() {
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    @Override
    public boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher, final BuildListener listener)
            throws InterruptedException, IOException {
        log.log(Level.FINE, "Is about to collect build metadata");

        checkArgument(null != listener, "Listener is null, something was wrong.");
        checkArgument(null != build, "Current build is null, something was wrong.");

        listener.getLogger().append("[deployment-sphere] Collecting build metadata\n");

        Collector<BuildMetaData> collector = new BuildVersionMetadataCollector();
        BuildMetaData buildMetaData = collector.collect(build, listener);
        log.log(Level.FINEST, format("Next build metadata was collected %s", buildMetaData));

        return true;
    }
}
