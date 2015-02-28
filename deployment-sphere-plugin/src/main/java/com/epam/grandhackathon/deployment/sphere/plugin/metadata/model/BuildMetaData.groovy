package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model

import lombok.ToString
import org.joda.time.DateTime

@ToString
public class BuildMetaData {

    private Integer number
    private String applicationName
    private String name
    private DateTime builtAt
}
