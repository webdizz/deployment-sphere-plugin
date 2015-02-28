package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import jenkins.model.Jenkins;
import org.joda.time.DateTime;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.skife.jdbi.v2.DBI;

import javax.inject.Inject;

public class GenericDao {

    private ModelMapper modelMapper = new ModelMapper();

    @Inject
    private DatabaseProvider databaseProvider;

    public GenericDao () {
        Jenkins.getInstance().getInjector().injectMembers(this);
    }

    public DBI database () {
        return databaseProvider.database();
    }

    public ModelMapper getModelMapper () {
        modelMapper.addConverter(new AbstractConverter<DateTime, Long>() {
            @Override
            protected Long convert (final DateTime source) {
                return source.getMillis();
            }
        });
        return modelMapper;
    }
}
