package com.epam.grandhackathon.deployment.sphere.plugin;

import hudson.Extension;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import hudson.model.AbstractProject;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lombok.Data;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

import com.epam.grandhackathon.deployment.sphere.plugin.config.Application;
import com.epam.grandhackathon.deployment.sphere.plugin.config.Environment;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.ApplicationMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.ApplicationDao;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.EnvironmentDao;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;

@Data
public class GlobalJobProperty extends JobProperty<AbstractProject<?, ?>> {

	@Extension
	public static final class CustomViewsTabBarDescriptor extends JobPropertyDescriptor {

		@Inject
		private EnvironmentDao environmentDao;
		
		@Inject
		private ApplicationDao applicationDao;

		private Converter<Application, ApplicationMetaData> applicationConverter = new Converter<Application, ApplicationMetaData>(){
			@Override
			protected ApplicationMetaData doForward(Application application) {
				return new ApplicationMetaData(application.getApplicationName());
			}

			@Override
			protected Application doBackward(ApplicationMetaData metaData) {
				return new Application(metaData.getApplicationName());
			}
			
		};
		
		private Converter<Environment, EnvironmentMetaData> environmentConverter = new Converter<Environment, EnvironmentMetaData>(){
			@Override
			protected EnvironmentMetaData doForward(Environment environment) {
				return new EnvironmentMetaData(environment.getTitle(), environment.getTitle());
			}

			@Override
			protected Environment doBackward(EnvironmentMetaData metaData) {
				return new Environment(metaData.getKey());
			}
		};
		
		public CustomViewsTabBarDescriptor() {
			super(GlobalJobProperty.class);
			PluginInjector.injectMembers(this);
			load();
		}
		
		
		public List<Application> getApplications() {
			Collection<ApplicationMetaData> metaDatas = applicationDao.findAll();
			return Lists.newArrayList(applicationConverter.reverse().convertAll(metaDatas));
		}
		

		public List<Environment> getEnvironments() {
			Collection<EnvironmentMetaData> metaDatas = environmentDao.findAll();
			return Lists.newArrayList(environmentConverter.reverse().convertAll(metaDatas));
		}

		@Override
		public String getDisplayName() {
			return "Deploy Sphere";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
			List<Application> applications = req.bindJSONToList(Application.class, formData.get("applications"));
			applicationDao.saveAll(applicationConverter.convertAll(applications));
			
			List<Environment> environments = req.bindJSONToList(Environment.class, formData.get("environments"));
			environmentDao.saveAll(environmentConverter.convertAll(environments));
			
			save();
			return false;
		}
	}
}
