package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model;

import com.google.common.collect.Lists;
import lombok.Data;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import java.util.List;

@Data
@ExportedBean
public class EnvironmentMetaData {

    @Exported
    private String identity;
    @Exported
    private final String title;
    @Exported
    private List<DeploymentMetaData> deployments = Lists.newArrayList();

}
