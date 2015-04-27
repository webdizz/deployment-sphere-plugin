package com.epam.grandhackathon.deployment.sphere.plugin;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepMonitor;

import java.io.IOException;
import java.util.logging.Level;

import javax.inject.Inject;

import jenkins.model.Jenkins;
import lombok.extern.java.Log;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import com.epam.grandhackathon.deployment.sphere.plugin.action.DynamicVariablesStorageAction;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector.Collector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector.DeployVersionMetaDataCollector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDao;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDao;
import com.google.common.base.Strings;

@Log
public class DeployVersionMetaDataPublisher extends hudson.tasks.Notifier {

    @DataBoundSetter
    private String deployedAppName;
    @Inject
    private EnvironmentDao environmentDao;
    @Inject
    private BuildMetaDataDao buildMetaDataDao;

    @DataBoundConstructor
    public DeployVersionMetaDataPublisher() {
        Jenkins.getInstance().getInjector().injectMembers(this);
    }
    
    public DeployVersionMetaDataPublisher(String deployedAppName){
    	this.deployedAppName = deployedAppName;
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    public String getDeployedAppName() {
        return deployedAppName;
    }

    @Override
    public boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher, final BuildListener listener)
            throws InterruptedException, IOException {
        log.log(Level.FINE, "Is about to collect deploy metadata");

        checkArgument(null != listener, "Listener is null, something was wrong.");
        checkArgument(null != build, "Current build is null, something was wrong.");

        listener.getLogger().append("[deployment-sphere] Collecting deploy metadata\n");
		repopulateEnvValues(build);
        Collector<DeploymentMetaData> collector = new DeployVersionMetaDataCollector();
        DeploymentMetaData metaData = collector.collect(build, listener);

        log.log(Level.FINEST, format("New deploy metadata has been collected %s", metaData));

        return true;
    }

	private void repopulateEnvValues(final AbstractBuild<?, ?> build) {
        getDescriptor().load();
        final String appName = getDeployedAppName();
        checkState(!Strings.isNullOrEmpty(appName), String.format("Invalid application name %s", appName));
        build.addAction(new DynamicVariablesStorageAction(Constants.BUILD_APP_NAME, appName));
	}

}
