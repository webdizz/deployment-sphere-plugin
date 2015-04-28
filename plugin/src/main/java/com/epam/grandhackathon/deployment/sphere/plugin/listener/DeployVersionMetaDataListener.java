package com.epam.grandhackathon.deployment.sphere.plugin.listener;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.model.ParameterDefinition;
import hudson.model.ParametersDefinitionProperty;
import hudson.model.listeners.ItemListener;
import hudson.tasks.Publisher;
import hudson.util.DescribableList;

import java.io.IOException;
import java.util.List;

import com.epam.grandhackathon.deployment.sphere.plugin.DeployVersionMetaDataPublisher;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.Constants;
import com.epam.grandhackathon.deployment.sphere.plugin.parameter.DeployMetaDataParameterDefinition;
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
			AbstractProject project = (AbstractProject)item;
			DescribableList<Publisher,Descriptor<Publisher>> publishers = project.getPublishersList();
			DeployVersionMetaDataPublisher dataPublisher = publishers.get(DeployVersionMetaDataPublisher.class);
			ParametersDefinitionProperty paramDefProp = (ParametersDefinitionProperty)project.getProperty(ParametersDefinitionProperty.class);
			if (null != dataPublisher && isNotExistDeployVersionParameter(paramDefProp)){
				addPublisherProperty(project, dataPublisher.getDeployedAppName());	
			}else{
				if (isNotEqualApplicationName(paramDefProp, dataPublisher)){
					removePublisherProperty(project);
					addPublisherProperty(project, dataPublisher.getDeployedAppName());	
				}
			}
		}	
	}

	private boolean isAbstractProject(Item item){
		return item instanceof AbstractProject;
	}
	
	private boolean isNotExistDeployVersionParameter(ParametersDefinitionProperty paramsDefinitionProperty){
		return (findDeployMetaDataParameter(paramsDefinitionProperty) == null);
	}
	
	private boolean isNotEqualApplicationName(ParametersDefinitionProperty paramsDefinitionProperty, DeployVersionMetaDataPublisher dataPublisher){
		DeployMetaDataParameterDefinition parameterDefinition = findDeployMetaDataParameter(paramsDefinitionProperty);
		if (null != parameterDefinition){
			return !(dataPublisher.getDeployedAppName().equals(parameterDefinition.getApplicationName()));
		}
		return true;
	}
	
	private DeployMetaDataParameterDefinition findDeployMetaDataParameter(ParametersDefinitionProperty paramsDefinitionProperty){
		if (null != paramsDefinitionProperty){
			for(ParameterDefinition parameterDefinition : paramsDefinitionProperty.getParameterDefinitions()){
				if (parameterDefinition instanceof DeployMetaDataParameterDefinition)
					return (DeployMetaDataParameterDefinition)parameterDefinition;
			}
		}
		return null;	
	}
	
	
	@SuppressWarnings("unchecked")
	private void addPublisherProperty(AbstractProject prj, String applicationName) {
		DeployMetaDataParameterDefinition deployMetaData = new DeployMetaDataParameterDefinition(Constants.DEPLOY_META_DATA, Constants.DEPLOY_META_DATA, applicationName);
		try {
			ParametersDefinitionProperty parametersDefinitionProperty = (ParametersDefinitionProperty)prj.getProperty(ParametersDefinitionProperty.class);
			List<ParameterDefinition> definitions = Lists.newArrayList();
			if (null != parametersDefinitionProperty){
				definitions = parametersDefinitionProperty.getParameterDefinitions();
			}
			definitions.add(deployMetaData);
			prj.addProperty(new ParametersDefinitionProperty(definitions));
			prj.save();
        } catch (IOException e) {
            Throwables.propagate(e);
        }		
	}
	
	@SuppressWarnings("unchecked")
	private void removePublisherProperty(AbstractProject prj) {
		try {
			ParametersDefinitionProperty parametersDefinitionProperty = (ParametersDefinitionProperty)prj.getProperty(ParametersDefinitionProperty.class);
			List<ParameterDefinition> definitions = Lists.newArrayList();
			if (null != parametersDefinitionProperty){
				for (ParameterDefinition parameterDefinition : parametersDefinitionProperty.getParameterDefinitions()){
					if (!(parameterDefinition instanceof DeployMetaDataParameterDefinition)){
						definitions.add(parameterDefinition);	
					}
				}
				prj.removeProperty(ParametersDefinitionProperty.class);
				prj.save();
				prj.addProperty(new ParametersDefinitionProperty(definitions));
				prj.save();
			}
        } catch (IOException e) {
            Throwables.propagate(e);
        }		
	}

}
