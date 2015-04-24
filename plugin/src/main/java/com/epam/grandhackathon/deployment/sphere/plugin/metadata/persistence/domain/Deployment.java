package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;


import java.io.Serializable;

import org.joda.time.DateTime;
import lombok.Data;

@Data
public class Deployment implements Serializable {
    private String key;
	private Build build;
    private Environment environment;
    private DateTime deployedAt;
}
