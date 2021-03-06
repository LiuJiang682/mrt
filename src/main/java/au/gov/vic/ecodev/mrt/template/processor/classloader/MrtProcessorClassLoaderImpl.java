package au.gov.vic.ecodev.mrt.template.processor.classloader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.template.processor.TemplateProcessor;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.update.TemplateUpdater;
import au.gov.vic.ecodev.utils.file.DirectoryFilesScanner;

@Service("mrtProcessorClassLoader")
public class MrtProcessorClassLoaderImpl implements MrtProcessorClassLoader {

	private static final Logger LOGGER = Logger.getLogger(MrtProcessorClassLoaderImpl.class);
	
	private static final String JAR_FILE_EXTENSION = ".jar";
	private static final String CLASS_NAME_TEMPLATE_API = "au.gov.vic.ecodev.mrt.template.processor.TemplateProcessor";
	private static final String ADD_URL = "addURL";
	
	private final MrtConfigProperties mrtConfigProperties;

	@Autowired
	public MrtProcessorClassLoaderImpl(final MrtConfigProperties mrtConfigProperties) {
		if (null == mrtConfigProperties) {
			throw new IllegalArgumentException("Parameter mrtConfigProperties cannot be null!");
		}
		this.mrtConfigProperties = mrtConfigProperties;
	}

	@Override
	public void load() throws Exception {
		// Parent first option -- Not working on Windows CMD or Git Bash
		// URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		// Parent last option
		URLClassLoader clsLoader = (URLClassLoader) this.getClass().getClassLoader();
		Class<URLClassLoader> sysClass = URLClassLoader.class;
		Method method = sysClass.getDeclaredMethod(ADD_URL, new Class[] { URL.class });
		method.setAccessible(true);

		// First, load the API jar
		String apiJarFile = mrtConfigProperties.getTemplateApiJarFile();
		File baseJar = new File(apiJarFile);
		method.invoke(clsLoader, new URL[] { baseJar.toURI().toURL() });
		Class.forName(CLASS_NAME_TEMPLATE_API);

		List<File> customTemplateJarFiles = getCustomTemplateJarFiles();
		for (File file : customTemplateJarFiles) {
			method.invoke(clsLoader, new URL[] { file.toURI().toURL() });
		}

		String testLoaded = mrtConfigProperties.getTestLoaded();
		if (StringUtils.isNotBlank(testLoaded)) {
			Class<?> cls = Class.forName(testLoaded);
			Object object = cls.newInstance();
			TemplateProcessor p = (TemplateProcessor) object;
			p.setTemplateProcessorContent(null);
		}
		
		String testLoadUpdater = mrtConfigProperties.getTestLoadTemplateUpdater();
		if (StringUtils.isNotBlank(testLoadUpdater)) {
			Class<?> updaterClass = Class.forName(testLoadUpdater);
			Object updaterObject = updaterClass.newInstance();
			TemplateUpdater updater = (TemplateUpdater) updaterObject;
			updater.getDaoClasses();
		}
		
		String testLoadDao = mrtConfigProperties.getTestLoadDao();
		if (StringUtils.isNotBlank(testLoadDao)) {
			Class<?> daoClass = Class.forName(testLoadDao, true, this.getClass().getClassLoader());
			Object daoObject = daoClass.newInstance();
			Dao dao = (Dao) daoObject;
			dao.setJdbcTemplate(null);
		}
	}

	protected final List<File> getCustomTemplateJarFiles() throws Exception {
		String customTemplateJarFileDir = mrtConfigProperties.getCustomTemplateJarFileDir();
		LOGGER.info(customTemplateJarFileDir);
		List<File> files = new DirectoryFilesScanner(customTemplateJarFileDir).scan();
		List<File> jarFiles = files.stream().filter(file -> file.getName().endsWith(JAR_FILE_EXTENSION))
				.collect(Collectors.toList());
		return jarFiles;
	}
}
