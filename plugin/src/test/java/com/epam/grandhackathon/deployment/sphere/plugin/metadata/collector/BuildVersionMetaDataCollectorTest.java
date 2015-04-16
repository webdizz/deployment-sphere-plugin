package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import hudson.EnvVars;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDao;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDaoMock;
import com.epam.grandhackathon.deployment.sphere.plugin.mock.EmptyChangeLogSet;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

@RunWith(MockitoJUnitRunner.class)
public class BuildVersionMetaDataCollectorTest {
	
	private static final String BUILD_VERSION = "1.2";
	private static final Integer BUILD_NUMBER = 1;
	private static final String BUILD_ID = "buildId";
	private static final String JOB_DISPLAY_NAME = "jobName";
	private static final String BUILD_APP_NAME = "app-name";

	private Injector injector;
	private BuildVersionMetaDataCollector metaDataCollector;
	
	private AbstractBuild<?, ?> build;
	private TaskListener listener;
	
	@Before
	public void setUp() throws Exception {
		injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            	bind(BuildMetaDataDao.class).to(BuildMetaDataDaoMock.class);
            }
        });
		initBuild();
		
		listener = Mockito.mock(TaskListener.class);
		Mockito.when(listener.getLogger()).thenReturn(new PrintStream(System.out));
		
		metaDataCollector = injector.getInstance(BuildVersionMetaDataCollector.class);
	}

	private void initBuild(){
		build = Mockito.mock(AbstractBuild.class);
		Mockito.when(build.getNumber()).thenReturn(BUILD_NUMBER);
		Mockito.when(build.getId()).thenReturn(BUILD_ID);
		Mockito.when(build.getChangeSet()).thenReturn(new EmptyChangeLogSet(build));
		Mockito.when(build.getDisplayName()).thenReturn(JOB_DISPLAY_NAME);
		Mockito.when(build.due()).thenReturn(Calendar.getInstance());
		TreeMap<String, String> treeMap = new TreeMap<String,String>();
		treeMap.put(Constants.BUILD_VERSION, BUILD_VERSION);
		treeMap.put(Constants.BUILD_APP_NAME, BUILD_APP_NAME);
		try {
			Mockito.when(build.getEnvironment(any(TaskListener.class))).thenReturn(new EnvVars(treeMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCollect() {
		String TEST_URL = "http://test.com/test";
		Mockito.when(build.getUrl()).thenReturn(TEST_URL);
		
		BuildMetaData result = metaDataCollector.collect(build, listener);
		assertThat(result.getBuildUrl(),is(TEST_URL));
	}
}
