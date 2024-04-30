package core;

import core.data.Config;
import core.pack.Button;
import core.pack.Nord;
import core.pack.Panel;
import core.pack.Window;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.sql.*;


public class Designer extends Window {

  //Configurable Variable
  int height = 700;
  int width = 1000;

  int rowsLimit = 0;
  String title = "Crud";

  Nord nord = new Nord();

  Config conf = new Config();
  public JTable table;
  public Button Update;
  public Button Remove;
  public Panel panel;
  public Panel Block;

  static Connection conn = null;
  static Statement stmt = null;

  public Designer() throws SQLException, ClassNotFoundException{
    setTitle(title);
    setSize(width,height);
    Exitable(true);
    isCentered(true);
    isResizable(false); 
    NullLayout();


    Widgets();
  }

  public void Widgets() throws SQLException, ClassNotFoundException{
    TopBar();
  }

  public void TopBar() throws ClassNotFoundException, SQLException{
    panel =  new Panel();
    panel.setColor(nord.Dark());
    panel.NullLayout();

    int ux = 5;
    int gx = 2;
    int uy = 5;


    //Add Button
    Button Add = new Button("Create");  
    Add.setSize(ux, uy, 100,50);
    Add.setBG(nord.Primary());
    panel.add(Add);
    
    //Update Button
    Update = new Button("Update");
    Update.setSize(Add.getWidth() + ux + gx,uy,100,50);
    Update.setBG(nord.Warning());
    Update.Isable(false);
    panel.add(Update);

    //Remove
    Remove = new Button("Remove");
    Remove.setSize(Update.getWidth() + Add.getWidth() + ux + gx + gx, uy,100, 50);
    Remove.setBG(nord.Danger());
    Remove.Isable(false);
    panel.add(Remove);

    panel.setSize(getWidth(), Add.getHeight() + uy + uy);      
    add(panel);
    TABLE();

    

    Add.addActionListener(new ActionListener() {
      @Override      
      public void actionPerformed(ActionEvent e) {    
        Ask(Remove, false);                
      }
    });      
    Update.addActionListener(new ActionListener() {
      @Override      
      public void actionPerformed(ActionEvent e) {    
        Ask(Remove, true);                
      }
    }); 
    Remove.addActionListener(new ActionListener() {
      @Override      
      public void actionPerformed(ActionEvent e) {   
        Config conf = new Config();
        try {
          conn = DriverManager.getConnection(conf.db_url, conf.username, conf.password);
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
        try {
          stmt = conn.createStatement();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
        int selectedRow = table.getSelectedRow();
        Object idnoValue = table.getValueAt(selectedRow, 1); // Assuming idno is the second column (index 1)
        String sql = "DELETE FROM ipt.students WHERE idno = '" + idnoValue + "'";
        try {
          int confirmationResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student?", "Confirmation", JOptionPane.YES_NO_OPTION);
          if (confirmationResult == JOptionPane.YES_OPTION) {
              stmt.executeUpdate(sql);
              remove(Block);
              TABLE();
              revalidate();
              repaint();
          } else {
              // User clicked No or closed the dialog
              JOptionPane.showMessageDialog(null, "You Cancelled the Deletion", "Information", JOptionPane.INFORMATION_MESSAGE);
          }

        } 
        catch (SQLException | ClassNotFoundException e1) {

        }
        
      }
    });   
  }

  public void Ask(Button butt, boolean isUpdate){
    Window AddStud = new Window();
    if (isUpdate) {
      AddStud.setTitle("Update Student");
    }
    else{
      AddStud.setTitle("Add Student");
    }
    AddStud.setLocationRelativeTo(butt);
    AddStud.setSize(500, 500);
    AddStud.NullLayout();
    AddStud.isResizable(false);

    int uy= 250;

    JLabel id_Label = new JLabel("ID");
    id_Label.setBounds(0,0 + uy,100,15);

    Font labelFont = id_Label.getFont();
    id_Label.setFont(new Font(labelFont.getName(), Font.PLAIN, 17));

    JTextField id = new JTextField();
    id.setBounds(0,15 + uy,AddStud.getWidth(),30);

    JLabel idno_Label = new JLabel("ID Number");
    idno_Label.setBounds(0,(15 * 3) + uy,100,15);
    idno_Label.setFont(new Font(labelFont.getName(), Font.PLAIN, 17));

    JTextField idno = new JTextField();
    idno.setBounds(0,(15 * 4) + uy,AddStud.getWidth(),30);
    
    JLabel fname_Label = new JLabel("First Name");
    fname_Label.setBounds(0,15 * 6 + uy,100,15);
    fname_Label.setFont(new Font(labelFont.getName(), Font.PLAIN, 17));

    JTextField fname = new JTextField();
    fname.setBounds(0,15 * 7 + uy,AddStud.getWidth(),30);

    JLabel lname_Label = new JLabel("Last Name");
    lname_Label.setBounds(0,15 * 9 + uy,100,15);
    lname_Label.setFont(new Font(labelFont.getName(), Font.PLAIN, 17));

    JTextField lname = new JTextField();
    lname.setBounds(0,15 * 10 + uy,AddStud.getWidth(),30);

    Button submit = new Button("Submit");
    submit.setBG(nord.Primary());
    submit.setBounds(0, 15*12+ uy, AddStud.getWidth(), 30);

    AddStud.add(idno_Label);
    AddStud.add(idno);
    AddStud.add(fname_Label);
    AddStud.add(fname);
    AddStud.add(lname_Label);
    AddStud.add(lname);
    AddStud.add(submit);

    if (isUpdate) {
      int selectedRow = table.getSelectedRow();

      if (selectedRow != -1) {
          Object idnoValue = table.getValueAt(selectedRow, 1); 
          Object fnameValue = table.getValueAt(selectedRow, 2); 
          Object lnameValue = table.getValueAt(selectedRow, 3); 

          idno.setText(idnoValue.toString());
          idno.setEditable(false);
          fname.setText(fnameValue.toString());
          lname.setText(lnameValue.toString());
      } else {
          System.out.println("No row selected.");
      }
            
    }
    else{

    }

    submit.addActionListener(new ActionListener() {
      @Override      
      public void actionPerformed(ActionEvent e) {   
        Config conf = new Config(); 
        try {
          Class.forName(conf.JDBC_DRIVER);
        } catch (ClassNotFoundException e1) {
          e1.printStackTrace();
        }
        try {
          conn = DriverManager.getConnection(conf.db_url, conf.username, conf.password);
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
        try {
          stmt = conn.createStatement();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
        
        if (isUpdate) {
          String sql = "UPDATE ipt.students SET fname = ?, lname = ? WHERE idno = ?";
          try(PreparedStatement pstmt = conn.prepareStatement(sql))
          {
            
            pstmt.setString(1, fname.getText());
            pstmt.setString(2, lname.getText());
            pstmt.setString(3, idno.getText());
            int confirmationResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this student?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmationResult == JOptionPane.YES_OPTION) {
              int rowsAffected  =  pstmt.executeUpdate();
              if(rowsAffected > 0 )
              {
                remove(Block);
                TABLE();
                revalidate();
                repaint();
                AddStud.dispose();
              }
            } else {
                // User clicked No or closed the dialog
                JOptionPane.showMessageDialog(null, "You Cancelled the Update", "Information", JOptionPane.INFORMATION_MESSAGE);
            }

 
          } 
          catch (SQLException | ClassNotFoundException e1) {
            e1.printStackTrace();
          }
          
        }
        else{
          String IDNO = idno.getText();
          String FIRSTNAME = fname.getText();
          String LASTNAME = lname.getText();
          
          String sql = "INSERT INTO ipt.students (idno, fname, lname) VALUES (?,?,?)";

          try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, IDNO);
            pstmt.setString(2, FIRSTNAME);
            pstmt.setString(3, LASTNAME);
            pstmt.executeUpdate();            
            remove(Block);
            TABLE();
            revalidate();
            repaint();
            AddStud.dispose();

          } catch (SQLException | ClassNotFoundException e1) {
            AddStud.dispose();
            JOptionPane.showMessageDialog(null, "The id value " + IDNO + " is already taken;", "Duplicate ID", JOptionPane.ERROR_MESSAGE);
          } 
           
        }
      }
    }); 
  }

  public void TABLE() throws ClassNotFoundException, SQLException{

    rowsLimit = 0;
    Block = new Panel();
    Block.setBounds(0,panel.getHeight(),getWidth(), 610);
    Block.setColor(nord.Danger());

    Class.forName(conf.JDBC_DRIVER);
    conn = DriverManager.getConnection(conf.db_url, conf.username, conf.password);
    stmt = conn.createStatement();

    Block.setLayout(new BorderLayout());

    Class.forName(conf.JDBC_DRIVER);
    conn = DriverManager.getConnection(conf.db_url, conf.username, conf.password);
    stmt = conn.createStatement();
    String sql = "SELECT * FROM ipt.students";

    try(ResultSet resultSet = stmt.executeQuery(sql)){
      while (resultSet.next()) {                
        rowsLimit += 1;
      }
    }

    Object[][] data = new Object[rowsLimit][4];
    String[] columns = {"ID", "ID Number", "First Name", "Last Name"};
    table = new JTable(data, columns);
    table.setDefaultEditor(Object.class, null);
    JScrollPane scrollPane = new JScrollPane(table);

    int cnt = 0;
    try(ResultSet resultSet = stmt.executeQuery(sql)){
      while (resultSet.next()) {
        data[cnt][0] = resultSet.getInt("ID");
        data[cnt][1] = resultSet.getString("IDNO");
        data[cnt][2] = resultSet.getString("fname");
        data[cnt][3] = resultSet.getString("lname");
        cnt++;
      }
      cnt = 0;
    }
    catch(Exception e){
      System.out.println(e.getMessage());
    }

    add(Block);
    Block.add(scrollPane, BorderLayout.CENTER);
    getContentPane().add(panel);

    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
          if (!e.getValueIsAdjusting()) {
              Update.Isable(true);
              Remove.Isable(true);
          }
      }
  });
  
  }
}