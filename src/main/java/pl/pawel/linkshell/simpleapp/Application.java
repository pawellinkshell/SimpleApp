package pl.pawel.linkshell.simpleapp;

import pl.pawel.linkshell.simpleapp.loader.SourceLoader;
import pl.pawel.linkshell.simpleapp.loader.ApplicationLoaderFactory;
import pl.pawel.linkshell.simpleapp.loader.ApplicationAnalysisResult;
import pl.pawel.linkshell.simpleapp.loader.context.SimpleApplicationContext;
import pl.pawel.linkshell.simpleapp.annotation.Flow;
import pl.pawel.linkshell.simpleapp.annotation.Runner;
import pl.pawel.linkshell.simpleapp.exception.missing.MissingAnnotationException;
import pl.pawel.linkshell.simpleapp.flow.ApplicationFlow;

import java.lang.annotation.Annotation;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by pawellinkshell on 18.02.2018.
 */
public final class Application {

    public static final boolean DEBUG_LOG = false;

    private static ApplicationFlow flow;
    private static SimpleApplicationContext context;

    private final static Logger LOG = Logger.getLogger(Application.class.getName());

    //  private static List<String> applicationClasses = new ArrayList<>();
    private static List<AbstractMap.SimpleEntry<Annotation, Class<?>>> applicationAnnotations = new ArrayList<>();

    public static void main(String[] args) {
        Application.run(Application.class, args);
    }

    public static void run(Class<?> applicationClass, String[] args) {
        Objects.requireNonNull(applicationClass);

        context = new SimpleApplicationContext();
        SourceLoader sourceLoader
                = ApplicationLoaderFactory.getAnalyzer(context);

        // 0) Check if application is ready
        ApplicationAnalysisResult applicationAnalysisResult = sourceLoader.getApplicationAnalysisResult();

        System.out.println();
//    boolean applicationTreeCreated = createApplicationOrder(classAnalysisResult.getApplicationPath());
//    boolean annotationTreeCreated = createAnnotationsTree(applicationTreeCreated);
//
//    // 1) Prepare application
//    boolean buildingApplication = true;
//    if (isPrepared(applicationClass, args)) {
//      try {
//        try {
//          buildingApplication &= checkRunnerClass();
//          buildingApplication &= checkFlowClass();
//
//          instantiateApplication(buildingApplication);
//        } catch (InstantiationException e) {
//          e.printStackTrace();
//        } catch (IllegalAccessException e) {
//          e.printStackTrace();
//        } catch (MissingConfigurationException e) {
//          e.printStackTrace();
//        }
//      } catch (ClassNotFoundException e) {
//        e.printStackTrace();
//      }
//    }
//
//    // 2) Run application
//    if (buildingApplication) {
//      flow.run();
//    }
//
//    // 2) Safe quit application
//    flow.quit();
    }

//  private static void instantiateApplication(boolean buildingApplication)
//      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//    if (buildingApplication) {
//      flow = (ApplicationFlow) Class.forName(applicationClasses.get(1)).newInstance();
//    }
//  }

    private static boolean checkFlowClass()
            throws MissingAnnotationException {
        if (hasApplicationFlowClass()) {
            return true;
        } else {
            throw new MissingAnnotationException(
                    "Missing @Flow annotation for application, \n"
                            + "please add mentioned annotation into your Flow Class \n"
                            + "named <<NAME>>Flow");
        }
    }

    private static boolean hasApplicationFlowClass() {
        return applicationAnnotations.get(1).getKey() instanceof Flow;
    }

    private static boolean checkRunnerClass()
            throws MissingAnnotationException {
        if (hasApplicationRunnerClass()) {
            return true;
        } else {
            throw new MissingAnnotationException(
                    "Missing @Runner annotation for application, \n"
                            + "please add mentioned annotation into your Runner Class  \n"
                            + "named <<NAME>>App");
        }
    }

    private static boolean hasApplicationRunnerClass() {
        return applicationAnnotations.get(0).getKey() instanceof Runner;
    }

//  private static boolean createAnnotationsTree(boolean applicationTreeCreated) {
//    if (applicationTreeCreated) {
//      try {
//        for (String className : Application.applicationClasses) {
//          Class<?> clazz = Class.forName(className);
//          Annotation[] classAnotations = clazz.getAnnotations();
//          if (classAnotations.length > 0) {
//            AbstractMap.SimpleEntry<Annotation, Class<?>> entry =
//                new AbstractMap.SimpleEntry<Annotation, Class<?>>(classAnotations[0], clazz);
//            LOG.info("createAnnotationsTree(): " + entry + " added to: Application.applicationAnnotations");
//            Application.applicationAnnotations.add(entry);
//          }
//        }
//        return true;
//      } catch (ClassNotFoundException e) {
//        e.printStackTrace();
//      }
//    }
//    return false;
//  }

//  private static boolean createApplicationOrder(String originalApplicationPath) {
//    try {
//      createTree(new File(originalApplicationPath));
//      Collections.sort(applicationClasses);
//      return true;
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    return false;
//  }

//  private static boolean isPrepared(Class<?> parserClass, String[] args) {
//    if (isDedicatedRunner(parserClass)) {
//      return true;
//    }
//    return false;
//  }

//  private static boolean isDedicatedRunner(Class<?> appClass) {
//    Class<?> forName;
//    try {
//      System.out.println(applicationClasses);
//      forName = Class.forName(applicationClasses.get(0));
//      LOG.info("isDedicatedRunner(): " + (forName == appClass));
//      return forName == appClass;
//    } catch (ClassNotFoundException e) {
//      e.printStackTrace();
//    }
//    return false;
//  }

}
