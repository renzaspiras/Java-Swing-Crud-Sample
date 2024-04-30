package core.pack;

import javax.swing.JButton;
import java.awt.*;

public class Button extends JButton {
  public Button(String text){
    setText(text);
    setBorder(null);
    setFocusPainted(false);
  }
  public void setSize(int x, int y, int width, int height){
    this.setBounds(x,y,width, height);
  }

  public void setBG(String hex){
    this.setBackground(Color.decode(hex));
  }

  /*
   * Is the button enabled or not?
   */
  public void Isable(boolean status){
    setEnabled(status);
  }

}
