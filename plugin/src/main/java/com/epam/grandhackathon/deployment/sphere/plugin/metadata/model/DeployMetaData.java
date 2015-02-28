package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model;

import org.joda.time.DateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeployMetaData extends ApplicationMetaData {
    private String deployedVersion;
    private Integer number;
    private String jobName;
    private DateTime deployedAt;
}
