package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.joda.time.DateTime;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@Data
@EqualsAndHashCode(callSuper = true)
@ExportedBean
public class DeployMetaData extends ApplicationMetaData {
    @Exported
    private String deployedVersion;
    @Exported
    private Integer number;
    @Exported
    private String jobName;
    @Exported
    private DateTime deployedAt;
}
