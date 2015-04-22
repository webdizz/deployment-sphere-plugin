package com.epam.grandhackathon.deployment.sphere.plugin;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;

import org.kohsuke.stapler.DataBoundConstructor;

public class ApplicationName implements Describable<ApplicationName> {

	@Extension
	public final static DescriptorImpl DESCRIPOR = new DescriptorImpl();
	private String applicationName;

	public static class DescriptorImpl extends Descriptor<ApplicationName> {

		@Override
		public String getDisplayName() {
			return "ApplicationName";
		}
	};


	@DataBoundConstructor
	public ApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	@Override
	public Descriptor<ApplicationName> getDescriptor() {
		return DESCRIPOR;
	}
}
