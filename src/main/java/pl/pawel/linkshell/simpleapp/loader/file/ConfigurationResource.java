package pl.pawel.linkshell.simpleapp.loader.file;

import pl.pawel.linkshell.simpleapp.loader.context.enums.ContextType;
import pl.pawel.linkshell.simpleapp.loader.context.enums.impl.ArchiveContextType;
import pl.pawel.linkshell.simpleapp.loader.exception.MissingFileException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by pawellinkshell on 24.02.2018.
 */
public class ConfigurationResource {
    public static final String CONFIG_PROPERTIES = "config.properties";
    private static Properties properties;

    private ConfigurationResource() {}

    public static void init(final ContextType contextType, Path applicationPath) {
        // Let load properties file only in startup
        // using lazy instantiation technique as a check
        if (properties == null) {
            properties = new Properties();

            try (InputStream inputStream = getInputStream(contextType, applicationPath)) {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    private static InputStream getInputStream(ContextType contextType, Path applicationPath) throws MissingFileException {
        // is archive workflow application
        if (isJARType(contextType)) {
            return getJARInputStream();
        }
        // normal workflow application
        return getFileInputStream(applicationPath);
    }

    private static FileInputStream getFileInputStream(Path applicationPath) throws MissingFileException {
        try {
            return new FileInputStream(getConfigProperties(applicationPath));
        } catch (FileNotFoundException e) {
            throw new MissingFileException("[INTERNAL] Missing `" + CONFIG_PROPERTIES
                    + "` file in context in `resources` folder"
                    + " for " + ConfigurationResource.class.getName() + " project");
        }
    }

    private static InputStream getJARInputStream() {
        return ConfigurationResource.class.getResourceAsStream("/" + CONFIG_PROPERTIES);
    }

    private static boolean isJARType(ContextType contextType) {
        return contextType.getName().equals(ArchiveContextType.JAR.getName());
    }

    private static String getConfigProperties(Path applicationPath){
        return applicationPath
                + FileSystems.getDefault().getSeparator()
                + CONFIG_PROPERTIES;
    }

    public static String[] getProperties(String key) {
        Objects.requireNonNull(key);
        return getCleanProperties(key);
    }

    private static String[] getCleanProperties(String key) {
        return Arrays.stream(properties.getProperty(key).split(","))
                .map(String::trim)
                .toArray(String[]::new);
    }

}