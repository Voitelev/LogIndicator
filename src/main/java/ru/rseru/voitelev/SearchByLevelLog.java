package ru.rseru.voitelev;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class SearchByLevelLog {

  private String line;
  private List point;
  private PrinterFile printerFile;
  private int i = 0;
  private int sizeList = 10;
  private String strSearchLevel;

  public SearchByLevelLog() throws FileNotFoundException {
    printerFile = new PrinterFile();
    point = new ArrayList();
    //file = new RandomAccessFile("src/main/resources/logs.log", "rw");
  }

  public void searchLevelLog(String searchLevel) throws IOException {
    strSearchLevel = searchLevel; //что бы можно было вызвать метод из этого класса
    long a = 0;
    while ((line = printerFile.getFile().readLine()) != null) {
      if (point.size() == sizeList) {
        sizeList += 10;
        break;
      }
      if (line.lastIndexOf(searchLevel) != -1) {
        if ((printerFile.getFile().getFilePointer() - line.getBytes().length) < 0) {
          point.add(a);
        } else {
          point.add(printerFile.getFile().getFilePointer() - line.getBytes().length);
        }

      }
    }
  }

  public void openFoundLog(DefaultListModel listModel) throws IOException {
    if (i == point.size()) {
      searchLevelLog(strSearchLevel);
    }
    printerFile.getFile().seek((Long) point.get(i));
    long valuePositionCursor = printerFile.getFile().getFilePointer();
    while ((line = printerFile.getFile().readLine()) != null) {
      listModel.addElement(line);
      break;
    }
    i++;
  }
  public void openFoundLogBack(DefaultListModel listModel) throws IOException {
      if (i != 0) {
        i-=1;
        printerFile.getFile().seek((Long) point.get(i));
        long valuePositionCursor = printerFile.getFile().getFilePointer();
        while ((line = printerFile.getFile().readLine()) != null) {
          listModel.addElement(line);
          break;
        }
      }

    }
  }

