package com.epam.grandhackathon.deployment.sphere.plugin;

import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.FreeStyleBuild;
import hudson.tasks.BuildStepMonitor;
import lombok.extern.java.Log;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Log
public class DeployVersionMetaDataPublisherTest {

	@Test
	public void shouldReturnBuildStepMonitorEqualsBuild() throws Exception {
		DeployVersionMetaDataPublisher publisher = mock(DeployVersionMetaDataPublisher.class);
		when(publisher.getRequiredMonitorService()).thenCallRealMethod();
		assertEquals("Illegal required monitor service ", publisher.getRequiredMonitorService(), BuildStepMonitor.BUILD);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowAnExceptionInCasePassedBuildIsNull() throws Exception {
		DeployVersionMetaDataPublisher publisher = mock(DeployVersionMetaDataPublisher.class);
		BuildListener buildListener = mock(BuildListener.class);
		when(publisher.perform((AbstractBuild)null, null, buildListener)).thenCallRealMethod();

        publisher.perform((AbstractBuild)null, null, buildListener);
	}

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionInCasePassedBuildListenerIsNull() throws Exception {
        DeployVersionMetaDataPublisher publisher = mock(DeployVersionMetaDataPublisher.class);
        AbstractBuild build = mock(FreeStyleBuild.class);
        when(publisher.perform(build, null, null)).thenCallRealMethod();

        publisher.perform(build, null, null);
    }

}
