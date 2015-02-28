package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model;

import org.joda.time.DateTime;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ExportedBean
public class DeploymentMetaData extends ApplicationMetaData {
    @Exported
    private String buildVersion;
    @Exported
    private DateTime deployedAt;
}
