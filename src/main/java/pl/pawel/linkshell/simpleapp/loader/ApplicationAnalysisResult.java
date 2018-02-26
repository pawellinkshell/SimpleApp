package pl.pawel.linkshell.simpleapp.loader;

import pl.pawel.linkshell.simpleapp.loader.directory.enums.Directoriable;
import pl.pawel.linkshell.simpleapp.loader.directory.enums.DirectoryGroup;
import pl.pawel.linkshell.simpleapp.loader.file.enums.extension.ExtensionGroup;
import pl.pawel.linkshell.simpleapp.loader.file.enums.extension.Extensionable;

import java.nio.file.Path;
import java.util.*;

public final class ApplicationAnalysisResult {

    private Map<Extensionable, List<Path>> mapFiles;
    private Map<Directoriable, List<Path>> mapDirectories;

    public ApplicationAnalysisResult(Set<Path> directories, List<Path> files) {
        this.mapFiles = new HashMap<>();
        this.mapDirectories = new HashMap<>();

        putFiles(files);
        putDirectory(directories);
    }

    private void putDirectory(Set<Path> setDirectories) {
        List<List<Path>> typeFileList = new ArrayList<>();

        List<Path> listDirectoris = new ArrayList<>();
        listDirectoris.addAll(setDirectories);

        // TODO putting Directory in some kind good proposal
        Path rootPath = listDirectoris.remove(0);
        for (DirectoryGroup directory : DirectoryGroup.values()) {
            List<Path> directories = new ArrayList<>();
            for (Directoriable item : directory.getDirectoryTypes()) {
//                directories = getDirectories()
            }
        }
    }

    private void putFiles(List<Path> files) {
        Objects.requireNonNull(files);

        Map<Extensionable, List<Path>> listTypeMap = new HashMap<>();
        for (ExtensionGroup extensionable : ExtensionGroup.values()) {
            List<Path> paths = new ArrayList<>();
            for (Extensionable item : extensionable.getFileTypes()) {
                paths = getPaths(files, item.extension());
                if (paths.size() != 0) {
                    listTypeMap.put(item, paths);
                }
            }

            if (files.size() == 0) {
                break;
            }
        }

        this.mapFiles = listTypeMap;
    }

    private List<Path> getPaths(List<Path> files, String extension) {
        List<Path> classFiles = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            String fileName = files.get(i).getFileName().toString();
            if (fileName.contains(extension)) {
                classFiles.add(files.remove(i));
                i--;
            }
        }
        return classFiles;
    }
}