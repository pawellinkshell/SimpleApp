package pl.pawel.linkshell.simpleapp.loader.directory.enums.impl;

import pl.pawel.linkshell.simpleapp.loader.directory.enums.Directoriable;

/**
 * Created by pawellinkshell on 26.02.2018.
 */
public enum RootDirectoryType implements Directoriable{
    ROOT{
        @Override
        public String directory() {
            return null;
        }
    },
    PACKAGE{
        @Override
        public String directory() {
            return null;
        }
    }
}
