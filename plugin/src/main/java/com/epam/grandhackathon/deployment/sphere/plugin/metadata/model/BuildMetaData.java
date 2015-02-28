package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model;

import org.joda.time.DateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class BuildMetaData extends ApplicationMetaData {

    private Integer number;
    private String jobName;
    private DateTime builtAt;
    private String buildVersion;
}
