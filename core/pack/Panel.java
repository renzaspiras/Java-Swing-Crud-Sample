package core.pack;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
  public Panel(){

  }
  public void setSize(int width, int height){
    this.setBounds(0,0,width, height);
  }

  //Set Color Via Hex String Value similar to CSS
  public void setColor(String hex){
    this.setBackground(Color.decode(hex));
  }

  public void DockTop(Container container){
    container.setLayout(new BorderLayout());
    container.add(this, BorderLayout.NORTH);
  }
  public void NullLayout(){
    setLayout(null); 
  }
}
