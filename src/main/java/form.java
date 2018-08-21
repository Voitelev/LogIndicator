import ru.rseru.voitelev.FileController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class form extends javax.swing.JDialog {
  private javax.swing.JPanel contentPane;
  private javax.swing.JButton buttonLouder;
  private JTextField textField2;
  private JButton OKButton;
  private JPanel panelTextAreaURL;
  private static List<JTextField> jTextField = new ArrayList<JTextField>();
  private int countServer;

  public form() {
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonLouder);
    buttonLouder.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        onOK();
      }
    });
    OKButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        countServer = Integer.parseInt(textField2.getText());
        for (int i = 0; i < countServer; i++) {
          JTextField textField = new JTextField();
          textField.setPreferredSize(new Dimension(390, 30));
          jTextField.add(textField);
          panelTextAreaURL.add(textField);
          panelTextAreaURL.revalidate();
        }
        System.out.print(panelTextAreaURL.getComponentCount());
      }
    });
  }

  private void onOK() {
    FileController fileController = new FileController();
    for (int i = 0; i < countServer; i++){
      System.out.println(jTextField.get(i).getText());
     // fileController.loader(jTextField.get(i).getText());
    }
    FormTextArea formTextArea = null;
    try {
      formTextArea = new FormTextArea();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    try {

      formTextArea.transferListModel();
    } catch (IOException e) {
      e.printStackTrace();
    }
    formTextArea.setVisible(true);

  }

  public static void main(String[] args) {
    form dialog = new form();
    dialog.pack();
    dialog.setVisible(true);
    System.exit(0);
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
  }
}
