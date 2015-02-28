package com.epam.grandhackathon.deployment.sphere.plugin.collector;

import hudson.tasks.BuildStepMonitor;

public class BuildVersionMetadataPublisher extends hudson.tasks.Notifier {
    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }
}
