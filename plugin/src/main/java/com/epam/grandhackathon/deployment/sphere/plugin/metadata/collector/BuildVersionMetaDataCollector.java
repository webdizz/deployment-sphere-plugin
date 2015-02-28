package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDao;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.util.DateFormatUtil;
import com.epam.grandhackathon.deployment.sphere.plugin.utils.EnvVarsResolver;
import com.google.common.base.Strings;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import hudson.scm.ChangeLogSet;
import jenkins.model.Jenkins;
import lombok.extern.java.Log;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.io.PrintStream;

import static com.epam.grandhackathon.deployment.sphere.plugin.TempConstants.APP_NAME;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;

@Log
public class BuildVersionMetaDataCollector implements Collector<BuildMetaData> {

    @Inject
    private BuildMetaDataDao metadataDao;

    public BuildVersionMetaDataCollector() {
        Jenkins.getInstance().getInjector().injectMembers(this);
    }

    @Override
    public BuildMetaData collect(AbstractBuild<?, ?> build, final TaskListener listener) {
        Long buildNumber = (long) build.getNumber();
        PrintStream logger = listener.getLogger();
        final String thisClassName = getClass().getName();

        logger.append(String.format("[%s]Build type: %s\n", thisClassName, build.getClass().getName()));
        logger.append(String.format("[%s]Build instance: %s\n", thisClassName, build));

        logger.append(String.format("[%s]Build Listener type: %s\n", thisClassName, listener.getClass().getName()));
        logger.append(String.format("[%s]Build Listener instance: %s\n", thisClassName, listener));

        String buildId = build.getId();
        ChangeLogSet<? extends hudson.scm.ChangeLogSet.Entry> changeSet = build.getChangeSet();
        log.fine(format("Resolved build number: %s, build id: %s, changeSet emptiness: %s", buildNumber, buildId,
                changeSet.isEmptySet()));

        final BuildMetaData buildMetaData = new BuildMetaData();
        buildMetaData.setNumber(buildNumber);
        buildMetaData.setJobName(build.getDisplayName());
        buildMetaData.setBuiltAt(DateFormatUtil.formatDate(new DateTime(build.due())));

        final EnvVarsResolver envResolver = new EnvVarsResolver(build, listener);

        final String buildVersion = envResolver.getValue(Constants.BUILD_VERSION);
        checkState(!Strings.isNullOrEmpty(buildVersion), String.format("Build version '%s' is not valid", buildVersion));
        buildMetaData.setBuildVersion(buildVersion);

        final String appName = envResolver.getValue(Constants.BUILD_APP_NAME);
        checkState(!Strings.isNullOrEmpty(appName), String.format("App Name '%s' is not valid", appName));
        buildMetaData.setApplicationName(appName);

        metadataDao.save(buildMetaData);
        return buildMetaData;
    }
}
