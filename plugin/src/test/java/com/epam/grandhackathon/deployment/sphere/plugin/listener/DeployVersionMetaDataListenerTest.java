package com.epam.grandhackathon.deployment.sphere.plugin.listener;

import com.epam.grandhackathon.deployment.sphere.plugin.PluginJenkinsRule;
import com.epam.grandhackathon.deployment.sphere.plugin.DeployVersionMetaDataPublisher;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;

import hudson.model.FreeStyleProject;
import hudson.model.ParameterDefinition;
import hudson.model.ParametersDefinitionProperty;
import lombok.extern.java.Log;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@Log
public class DeployVersionMetaDataListenerTest {
    @Rule
    public PluginJenkinsRule jenkinsRule = new PluginJenkinsRule();

    @Test
    public void shouldCreateConfigParameterOnCreate() throws Exception {
        log.info("Create project with Deployment Sphere plugin");
        assertTrue(checkProjectConfigurationWithPlugin(true));
        log.info("Done.");

    }

    @Test
    public void shouldCreateConfigParameterOnUpdate() throws Exception {
        log.info("Update project with Deployment Sphere plugin");
        assertTrue(checkProjectConfigurationWithPlugin(false));
        log.info("Done.");
    }

    private boolean checkProjectConfigurationWithPlugin(boolean isCreate) throws Exception {
        FreeStyleProject project = jenkinsRule.createFreeStyleProject("Deployment job");
        project.getPublishersList().add(new DeployVersionMetaDataPublisher());
        project.save();
        DeployVersionMetaDataListener listener = new DeployVersionMetaDataListener();
        if (isCreate) {
            listener.onCreated(project);
        } else {
            listener.onUpdated(project);
        }
        ParametersDefinitionProperty paramDefProp = (ParametersDefinitionProperty) project.getProperty(ParametersDefinitionProperty.class);
        if (null != paramDefProp) {
            for (ParameterDefinition pDef : paramDefProp.getParameterDefinitions()) {
                if (pDef.getName().equals(Constants.DEPLOY_META_DATA)) {
                    return true;
                }
            }
        }
        return false;
    }
}
