package com.epam.jenkins.deployment.sphere.plugin;

import com.epam.jenkins.deployment.sphere.plugin.metadata.model.ApplicationMetaData;
import com.epam.jenkins.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.jenkins.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.jenkins.deployment.sphere.plugin.metadata.persistence.dao.ApplicationDao;
import com.epam.jenkins.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDao;
import com.epam.jenkins.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDao;
import com.google.common.collect.Lists;

import hudson.Extension;
import hudson.Functions;
import hudson.model.Describable;
import hudson.model.RootAction;
import hudson.model.Descriptor;
import hudson.model.ParameterDefinition.ParameterDescriptor;
import hudson.security.ACL;
import hudson.security.AccessControlled;
import hudson.security.Permission;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;

import org.acegisecurity.AccessDeniedException;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ExportedBean(defaultVisibility = 999)
@Extension
public class CommitSphereDashboardAction implements RootAction, AccessControlled{

	
	
    @Inject
    private ApplicationDao applicationDao;
    
    @Inject
    private BuildMetaDataDao buildMetaDataDao;

    public String environment = "";
    
    @DataBoundConstructor
    public CommitSphereDashboardAction() {
        Jenkins.getInstance().getInjector().injectMembers(this);
    }

    @Override
    public String getIconFileName() {
        return "/plugin/deployment-sphere/icons/main_logo.jpg";
    }

    @Override
    public String getDisplayName() {
        return "Commit Sphere";
    }


	@Override
    public String getUrlName() {
        return "/"+ "commit-sphere";
    }

    @Exported
    public String getResourceUrl() {
        return Functions.getResourcePath();
    }

    
    @Exported
    public Collection<ApplicationMetaData> getApplications() {
		return applicationDao.findAll();
    }
    
    @Exported
    public Collection<BuildMetaData> getBuilds() {
		return buildMetaDataDao.findAll();
    }
    
    @Nonnull
    @Override
    public ACL getACL() {
        return Jenkins.getInstance().getACL();
    }

    @Override
    public void checkPermission(@Nonnull final Permission permission) throws AccessDeniedException {
        getACL().checkPermission(permission);

    }

    @Override
    public boolean hasPermission(@Nonnull final Permission permission) {
        return getACL().hasPermission(permission);
    }

	
	
    

}
