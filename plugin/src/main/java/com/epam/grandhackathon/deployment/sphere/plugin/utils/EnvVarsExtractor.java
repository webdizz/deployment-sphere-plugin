package com.epam.grandhackathon.deployment.sphere.plugin.utils;

import java.io.IOException;
import java.util.logging.Level;

import lombok.extern.java.Log;

import com.google.common.base.Throwables;

import hudson.EnvVars;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;

@Log
public final class EnvVarsExtractor {
    private final AbstractBuild<?, ?> build;
    private final TaskListener taskListener;

    public EnvVarsExtractor(final AbstractBuild<?, ?> build, final TaskListener taskListener) {
        this.build = build;
        this.taskListener = taskListener;
    }

    public String getValue(final String key) {
        return extract().get(key);
    }

    public void putValue(final String key, final String value) {
        extract().put(key, value);
    }

    public EnvVars extract() {
        try {
            return build.getEnvironment(this.taskListener);
        } catch (final IOException | InterruptedException exc) {
            log.log(Level.SEVERE, "Failed getting env variables", exc);
            throw Throwables.propagate(exc);
        }
    }
}
