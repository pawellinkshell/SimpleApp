package pl.pawel.linkshell.simpleapp.loader.exception;

import java.io.FileNotFoundException;

/**
 * Created by pawellinkshell on 25.02.2018.
 */
public class MissingFileException extends FileNotFoundException{
    public MissingFileException(String s) {
        super(s);
    }
}
