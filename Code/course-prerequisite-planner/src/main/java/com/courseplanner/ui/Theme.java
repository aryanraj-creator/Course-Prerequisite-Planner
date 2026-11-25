package com.courseplanner.ui;
import java.awt.*;
import javax.swing.*;
public final class Theme {
 public static final Color BG=new Color(245,247,251), NAVY=new Color(20,34,58), BLUE=new Color(48,105,210), TEXT=new Color(35,47,66), MUTED=new Color(112,125,145), GREEN=new Color(35,155,105), ORANGE=new Color(230,145,45), RED=new Color(205,65,75), WHITE=Color.WHITE;
 private Theme(){}
 public static void apply(){try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception ignored){} UIManager.put("Button.arc",12);UIManager.put("Component.arc",10);UIManager.put("TextField.arc",8);UIManager.put("Table.rowHeight",30);UIManager.put("Table.selectionBackground",new Color(222,234,255));UIManager.put("Table.selectionForeground",TEXT);}
 public static JButton button(String label){JButton b=new JButton(label);b.setFocusPainted(false);b.setBackground(BLUE);b.setForeground(WHITE);b.setFont(new Font("SansSerif",Font.BOLD,13));b.setBorder(BorderFactory.createEmptyBorder(10,16,10,16));return b;}
}
