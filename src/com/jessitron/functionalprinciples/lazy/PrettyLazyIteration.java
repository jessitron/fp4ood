package com.jessitron.functionalprinciples.lazy;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Iterables.*;

public class PrettyLazyIteration {
  private static final Predicate<String> STARTS_WITH_BUG_PREDICATE = new Predicate<String>() {
    public boolean apply(String s) {
      return s.startsWith("BUG");
    }
  };
  private static final Function<String,String> TRANSFORM_BUG_LINE_FUNCTION = new Function<String, String>() {
    public String apply(String input) {
      String[] thing = input.split(" ");
      return "Saw the bug at " + thing[0] + " on " + thing[1];
    }
  };

  // objectives:
  // read from a file stream one line at a time.
  // ignore lines that don't start with BUG: blah
  // take the next five BUG lines
  // publish those somewhere.

  @Test
  public void functionalStyle() throws IOException {
    final RandomAccessFile br = new RandomAccessFile("temp.thingie", "r");

    for (String s : copyOf(limit(transform(filter(new RandomFileIterable(br), STARTS_WITH_BUG_PREDICATE), TRANSFORM_BUG_LINE_FUNCTION), 40))) {
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
      }catch ( InterruptedException e) {
          throw new RuntimeException(e);
      }
      catch ( IOException e) {
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
