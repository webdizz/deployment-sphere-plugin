package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model;

import lombok.Data;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import java.util.List;

@Data
@ExportedBean
public class EnvironmentMetaData {

    @Exported
    private Long identity;
    @Exported
    private final String title;
    @Exported
    private List<DeploymentMetaData> deployments;

}
