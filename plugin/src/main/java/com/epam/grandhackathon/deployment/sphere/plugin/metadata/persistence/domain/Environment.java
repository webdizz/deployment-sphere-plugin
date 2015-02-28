package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;

import java.io.Serializable;
import javax.persistence.Id;

import org.hibernate.annotations.Entity;
import org.jenkinsci.plugins.database.jpa.GlobalTable;
import lombok.Data;

@Entity
@Data
@GlobalTable
public class Environment implements Serializable {
    @Id
    private Long identity;
    private String name;
}
