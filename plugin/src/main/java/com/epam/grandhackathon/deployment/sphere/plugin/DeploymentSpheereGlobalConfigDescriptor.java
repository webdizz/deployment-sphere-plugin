package com.epam.grandhackathon.deployment.sphere.plugin;

import static com.google.common.base.Preconditions.checkState;
import hudson.Extension;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import hudson.model.AbstractProject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.StaplerRequest;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.ApplicationMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDao;
import com.google.common.base.Strings;

public class DeploymentSpheereGlobalConfigDescriptor extends JobProperty<AbstractProject<?, ?>>  {
	
	//private List<ApplicationName> applications = new ArrayList<ApplicationName>();
	
	/*@DataBoundConstructor
	public DeploymentSpheereGlobalConfigDescriptor(List<ApplicationName> applications){
		if (applications==null){
			applications = new ArrayList<ApplicationName>();		
		}
		this.applications = applications;
	}*/
	
    @Extension
    public static final class CustomViewsTabBarDescriptor extends JobPropertyDescriptor {
    	private List<ApplicationName> applications = new ArrayList<ApplicationName>();
    	private String enviroments;
    	
    	public List<ApplicationName> getApplications(){
    		return applications;
        }
    	
    	public String getEnviroments() {
    		return enviroments;
    	}
    	
    	public void setEnviroments(String enviroments) {
			this.enviroments = enviroments;
		}
    	
    	@Inject
        private EnvironmentDao environmentDao;
    	
        public CustomViewsTabBarDescriptor() {
        	super(DeploymentSpheereGlobalConfigDescriptor.class);
            load();
            Jenkins.getInstance().getInjector().injectMembers(this);
        }

        @Override
        public String getDisplayName() {
            return "Custom Views TabBar";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
        	List<EnvironmentMetaData> enviroments = req.bindJSONToList(EnvironmentMetaData.class, formData);
        	List<ApplicationMetaData> applications = req.bindJSONToList(ApplicationMetaData.class,formData);
        	//req.bindJSONToList(type, src)
        	
        	/*String labelText = formData.getString("enviroments");
        	checkState(!Strings.isNullOrEmpty(labelText), String.format("Invalid enviroments", labelText));
        	String[] enviroments = StringUtils.split(labelText, ", ;");
        	environmentDao.deleteAll();
        	for (String string : enviroments) {
        		environmentDao.save(new EnvironmentMetaData(string));
			}
        	this.enviroments = formData.getString("enviroments");*/
            save();
            return false;
        }
    }
}
