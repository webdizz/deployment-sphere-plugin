package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;

import lombok.Data;
import org.hibernate.annotations.Entity;
import org.jenkinsci.plugins.database.jpa.GlobalTable;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
