package pl.pawel.linkshell.simpleapp.loader;

import pl.pawel.linkshell.simpleapp.loader.context.SimpleApplicationContext;
import pl.pawel.linkshell.simpleapp.loader.context.enums.ContextType;
import pl.pawel.linkshell.simpleapp.loader.context.enums.impl.ApplicationContextType;
import pl.pawel.linkshell.simpleapp.loader.context.enums.impl.ArchiveContextType;
import pl.pawel.linkshell.simpleapp.loader.exception.UnsupportedContextException;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Created by pawellinkshell on 24.02.2018.
 */
public class ApplicationLoaderFactory {

    public static SourceLoader getAnalyzer(final SimpleApplicationContext context) {
        Objects.requireNonNull(context);

        ContextType contextType = context.getContextType();
        Path applicationPath = context.getApplicationPath();
        if (isJARContext(contextType)) {
            return new JARSourceLoader(applicationPath);
        } else if (isApplicationContext(contextType)) {
            return new NativeSourceLoader(applicationPath);
        }
        throw new UnsupportedContextException("ContextType " + contextType + " is not supported by "
                + "SimpleApp Framework");
    }

    private static boolean isApplicationContext(final ContextType contextType) {
        return contextType.getName().equals(ApplicationContextType.NORMAL.getName());
    }

    private static boolean isJARContext(final ContextType contextType) {
        return contextType.getName().equals(ArchiveContextType.JAR.getName());
    }

}