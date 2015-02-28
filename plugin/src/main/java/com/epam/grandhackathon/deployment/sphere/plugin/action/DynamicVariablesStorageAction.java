package com.epam.grandhackathon.deployment.sphere.plugin.action;

import hudson.EnvVars;
import hudson.model.EnvironmentContributingAction;
import hudson.model.InvisibleAction;
import hudson.model.AbstractBuild;

public class DynamicVariablesStorageAction extends InvisibleAction implements EnvironmentContributingAction {

    private final String key;
    private final String value;

    public DynamicVariablesStorageAction(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void buildEnvVars(final AbstractBuild<?, ?> build, final EnvVars env) {
        env.put(key, value);
    }

}
