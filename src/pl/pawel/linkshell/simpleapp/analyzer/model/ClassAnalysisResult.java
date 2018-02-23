package pl.pawel.linkshell.simpleapp.analyzer.model;

public final class ClassAnalysisResult {

  private final String applicationPath;

  public String getApplicationPath() {
    return this.applicationPath;
  }

  public ClassAnalysisResult(String applicationPath) {
    this.applicationPath = applicationPath;
  }


}
