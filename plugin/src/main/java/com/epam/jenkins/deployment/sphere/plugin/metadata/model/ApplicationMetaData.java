package com.epam.jenkins.deployment.sphere.plugin.metadata.model;

import lombok.Data;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@Data
@ExportedBean
public class ApplicationMetaData  {
    @Exported
    private String applicationName;

    public ApplicationMetaData() {
	}
    
    @DataBoundConstructor
	public ApplicationMetaData(String applicationName) {
		this.applicationName = applicationName;
	}
}
