package com.epam.jenkins.deployment.sphere.plugin.utils;

import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.scm.ChangeLogSet.Entry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.codehaus.jackson.map.ObjectMapper;

import com.epam.jenkins.deployment.sphere.plugin.metadata.model.Commit;
import com.epam.jenkins.deployment.sphere.plugin.metadata.model.MetaData;
import com.google.common.base.Throwables;
import com.google.inject.Singleton;

@Log
@Singleton
public class MetaDataExtractor {

    public String getMetaData(final AbstractBuild<?, ?> build) {
        String jsonMetaData = "";
        try {
            MetaData metaData = collectMetaData(build);
            jsonMetaData = new ObjectMapper().writeValueAsString(metaData);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed serializing MetaData to String", e);
            throw Throwables.propagate(e);
        }
        return jsonMetaData;
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
