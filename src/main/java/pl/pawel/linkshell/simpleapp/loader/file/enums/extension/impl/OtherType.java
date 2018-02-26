package pl.pawel.linkshell.simpleapp.loader.file.enums.extension.impl;

import pl.pawel.linkshell.simpleapp.loader.file.enums.extension.Extensionable;

/**
 * Created by pawellinkshell on 25.02.2018.
 */
public enum OtherType implements Extensionable {
    // Non classified extensions
    OTHER{
        @Override
        public String extension() {
            return ".";
        }
    }
}
