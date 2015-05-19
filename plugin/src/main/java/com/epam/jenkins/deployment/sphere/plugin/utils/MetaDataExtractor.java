package com.epam.jenkins.deployment.sphere.plugin.utils;

import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.scm.ChangeLogSet.Entry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.epam.jenkins.deployment.sphere.plugin.metadata.model.Commit;
import com.epam.jenkins.deployment.sphere.plugin.metadata.model.MetaData;
import com.google.inject.Singleton;

@Singleton
public class MetaDataExtractor {

    public MetaData getMetaData(final AbstractBuild<?, ?> build) {
        MetaData metaData = collectMetaData(build);
        return metaData;
    }

    private MetaData collectMetaData(final AbstractBuild<?, ?> build) {
        AbstractBuild<?, ?> tmpBuild = build;
        MetaData metaData = new MetaData();
        if (tmpBuild.getResult() == Result.SUCCESS) {
            metaData.setCommits(getCommits(tmpBuild));
        } else {
            metaData.setCommits(getFailedBuildCommits(tmpBuild));
        }
        return metaData;

    }

    private List<Commit> getFailedBuildCommits(AbstractBuild<?, ?> tmpBuild) {
        List<Commit> commits = new ArrayList<>();
        while (tmpBuild != null && tmpBuild.getResult() != Result.SUCCESS) {
            commits.addAll(getCommits(tmpBuild));
            tmpBuild = tmpBuild.getPreviousBuild();
        }
        return commits;
    }

    private List<Commit> getCommits(AbstractBuild<?, ?> build) {
        List<Commit> commits = new ArrayList<>();
        Iterator<? extends Entry> iterator = build.getChangeSet().iterator();
        while (iterator.hasNext()) {
            commits.add(buildCommit(iterator.next()));
        }
        return commits;
    }

    private Commit buildCommit(final Entry commitEntry) {
        Commit commit = new Commit();
        commit.setAuthor(commitEntry.getAuthor().getFullName());
        commit.setId(commitEntry.getCommitId());
        commit.setMessage(commitEntry.getMsg());
        return commit;
    }

}
