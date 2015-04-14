package com.epam.grandhackathon.deployment.sphere.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.FreeStyleBuild;
import hudson.tasks.BuildStepMonitor;

import org.junit.Test;

public class DeployVersionMetaDataPublisherTest {

	@Test
	public void shouldReturnMonitorServiceBuild() throws Exception {
		DeployVersionMetaDataPublisher publisher = mock(DeployVersionMetaDataPublisher.class);
		when(publisher.getRequiredMonitorService()).thenCallRealMethod();
		assertEquals("Illegal required monitor service ", publisher.getRequiredMonitorService(), BuildStepMonitor.BUILD);
	}

	@Test
	public void shouldOnPerformRejectNullValues() throws Exception {
		DeployVersionMetaDataPublisher publisher = mock(DeployVersionMetaDataPublisher.class);
		
		boolean buildValueRejected = false;
		BuildListener buildListener = mock(BuildListener.class);
		when(publisher.perform((AbstractBuild)null, null, buildListener)).thenCallRealMethod();
		try{
			publisher.perform((AbstractBuild)null, null, buildListener);
		}catch(IllegalArgumentException iaex){
			buildValueRejected = true;
		}finally{
			assertTrue("Build is null", buildValueRejected);
		}
		
		AbstractBuild build = mock(FreeStyleBuild.class);
		boolean listenerValueRejected = false;
		when(publisher.perform(build, null, null)).thenCallRealMethod();
		try{
			publisher.perform(build, null, null);
		}catch(IllegalArgumentException iaex){
			listenerValueRejected = true;
		}finally{
			assertTrue("Listener is null", listenerValueRejected);
		}
	}

}
