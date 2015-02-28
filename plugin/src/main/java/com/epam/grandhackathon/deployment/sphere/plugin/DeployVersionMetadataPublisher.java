package com.epam.grandhackathon.deployment.sphere.plugin;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepMonitor;

import java.io.IOException;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.kohsuke.stapler.DataBoundConstructor;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector.Collector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector.DeployVersionMetadataCollector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeployMetaData;

@Log
public class DeployVersionMetadataPublisher extends hudson.tasks.Notifier {

    @DataBoundConstructor
    public DeployVersionMetadataPublisher() {
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    @Override
    public boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher, final BuildListener listener)
            throws InterruptedException, IOException {
        log.log(Level.FINE, "Is about to collect deploy metadata");

        checkArgument(null != listener, "Listener is null, something was wrong.");
        checkArgument(null != build, "Current build is null, something was wrong.");

        listener.getLogger().append("[deployment-sphere] Collecting deploy metadata\n");

        Collector<DeployMetaData> collector = new DeployVersionMetadataCollector();
        DeployMetaData metaData = collector.collect(build);

        log.log(Level.FINEST, format("Next deploy metadata has been collected %s", metaData));

        return true;
    }
}
