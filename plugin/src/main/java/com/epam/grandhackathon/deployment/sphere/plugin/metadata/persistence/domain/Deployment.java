package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.jenkinsci.plugins.database.jpa.GlobalTable;
import org.joda.time.DateTime;
import lombok.Data;

@Entity
@Data
@GlobalTable
public class Deployment implements Serializable {
    @Id
    private Long identity;
    private DateTime deployedAt;
    @ManyToOne
    private Build build;
    @ManyToOne
    private Environment environment;
}
