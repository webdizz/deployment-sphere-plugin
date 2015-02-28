package com.epam.grand.hackathon.deployment.sphere.plugin;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.tasks.Builder;
import jenkins.YesNoMaybe;

@Extension(dynamicLoadable = YesNoMaybe.YES)
public class PluginDescriptor extends Descriptor<Builder> {

    public PluginDescriptor() {
        super(JobExecutionAggregatorConfigurationBuilder.class);
    }

    public String getDisplayName() {
        return "Deployment Sphere";
    }
}