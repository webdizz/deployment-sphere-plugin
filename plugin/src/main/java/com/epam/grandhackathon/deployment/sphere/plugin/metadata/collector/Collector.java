package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector;

import hudson.model.AbstractBuild;

public interface Collector<T> {

    T collect(AbstractBuild<?, ?> build);
}
