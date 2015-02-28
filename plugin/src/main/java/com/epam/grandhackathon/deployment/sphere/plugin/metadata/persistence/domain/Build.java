package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;

import java.io.Serializable;

import org.joda.time.DateTime;
import lombok.Data;

@Data
public class Build implements Serializable {
    private String applicationName;

    private String buildVersion;
    private Long buildNumber;
    private DateTime builtAt;
}
