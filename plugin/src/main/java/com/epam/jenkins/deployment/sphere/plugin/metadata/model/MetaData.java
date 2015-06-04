package com.epam.jenkins.deployment.sphere.plugin.metadata.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@Data
@ExportedBean
public class MetaData {
    @Exported
    private Set<CommitMetaData> commits = new HashSet<>();
    @Exported
    private Set<JiraIssueMetaData> tickets = new HashSet<>();
}
