package com.epam.grandhackathon.deployment.sphere.plugin;

import static org.junit.Assert.assertNotNull;
import hudson.EnvVars;
import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.slaves.EnvironmentVariablesNodeProperty;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.grandhackathon.deployment.sphere.plugin.listener.DeployVersionMetaDataListenerTest;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;

public class DeployMetaDataParameterValueTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeployVersionMetaDataListenerTest.class);
    
	@Rule
    public JenkinsRule j = new JenkinsRule();
	private String environmentKey = "qa";
	private String buildVersion = "0.1";
	private String applicationName = "app1";
	
	@Test
	public void testBuildEnvVars() throws Exception {
		LOGGER.info("Test buildEnvVars");
		FreeStyleProject project = j.createFreeStyleProject("Deployment test job");	
		FreeStyleBuild build = project.scheduleBuild2(0).get();
		DeployMetaDataParameterValue value = new DeployMetaDataParameterValue(Constants.DEPLOY_META_DATA, environmentKey, buildVersion, applicationName);
		EnvironmentVariablesNodeProperty prop = new EnvironmentVariablesNodeProperty();
	    EnvVars envVars = prop.getEnvVars();
		value.buildEnvVars(build, envVars);
		assertNotNull(envVars.get(Constants.BUILD_VERSION));
		assertNotNull(envVars.get(Constants.ENV_NAME));
		assertNotNull(envVars.get(Constants.BUILD_APP_NAME));
		LOGGER.info("Done");
	}
	
	@Test
	public void testCreateVariableResolver() throws Exception{
		LOGGER.info("Test ceateVariableResolver");
		FreeStyleProject project = j.createFreeStyleProject("Deployment test job 2");	
		FreeStyleBuild build = project.scheduleBuild2(0).get();
		DeployMetaDataParameterValue value = new DeployMetaDataParameterValue(Constants.DEPLOY_META_DATA, environmentKey, buildVersion, applicationName);
		assertNotNull(value.createVariableResolver(build).resolve(Constants.ENV_NAME));
		assertNotNull(value.createVariableResolver(build).resolve(Constants.BUILD_VERSION));
		assertNotNull(value.createVariableResolver(build).resolve(Constants.BUILD_APP_NAME));
		LOGGER.info("Done");
	}

}
