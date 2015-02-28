package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;

@BindingAnnotation(BindBuild.BuildBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindBuild {

    public static class BuildBinderFactory implements BinderFactory {
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
