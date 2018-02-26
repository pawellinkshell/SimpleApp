package pl.pawel.linkshell.simpleapp.exception.missing;

import pl.pawel.linkshell.simpleapp.exception.missing.MissingAnnotationException;

public class MissingConfigurationException extends MissingAnnotationException {

  public MissingConfigurationException(String cause) {
    super(cause);
  }
}
