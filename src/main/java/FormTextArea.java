import ru.rseru.voitelev.PrinterFile;
import ru.rseru.voitelev.SearchByLevelLog;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormTextArea extends javax.swing.JDialog {
  private JPanel panel1;


  private JScrollPane ScrollPanel;
  private JList list1;
  private JButton searchBackButton;
  private JComboBox LevelLogComboBox;
  private JButton searchForwardButton;
  private JButton searchButton;
  private JTextField textFieldData;
  private DefaultListModel listModel;
  private int previousPositionCursor = 0;
  private int sizeFile;
  private PrinterFile printerFile;
  private SearchByLevelLog searchByLevelLog;
  private Date searchDate;


  FormTextArea() throws FileNotFoundException {

    setContentPane(panel1);
    setModal(true);
    panel1.setSize(600, 800);
    pack();
    //  setVisible(true);
    listModel = new DefaultListModel();
    list1.setModel(listModel);
    printerFile = new PrinterFile();
    searchByLevelLog = new SearchByLevelLog();
    ScrollPanel.setAutoscrolls(false);
    ScrollPanel.getViewport().addChangeListener(new FormTextArea.ListenAdditionsScrolled());
    searchForwardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          DefaultListModel listModel = (DefaultListModel) list1.getModel();
          listModel.removeAllElements();
          searchByLevelLog.openFoundLog(listModel);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });
    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          String stringDate = textFieldData.getText();
          String stringDateFormat = "yyyy-MM-dd";
          SimpleDateFormat format = new SimpleDateFormat(stringDateFormat, Locale.US);
          try {
            searchDate = format.parse(stringDate);
          } catch (ParseException e1) {
            e1.printStackTrace();
          }
          searchByLevelLog.searchLevelLog((String) LevelLogComboBox.getSelectedItem(), searchDate);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });
    searchBackButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          DefaultListModel listModel = (DefaultListModel) list1.getModel();
          listModel.removeAllElements();
          searchByLevelLog.openFoundLogBack(listModel);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });
  }

  public class ListenAdditionsScrolled implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
      int extent = ScrollPanel.getVerticalScrollBar().getModel().getExtent();
      if (ScrollPanel.getVerticalScrollBar().getValue() + extent == ScrollPanel.getVerticalScrollBar().getMaximum()
              && ScrollPanel.getVerticalScrollBar().getValue() != 0) {
        try {
          DefaultListModel listModel = (DefaultListModel) list1.getModel();
          listModel.removeAllElements();
          printerFile.outputText(50000, listModel);
          ScrollPanel.getVerticalScrollBar().setValue(1);
          System.out.println("я добавил ввниз");
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
      try {
        //System.out.println(printerFile.getFile().getFilePointer());
        if (ScrollPanel.getVerticalScrollBar().getValue() == 0 && printerFile.getFile().getFilePointer() > 50300) {
          DefaultListModel listModel = (DefaultListModel) list1.getModel();
          listModel.removeAllElements();
          list1.removeAll();
          panel1.revalidate();
          printerFile.outputTextDown(listModel);
          ScrollPanel.getVerticalScrollBar().setValue(ScrollPanel.getVerticalScrollBar().getMaximum() - extent - 1);
          System.out.println("я добавил вверх");
        }
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }

  void transferListModel() throws IOException {
    printerFile.outputText(50000, listModel);

  }

  public String getSelectedItem() {
    return (String) LevelLogComboBox.getSelectedItem();
  }


}
