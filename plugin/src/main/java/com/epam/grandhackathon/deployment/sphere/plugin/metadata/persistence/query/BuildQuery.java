package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.query;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;

@RegisterMapper(BuildQuery.Mapper.class)
public interface BuildQuery {

    @SqlQuery("SELECT * FROM BUILDS WHERE application_name = :application_name AND build_version = :build_version")
    Build find(@Bind("application_name") String applicationName, @Bind("build_version") String buildVersion);


    @SqlQuery("SELECT * FROM BUILDS WHERE build_version = :build_version")
    Build find( @Bind("build_version") String buildVersion);



    @SqlUpdate("INSERT INTO BUILDS (application_name, build_version, build_number, built_at) values (:application_name, :build_version, :build_number, :built_at)")
    int save(@BindBuild Build build);

    @SqlQuery("SELECT * FROM BUILDS")
    List<Build> all();

    @BindingAnnotation(BindBuild.BinderFactory.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER})
    @interface BindBuild {

        class BinderFactory implements org.skife.jdbi.v2.sqlobject.BinderFactory {
            public Binder build(Annotation annotation) {
                return new Binder<BindBuild, Build>() {
                    @Override
                    public void bind(final SQLStatement<?> q, final BindBuild bind, final Build arg) {
                        q.bind("application_name", arg.getApplicationName());
                        q.bind("build_version", arg.getBuildVersion());
                        q.bind("build_number", arg.getBuildNumber());
                        if (null != arg.getBuiltAt()) {
                            q.bind("built_at", arg.getBuiltAt().getMillis());
                        }
                    }
                };
            }
        }
    }

    class Mapper implements ResultSetMapper<Build> {
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

}
