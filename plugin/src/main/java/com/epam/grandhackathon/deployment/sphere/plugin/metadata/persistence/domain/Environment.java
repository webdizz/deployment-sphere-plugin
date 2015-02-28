package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain;

import java.io.Serializable;

import lombok.Data;

//@Entity
@Data
//@GlobalTable
public class Environment implements Serializable {
//    @Id
    private String key;
    private String title;
}
