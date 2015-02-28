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
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector.DeployVersionMetaDataCollector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;

@Log
public class DeployVersionMetaDataPublisher extends hudson.tasks.Notifier {

    @DataBoundConstructor
    public DeployVersionMetaDataPublisher() {
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

        Collector<DeploymentMetaData> collector = new DeployVersionMetaDataCollector();
        DeploymentMetaData metaData = collector.collect(build, listener);

        log.log(Level.FINEST, format("New deploy metadata has been collected %s", metaData));

        return true;
    }
}
