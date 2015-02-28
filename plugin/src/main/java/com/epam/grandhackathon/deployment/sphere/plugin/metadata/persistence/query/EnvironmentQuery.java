package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.query;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Environment;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.lang.annotation.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RegisterMapper(EnvironmentQuery.Mapper.class)
public interface EnvironmentQuery {

    @SqlQuery("SELECT * FROM ENVIRONMENTS WHERE identity = :identity AND title = :title")
    Environment find (@Bind("identity") String applicationName, @Bind("title") String title);

    @SqlUpdate("INSERT INTO ENVIRONMENTS (identity, title) values (:identity, :identity)")
    int save (@BindEnvironment Environment build);

    @SqlQuery("SELECT * FROM ENVIRONMENTS")
    List<Environment> all ();

    @BindingAnnotation(BindEnvironment.BinderFactory.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER})
    @interface BindEnvironment {

        class BinderFactory implements org.skife.jdbi.v2.sqlobject.BinderFactory {
            public Binder build (Annotation annotation) {
                return new Binder<BindEnvironment, Environment>() {
                    @Override
                    public void bind (final SQLStatement<?> q, final BindEnvironment bind, final Environment arg) {
                        q.bind("identity", arg.getIdentity());
                        q.bind("title", arg.getTitle());
                    }
                };
            }
        }
    }

    class Mapper implements ResultSetMapper<Environment> {
        @Override
        public Environment map (final int index, final ResultSet resultSet, final StatementContext ctx) throws
                SQLException {
            Environment build = new Environment();
            build.setTitle(resultSet.getString("title"));
            build.setIdentity(resultSet.getString("identity"));
            return build;
        }
    }
}
