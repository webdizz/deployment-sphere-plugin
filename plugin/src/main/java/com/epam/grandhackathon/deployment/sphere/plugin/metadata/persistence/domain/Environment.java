package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;

import lombok.Data;

import org.jenkinsci.plugins.database.jpa.GlobalTable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@GlobalTable
public class Environment implements Serializable {
    @Id
    private Long identity;

    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private Build build;


}
