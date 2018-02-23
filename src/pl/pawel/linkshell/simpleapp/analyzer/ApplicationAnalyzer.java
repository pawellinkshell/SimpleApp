package pl.pawel.linkshell.simpleapp.analyzer;

import java.util.logging.Logger;
import pl.pawel.linkshell.simpleapp.Application;
import pl.pawel.linkshell.simpleapp.analyzer.model.ClassAnalysisResult;

public class ApplicationAnalyzer {

  private ClassAnalysisResult classAnalysisResult;
  private AnnotationAnalysisResult annotationAnalysisResult;

  private final static Logger LOG = Logger.getLogger(ApplicationAnalyzer.class.getSimpleName());

  public ApplicationAnalyzer(Class<?> runningApplicationClass) {
    if (runningApplicationClass == null) {
      throw new NullPointerException();
    }

    this.classAnalysisResult = procedWithClassAnalyzer(runningApplicationClass);
    this.annotationAnalysisResult = procedWithAnnotationAnalyzer(runningApplicationClass);
  }

  private AnnotationAnalysisResult procedWithAnnotationAnalyzer(
      Class<?> runningApplicationClass) {
    AnnotationAnalyzer annotationAnalyzer = new AnnotationAnalyzer(runningApplicationClass);

    return annotationAnalyzer.getAnalysis();
  }

  private ClassAnalysisResult procedWithClassAnalyzer(Class<?> runningApplicationClass) {
    ClassAnalyzer classAnalyzer = new ClassAnalyzer(runningApplicationClass);
    return classAnalyzer.getAnalysis();
  }

  public ClassAnalysisResult getClassAnalysisResult() {
    return this.classAnalysisResult;
  }
}
