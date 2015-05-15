package com.epam.jenkins.deployment.sphere.plugin.metadata.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@Data
@ExportedBean
public class MetaData {
    @Exported
    private List<Commit> commits = new ArrayList<>();
    @Exported
    private List<Ticket> tickets = new ArrayList<>();

}
