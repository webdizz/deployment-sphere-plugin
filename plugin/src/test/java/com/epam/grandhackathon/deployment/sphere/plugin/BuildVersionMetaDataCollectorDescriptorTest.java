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

public class BuildVersionMetaDataCollectorDescriptorTest {

	@Test
	public void shouldAlwaysApplicable() throws Exception {
		BuildVersionMetaDataCollectorDescriptor buildCollectorDescriptorMock = mock(BuildVersionMetaDataCollectorDescriptor.class);
		when(buildCollectorDescriptorMock.isApplicable(FreeStyleProject.class)).thenCallRealMethod();
		assertTrue(buildCollectorDescriptorMock.isApplicable(FreeStyleProject.class));
		when(buildCollectorDescriptorMock.isApplicable(Project.class)).thenCallRealMethod();
		assertTrue(buildCollectorDescriptorMock.isApplicable(Project.class));
	}

	@Test
	public void shouldCheckVersionPattern() throws Exception{
		String version = "0.1.10";
		BuildVersionMetaDataCollectorDescriptor buildCollectorDescriptor = mock(BuildVersionMetaDataCollectorDescriptor.class);
		when(buildCollectorDescriptor.doCheckVersionPattern(anyString())).thenCallRealMethod();
		assertEquals(buildCollectorDescriptor.doCheckVersionPattern(null).kind, FormValidation.Kind.ERROR);
		assertEquals(buildCollectorDescriptor.doCheckVersionPattern("").kind, FormValidation.Kind.ERROR);
		assertEquals(buildCollectorDescriptor.doCheckVersionPattern(version).kind, FormValidation.Kind.OK);	
	}
		

	@Test
	public void shouldCheckAppName() throws Exception{
		String appName = "app1";
		BuildVersionMetaDataCollectorDescriptor buildCollectorDescriptor = mock(BuildVersionMetaDataCollectorDescriptor.class);
		when(buildCollectorDescriptor.doCheckAppName(anyString())).thenCallRealMethod();
		assertEquals(buildCollectorDescriptor.doCheckAppName(null).kind, FormValidation.Kind.ERROR);
		assertEquals(buildCollectorDescriptor.doCheckAppName("").kind, FormValidation.Kind.ERROR);
		assertEquals(buildCollectorDescriptor.doCheckAppName(appName).kind, FormValidation.Kind.OK);
	}

}
