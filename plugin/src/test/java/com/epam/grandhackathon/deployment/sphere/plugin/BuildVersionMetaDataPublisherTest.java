package com.epam.grandhackathon.deployment.sphere.plugin;

import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.FreeStyleBuild;
import hudson.tasks.BuildStepMonitor;
import lombok.extern.java.Log;
import org.junit.Test;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Log
public class BuildVersionMetaDataPublisherTest {

	@Test
	public void shouldReturnMonitorServiceBuild() throws Exception {
		BuildVersionMetaDataPublisher publisher = new BuildVersionMetaDataPublisher();
		assertEquals("Illegal build monitor step value ", publisher.getRequiredMonitorService(), BuildStepMonitor.BUILD);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowAnExceptionInCasePassedBuildIsNull() throws Exception {
        BuildVersionMetaDataPublisher publisher = new BuildVersionMetaDataPublisher();
		BuildListener buildListener = mock(BuildListener.class);
        publisher.perform((AbstractBuild) null, null, buildListener);
	}

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionInCasePassedBuildListenerIsNull() throws Exception {
        BuildVersionMetaDataPublisher publisher = new BuildVersionMetaDataPublisher();
        AbstractBuild build = mock(FreeStyleBuild.class);
        publisher.perform(build, null, null);
    }

	@Test
	public void shouldSuccessfulPreBuild() throws Exception {
		BuildVersionMetaDataPublisher publisher = mock(BuildVersionMetaDataPublisher.class);
		AbstractBuild build = mock(AbstractBuild.class);
		BuildListener listener = mock(BuildListener.class);
		PrintStream printStream = mock(PrintStream.class);
		
		when(listener.getLogger()).thenReturn(printStream);
		when(publisher.getVersionPattern()).thenReturn("x.x.{v}");
		when(publisher.prebuild(build, listener)).thenCallRealMethod();
		
		assertTrue(publisher.prebuild(build, listener));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowAnExceptionInCaseVersionIsNullOnPreBuild() throws Exception {
		BuildVersionMetaDataPublisher publisher = mock(BuildVersionMetaDataPublisher.class);
		AbstractBuild build = mock(AbstractBuild.class);
		BuildListener listener = mock(BuildListener.class);
		PrintStream printStream = mock(PrintStream.class);
		
		when(listener.getLogger()).thenReturn(printStream);
		when(publisher.getVersionPattern()).thenReturn("");
		when(publisher.prebuild(build, listener)).thenCallRealMethod();
		
		publisher.prebuild(build, listener);
	}

}
