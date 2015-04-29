package com.epam.grandhackathon.deployment.sphere.plugin.parameter;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.TreeMap;

import lombok.extern.java.Log;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import com.epam.grandhackathon.deployment.sphere.plugin.BuildVersionMetaDataCollectorDescriptor;
import com.epam.grandhackathon.deployment.sphere.plugin.PluginJenkinsRule;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector.BuildVersionMetaDataCollector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.ApplicationMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDao;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDaoMock;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDao;
import com.epam.grandhackathon.deployment.sphere.plugin.mock.EmptyChangeLogSet;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

@Log
@RunWith(Theories.class)
public class DeployMetaDataParameterDefinitionTest {


	@Rule
	public PluginJenkinsRule jenkinsRule = new PluginJenkinsRule();
	
	private static final String APP_NAME = "appName";
	private static Collection<BuildMetaData> buildMetaDataList = new ArrayList<BuildMetaData>();
	private static Collection<EnvironmentMetaData> environmentMetaDataList = new ArrayList<EnvironmentMetaData>();
	
	static {
		BuildMetaData buildMetaData1 = new BuildMetaData();
		buildMetaData1.setApplicationName(APP_NAME);
		buildMetaData1.setBuildVersion("1.00");
		BuildMetaData buildMetaData2 = new BuildMetaData();
		buildMetaData2.setApplicationName(APP_NAME);
		buildMetaData2.setBuildVersion("2.00");
		BuildMetaData buildMetaData3 = new BuildMetaData();
		buildMetaData3.setApplicationName(APP_NAME);
		buildMetaData3.setBuildVersion("2.03");
		
		buildMetaDataList.add(buildMetaData1);
		buildMetaDataList.add(buildMetaData2);
		buildMetaDataList.add(buildMetaData3);
		
		EnvironmentMetaData environmentMetaData1 = new EnvironmentMetaData("env1");
		EnvironmentMetaData environmentMetaData2 = new EnvironmentMetaData("env2");
		environmentMetaDataList.add(environmentMetaData1);
		environmentMetaDataList.add(environmentMetaData2);
	}
	
	@DataPoints
    public static final BuildDefinitionTestInput[] testData = new BuildDefinitionTestInput[] {
    	new BuildDefinitionTestInput(buildMetaDataList
    			, Arrays.asList(new String[]{"1.00", "2.00", "2.03"} ))
    };
	
	@DataPoints
	public static final EnvironmentDefinitionTestInput[] testData2 = new EnvironmentDefinitionTestInput[] {
    	new EnvironmentDefinitionTestInput(environmentMetaDataList
    			, Arrays.asList(new String[]{"env1", "env2"}))
    };
	
	@Theory
	public void shouldCorrectlyGetBuildVersions(BuildDefinitionTestInput input){
		BuildMetaDataDao buildMetaDataDaoMock = mock(BuildMetaDataDao.class);
		DeployMetaDataParameterDefinition definition = mock(DeployMetaDataParameterDefinition.class);
		when(buildMetaDataDaoMock.findByAppName(Matchers.anyString())).thenReturn(input.getInput());
		when(definition.getBuildMetaDataDao()).thenReturn(buildMetaDataDaoMock);
		when(definition.getBuildVersions()).thenCallRealMethod();
	 	assertTrue(CollectionUtils.isEqualCollection(definition.getBuildVersions(), input.getResult()));
	}
	
	
	@Theory
	public void shouldCorrectlyGetEnvironmentKeys(EnvironmentDefinitionTestInput input){
		EnvironmentDao environmentDaoMock = mock(EnvironmentDao.class);
		DeployMetaDataParameterDefinition definition = mock(DeployMetaDataParameterDefinition.class);
		when(environmentDaoMock.findAll()).thenReturn(input.getInput());
		when(definition.getEnvironmentDao()).thenReturn(environmentDaoMock);
		when(definition.getEnvironmentKeys()).thenCallRealMethod();
	 	assertTrue(CollectionUtils.isEqualCollection(definition.getEnvironmentKeys(), input.getResult()));
	}
}
