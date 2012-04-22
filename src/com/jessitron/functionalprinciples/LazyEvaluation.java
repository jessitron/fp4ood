package com.jessitron.functionalprinciples;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LazyEvaluation {
  
  @Test
  public void conditionalsAreLazy() {
      Noticer first = new Noticer();    
    Noticer second = new Noticer();
    
    boolean result = ((true) ? first.call() : second.call());

    assertThat(first.wasCalled, is(true));
    assertThat(second.wasCalled, is(false));
  }
  
  @Test
  public void methodParametersAreNotLazy() {

    Noticer first = new Noticer();
    Noticer second = new Noticer();

    boolean result = returnFirstArgument(first.call(), second.call());

    assertThat(first.wasCalled, is(true));
    assertThat(second.wasCalled, is(true));
  }
  
  public boolean returnFirstArgument(boolean first, boolean second) {
    return first;
  }
  
  public class Noticer {
    public boolean wasCalled = false;
    
    public boolean call() {
      wasCalled = true;
      return true;
    }
  }
}
