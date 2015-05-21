package com.epam.jenkins.deployment.sphere.plugin.metadata.model;

import java.util.Comparator;

import com.google.inject.Singleton;

@Singleton
public class JiraIssueIdComparator implements Comparator<JiraIssueMetaData> {

    @Override
    public int compare(JiraIssueMetaData issue_1, JiraIssueMetaData issue_2) {
        return issue_1.getId().compareTo(issue_2.getId());
    }
}
