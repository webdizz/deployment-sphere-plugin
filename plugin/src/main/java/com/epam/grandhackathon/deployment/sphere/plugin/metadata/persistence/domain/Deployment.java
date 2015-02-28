package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.jenkinsci.plugins.database.jpa.GlobalTable;
import org.joda.time.DateTime;
import lombok.Data;

@Entity
@Data
@GlobalTable
@IdClass(DeploymentPk.class)
public class Deployment implements Serializable {
    @Id
    private String applicationName;
    @Id
    private String buildVersion;
    private DateTime deployedAt;
}
