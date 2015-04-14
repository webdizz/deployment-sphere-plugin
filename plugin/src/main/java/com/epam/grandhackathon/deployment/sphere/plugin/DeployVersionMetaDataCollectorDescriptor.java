package com.epam.grandhackathon.deployment.sphere.plugin;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;

import jenkins.YesNoMaybe;
import lombok.extern.java.Log;

import org.kohsuke.stapler.QueryParameter;

import com.google.common.base.Strings;

@Log
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class DeployVersionMetaDataCollectorDescriptor extends BuildStepDescriptor<Publisher> {

	public DeployVersionMetaDataCollectorDescriptor() {
        super(DeployVersionMetaDataPublisher.class);
        load();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
        log.log(Level.FINE, String.format("Current project is of type %s", jobType));
        return true;
    }

    public String getDisplayName() {
        return PluginConstants.DEPLOY_METADATA_COLLECTOR_NAME;
    }

    public FormValidation doCheckDeployedAppName(@QueryParameter String deployedAppName) throws IOException,
            ServletException {
        if (Strings.isNullOrEmpty(deployedAppName)) {
            return FormValidation.error("Please set the application name to deploy");
        }
        return FormValidation.ok();
    }
}