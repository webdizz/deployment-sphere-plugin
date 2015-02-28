package com.epam.grandhackathon.deployment.sphere.plugin.model;

import lombok.Data;

@Data
public class DeployMetaData extends ApplicationMetaData {
    private String deployVersion;
}
