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
	
	private static final Character CLASSPATH_DELIMETER = '/';
	private static final String JAR_FILE_NAME = "classes.jar";
	private static final String MIGRATION_SOURCE_PATH = "db/migration";
	private static final String MIGRATION_DIST_PATH = File.separator + ".." + File.separator + "classes" + File.separator + MIGRATION_SOURCE_PATH;
	private static final String DB_VERSION_TABLE = "SCHEMA_VERSION";
	
	public void migrate(@NonNull DataSource dataSource) {
		log.info("Db migration started");
		Flyway flyway = new Flyway();
		flyway.setLocations(prepareMigrationLocation());
		flyway.setBaselineOnMigrate(true);
		flyway.setTable(DB_VERSION_TABLE);
		flyway.setDataSource(dataSource);
		flyway.setClassLoader(this.getClass().getClassLoader());
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
			log.warning("message" + e);
			return "classpath:" + MIGRATION_SOURCE_PATH;
		}
	}
	
	private JarFile getClassJarFile(Class<?> clazz) throws IOException {
		String path = CLASSPATH_DELIMETER + clazz.getName().replace('.', CLASSPATH_DELIMETER) + ".class";
		String url = clazz.getResource(path).toString();
		String jarUriPrefix = "jar:file:";
		int bangIndex = url.indexOf("!");
		if (bangIndex == -1){
			throw new IOException("Jar file not found");
		}
		return new JarFile(url.substring(jarUriPrefix.length() + 1, url.indexOf("!")));
	}

	private void copyMigrationToClassPath(JarFile jarFile, String jarDirectory, String destinationDirectory) throws IOException {
		for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements();) {
			JarEntry entry = entries.nextElement();
			if (isMigrationEntry(entry, jarDirectory)) {
				String destinationFileName = destinationDirectory + File.separator + entry.getName().substring(jarDirectory.length());
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
	
	private boolean isMigrationEntry(JarEntry entry, String jarDirectory){
		return entry.getName().startsWith(jarDirectory + CLASSPATH_DELIMETER) && !entry.isDirectory();
	}
	
}