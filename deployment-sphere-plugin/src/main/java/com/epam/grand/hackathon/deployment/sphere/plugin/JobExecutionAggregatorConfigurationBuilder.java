package com.epam.grand.hackathon.deployment.sphere.plugin;

import java.io.IOException;

import org.kohsuke.stapler.DataBoundConstructor;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.Builder;
import lombok.extern.java.Log;

@Log
public class JobExecutionAggregatorConfigurationBuilder extends Builder {

    @DataBoundConstructor
    public JobExecutionAggregatorConfigurationBuilder() {
    }

    @Override
    public boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher, final BuildListener listener) throws IOException, InterruptedException {
        return false;
    }
}
