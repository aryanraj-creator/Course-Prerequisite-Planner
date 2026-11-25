package com.courseplanner.app;
import com.courseplanner.db.Database;import com.courseplanner.ui.*;import com.courseplanner.util.SampleData;import javax.swing.*;
public class Main {
 public static void main(String[] args){Theme.apply();try{Database.initialize();if(new com.courseplanner.repository.CourseRepository().findAll().isEmpty())SampleData.seed();}catch(Exception e){JOptionPane.showMessageDialog(null,"Database initialization failed: "+e.getMessage(),"Startup error",JOptionPane.ERROR_MESSAGE);return;}SwingUtilities.invokeLater(()->new MainFrame().setVisible(true));}
}
