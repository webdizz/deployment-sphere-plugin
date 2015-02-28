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
@IdClass(BuildPk.class)
public class Build implements Serializable {

    @Id
    private Long number;
    @Id
    private String jobName;
    private DateTime builtAt;
    private String buildVersion;
}
