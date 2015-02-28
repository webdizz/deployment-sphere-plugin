package com.epam.grandhackathon.deployment.sphere.plugin;

import static java.lang.String.format;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.util.logging.Level;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import hudson.EnvVars;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector.BuildVersionMetaDataCollector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.utils.EnvVarsExtractor;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;

@Log
public class BuildVersionMetaDataPublisher extends Notifier {

    @DataBoundSetter
    private String versionPattern;

    @DataBoundConstructor
    public BuildVersionMetaDataPublisher() {
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    public String getVersionPattern() {
        return versionPattern;
    }

    @Override
    public boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher, final BuildListener listener)
            throws InterruptedException, IOException {
        log.log(Level.FINE, "Is about to collect build metadata");

        checkArgument(null != listener, "Listener is null, something was wrong.");
        checkArgument(null != build, "Current build is null, something was wrong.");

        listener.getLogger().append("[deployment-sphere] Collecting build metadata\n");

        BuildMetaData buildMetaData = new BuildVersionMetaDataCollector().collect(build, listener);
        log.log(Level.FINEST, format("Next build metadata was collected %s", buildMetaData));

        return true;
    }

    @Override
    public boolean prebuild(final AbstractBuild<?, ?> build, final BuildListener listener) {
        final String pattern = getVersionPattern();
        checkArgument(!Strings.isNullOrEmpty(pattern), "Build Pattern version value must be provided");

        final String buildVersion = pattern.replace("{v}", String.valueOf(build.getNumber()));

        final EnvVarsExtractor envExtractor = new EnvVarsExtractor(build, listener);
        envExtractor.putValue(Constants.BUILD_VERSION, buildVersion);

        return true;
    }
}
