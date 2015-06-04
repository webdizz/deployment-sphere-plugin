package com.epam.jenkins.deployment.sphere.plugin.metadata.model;

import com.google.common.collect.Lists;

import lombok.Data;

import org.codehaus.jackson.map.ObjectMapper;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import java.io.IOException;
import java.util.List;

@Data
@ExportedBean
public class EnvironmentMetaData {

    @Exported
    private String key;
    @Exported
    private String title;
    @Exported
    private List<DeploymentMetaData> deployments = Lists.newArrayList();
    
    public EnvironmentMetaData(String key, String title) {
    	this.key = key;
    	this.title = title;
	}
    
    @DataBoundConstructor
	public EnvironmentMetaData(String key, String title, List<DeploymentMetaData> deployments) {
		this(key, title);
		this.deployments = deployments;
	}
    
    @Override
    public String toString() {
        String toString = null;
        try {
            toString =  new ObjectMapper().writeValueAsString(this);
        } catch (IOException e) {
        }
        return toString;
    }
}
