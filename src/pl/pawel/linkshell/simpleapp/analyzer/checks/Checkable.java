package pl.pawel.linkshell.simpleapp.analyzer.checks;

public interface Checkable<T> {

  boolean check(T object);

}
