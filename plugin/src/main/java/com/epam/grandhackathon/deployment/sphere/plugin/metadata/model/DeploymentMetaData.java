package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@Data
@EqualsAndHashCode(callSuper = true)
@ExportedBean
public class DeploymentMetaData extends ApplicationMetaData {
    @Exported
    private String buildVersion;
    @Exported
    private String deployedAt;
}
