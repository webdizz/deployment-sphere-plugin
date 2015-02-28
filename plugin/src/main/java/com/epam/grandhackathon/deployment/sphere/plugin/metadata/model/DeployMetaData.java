package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeployMetaData extends ApplicationMetaData {
    private String deployVersion;
}
