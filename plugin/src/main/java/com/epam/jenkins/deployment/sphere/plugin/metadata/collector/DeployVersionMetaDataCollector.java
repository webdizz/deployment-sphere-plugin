package com.epam.jenkins.deployment.sphere.plugin.metadata.collector;

import com.epam.jenkins.deployment.sphere.plugin.metadata.Constants;
import com.epam.jenkins.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.jenkins.deployment.sphere.plugin.metadata.persistence.dao.DeploymentMetaDataDao;
import com.epam.jenkins.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDao;
import com.epam.jenkins.deployment.sphere.plugin.metadata.persistence.domain.Environment;
import com.epam.jenkins.deployment.sphere.plugin.utils.DateFormatUtil;
import com.epam.jenkins.deployment.sphere.plugin.utils.EnvVarsResolver;
import com.google.common.base.Strings;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import jenkins.model.Jenkins;
import lombok.extern.java.Log;
import org.joda.time.DateTime;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkState;

@Log
public final class DeployVersionMetaDataCollector implements Collector<DeploymentMetaData> {

    @Inject
    private DeploymentMetaDataDao deploymentMetaDataDao;

    @Inject
    private EnvironmentDao environmentDao;

    public DeployVersionMetaDataCollector () {
        Jenkins.getInstance().getInjector().injectMembers(this);
    }

    @Override
    public DeploymentMetaData collect (final AbstractBuild<?, ?> build, final TaskListener taskListener) {
        // next variables should be resolved from context
        EnvVarsResolver envVarsResolver = new EnvVarsResolver(build, taskListener);

        String buildVersion = envVarsResolver.getValue(Constants.BUILD_VERSION);
        checkState(!Strings.isNullOrEmpty(buildVersion), String.format("Invalid build version", buildVersion));

        String applicationName = envVarsResolver.getValue(Constants.BUILD_APP_NAME);
        checkState(!Strings.isNullOrEmpty(applicationName), String.format("Invalid application name", applicationName));

        String envName = envVarsResolver.getValue(Constants.ENV_NAME);
        checkState(!Strings.isNullOrEmpty(envName), String.format("Invalid env name", envName));


        Environment environment = environmentDao.find(envName);

        // persist data
        DeploymentMetaData deploymentMetaData = new DeploymentMetaData();
        deploymentMetaData.setApplicationName(applicationName);
        deploymentMetaData.setDeployedAt(DateFormatUtil.formatDate(new DateTime(build.due())));
        deploymentMetaData.setBuildVersion(buildVersion);
        deploymentMetaData.setEnvironmentKey(environment.getKey());
        deploymentMetaDataDao.save(deploymentMetaData);
        return deploymentMetaData;
    }

}
