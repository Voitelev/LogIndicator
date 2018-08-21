package ru.rseru.voitelev;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PrinterFile {
  private RandomAccessFile file;
  private int[] valuePositionCursor = new int[3];

  public PrinterFile() throws FileNotFoundException {
    file = new RandomAccessFile("src/main/resources/logs.log", "rw");
  }

  public void outputText(int positionCursor, DefaultListModel listModel) throws IOException {
    String line;

    for (int i = 0; i < valuePositionCursor.length; i++) {
      if (i < valuePositionCursor.length - 1) {
        valuePositionCursor[i] = valuePositionCursor[i + 1];
      }
    }
    positionCursor = positionCursor + (int) file.getFilePointer();
    while ((line = file.readLine()) != null) {
      listModel.addElement(line);
      if (file.getFilePointer() > Math.abs(positionCursor)) {
        valuePositionCursor[2] = (int) file.getFilePointer();

        for (int aValuePositionCursor : valuePositionCursor) {
          System.out.println(aValuePositionCursor);
        }
        break;
      }
    }
  }

  public void outputTextDown( DefaultListModel listModel) throws IOException {
    String line;
    file.seek(valuePositionCursor[0]);

    while ((line = file.readLine()) != null) {

      listModel.addElement(line);
      if (file.getFilePointer() > valuePositionCursor[1]) {
        valuePositionCursor[2] = (int) file.getFilePointer();
        if(valuePositionCursor[0] != 0 ){
          valuePositionCursor[1] = valuePositionCursor[0];
          valuePositionCursor[0] -= 50000;
        }else {
          valuePositionCursor[1] = 0;
        }
        if(valuePositionCursor[0] < 1000){
          valuePositionCursor[0] = 0;
        }
        for (int aValuePositionCursor : valuePositionCursor) {
          System.out.println(aValuePositionCursor);
        }
        break;
      }
    }
  }
  public RandomAccessFile getFile() {
    return file;
  }
}
