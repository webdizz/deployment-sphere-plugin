package com.epam.grandhackathon.deployment.sphere.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.isA;

import java.io.PrintStream;

import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.FreeStyleBuild;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepMonitor;

import org.junit.Test;
import org.mockito.Mockito;

import com.epam.grandhackathon.deployment.sphere.plugin.action.DynamicVariablesStorageAction;

public class BuildVersionMetaDataPublisherTest {

	@Test
	public void shouldReturnMonitorServiceBuild() throws Exception {
		BuildVersionMetaDataPublisher publisher = new BuildVersionMetaDataPublisher();
		assertEquals("Illegal required monitor service ", publisher.getRequiredMonitorService(), BuildStepMonitor.BUILD);
	}

	@Test
	public void shouldOnPerformRejectNullValues() throws Exception {
		BuildVersionMetaDataPublisher publisher = new BuildVersionMetaDataPublisher();
		boolean buildValueRejected = false;
		BuildListener buildListener = mock(BuildListener.class);
		try{
			publisher.perform((AbstractBuild)null, null, buildListener);
		}catch(IllegalArgumentException iaex){
			buildValueRejected = true;
		}finally{
			assertTrue("Build is null", buildValueRejected);
		}
		
		AbstractBuild build = mock(FreeStyleBuild.class);
		boolean listenerValueRejected = false;
		try{
			publisher.perform(build, null, null);
		}catch(IllegalArgumentException iaex){
			listenerValueRejected = true;
		}finally{
			assertTrue("Listener is null", listenerValueRejected);
		}
		
	}

	@Test
	public void shouldPrebuildPublisher() throws Exception {
		BuildVersionMetaDataPublisher publisher = mock(BuildVersionMetaDataPublisher.class);
		AbstractBuild build = mock(AbstractBuild.class);
		BuildListener listener = mock(BuildListener.class);
		PrintStream printStream = mock(PrintStream.class);
		
		when(listener.getLogger()).thenReturn(printStream);
		when(publisher.getVersionPattern()).thenReturn("x.x.{v}");
		when(publisher.prebuild(build, listener)).thenCallRealMethod();
		
		assertTrue(publisher.prebuild(build, listener));
		
	}
	
	@Test
	public void shouldThrowExceptionPrebuildPublisher() throws Exception {
		BuildVersionMetaDataPublisher publisher = mock(BuildVersionMetaDataPublisher.class);
		AbstractBuild build = mock(AbstractBuild.class);
		BuildListener listener = mock(BuildListener.class);
		PrintStream printStream = mock(PrintStream.class);
		
		when(listener.getLogger()).thenReturn(printStream);
		when(publisher.getVersionPattern()).thenReturn("");
		when(publisher.prebuild(build, listener)).thenCallRealMethod();
		
		boolean exceptionOccured = false;
		
		try{
			publisher.prebuild(build, listener);	
		}catch(IllegalArgumentException iaex){
			exceptionOccured = true;
		}finally{
			assertTrue("Build Pattern version value must be provided", exceptionOccured);
		}
	}
	

}
