package com.epam.grandhackathon.deployment.sphere.plugin.action;

import static org.junit.Assert.assertEquals;
import hudson.EnvVars;
import hudson.model.FreeStyleBuild;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;

public class DynamicVariablesStorageActionTest {
	private final String key1 = "key_1";
	private final String keyValue1 = "key_value_1";
	private final String key2 = "key_2";
	private final String keyValue2 = "key_value_2";
	
    @Test
    public void shouldRepopulateEnvVarsUseMapConstructor() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put(key1, keyValue1);
		map.put(key2, keyValue2);
		DynamicVariablesStorageAction dynamicStorage = new DynamicVariablesStorageAction(map);
		FreeStyleBuild build = Mockito.mock(FreeStyleBuild.class);
		EnvVars env = new EnvVars();
		dynamicStorage.buildEnvVars(build, env);
		assertEquals(env.get(key1), keyValue1);
		assertEquals(env.get(key2), keyValue2);
		assertEquals(env.size(), 2);
    }
    

    @Test
    public void shouldRepopulateEnvVarsUseStringConstructor() throws Exception {
		DynamicVariablesStorageAction dynamicStorage = new DynamicVariablesStorageAction(key1, keyValue1);
		FreeStyleBuild build = Mockito.mock(FreeStyleBuild.class);
		EnvVars env = new EnvVars();
		dynamicStorage.buildEnvVars(build, env);
		assertEquals(env.get(key1), keyValue1);
		assertEquals(env.size(), 1);
    }
}