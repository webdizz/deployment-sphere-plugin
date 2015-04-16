package com.epam.grandhackathon.deployment.sphere.plugin.listener;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import hudson.model.FreeStyleProject;
import hudson.model.ParameterDefinition;
import hudson.model.ParametersDefinitionProperty;

import org.junit.Rule;
import org.junit.Test;

import com.epam.grandhackathon.deployment.sphere.plugin.PluginJenkinsRule;
import com.epam.grandhackathon.deployment.sphere.plugin.DeployVersionMetaDataPublisher;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;

public class DeployVersionMetaDataListenerTest {
	@Rule
	public PluginJenkinsRule jenkinsRule = new PluginJenkinsRule();

	@Test
	public void shouldCreateConfigParameterOnCreateProject() throws Exception {
		assertThat(checkProjectConfigurationWithPlugin(true), is(true));
	}

	@Test
	public void shouldCreateConfigParameterOnUpdateProject() throws Exception {
		assertThat(checkProjectConfigurationWithPlugin(false), is(true));
	}

	private boolean checkProjectConfigurationWithPlugin(boolean isCreate)
			throws Exception {
		FreeStyleProject project = jenkinsRule
				.createFreeStyleProject("Deployment job");
		project.getPublishersList().add(new DeployVersionMetaDataPublisher());
		project.save();
		DeployVersionMetaDataListener listener = new DeployVersionMetaDataListener();
		if (isCreate) {
			listener.onCreated(project);
		} else {
			listener.onUpdated(project);
		}
		ParametersDefinitionProperty paramDefProp = (ParametersDefinitionProperty) project
				.getProperty(ParametersDefinitionProperty.class);
		if (null != paramDefProp) {
			for (ParameterDefinition pDef : paramDefProp
					.getParameterDefinitions()) {
				if (pDef.getName().equals(Constants.DEPLOY_META_DATA)) {
					return true;
				}
			}
		}
		return false;
	}
}
