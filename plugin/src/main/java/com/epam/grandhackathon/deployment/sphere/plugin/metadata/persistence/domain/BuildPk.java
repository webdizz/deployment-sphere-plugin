package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class BuildPk implements Serializable {
    private Integer number;
    private String jobName;
}
