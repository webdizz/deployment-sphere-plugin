package com.epam.grandhackathon.deployment.sphere.plugin.config;

import hudson.util.ListBoxModel;

import java.util.Collection;

import javax.inject.Inject;

import com.epam.grandhackathon.deployment.sphere.plugin.PluginInjector;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.ApplicationMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.ApplicationDao;

public class GlobalConfigHelper {
	
	@Inject
	private ApplicationDao applicationDao;
	
	public GlobalConfigHelper() {
        PluginInjector.injectMembers(this);
	}
	
	public ListBoxModel getApplications() {
		Collection<ApplicationMetaData> applications = applicationDao.findAll();
		ListBoxModel items = new ListBoxModel();
		for (ApplicationMetaData option : applications) {			
			items.add(option.getApplicationName());
		}
		return items;
	}
}
