package com.epam.jenkins.deployment.sphere.plugin.metadata.collector;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import hudson.EnvVars;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import lombok.extern.java.Log;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.jenkins.deployment.sphere.plugin.PluginJenkinsRule;
import com.epam.jenkins.deployment.sphere.plugin.metadata.Constants;
import com.epam.jenkins.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.jenkins.deployment.sphere.plugin.metadata.persistence.dao.DeploymentMetaDataDao;
import com.epam.jenkins.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDao;
import com.epam.jenkins.deployment.sphere.plugin.metadata.persistence.domain.Environment;
import com.epam.jenkins.deployment.sphere.plugin.utils.DateFormatUtil;

@Log
public class DeployVersionMetaDataCollectorTest {
	
	@Rule
	public PluginJenkinsRule jenkinsRule = new PluginJenkinsRule();
	
	@Mock
	DeploymentMetaDataDao deploymentMetaDataDao;
	
	@Mock
	EnvironmentDao environmentDao;
	
	
	@InjectMocks
	DeployVersionMetaDataCollector metaDataCollector;
	
	private static final Calendar DEPLOYMENT_DATE = new GregorianCalendar();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		Environment env = new Environment();
		env.setKey("env_key");
		when(environmentDao.find("env-name")).thenReturn(env);
	}
	
	@Test
	public void shouldGetDateFromBuildAntPutInDeploymentMetaData() {
		AbstractBuild<?, ?> build = initBuild();
		TaskListener listener = mock(TaskListener.class);
		when(listener.getLogger()).thenReturn(new PrintStream(System.out));
		
		DeploymentMetaData result = metaDataCollector.collect(build, listener);
		assertThat(result.getDeployedAt(), Matchers.is(DateFormatUtil.formatDate(new DateTime(DEPLOYMENT_DATE))));
	}
	
	private AbstractBuild<?, ?> initBuild(){
		AbstractBuild<?, ?> build = mock(AbstractBuild.class);
		TreeMap<String, String> treeMap = new TreeMap<String,String>();
		when(build.due()).thenReturn(DEPLOYMENT_DATE);
		treeMap.put(Constants.BUILD_VERSION, "1.2");
		treeMap.put(Constants.BUILD_APP_NAME, "app-name");
		treeMap.put(Constants.ENV_NAME, "env-name");
		try {
			when(build.getEnvironment(any(TaskListener.class))).thenReturn(new EnvVars(treeMap));
		} catch (Exception e) {
			log.info("Get environment was failed:" + e.getMessage());
		}
		return build;
	}

	
}
