package com.epam.jenkins.deployment.sphere.plugin.metadata.collector;

import hudson.model.TaskListener;
import hudson.model.AbstractBuild;

public interface Collector<T> {

    T collect(AbstractBuild<?, ?> build, TaskListener taskListener);
}
