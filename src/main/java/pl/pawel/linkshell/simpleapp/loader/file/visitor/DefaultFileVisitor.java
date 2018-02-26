package pl.pawel.linkshell.simpleapp.loader.file.visitor;

import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * Created by pawellinkshell on 25.02.2018.
 */
public interface DefaultFileVisitor extends FileVisitor<Path> {
    Set<Path> getDirectories();
    List<Path> getFiles();
}
