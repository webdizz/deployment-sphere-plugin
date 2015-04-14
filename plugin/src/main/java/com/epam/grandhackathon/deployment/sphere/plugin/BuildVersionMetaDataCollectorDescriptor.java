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
public class BuildVersionMetaDataCollectorDescriptor extends BuildStepDescriptor<Publisher> {

    public BuildVersionMetaDataCollectorDescriptor() {
        super(BuildVersionMetaDataPublisher.class);
        load();
    }

    @Override
    public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
        log.log(Level.FINE, String.format("Current project is of type %s", jobType));
        return true;
    }

    public String getDisplayName() {
        return PluginConstants.BUILD_METADATA_COLLECTOR_NAME;
    }

    public FormValidation doCheckVersionPattern(@QueryParameter String versionPattern) throws IOException,
            ServletException {
        if (Strings.isNullOrEmpty(versionPattern)) {
            return FormValidation
                    .error("Please set the version pattern in the following format. x.x.{v}, where x - is any string value");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckAppName(@QueryParameter String appName) throws IOException, ServletException {
        if (Strings.isNullOrEmpty(appName)) {
            return FormValidation.error("Please set the application name");
        }
        return FormValidation.ok();
    }

}