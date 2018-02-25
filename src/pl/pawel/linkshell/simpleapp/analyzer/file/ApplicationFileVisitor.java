package pl.pawel.linkshell.simpleapp.analyzer.file;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Created by pawellinkshell on 24.02.2018.
 */
public class ApplicationFileVisitor implements FileVisitor<Path> {

    private String[] excludePaths = SimpleAppConfig.getProperties("application.file.visitor.exclude.path");
    private String[] excludeFiles = SimpleAppConfig.getProperties("application.file.visitor.exclude.file");

    private List<Path> files = new ArrayList<>();
    private Set<Path> directories = new TreeSet<>();

    public Set<Path> getDirectories() {
        return directories;
    }

    public List<Path> getFiles() {
        return files;
    }

    public ApplicationFileVisitor() {}

    @Override
    public FileVisitResult preVisitDirectory(final Path directory, final BasicFileAttributes attrs) throws IOException {
        Objects.requireNonNull(directory);
        Objects.requireNonNull(attrs);

        if (isExcludedPath(directory)) {
            return FileVisitResult.SKIP_SUBTREE;
        }
        return FileVisitResult.CONTINUE;
    }

    private boolean isExcludedPath(final Path directory) {
        return isExcluded(directory, excludePaths);
    }

    private boolean isExcluded(final Path excludedPath, final String[] excludeSource) {
        PathMatcher pathMatcher;
        for (String excluded : excludeSource) {
            pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + "**/*" + excluded);
            if (pathMatcher.matches(excludedPath) && isNotEmpty(excluded)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotEmpty(final String exclude) {
        return exclude.length() > 0;
    }

    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        if (!isExcludedFile(file)) {
            files.add(file);
        }
        return FileVisitResult.CONTINUE;
    }

    private boolean isExcludedFile(final Path file) {
        return isExcluded(file, excludeFiles);
    }

    @Override
    public FileVisitResult visitFileFailed(final Path file, final IOException exception) throws IOException {
        Objects.requireNonNull(file);
        throw exception;
    }

    @Override
    public FileVisitResult postVisitDirectory(final Path directory, final IOException exc) throws IOException {
        Objects.requireNonNull(directory);
        if (exc != null)
            throw exc;

        directories.add(directory);
        return FileVisitResult.CONTINUE;
    }
}