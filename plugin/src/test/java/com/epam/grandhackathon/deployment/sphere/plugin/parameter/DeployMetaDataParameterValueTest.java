package com.epam.grandhackathon.deployment.sphere.plugin.parameter;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import hudson.EnvVars;
import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import lombok.extern.java.Log;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import static org.junit.Assert.assertNotNull;

@Log
public class DeployMetaDataParameterValueTest {
    
	@Rule
    public JenkinsRule j = new JenkinsRule();
	private String environmentKey = "qa";
	private String buildVersion = "0.1";
	private String applicationName = "app1";
	
	@Test
	public void shouldRepopulateBuildEnvVars() throws Exception {
		log.info("Test buildEnvVars");
		FreeStyleProject project = j.createFreeStyleProject("Deployment test job");	
		FreeStyleBuild build = project.scheduleBuild2(0).get();
		DeployMetaDataParameterValue value = new DeployMetaDataParameterValue(Constants.DEPLOY_META_DATA, environmentKey, buildVersion, applicationName);
		EnvironmentVariablesNodeProperty prop = new EnvironmentVariablesNodeProperty();
	    EnvVars envVars = prop.getEnvVars();
		value.buildEnvVars(build, envVars);
		assertNotNull(envVars.get(Constants.BUILD_VERSION));
		assertNotNull(envVars.get(Constants.ENV_NAME));
		assertNotNull(envVars.get(Constants.BUILD_APP_NAME));
		log.info("Done");
	}
	
	@Test
	public void shouldResolveVariableValue() throws Exception{
		log.info("Test ceateVariableResolver");
		FreeStyleProject project = j.createFreeStyleProject("Deployment test job 2");	
		FreeStyleBuild build = project.scheduleBuild2(0).get();
		DeployMetaDataParameterValue value = new DeployMetaDataParameterValue(Constants.DEPLOY_META_DATA, environmentKey, buildVersion, applicationName);
		assertNotNull(value.createVariableResolver(build).resolve(Constants.ENV_NAME));
		assertNotNull(value.createVariableResolver(build).resolve(Constants.BUILD_VERSION));
		assertNotNull(value.createVariableResolver(build).resolve(Constants.BUILD_APP_NAME));
		log.info("Done");
	}

}
