package pl.pawel.linkshell.simpleapp;

import com.sun.deploy.util.ArrayUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import pl.pawel.linkshell.simpleapp.analyzer.ApplicationAnalyzer;
import pl.pawel.linkshell.simpleapp.analyzer.model.ClassAnalysisResult;
import pl.pawel.linkshell.simpleapp.annotation.Flow;
import pl.pawel.linkshell.simpleapp.annotation.Runner;
import pl.pawel.linkshell.simpleapp.exception.MissingConfigurationException;
import pl.pawel.linkshell.simpleapp.flow.ApplicationFlow;

/**
 * Created by pawellinkshell on 18.02.2018.
 */
public final class Application {

  public static final boolean DEBUG_LOG = false;

  private static ApplicationFlow flow;

  private final static Logger LOG = Logger.getLogger(Application.class.getName());

//  private static List<String> applicationClasses = new ArrayList<>();
  private static List<AbstractMap.SimpleEntry<Annotation, Class<?>>> applicationAnnotations = new ArrayList<>();

  public static void run(Class<?> applicationClass, String[] args) {
    ApplicationAnalyzer applicationAnalyzer = new ApplicationAnalyzer(applicationClass);

    // 0) Check if application is ready
    ClassAnalysisResult classAnalysisResult = applicationAnalyzer.getClassAnalysisResult();

    boolean applicationTreeCreated = createApplicationOrder(classAnalysisResult.getApplicationPath());
    boolean annotationTreeCreated = createAnnotationsTree(applicationTreeCreated);

    // 1) Prepare application
    boolean buildingApplication = true;
    if (isPrepared(applicationClass, args)) {
      try {
        try {
          buildingApplication &= checkRunnerClass();
          buildingApplication &= checkFlowClass();

          instantiateApplication(buildingApplication);
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (MissingConfigurationException e) {
          e.printStackTrace();
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }

    // 2) Run application
    if (buildingApplication) {
      flow.run();
    }

    // 2) Safe quit application
    flow.quit();
  }

  private static void instantiateApplication(boolean buildingApplication)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    if (buildingApplication) {
      flow = (ApplicationFlow) Class.forName(applicationClasses.get(1)).newInstance();
    }
  }

  private static boolean checkFlowClass()
      throws MissingConfigurationException {
    if (hasApplicationFlowClass()) {
      return true;
    } else {
      throw new MissingConfigurationException(
          "Missing @Flow annotation for application, \n"
              + "please add mentioned annotation into your Flow Class \n"
              + "named <<NAME>>Flow");
    }
  }

  private static boolean hasApplicationFlowClass() {
    return applicationAnnotations.get(1).getKey() instanceof Flow;
  }

  private static boolean checkRunnerClass()
      throws MissingConfigurationException {
    if (hasApplicationRunnerClass()) {
      return true;
    } else {
      throw new MissingConfigurationException(
          "Missing @Runner annotation for application, \n"
              + "please add mentioned annotation into your Runner Class  \n"
              + "named <<NAME>>App");
    }
  }

  private static boolean hasApplicationRunnerClass() {
    return applicationAnnotations.get(0).getKey() instanceof Runner;
  }

  private static boolean createAnnotationsTree(boolean applicationTreeCreated) {
    if (applicationTreeCreated) {
      try {
        for (String className : Application.applicationClasses) {
          Class<?> clazz = Class.forName(className);
          Annotation[] classAnotations = clazz.getAnnotations();
          if (classAnotations.length > 0) {
            AbstractMap.SimpleEntry<Annotation, Class<?>> entry =
                new AbstractMap.SimpleEntry<Annotation, Class<?>>(classAnotations[0], clazz);
            LOG.info("createAnnotationsTree(): " + entry + " added to: Application.applicationAnnotations");
            Application.applicationAnnotations.add(entry);
          }
        }
        return true;
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  private static boolean createApplicationOrder(String originalApplicationPath) {
    try {
      createTree(new File(originalApplicationPath));
      Collections.sort(applicationClasses);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  private static void createTree(File directory) throws IOException {
    Files.walkFileTree(directory.toPath(), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
          throws IOException {
        if (isClassFile(file)) {
          String className = getClassName(file);

          if (!isAnnotationClass(className)) {
            LOG.info("visitFile(): " + className + " added to: Application.applicationClasses");
            Application.applicationClasses.add(className);
          }
        } else if (isJarFile(file)) {
          LOG.info("visitFile(): " + file.toString() + " file.toString()");

          List<String> classNames = new ArrayList<String>();
          ZipInputStream zip = new ZipInputStream(new FileInputStream(file.toString()));

          String includePackage = null;
          for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
              String className = entry.getName().replace('/', '.'); // including ".class"

              if (includePackage == null) {
                int lastIndex = className.lastIndexOf(".");
                includePackage = className.substring(0, lastIndex);
                lastIndex = includePackage.lastIndexOf(".");
                includePackage = includePackage.substring(0, lastIndex);
                LOG.info("visitFile(): " + includePackage + " includePackage");
              }

              if (className.contains(includePackage)) {
                classNames.add(className.substring(0, className.length() - ".class".length()));
              }
            }
          }
          for (String className : classNames) {
            LOG.info("visitFile(): " + className + " className");
          }

          LOG.info("visitFile(): " + classNames + " added to: Application.applicationClasses");
          Application.applicationClasses.addAll(classNames);
        }
        return FileVisitResult.CONTINUE;
      }

      private boolean isJarFile(Path file) {
        return "jar".equals(getExtension(file));
      }

      private boolean isAnnotationClass(String className) {
        return className.contains("annotation");
      }

      private String getClassName(Path file) {
        return getCleanClassName(file);
      }

      private String getCleanClassName(Path file) {
        String[] javaClassPackageNames = file.toString().split(Pattern.quote("\\production\\"));
        String[] dirtyClassNames = javaClassPackageNames[1].split(Pattern.quote("\\"));
        dirtyClassNames[dirtyClassNames.length - 1] = dirtyClassNames[dirtyClassNames.length - 1]
            .split(Pattern.quote("."))[0];
        dirtyClassNames = Arrays.copyOfRange(dirtyClassNames, 1, dirtyClassNames.length);
        String tmp = ArrayUtil.arrayToString(dirtyClassNames);
        tmp = tmp.replace(" ", ".");
        String className = tmp.substring(0, tmp.length() - 1);
        LOG.info("getCleanClassName(): " + className.replace("\\", "."));
        return className.replace("\\", ".");
      }

      private boolean isClassFile(Path file) {
        return "class".equals(getExtension(file));
      }

      private String getExtension(Path file) {
        String fileName = file.getFileName().toString();
        int lastIndex = fileName.lastIndexOf(".") + 1;
        LOG.info("getExtension(): " + fileName.substring(lastIndex));
        return fileName.substring(lastIndex);
      }
    });
  }

  private static boolean isPrepared(Class<?> parserClass, String[] args) {
    if (isDedicatedRunner(parserClass)) {
      return true;
    }
    return false;
  }

  private static boolean isDedicatedRunner(Class<?> appClass) {
    Class<?> forName;
    try {
      System.out.println(applicationClasses);
      forName = Class.forName(applicationClasses.get(0));
      LOG.info("isDedicatedRunner(): " + (forName == appClass));
      return forName == appClass;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return false;
  }

}
