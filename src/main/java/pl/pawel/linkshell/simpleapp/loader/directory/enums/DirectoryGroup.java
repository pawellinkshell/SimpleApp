package pl.pawel.linkshell.simpleapp.loader.directory.enums;

import pl.pawel.linkshell.simpleapp.loader.directory.enums.impl.*;

/**
 * Created by pawellinkshell on 26.02.2018.
 */
public enum DirectoryGroup {
    ROOT(RootDirectoryType.values()),
    SOURCE(SourceDirectoryType.values()),
    RESOURCE(ResourceDirectoryType.values()),
    WEB(WebDirectoryType.values()),
    TEST(TestDirectoryType.values()),
    OTHER(OtherDirectoryType.values());

    private Directoriable[] directoryTypes;

    DirectoryGroup(Directoriable[] directoryTypes) {
        this.directoryTypes = directoryTypes;
    }

    public Directoriable[] getDirectoryTypes() {
        return directoryTypes;
    }
}
