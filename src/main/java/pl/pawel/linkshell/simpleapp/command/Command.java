package pl.pawel.linkshell.simpleapp.command;

/**
 * Created by pawellinkshell on 18.02.2018.
 */
public final class Command {

    public final static boolean isQuit(final String command) {
        if ("quit".equalsIgnoreCase(command)
                || ":q".equalsIgnoreCase(command)
                || "q".equalsIgnoreCase(command)
                || "exit".equalsIgnoreCase(command)
                || "e".equalsIgnoreCase(command)
                || "e".equalsIgnoreCase(command)) {
            return true;
        }
        return false;
    }

    public final static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
}
