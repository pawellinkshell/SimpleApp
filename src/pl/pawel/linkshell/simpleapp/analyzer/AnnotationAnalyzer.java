package pl.pawel.linkshell.simpleapp.analyzer;

import java.lang.annotation.Annotation;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class AnnotationAnalyzer {
  private List<SimpleEntry<Annotation, Class<?>>> applicationAnnotations = new ArrayList<>();

  public AnnotationAnalyzer(Class<?> runningApplicationClass) {

  }


  public AnnotationAnalysisResult getAnalysis() {
    return null;
  }
}
