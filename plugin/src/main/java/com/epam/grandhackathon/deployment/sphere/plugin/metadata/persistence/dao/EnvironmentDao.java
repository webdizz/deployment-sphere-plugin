package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Environment;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.query.BuildQuery;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.query.EnvironmentQuery;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.util.DateFormatUtil;
import com.google.common.collect.Lists;
import jenkins.model.Jenkins;
import lombok.extern.java.Log;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.skife.jdbi.v2.Handle;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

@Log
public class EnvironmentDao  extends GenericDao {



    public Collection<EnvironmentMetaData> findAll () {

        try (Handle handle = database().open()) {
            EnvironmentQuery query = handle.attach(EnvironmentQuery.class);
            List<Environment> builds = query.all();
            log.fine(format("There are builds buildNumber in database '%s'", builds.size()));
        }

        EnvironmentMetaData production = new EnvironmentMetaData("Production");
        DeploymentMetaData prodDeploy1 = new DeploymentMetaData();
        prodDeploy1.setBuildVersion("123123123");
        prodDeploy1.setDeployedAt(DateFormatUtil.formatDate(DateTime.now()));
        prodDeploy1.setApplicationName("CQ");
        DeploymentMetaData prodDeploy2 = new DeploymentMetaData();
        prodDeploy2.setBuildVersion("1231231234");
        prodDeploy2.setDeployedAt(DateFormatUtil.formatDate(DateTime.now()));
        prodDeploy2.setApplicationName("Hybris");

        production.setIdentity(123123L);
        production.setDeployments(Lists.newArrayList(prodDeploy1, prodDeploy2));

        return Lists.newArrayList(production, new EnvironmentMetaData("QA"), new EnvironmentMetaData(
                "Staging"));
    }


}
