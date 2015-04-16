package com.epam.grandhackathon.deployment.sphere.plugin;

import hudson.model.AbstractProject;

public class TestApplicableInput extends TestInput<Class<? extends AbstractProject>, Boolean> {

	public TestApplicableInput(Class<? extends AbstractProject> input, Boolean result) {
		super(input, result);
	}
}