package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;

import java.io.Serializable;

import org.joda.time.DateTime;
import lombok.Data;

//@Entity
@Data
//@GlobalTable
//@IdClass(BuildPk.class)
public class Build implements Serializable {
//    @Id
    private String applicationName;
//    @Id
    private String buildVersion;
    private Long number;
    private DateTime builtAt;
}
