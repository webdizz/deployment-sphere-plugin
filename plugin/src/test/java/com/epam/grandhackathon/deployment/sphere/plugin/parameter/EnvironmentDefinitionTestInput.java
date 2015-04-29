package com.epam.grandhackathon.deployment.sphere.plugin.parameter;

import java.util.Collection;

import com.epam.grandhackathon.deployment.sphere.plugin.TestInput;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;

public class EnvironmentDefinitionTestInput extends TestInput<Collection<EnvironmentMetaData>, Collection<String>> {

	public EnvironmentDefinitionTestInput(Collection<EnvironmentMetaData> input,
			Collection<String> result) {
		super(input, result);
	}

}
