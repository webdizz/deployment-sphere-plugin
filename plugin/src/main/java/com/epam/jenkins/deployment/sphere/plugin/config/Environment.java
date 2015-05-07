package com.epam.jenkins.deployment.sphere.plugin.config;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;

import org.kohsuke.stapler.DataBoundConstructor;

public class Environment implements Describable<Environment> {
	@Extension
	public final static DescriptorImpl DESCRIPOR = new DescriptorImpl();
	private String title;

	public static class DescriptorImpl extends Descriptor<Environment> {

		@Override
		public String getDisplayName() {
			return "Enviroment";
		}
	};


	@DataBoundConstructor
	public Environment(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public Descriptor<Environment> getDescriptor() {
		return DESCRIPOR;
	}
}
