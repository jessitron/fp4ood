package com.jessitron.functionalprinciples;

import com.google.common.collect.Iterables;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
import java.util.Iterator;

public class AddToFileForever {

    // TODO: download Java 7

  @Test
  public void addToFileForever() throws IOException {
    BufferedWriter writer = null; //Files.newBufferedWriter(Paths.get("pretendLogFile.txt"), Charset.defaultCharset(), StandardOpenOption.APPEND, StandardOpenOption.WRITE);

    Iterator<String> iter = Iterables.cycle("blah blah blah blah", "anoetuhneth", "BUG: 1234 45:56:05", "BUG: 4567 12:56:33", "notaeutntih", "BUG: 4456 17:54:22").iterator();

    try {
      while (true) {
        writer.write(iter.next() + "\n");
        Thread.sleep(100);
        writer.flush();
        System.out.println("wrote something");
      }
    } catch (InterruptedException e) {
      writer.flush();
      writer.close();
    }
  }
}
