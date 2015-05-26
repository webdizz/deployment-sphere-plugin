package com.epam.jenkins.deployment.sphere.plugin.metadata.model;

import lombok.Data;

import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@Data
@ExportedBean
public class CommitMetaData {
    @Exported
    private String author;
    @Exported
    private String id;
    @Exported
    private String message;
}
