package pl.pawel.linkshell.simpleapp.analyzer.model;

public class ClassAnalysisBuilder {

  private String applicationPath;

  public ClassAnalysisBuilder setApplicationPath(String applicationPath) {
    this.applicationPath = applicationPath;
    return this;
  }

  public ClassAnalysisResult build() {
    return new ClassAnalysisResult(applicationPath);
  }
}