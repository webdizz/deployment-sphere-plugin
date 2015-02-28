package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.query;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BindBuild;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.mapper.BuildMapper;

@RegisterMapper(BuildMapper.class)
public interface BuildQuery {

    @SqlQuery("SELECT * FROM BUILDS WHERE application_name = :application_name AND build_version = :build_version")
    Build find(@Bind("application_name") String applicationName, @Bind("build_version") String buildVersion);

    @SqlUpdate("INSERT INTO BUILDS (application_name, build_version, build_number, built_at) values (:application_name, :build_version, :build_number, :built_at)")
    int save(@BindBuild Build build);

    @SqlQuery("SELECT * FROM BUILDS")
    List<Build> all();
}
