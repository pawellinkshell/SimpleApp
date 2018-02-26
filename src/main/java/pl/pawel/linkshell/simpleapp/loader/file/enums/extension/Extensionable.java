package pl.pawel.linkshell.simpleapp.loader.file.enums.extension;

/**
 * Created by pawellinkshell on 25.02.2018.
 */
public interface Extensionable {
    default String extension() {
        return "." + this.toString().toLowerCase();
    }
}
