package com.epam.grandhackathon.deployment.sphere.plugin;

import static com.google.common.base.Preconditions.checkState;
import hudson.Extension;
import hudson.views.ViewsTabBar;
import hudson.views.ViewsTabBarDescriptor;

import javax.inject.Inject;

import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDao;
import com.google.common.base.Strings;

public class DeploymentSpheereGlobalConfigDescriptor extends ViewsTabBar  {
	@DataBoundConstructor
    public DeploymentSpheereGlobalConfigDescriptor() {
        super();
	}
    
	
    @Extension
    public static final class CustomViewsTabBarDescriptor extends ViewsTabBarDescriptor {
    	@Inject
        private EnvironmentDao environmentDao;
    	
        public CustomViewsTabBarDescriptor() {
            load();
            Jenkins.getInstance().getInjector().injectMembers(this);
        }

        @Override
        public String getDisplayName() {
            return "Custom Views TabBar";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
        	String labelText = formData.getString("enviroments");
        	checkState(!Strings.isNullOrEmpty(labelText), String.format("Invalid enviroments", labelText));
        	EnvironmentMetaData environmentMetaData = new EnvironmentMetaData(labelText);
        	environmentDao.save(environmentMetaData);
            save();
            return false;
        }
    }
}
