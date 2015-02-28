package com.epam.grandhackathon.deployment.sphere.plugin;

import hudson.Extension;
import hudson.model.ParametersAction;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean(defaultVisibility = 999)
@Extension
public class DeploymentDetailsAction extends ParametersAction {

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Deployment Details";
    }

    @Override
    public String getUrlName() {
        return "/deployment-sphere/{}";
    }

}
