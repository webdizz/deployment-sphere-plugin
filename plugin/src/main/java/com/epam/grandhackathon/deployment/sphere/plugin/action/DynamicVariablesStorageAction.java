package com.epam.grandhackathon.deployment.sphere.plugin.action;

import java.util.HashMap;
import java.util.Map;

import hudson.EnvVars;
import hudson.model.EnvironmentContributingAction;
import hudson.model.InvisibleAction;
import hudson.model.AbstractBuild;

public class DynamicVariablesStorageAction extends InvisibleAction implements EnvironmentContributingAction {

    private final Map<String, String> valuesMap;

    public DynamicVariablesStorageAction(final Map<String, String> map) {
        this.valuesMap = map;
    }

    public DynamicVariablesStorageAction(final String key, final String value) {
        this.valuesMap = new HashMap<>();
        this.valuesMap.put(key, value);
    }

    @Override
    public void buildEnvVars(final AbstractBuild<?, ?> build, final EnvVars env) {
        for (String key : this.valuesMap.keySet()) {
            env.put(key, this.valuesMap.get(key));
        }
    }

}
