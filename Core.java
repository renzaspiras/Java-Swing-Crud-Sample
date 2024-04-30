import core.Designer;

import java.sql.SQLException;

import javax.swing.*;
/**
 * Written in Java 22
 */
public class Core {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          new Designer();
        } 
        catch (SQLException | ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
  });
  }
}