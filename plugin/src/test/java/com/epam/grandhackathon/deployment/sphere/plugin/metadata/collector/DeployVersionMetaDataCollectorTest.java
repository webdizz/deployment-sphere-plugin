package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector;

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

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;

import com.epam.grandhackathon.deployment.sphere.plugin.PluginJenkinsRule;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.DeploymentMetaDataDao;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.DeploymentMetaDataDaoMock;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDao;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDaoMock;
import com.epam.grandhackathon.deployment.sphere.plugin.utils.DateFormatUtil;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

@Log
public class DeployVersionMetaDataCollectorTest {
	
	@Rule
	public PluginJenkinsRule jenkinsRule = new PluginJenkinsRule();
	
	private static final Calendar DEPLOYMENT_DATE = new GregorianCalendar();
	
	@Test
	public void shouldGetDateFromBuildAntPutInDeploymentMetaData() {
		DeployVersionMetaDataCollector metaDataCollector = getMetaDataCollectorWithMocks();
		AbstractBuild<?, ?> build = initBuild();
		TaskListener listener = mock(TaskListener.class);
		when(listener.getLogger()).thenReturn(new PrintStream(System.out));
		
		DeploymentMetaData result = metaDataCollector.collect(build, listener);
		assertThat(result.getDeployedAt(),is(DateFormatUtil.formatDate(new DateTime(DEPLOYMENT_DATE))));
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
	private DeployVersionMetaDataCollector getMetaDataCollectorWithMocks() {
		Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            	bind(DeploymentMetaDataDao.class).to(DeploymentMetaDataDaoMock.class);
            	bind(EnvironmentDao.class).to(EnvironmentDaoMock.class);
            }
        });
		DeployVersionMetaDataCollector metaDataCollector = injector.getInstance(DeployVersionMetaDataCollector.class);
		return metaDataCollector;
	}
	
	
}
