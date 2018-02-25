package pl.pawel.linkshell.simpleapp.exception;

/**
 * Created by pawellinkshell on 24.02.2018.
 */
public class MissingAnnotationException extends AnnotationNotFoundException{
    public MissingAnnotationException(String cause) {
        super(cause);
    }
}
