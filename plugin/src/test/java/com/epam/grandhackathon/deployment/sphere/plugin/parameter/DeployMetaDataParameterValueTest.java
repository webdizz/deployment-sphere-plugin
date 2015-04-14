package com.epam.grandhackathon.deployment.sphere.plugin.parameter;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import hudson.EnvVars;
import hudson.model.FreeStyleBuild;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import lombok.extern.java.Log;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

@Log
public class DeployMetaDataParameterValueTest {
    
	private String environmentKey = "qa";
	private String buildVersion = "0.1";
	private String applicationName = "app1";
	
	@Test
	public void shouldRepopulateBuildEnvVars() throws Exception {
		log.info("Test buildEnvVars");
		FreeStyleBuild build = Mockito.mock(FreeStyleBuild.class);
		DeployMetaDataParameterValue value = new DeployMetaDataParameterValue(Constants.DEPLOY_META_DATA, environmentKey, buildVersion, applicationName);
		EnvironmentVariablesNodeProperty prop = new EnvironmentVariablesNodeProperty();
	    EnvVars envVars = prop.getEnvVars();
		value.buildEnvVars(build, envVars);
		assertEquals(envVars.get(Constants.BUILD_VERSION), buildVersion);
		assertEquals(envVars.get(Constants.ENV_NAME), environmentKey);
		assertEquals(envVars.get(Constants.BUILD_APP_NAME), applicationName);
		log.info("Done");
	}
	
	@Test
	public void shouldResolveVariableValue() throws Exception{
		log.info("Test createVariableResolver");
		FreeStyleBuild build = Mockito.mock(FreeStyleBuild.class);
		DeployMetaDataParameterValue value = new DeployMetaDataParameterValue(Constants.DEPLOY_META_DATA, environmentKey, buildVersion, applicationName);
		assertEquals(value.createVariableResolver(build).resolve(Constants.ENV_NAME), environmentKey);
		assertEquals(value.createVariableResolver(build).resolve(Constants.BUILD_VERSION), buildVersion);
		assertEquals(value.createVariableResolver(build).resolve(Constants.BUILD_APP_NAME), applicationName);
		log.info("Done");
	}

}
