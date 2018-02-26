package pl.pawel.linkshell.simpleapp.loader.context;

import pl.pawel.linkshell.simpleapp.loader.file.ConfigurationResource;
import pl.pawel.linkshell.simpleapp.loader.context.enums.ContextType;
import pl.pawel.linkshell.simpleapp.loader.context.enums.impl.ApplicationContextType;
import pl.pawel.linkshell.simpleapp.loader.context.enums.impl.ArchiveContextType;

import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

/**
 * Created by pawellinkshell on 25.02.2018.
 */
public class SimpleApplicationContext {
    private Path applicationPath;

    private ContextType contextType;

    public ContextType getContextType() {
        return contextType;
    }

    public Path getApplicationPath() {
        return applicationPath;
    }

    public SimpleApplicationContext() {
        // 1) Get root application Path
        try {
            this.applicationPath = Paths.get(SimpleApplicationContext.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // 2) Set context type
        if (isArchiveApplication(applicationPath)) {
            this.contextType = ArchiveContextType.JAR;
        } else {
            this.contextType = ApplicationContextType.NORMAL;
        }

        // 3) Activate reading properties file
        ConfigurationResource.init(contextType, applicationPath);
    }

    private boolean isArchiveApplication(final Path rootPath) {
        PathMatcher pathMatcher;
        for (ArchiveContextType excluded : ArchiveContextType.values()) {
            pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + "**/*" + excluded.getName());
            if (pathMatcher.matches(rootPath)) {
                return true;
            }
        }
        return false;
    }
}
