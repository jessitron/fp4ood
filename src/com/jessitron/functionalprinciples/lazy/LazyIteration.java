package com.jessitron.functionalprinciples.lazy;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

public class LazyIteration {

  // objectives:
  // read from a file stream one line at a time.
  // ignore lines that don't start with BUG: blah
  // take the next five BUG lines
  // publish those somewhere.

  @Test
  public void traditionalFile() throws IOException, InterruptedException {

    RandomAccessFile file = new RandomAccessFile("pretendLogFile.txt", "r");

    int bugCount = 0;
    String nextLine = file.readLine();
    while (bugCount < 40) {
      if (nextLine.startsWith("BUG")) {
        String[] words = nextLine.split(" ");
        report("Saw the bug at " + words[0] + " on " + words[1]);
        bugCount++;
      }
      waitUntilFileHasMoreData(file);
      nextLine = file.readLine();
    }

    file.close();

  }

  @Test
  public void functionalStyle() throws IOException {
    final RandomAccessFile br = new RandomAccessFile("temp.thingie", "r");

    Iterable<String> fileIterable = new RandomFileIterable(br);

    // Which lines apply?
    Iterable<String> bugLinesOnly = Iterables.filter(fileIterable, new Predicate<String>() {
      public boolean apply(String s) {
        return s.startsWith("BUG");
      }
    });

    // How to transform each line
    Iterable<String> formattedBugLines = Iterables.transform(bugLinesOnly, new Function<String, String>() {
      public String apply(String input) {
        String[] thing = input.split(" ");
        return "Saw the bug at " + thing[0] + " on " + thing[1];
      }
    });

    // How many lines to choose
    Iterable<String> onlySoMany = Iterables.limit(formattedBugLines, 40);

    // Now actually do something for each one.
    for (String s : ImmutableList.copyOf(onlySoMany)) {
      report(s);
    }


    br.close();
  }

  private void report(String s) {
    System.out.println(s);
  }

  private static class FileIterator implements Iterator<String> {

    private final RandomAccessFile file;

    private FileIterator(RandomAccessFile file) {
      this.file = file;
    }

    @Override
    public String next() {
      try {
        waitUntilFileHasMoreData(file);
        return file.readLine();
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public boolean hasNext() {
      return true;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private static class RandomFileIterable implements Iterable<String> {
    private final RandomAccessFile br;

    public RandomFileIterable(RandomAccessFile br) {
      this.br = br;
    }

    @Override
    public Iterator<String> iterator() {
      return new FileIterator(br);
    }
  }


  private static void waitUntilFileHasMoreData(RandomAccessFile br) throws IOException, InterruptedException {
    while (br.length() <= br.getFilePointer()) {
      Thread.sleep(100);    // wait for more input
    }
  }

}
