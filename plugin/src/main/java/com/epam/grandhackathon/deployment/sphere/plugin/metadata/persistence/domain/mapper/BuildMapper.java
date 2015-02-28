package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;

public class BuildMapper implements ResultSetMapper<Build> {
    @Override
    public Build map(final int index, final ResultSet resultSet, final StatementContext ctx) throws SQLException {
        Build build = new Build();
        build.setApplicationName(resultSet.getString("application_name"));
        build.setBuildVersion(resultSet.getString("build_version"));
        build.setBuildNumber(resultSet.getLong("build_number"));
        build.setBuiltAt(new DateTime(resultSet.getLong("built_at")));
        return build;
    }
}
