package pl.pawel.linkshell.simpleapp.flow;

import pl.pawel.linkshell.simpleapp.annotation.Flow;

@Flow
public abstract class ApplicationFlow {

  private static final int SUCCESFULL_TERMINATION = 0;
  private static final int BAD_TERMINATION = -1;

  protected boolean prepare(String args){
    return true;
  }

  public abstract boolean run();

  private void quit(boolean safeQuit){
    if (safeQuit) {
      System.exit(SUCCESFULL_TERMINATION);
    }
    System.exit(BAD_TERMINATION);
  }

  public void quit() {
    quit(true);
  }
}

