package pl.pawel.linkshell.simpleapp.exception.notfound;

/**
 * Created by pawellinkshell on 24.02.2018.
 */
public class AnnotationNotFoundException extends ClassNotFoundException{
    public AnnotationNotFoundException(String cause) {
        super(cause);
    }
}
