package com.epam.grandhackathon.deployment.sphere.plugin;

import static java.lang.String.format;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.ChoiceParameterDefinition;
import hudson.model.ParameterDefinition;
import hudson.model.ParametersDefinitionProperty;
import hudson.tasks.BuildStepMonitor;
import jenkins.model.Jenkins;
import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.action.DynamicVariablesStorageAction;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector.Collector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector.DeployVersionMetaDataCollector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDao;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDao;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

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

        Collector<DeploymentMetaData> collector = new DeployVersionMetaDataCollector();
        DeploymentMetaData metaData = collector.collect(build, listener);

        log.log(Level.FINEST, format("New deploy metadata has been collected %s", metaData));

        repopulateValues(build);
        return true;
    }

    @Override
    public boolean prebuild(final AbstractBuild<?, ?> build, final BuildListener listener) {
        repopulateValues(build);
        return true;
    }

    private void repopulateValues(final AbstractBuild<?, ?> build) {
        getDescriptor().load();
        final String appName = getDeployedAppName();
        checkState(!Strings.isNullOrEmpty(appName), String.format("Invalid application name", appName));
        build.addAction(new DynamicVariablesStorageAction(Constants.BUILD_APP_NAME, appName));

        try {
            if (null != build.getProject().getProperty(ParametersDefinitionProperty.class)) {
                log.log(Level.FINE, "Is about to remove project property");
                build.getProject().removeProperty(ParametersDefinitionProperty.class);
                build.getProject().save();
            }
            ParameterDefinition buildVersions = getBuildVersionChoices(appName);
            ParameterDefinition envs = getEnvChoices();
            build.getProject().addProperty(new ParametersDefinitionProperty(envs, buildVersions));
            build.getProject().save();
            build.getProject().doReload();
            build.getProject().doReload();

        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    private ParameterDefinition getBuildVersionChoices(final String appName) {
        Collection<BuildMetaData> builds = buildMetaDataDao.findByAppName(appName);
        List<String> versions = Lists.newArrayList();

        for (BuildMetaData build : builds) {
            versions.add(build.getBuildVersion());
        }
        return new ChoiceParameterDefinition(Constants.BUILD_VERSION,
                versions.toArray(new String[versions.size()]), "");
    }

    private ParameterDefinition getEnvChoices() {
        Collection<EnvironmentMetaData> envs = environmentDao.findAll();
        List<String> names = Lists.newArrayList();
        for (EnvironmentMetaData env : envs) {
            names.add(env.getTitle());
        }
        ChoiceParameterDefinition choices = new ChoiceParameterDefinition(Constants.ENV_NAME,
                names.toArray(new String[names.size()]), "");
        return choices;
    }

}
