package pl.pawel.linkshell.simpleapp.analyzer;

import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import pl.pawel.linkshell.simpleapp.Application;
import pl.pawel.linkshell.simpleapp.analyzer.checks.Checkable;
import pl.pawel.linkshell.simpleapp.analyzer.model.ClassAnalysisResult;
import pl.pawel.linkshell.simpleapp.analyzer.model.ClassAnalysisBuilder;

public class ClassAnalyzer {

  private List<String> applicationClasses = new ArrayList<>();
  private List<Checkable<?>> checkableList = new ArrayList<>();

  private ClassAnalysisBuilder analysisBuilder = new ClassAnalysisBuilder();

  private final static Logger LOG = Logger.getLogger(ClassAnalyzer.class.getSimpleName());

  public ClassAnalyzer(Class<?> runningApplicationClass) {

    analysisBuilder.setApplicationPath(getApplicationPath(runningApplicationClass ));

  }

  private final String getApplicationPath(Class<?> runningApplicationClass) {
    ProtectionDomain protectionDomain = runningApplicationClass.getProtectionDomain();
    CodeSource codeSource = protectionDomain.getCodeSource();
    URL location = codeSource.getLocation();
    if (Application.DEBUG_LOG) {
      LOG.info(location.getPath());
    }
    return location.getPath();
  }

  public ClassAnalysisResult getAnalysis() {
    return analysisBuilder.build();
  }


}
