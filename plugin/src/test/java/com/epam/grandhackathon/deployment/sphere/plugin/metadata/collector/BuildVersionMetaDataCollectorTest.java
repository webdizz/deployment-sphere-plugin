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

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDao;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDaoMock;
import com.epam.grandhackathon.deployment.sphere.plugin.mock.EmptyChangeLogSet;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

@RunWith(Theories.class)
public class BuildVersionMetaDataCollectorTest {

	@DataPoints
    public static final String[][] testData = new String [][]{
		{"1", "buildId",  "jobName", "1.2", "app-name", "http://test.com/test"}
    };

	private AbstractBuild<?, ?> initBuild(String[] testData){
		AbstractBuild<?, ?> build = Mockito.mock(AbstractBuild.class);
		Mockito.when(build.getNumber()).thenReturn(Integer.parseInt(testData[0]));
		Mockito.when(build.getId()).thenReturn(testData[1]);
		Mockito.when(build.getChangeSet()).thenReturn(new EmptyChangeLogSet(build));
		Mockito.when(build.getDisplayName()).thenReturn(testData[2]);
		Mockito.when(build.due()).thenReturn(Calendar.getInstance());
		TreeMap<String, String> treeMap = new TreeMap<String,String>();
		treeMap.put(Constants.BUILD_VERSION, testData[3]);
		treeMap.put(Constants.BUILD_APP_NAME, testData[4]);
		try {
			Mockito.when(build.getEnvironment(any(TaskListener.class))).thenReturn(new EnvVars(treeMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return build;
	}
	private BuildVersionMetaDataCollector getMetaDataCollectorWithMocks() {
		Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            	bind(BuildMetaDataDao.class).to(BuildMetaDataDaoMock.class);
            }
        });
		BuildVersionMetaDataCollector metaDataCollector = injector.getInstance(BuildVersionMetaDataCollector.class);
		return metaDataCollector;
	}
	
	@Theory
	public void shouldGetUrlFromBuildAntPutInBuildMetaDataUrl(String[] testData) {
		BuildVersionMetaDataCollector metaDataCollector = getMetaDataCollectorWithMocks();
		AbstractBuild<?, ?> build = initBuild(testData);
		
		Mockito.when(build.getUrl()).thenReturn(testData[5]);
		
		TaskListener listener = Mockito.mock(TaskListener.class);
		Mockito.when(listener.getLogger()).thenReturn(new PrintStream(System.out));
		
		BuildMetaData result = metaDataCollector.collect(build, listener);
		assertThat(result.getBuildUrl(),is(testData[5]));
	}
	
}
