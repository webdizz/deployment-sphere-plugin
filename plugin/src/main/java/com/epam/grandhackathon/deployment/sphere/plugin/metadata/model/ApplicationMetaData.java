package com.epam.grandhackathon.deployment.sphere.plugin.metadata.model;

import lombok.Data;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@Data
@ExportedBean
public class ApplicationMetaData  {
    @Exported
    private String applicationName;
}
