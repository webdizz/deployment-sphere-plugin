package com.epam.grand.hackathon.deployment.sphere.plugin;


import hudson.Extension;
import hudson.model.RootAction;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean(defaultVisibility = 999)
@Extension
public class SphereDeployBoardAction implements RootAction {

    @Override
    public String getIconFileName () {
        return null;
    }

    @Override
    public String getDisplayName () {
        return "sphere";
    }

    @Override
    public String getUrlName () {
        return "/sphere";
    }
}
