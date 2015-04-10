package com.epam.grandhackathon.deployment.sphere.plugin.listener;

import hudson.model.FreeStyleProject;
import hudson.model.ParameterDefinition;
import hudson.model.ParametersDefinitionProperty;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.grandhackathon.deployment.sphere.plugin.DeployVersionMetaDataPublisher;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;

public class DeployVersionMetaDataListenerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeployVersionMetaDataListenerTest.class);
    
	@Rule
    public JenkinsRule j = new JenkinsRule();
	
	@Test
	public void testOnCreatedItem() throws Exception {
		LOGGER.info("Create project with Deployment Sphere plugin");
		checkProjectConfigurationWithPlugin();
		LOGGER.info("Done.");
		
	}

	@Test
	public void testOnUpdatedItem() throws Exception{
		LOGGER.info("Update project with Deployment Sphere plugin");
		checkProjectConfigurationWithPlugin();
		LOGGER.info("Done.");	
	}
	
	private void checkProjectConfigurationWithPlugin() throws Exception{
		FreeStyleProject project = j.createFreeStyleProject("Deployment job");
		project.getPublishersList().add(new DeployVersionMetaDataPublisher());
		project.save();
		DeployVersionMetaDataListener listener = new DeployVersionMetaDataListener();
		listener.onCreated(project);
		ParametersDefinitionProperty paramDefProp = (ParametersDefinitionProperty)project.getProperty(ParametersDefinitionProperty.class);
		if (null != paramDefProp){
			for(ParameterDefinition pDef : paramDefProp.getParameterDefinitions()){
				if (pDef.getName().equals(Constants.DEPLOY_META_DATA)){
					return;
				}
			}
		}
		throw new Exception("Project configurution failure with Deployment Sphere plugin");	
	}

}
