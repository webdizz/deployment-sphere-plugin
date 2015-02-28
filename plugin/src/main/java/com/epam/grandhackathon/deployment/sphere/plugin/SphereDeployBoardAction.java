package com.epam.grandhackathon.deployment.sphere.plugin;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.Messages;
import com.epam.grandhackathon.deployment.sphere.plugin.service.EnvironmentDataService;
import hudson.Extension;
import hudson.model.RootAction;
import hudson.security.ACL;
import hudson.security.AccessControlled;
import hudson.security.Permission;
import jenkins.model.Jenkins;
import org.acegisecurity.AccessDeniedException;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;

@ExportedBean(defaultVisibility = 999)
@Extension
public class SphereDeployBoardAction implements RootAction, AccessControlled {

    public SphereDeployBoardAction () {
        Jenkins.getInstance().getInjector().injectMembers(this);
    }

    @Inject
    private EnvironmentDataService environmentDataService;

    @Override
    public String getIconFileName () {
        return "/plugin/deployment-sphere/icons/main_logo.jpg";
    }

    @Override
    public String getDisplayName () {
        return Messages.plugin_title();
    }

    @Override
    public String getUrlName () {
        return "/deployment-sphere";
    }

    @Exported
    public List<EnvironmentMetaData> getEnvironments () {
        return environmentDataService.getAllEnvironments();
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
