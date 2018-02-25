package pl.pawel.linkshell.simpleapp.analyzer.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by pawellinkshell on 24.02.2018.
 */
public class SimpleAppConfig {
    public static final String CONFIG_PROPERTIES = "config.properties";
    private static Properties properties;

    static{
        //TODO refactor this try-catch hell
        properties = new Properties();
        try(InputStream inputStream = new FileInputStream(getConfigProperties())) {
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            try (InputStream inputStream = SimpleAppConfig.class.getResourceAsStream("/" + CONFIG_PROPERTIES)){
                properties.load(inputStream);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SimpleAppConfig() {}

    private static String getConfigProperties() throws URISyntaxException {
        return Paths.get("").toAbsolutePath().toString()
                + FileSystems.getDefault().getSeparator()
                + "resources"
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