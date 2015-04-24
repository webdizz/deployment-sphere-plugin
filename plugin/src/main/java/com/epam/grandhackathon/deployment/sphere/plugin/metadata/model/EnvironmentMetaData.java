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
    private String key = null;
    @Exported
    private String title = null;
    @Exported
    private List<DeploymentMetaData> deployments = Lists.newArrayList();
    
    public EnvironmentMetaData() {
    	
    }
    public EnvironmentMetaData(String key, String title) {
    	this.key = key;
    	this.title = title;
	}
    
    @DataBoundConstructor
	public EnvironmentMetaData(String key, String title, List<DeploymentMetaData> deployments) {
		this.key = key;
		this.title = title;
		this.deployments = deployments;
	}
    
}
