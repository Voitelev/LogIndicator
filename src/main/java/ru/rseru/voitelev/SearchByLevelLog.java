package ru.rseru.voitelev;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchByLevelLog {

  private String line;
  private List point;
  private PrinterFile printerFile;
  private int i = 0;
  private int sizeList = 5;
  private String strSearchLevel;
  private String regexDate;
  private Date date;
  private Date searchDate;

  public SearchByLevelLog() throws FileNotFoundException {
    printerFile = new PrinterFile();
    point = new ArrayList();
    regexDate = "(\\d{4}-\\d{2}-\\d{2})";
    //file = new RandomAccessFile("src/main/resources/logs.log", "rw");
  }

  public void searchLevelLog(String searchLevel, Date dateSearch) throws IOException {
    strSearchLevel = searchLevel; //что бы можно было вызвать метод из этого класса
    searchDate = dateSearch; // то же самое для даты // надо переделать говно
    long a = 0;
    while ((line = printerFile.getFile().readLine()) != null) {
      if (point.size() == sizeList) {
        sizeList += 5;
        break;
      }
      Matcher m = Pattern.compile(regexDate).matcher(line);
      if (m.find()) {
        try {
           date = new SimpleDateFormat("yyyy-MM-dd").parse(m.group(1));
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      if (date.after(dateSearch)) {

        if (line.lastIndexOf(searchLevel) != -1) {
          if ((printerFile.getFile().getFilePointer() - line.getBytes().length) < 0) {
            point.add(a);
          } else {
            point.add(printerFile.getFile().getFilePointer() - line.getBytes().length);
          }

        }
      }
    }
  }

  public void openFoundLog(DefaultListModel listModel) throws IOException {
    if (i == point.size()) {
      searchLevelLog(strSearchLevel, searchDate);
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
      i -= 1;
      printerFile.getFile().seek((Long) point.get(i));
      long valuePositionCursor = printerFile.getFile().getFilePointer();
      while ((line = printerFile.getFile().readLine()) != null) {
        listModel.addElement(line);
        break;
      }
    }

  }
}

