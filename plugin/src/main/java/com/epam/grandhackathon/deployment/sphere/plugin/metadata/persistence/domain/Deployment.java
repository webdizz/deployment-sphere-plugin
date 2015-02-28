package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;


import java.io.Serializable;

import org.joda.time.DateTime;
import lombok.Data;

//@Entity
@Data
//@GlobalTable
public class Deployment implements Serializable {
//    @Id
    private Long identity;
    private DateTime deployedAt;
//    @ManyToOne
    private Build build;
//    @ManyToOne
    private Environment environment;
}
