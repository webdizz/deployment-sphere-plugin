package com.epam.grandhackathon.deployment.sphere.plugin.listener;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.AbstractProject;
import hudson.model.ChoiceParameterDefinition;
import hudson.model.Descriptor;
import hudson.model.ParameterDefinition;
import hudson.model.ParametersDefinitionProperty;
import hudson.model.listeners.ItemListener;
import hudson.tasks.Publisher;
import hudson.util.DescribableList;

import java.io.IOException;
import java.util.List;

import com.epam.grandhackathon.deployment.sphere.plugin.DeployMetaDataParameterDefinition;
import com.epam.grandhackathon.deployment.sphere.plugin.DeployVersionMetaDataPublisher;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

@Extension
public class DeployVersionMetaDataListener extends ItemListener {

	@Override
	public void onCreated(Item item) {
		configureJobWithDeployVersionPublisher(item);	
	}

	@Override
	public void onUpdated(Item item) {
		configureJobWithDeployVersionPublisher(item);
	}
	
	private void configureJobWithDeployVersionPublisher(Item item){
		if (isAbstractProject(item)){
			AbstractProject prj = (AbstractProject)item;
			DescribableList<Publisher,Descriptor<Publisher>> publishers = prj.getPublishersList();
			DeployVersionMetaDataPublisher dataPublisher = publishers.get(DeployVersionMetaDataPublisher.class);
			if (dataPublisher != null){
				ParametersDefinitionProperty paramDefProp = (ParametersDefinitionProperty)prj.getProperty(ParametersDefinitionProperty.class);
				if (null != paramDefProp)
					for(ParameterDefinition pDef : paramDefProp.getParameterDefinitions()){
						if (pDef.getName().equals(Constants.DEPLOY_META_DATA))
							return;
					}
				addPublisherProperty(prj, paramDefProp, dataPublisher.getDeployedAppName());	
			}
		}	
	}

	private boolean isAbstractProject(Item item){
		return item instanceof AbstractProject;
	}
	
	private void addPublisherProperty(AbstractProject prj, ParametersDefinitionProperty paramDefProp, String applicationName){
		DeployMetaDataParameterDefinition deployMetaData = new DeployMetaDataParameterDefinition(Constants.DEPLOY_META_DATA, Constants.DEPLOY_META_DATA, "", "", applicationName);
		try {
			List<ParameterDefinition> definitions = Lists.newArrayList();;
			if (null != paramDefProp){
				definitions = paramDefProp.getParameterDefinitions();
			}
			definitions.add(deployMetaData);
			prj.addProperty(new ParametersDefinitionProperty(definitions));
			prj.save();
        } catch (IOException e) {
            Throwables.propagate(e);
        }		
	}

}
