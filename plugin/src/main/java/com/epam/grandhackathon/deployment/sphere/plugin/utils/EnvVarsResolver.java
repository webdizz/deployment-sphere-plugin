package com.epam.grandhackathon.deployment.sphere.plugin.utils;

import hudson.EnvVars;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;

import java.io.IOException;
import java.util.logging.Level;

import lombok.extern.java.Log;

import com.google.common.base.Throwables;

@Log
public final class EnvVarsResolver {
    private final AbstractBuild<?, ?> build;
    private final TaskListener taskListener;

    public EnvVarsResolver(final AbstractBuild<?, ?> build, final TaskListener taskListener) {
        this.build = build;
        this.taskListener = taskListener;
    }

    public String getValue(final String key) {
        EnvVars env = getEnvVars(this.build, this.taskListener);
        return env.get(key);
    }

    private static EnvVars getEnvVars(final AbstractBuild<?, ?> build, final TaskListener taskListener) {
        try {
            return build.getEnvironment(taskListener);
        } catch (final IOException | InterruptedException exc) {
            log.log(Level.SEVERE, "Failed getting env variables", exc);
            throw Throwables.propagate(exc);
        }
    }
}
