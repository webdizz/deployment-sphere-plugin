package com.epam.grandhackathon.deployment.sphere.plugin;


import hudson.Extension;
import hudson.model.RootAction;
import hudson.security.ACL;
import hudson.security.AccessControlled;
import hudson.security.Permission;
import jenkins.model.Jenkins;
import org.acegisecurity.AccessDeniedException;
import org.kohsuke.stapler.export.ExportedBean;

import javax.annotation.Nonnull;

@ExportedBean(defaultVisibility = 999)
@Extension
public class SphereDeployBoardAction implements RootAction, AccessControlled {

    @Override
    public String getIconFileName () {
        return "/plugin/deployment-sphere/icons/main_logo.jpg";
    }

    @Override
    public String getDisplayName () {
        return "sphere";
    }

    @Override
    public String getUrlName () {
        return "/sphere";
    }

    @Nonnull
    @Override
    public ACL getACL () {
        return Jenkins.getInstance().getACL();
    }

    @Override
    public void checkPermission (@Nonnull final Permission permission) throws AccessDeniedException {
        getACL().checkPermission(permission);

    }

    @Override
    public boolean hasPermission (@Nonnull final Permission permission) {
        return getACL().hasPermission(permission);
    }
}
