package com.epam.grandhackathon.deployment.sphere.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import hudson.model.FreeStyleProject;
import hudson.model.Project;
import hudson.util.FormValidation;

import org.junit.Test;

public class DeployVersionMetaDataCollectorDescriptorTest {

	@Test
	public void shouldAlwaysApplicable() throws Exception {
		DeployVersionMetaDataCollectorDescriptor deployCollectorDescriptor = mock(DeployVersionMetaDataCollectorDescriptor.class);
		when(deployCollectorDescriptor.isApplicable(FreeStyleProject.class)).thenCallRealMethod();
		assertTrue(deployCollectorDescriptor.isApplicable(FreeStyleProject.class));
		when(deployCollectorDescriptor.isApplicable(Project.class)).thenCallRealMethod();
		assertTrue(deployCollectorDescriptor.isApplicable(Project.class));
	}

	@Test
	public void shouldCheckDeployedAppName() throws Exception {
		String appName = "app1";
		DeployVersionMetaDataCollectorDescriptor versionCollectorDescriptor = mock(DeployVersionMetaDataCollectorDescriptor.class);
		when(versionCollectorDescriptor.doCheckDeployedAppName(anyString())).thenCallRealMethod();
		assertEquals(versionCollectorDescriptor.doCheckDeployedAppName(null).kind, FormValidation.Kind.ERROR);
		assertEquals(versionCollectorDescriptor.doCheckDeployedAppName("").kind, FormValidation.Kind.ERROR);
		assertEquals(versionCollectorDescriptor.doCheckDeployedAppName(appName).kind, FormValidation.Kind.OK);
	}

}
