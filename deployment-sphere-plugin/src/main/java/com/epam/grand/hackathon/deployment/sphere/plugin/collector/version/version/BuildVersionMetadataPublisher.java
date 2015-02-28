package com.epam.grand.hackathon.deployment.sphere.plugin.collector.version.version;

import hudson.tasks.BuildStepMonitor;

public class BuildVersionMetadataPublisher extends hudson.tasks.Notifier {
    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }
}
