package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model;

import org.joda.time.DateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BuildMetaData extends ApplicationMetaData {

    private Integer number;
    private String applicationName;
    private String name;
    private DateTime builtAt;
    private String buildVersion;
}
