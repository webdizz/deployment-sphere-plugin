package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.sql.DataSource;

import lombok.NonNull;
import lombok.extern.java.Log;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import com.google.common.io.ByteStreams;

@Log
public class DatabaseMigrator {
	
	private static final Character PATH_DELIMETER = '\\';
	private static final Character CLASSPATH_DELIMETER = '/';
	private static final String JAR_FILE_NAME = "classes.jar";
	private static final String MIGRATION_SOURCE_PATH = "db/migration";
	private static final String MIGRATION_DIST_PATH = PATH_DELIMETER + ".." + PATH_DELIMETER + "classes" + PATH_DELIMETER + MIGRATION_SOURCE_PATH;
	private static final String DB_VERSION_TABLE = "SCHEMA_VERSION";
	
	public void migrate(@NonNull DataSource dataSource) {
		log.info("Db migration started");
		Flyway flyway = new Flyway();
		flyway.setLocations(prepareMigrationLocation());
		flyway.setBaselineOnMigrate(true);
		flyway.setTable(DB_VERSION_TABLE);
		flyway.setDataSource(dataSource);
		flyway.setClassLoader(this.getClass().getClassLoader());
		for (MigrationInfo i : flyway.info().all()) {
			log.info("Migrate task: " + i.getVersion() + " : " + i.getDescription() + " from file: " + i.getScript());
		}
        flyway.migrate();
		log.info("Db migration successfully done");
	}
	
	private String prepareMigrationLocation() {
		try {
			JarFile jarFile = getClassJarFile(DatabaseMigrator.class);
			String jarLocation = jarFile.getName().replace(JAR_FILE_NAME, "");
			copyMigrationToClassPath(jarFile, MIGRATION_SOURCE_PATH, jarLocation + MIGRATION_DIST_PATH);
			return "filesystem:" + jarLocation + MIGRATION_DIST_PATH;
		} catch (IOException e) {
			log.warning("Loading migration files error: " + e.getMessage());
			return MIGRATION_SOURCE_PATH;
		}
	}
	
	private JarFile getClassJarFile(Class<?> clazz) throws IOException {
		String path = CLASSPATH_DELIMETER + clazz.getName().replace('.', CLASSPATH_DELIMETER) + ".class";
		String url = clazz.getResource(path).toString();
		String jarUriPrefix = "jar:file:";
		return new JarFile(url.substring(jarUriPrefix.length() + 1, url.indexOf("!")));
	}

	private void copyMigrationToClassPath(JarFile jarFile, String jarDirectory, String destinationDirectory) throws IOException {
		for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements();) {
			JarEntry entry = entries.nextElement();
			if (entry.getName().startsWith(jarDirectory + CLASSPATH_DELIMETER) && !entry.isDirectory()) {
				String destinationFileName = destinationDirectory + PATH_DELIMETER + entry.getName().substring(jarDirectory.length());
				File destinationFile = new File(destinationFileName);
				File parentFile = destinationFile.getParentFile();
				if (parentFile != null) {
					parentFile.mkdirs();
				}
				try(InputStream in = jarFile.getInputStream(entry);
						OutputStream out = new FileOutputStream(destinationFile)) {
					ByteStreams.copy(in, out);
				}
			}
		}
	}
	
}