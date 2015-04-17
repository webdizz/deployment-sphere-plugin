package com.epam.grandhackathon.deployment.sphere.plugin;

import java.util.logging.Level;

import jenkins.model.Jenkins;
import lombok.extern.java.Log;

@Log
public class PluginInjector {
	public static void injectMembers(Object target) {
		Jenkins instance = Jenkins.getInstance();
		if(instance == null){
			log.log(Level.SEVERE, "Jenkins Instance cannot be loaded");
		} else {
			instance.getInjector().injectMembers(target);
		}
	}
}
