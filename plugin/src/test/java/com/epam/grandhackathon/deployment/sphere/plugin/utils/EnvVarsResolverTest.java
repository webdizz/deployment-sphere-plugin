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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
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
	
	private static final String TEST_ENV_KEY = "qa";
	private static final String TEST_BUILD_VERSION = "0.8";
	private static final String TEST_APP_NAME = "app1";
	
	private EnvVarsResolver resolver;

	@Before
	public void setUp() throws Exception {
		FreeStyleProject project = j.createFreeStyleProject("Deployment test job");	
		FreeStyleBuild build = project.scheduleBuild2(0).get();
		DeployMetaDataParameterValue value = new DeployMetaDataParameterValue(Constants.DEPLOY_META_DATA, TEST_ENV_KEY, TEST_BUILD_VERSION, TEST_APP_NAME);
		EnvironmentVariablesNodeProperty prop = new EnvironmentVariablesNodeProperty();
	    EnvVars envVars = prop.getEnvVars();
		value.buildEnvVars(build, envVars);
		
		build.addAction(new DynamicVariablesStorageAction(envVars));
		
		LogTaskListener listener = new LogTaskListener(log.getLogger(Jenkins.class.getName()), Level.INFO);
		resolver = new EnvVarsResolver(build, listener);
	}

	@Test
	public void shouldCheckValuesFromResolverGetter() throws Exception {
		assertThat(TEST_BUILD_VERSION.equals(resolver.getValue(Constants.BUILD_VERSION)), is(true));
		assertThat(TEST_ENV_KEY.equals(resolver.getValue(Constants.ENV_NAME)), is(true));
		assertThat(TEST_APP_NAME.equals(resolver.getValue(Constants.BUILD_APP_NAME)), is(true));
	}

}
