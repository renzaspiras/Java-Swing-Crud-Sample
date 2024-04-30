package core.pack;

import java.awt.Color;

import javax.swing.JFrame;

/**
 * Window
 */
public class Window extends JFrame{

  public Window(){
    //Important Defaults that has to be on the init of JFrame
    setVisible(true);
  }
  
  public void Exitable(boolean status){
    if(status){
      setDefaultCloseOperation(EXIT_ON_CLOSE);      
    }
  }

  public void NullLayout(){
    setLayout(null); 
  }
  public void setFlowLayout(){
    
  }

  //sets background
  public void setBG(String hex){
    this.setBackground(Color.decode(hex));
  }

  public void isResizable(boolean status){
    setResizable(status);
  }

  public void isCentered(boolean centered){
    if(centered){
      setLocationRelativeTo(null);
    }
    else{
      //ignore, since it is not centered, must set bounds itself....
    }
  }

}