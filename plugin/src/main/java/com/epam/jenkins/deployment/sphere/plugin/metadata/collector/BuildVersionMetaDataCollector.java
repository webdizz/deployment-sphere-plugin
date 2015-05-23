package com.epam.jenkins.deployment.sphere.plugin.metadata.collector;

import hudson.model.TaskListener;
import hudson.model.AbstractBuild;
import hudson.scm.ChangeLogSet;

import java.io.PrintStream;

import javax.inject.Inject;

import lombok.extern.java.Log;

import org.joda.time.DateTime;

import com.epam.jenkins.deployment.sphere.plugin.PluginInjector;
import com.epam.jenkins.deployment.sphere.plugin.metadata.Constants;
import com.epam.jenkins.deployment.sphere.plugin.metadata.jira.JiraMetaDataCollector;
import com.epam.jenkins.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.jenkins.deployment.sphere.plugin.metadata.model.JiraIssueMetaData;
import com.epam.jenkins.deployment.sphere.plugin.metadata.persistence.dao.BuildMetaDataDao;
import com.epam.jenkins.deployment.sphere.plugin.utils.DateFormatUtil;
import com.epam.jenkins.deployment.sphere.plugin.utils.EnvVarsResolver;
import com.google.common.base.Strings;

import static java.lang.String.format;

import static com.google.common.base.Preconditions.checkState;

@Log
public class BuildVersionMetaDataCollector implements Collector<BuildMetaData> {

    @Inject
    private BuildMetaDataDao metadataDao;

    @Inject
    private JiraMetaDataCollector jiraMetaDataCollector;

    public BuildVersionMetaDataCollector() {
        PluginInjector.injectMembers(this);
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
        log.fine(format("Resolved build buildNumber: %s, build id: %s, changeSet emptiness: %s", buildNumber, buildId,
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
        buildMetaData.setBuildUrl(build.getUrl());

        // FIXME Temporary code. The sole purpose - to check whether the JiraMetaDataCollector works appropriately.
        for (JiraIssueMetaData metaData : jiraMetaDataCollector.collect(build, listener)) {
            logger.append("From JIRA:\n" + metaData);
        } // End of temporary code

        metadataDao.save(buildMetaData);
        return buildMetaData;
    }
}
