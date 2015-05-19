package com.epam.jenkins.deployment.sphere.plugin.metadata.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@ExportedBean
public class BuildMetaData extends ApplicationMetaData {

    @Exported
    private Long number;
    @Exported
    private String jobName;
    @Exported
    private String builtAt;
    @Exported
    private String buildVersion;
    @Exported
    private String buildUrl;
    @Exported
    private MetaData metaData;
    
}
