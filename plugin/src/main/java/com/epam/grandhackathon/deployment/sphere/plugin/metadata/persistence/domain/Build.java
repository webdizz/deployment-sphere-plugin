package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Build implements Serializable {
    private String applicationName;

    private String buildVersion;

    private Long number;

    private Long builtAt;
}
