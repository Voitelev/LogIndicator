package ru.rseru.voitelev;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileController {

  public void loader(String strUrl) {
    URL url = null;
    try {
      url = new URL(strUrl);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    try {
      ReadableByteChannel rbc = Channels.newChannel(url.openStream());
      FileOutputStream fos = new FileOutputStream("src/main/resources/logs.txt");
      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
      fos.close();
      rbc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


