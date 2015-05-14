package com.epam.jenkins.deployment.sphere.plugin.utils;

import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.scm.ChangeLogSet;
import hudson.scm.ChangeLogSet.Entry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.epam.jenkins.deployment.sphere.plugin.metadata.model.Commit;
import com.epam.jenkins.deployment.sphere.plugin.metadata.model.SubMetaData;

public class SubMetaDataExtractor {

    public static String getMetaData(AbstractBuild<?, ?> build) {
        SubMetaData metaData = new SubMetaData();
        if (build.getResult() == Result.SUCCESS) {
            metaData.setCommits(getCommits(build));
        } else {
            List<Commit> commits = new ArrayList<>();
            while (build != null && build.getResult() != Result.SUCCESS) {
                commits.addAll(getCommits(build));
                build = build.getPreviousBuild();
            }
            metaData.setCommits(commits);
        }
        String jsonMetaData = "";
        try {
            jsonMetaData = new ObjectMapper().writeValueAsString(metaData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonMetaData;
    }

    private static List<Commit> getCommits(AbstractBuild<?, ?> build) {
        List<Commit> commits = new ArrayList<>();
        ChangeLogSet<? extends Entry> changeLogSet = build.getChangeSet();
        Iterator<? extends Entry> iterator = changeLogSet.iterator();
        while (iterator.hasNext()) {
            commits.add(commitBuilder((Entry) iterator.next()));
        }
        return commits;
    }

    private static Commit commitBuilder(final Entry commitEntry) {
        Commit commit = new Commit();
        commit.setAuthor(commitEntry.getAuthor().getFullName());
        commit.setId(commitEntry.getCommitId());
        commit.setMessage(commitEntry.getMsg());
        return commit;
    }
    
}
