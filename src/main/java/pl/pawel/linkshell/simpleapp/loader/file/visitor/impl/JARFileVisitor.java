package pl.pawel.linkshell.simpleapp.loader.file.visitor.impl;

import pl.pawel.linkshell.simpleapp.loader.file.ConfigurationResource;
import pl.pawel.linkshell.simpleapp.loader.file.visitor.DefaultFileVisitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Created by pawellinkshell on 25.02.2018.
 */
public class JARFileVisitor implements DefaultFileVisitor {

    private String[] excludePaths = ConfigurationResource.getProperties("application.file.visitor.exclude.path");
    private String[] excludeFiles = ConfigurationResource.getProperties("application.file.visitor.exclude.file");

    private List<Path> files = new ArrayList<>();
    private Set<Path> directories = new TreeSet<>();

    @Override
    public Set<Path> getDirectories() {
        return directories;
    }

    @Override
    public List<Path> getFiles() {
        return files;
    }

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
        for (String excluded : excludeSource) {
            if (excludedPath.toString().contains(excluded)) {
                return true;
            }
        }
        return false;
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
