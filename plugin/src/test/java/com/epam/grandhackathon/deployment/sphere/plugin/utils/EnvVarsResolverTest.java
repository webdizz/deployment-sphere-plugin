package com.epam.grandhackathon.deployment.sphere.plugin.utils;

import java.util.logging.Level;

import jenkins.model.Jenkins;
import lombok.extern.java.Log;
import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.TaskListener;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import hudson.util.LogTaskListener;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.hudson.test.JenkinsRule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import com.epam.grandhackathon.deployment.sphere.plugin.action.DynamicVariablesStorageAction;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.epam.grandhackathon.deployment.sphere.plugin.parameter.DeployMetaDataParameterValue;

@RunWith(MockitoJUnitRunner.class)
@Log
public class EnvVarsResolverTest {
	
	@Mock
	private AbstractBuild<?, ?> build;

	@Mock
	private TaskListener taskListener;
	@InjectMocks
	private EnvVarsResolver envVarsResolver;

	@Rule
    public JenkinsRule j = new JenkinsRule();
	
	private String environmentKey = "qa";
	private String buildVersion = "0.8";
	private String applicationName = "app1";
	
	private EnvVarsResolver resolver;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		FreeStyleProject project = j.createFreeStyleProject("Deployment test job");	
		FreeStyleBuild build = project.scheduleBuild2(0).get();
		DeployMetaDataParameterValue value = new DeployMetaDataParameterValue(Constants.DEPLOY_META_DATA, environmentKey, buildVersion, applicationName);
		EnvironmentVariablesNodeProperty prop = new EnvironmentVariablesNodeProperty();
	    EnvVars envVars = prop.getEnvVars();
		value.buildEnvVars(build, envVars);
		
		build.addAction(new DynamicVariablesStorageAction(envVars));
		
		LogTaskListener listener = new LogTaskListener(log.getLogger(Jenkins.class.getName()), Level.INFO);
		resolver = new EnvVarsResolver(build, listener);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetValue() throws Exception {
		log.info("Test testGetValue");
		assertTrue(buildVersion.equals(resolver.getValue(Constants.BUILD_VERSION)));
		assertTrue(environmentKey.equals(resolver.getValue(Constants.ENV_NAME)));
		assertTrue(applicationName.equals(resolver.getValue(Constants.BUILD_APP_NAME)));
		log.info("Test testGetValue DONE");
	}

}
