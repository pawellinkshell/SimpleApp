package pl.pawel.linkshell.simpleapp.loader;

import pl.pawel.linkshell.simpleapp.loader.file.visitor.impl.ApplicationFileVisitor;
import pl.pawel.linkshell.simpleapp.loader.file.visitor.DefaultFileVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Created by pawellinkshell on 24.02.2018.
 */
public class NativeSourceLoader implements SourceLoader {
    private final static DefaultFileVisitor fileVisitor = new ApplicationFileVisitor();

    private ApplicationAnalysisResult applicationAnalysisResult;

    public NativeSourceLoader(Path path) {
        Objects.requireNonNull(path);

        try {
            Files.walkFileTree(path, fileVisitor);
            applicationAnalysisResult = new ApplicationAnalysisResult(fileVisitor.getDirectories(), fileVisitor.getFiles());
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ApplicationAnalysisResult getApplicationAnalysisResult() {
        return this.applicationAnalysisResult;
    }
}
