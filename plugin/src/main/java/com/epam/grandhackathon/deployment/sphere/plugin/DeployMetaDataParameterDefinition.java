package com.epam.grandhackathon.deployment.sphere.plugin;

import hudson.Extension;
import hudson.model.ChoiceParameterDefinition;
import hudson.model.ParameterValue;
import hudson.model.Job;
import hudson.model.ParameterDefinition;
import hudson.model.ParametersDefinitionProperty;
import hudson.util.ListBoxModel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import jenkins.model.Jenkins;
import lombok.extern.java.Log;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.export.Exported;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDao;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDao;
import com.google.common.collect.Lists;

@Log
public class DeployMetaDataParameterDefinition extends ParameterDefinition {
    @Inject
    private BuildMetaDataDao buildMetaDataDao;
    @Inject
    private EnvironmentDao environmentDao;
	
    private String environmentKey;
    private String buildVersion;
    private String applicationName;

	@DataBoundConstructor
	public DeployMetaDataParameterDefinition(String name, String description, String environmentKey, String buildVersion, String applicationName) {
		super(name, description);
		this.environmentKey = environmentKey;
		this.buildVersion = buildVersion;
		this.applicationName = applicationName;
	}
	
	@Exported
	public String getApplicationName(){
		return applicationName;
	}

	@Override
	public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
		DeployMetaDataParameterValue value = req.bindJSON(DeployMetaDataParameterValue.class, jo);
		return value;
	}

	@Override
	public ParameterValue createValue(StaplerRequest req) {
		return new DeployMetaDataParameterValue(getName(), this.environmentKey, this.buildVersion, this.applicationName, "Deploy meta data parameter");
	}
	
	public List<String> getBuildVersions() {
		Jenkins.getInstance().getInjector().injectMembers(this);
		Collection<BuildMetaData> builds = buildMetaDataDao.findByAppName(applicationName);
        List<String> versions = Lists.newArrayList();
        for (BuildMetaData build : builds) {
            versions.add(build.getBuildVersion());
        }
        return versions;
    }

    public List<String> getEnvironmentKeys() {
    	Jenkins.getInstance().getInjector().injectMembers(this);
    	Collection<EnvironmentMetaData> envs = environmentDao.findAll();
        List<String> environments = Lists.newArrayList();
        for (EnvironmentMetaData env : envs) {
            environments.add(env.getTitle());
        }
        return environments;
    }

	@Extension
	public static final class DescriptorImpl extends ParameterDescriptor {

		@Override
		public String getDisplayName() {
			return "Deploy meta data parameter";
		}
	}
}