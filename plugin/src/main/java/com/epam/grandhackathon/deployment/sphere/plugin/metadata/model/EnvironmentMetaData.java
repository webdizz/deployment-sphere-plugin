package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model;

import com.google.common.collect.Lists;

import lombok.Data;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import java.util.List;

@Data
@ExportedBean
public class EnvironmentMetaData {

    @Exported
    private String identity = null;
    @Exported
    private String title = null;
    @Exported
    private List<DeploymentMetaData> deployments = Lists.newArrayList();
    
    public EnvironmentMetaData() {
	}
    
    @DataBoundConstructor
	public EnvironmentMetaData(String identity, String title, List<DeploymentMetaData> deployments) {
		this.identity = identity;
		this.title = title;
		this.deployments = deployments;
	}
    
}
