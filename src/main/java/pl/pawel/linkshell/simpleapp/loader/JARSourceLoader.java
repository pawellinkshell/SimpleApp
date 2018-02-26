package pl.pawel.linkshell.simpleapp.loader;

import pl.pawel.linkshell.simpleapp.loader.file.visitor.impl.JARFileVisitor;
import pl.pawel.linkshell.simpleapp.loader.file.visitor.DefaultFileVisitor;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pawellinkshell on 24.02.2018.
 */
public class JARSourceLoader implements SourceLoader {
    private final static DefaultFileVisitor fileVisitor = new JARFileVisitor();
    private ApplicationAnalysisResult applicationAnalysisResult;

    public JARSourceLoader(Path applicationPath) {
        Map<String, String> jarProperties = new HashMap<>();
        jarProperties.put("create", "false");
        jarProperties.put("encoding", "UTF-8");

        URI jarURIFile = URI.create("jar:file:" + applicationPath.toUri().getPath());
        try (FileSystem jarFileSystem = FileSystems.newFileSystem(jarURIFile, jarProperties)) {
            Path rootPath = jarFileSystem.getPath("/");
            Files.walkFileTree(rootPath, fileVisitor);
            applicationAnalysisResult = new ApplicationAnalysisResult(fileVisitor.getDirectories(), fileVisitor.getFiles());
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ApplicationAnalysisResult getApplicationAnalysisResult() {
        return null;
    }
}
