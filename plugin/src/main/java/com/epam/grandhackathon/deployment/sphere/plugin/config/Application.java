package com.epam.grandhackathon.deployment.sphere.plugin.config;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;

import org.kohsuke.stapler.DataBoundConstructor;

public class Application implements Describable<Application> {

	@Extension
	public final static DescriptorImpl DESCRIPOR = new DescriptorImpl();
	private String applicationName;

	public static class DescriptorImpl extends Descriptor<Application> {

		@Override
		public String getDisplayName() {
			return "Application";
		}
	};


	@DataBoundConstructor
	public Application(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	@Override
	public Descriptor<Application> getDescriptor() {
		return DESCRIPOR;
	}
}
