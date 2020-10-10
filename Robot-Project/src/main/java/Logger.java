import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/* static methods so that each location the logger is used will output to the same set logger */

public class Logger
{
  private static JTextArea logger;
  private static Object mutex = new Object();


public static void setLogger(JTextArea newLogger)
{
  synchronized(mutex)
  {
  logger = newLogger;
  }
}

public static void logMessage(String message)
{
  synchronized(mutex)
  {
  SwingUtilities.invokeLater(() ->
  {
  logger.append(message+"\n");
});
}
}



}
