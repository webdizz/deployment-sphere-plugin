package com.epam.grandhackathon.deployment.sphere.plugin.parameter;

import java.util.Collection;

import com.epam.grandhackathon.deployment.sphere.plugin.TestInput;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;

public class BuildDefinitionTestInput extends TestInput<Collection<BuildMetaData>, Collection<String>> {

	public BuildDefinitionTestInput(Collection<BuildMetaData> input,
			Collection<String> result) {
		super(input, result);
	}

}
