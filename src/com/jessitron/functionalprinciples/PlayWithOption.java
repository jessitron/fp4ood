package com.jessitron.functionalprinciples;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PlayWithOption {
  
  @Test
  public void whatCanItDo() {
    Optional<String> banana = Optional.of("banana");
    Optional<String> noBanana = Optional.absent();

    if (banana.isPresent()) {
       String contents = banana.get();  // get the contained object
    }
    // you can always call .equals on it
    assertThat(noBanana.equals(banana), is(false));
    
    // wrap a nullable object
    Optional<String> mightBeAbsent =  Optional.fromNullable(null);
    
    // null == null
    assertThat(mightBeAbsent.equals(noBanana), is(true));

    // chain them, or get a value with a default
    noBanana.or(banana).or("fruit");
    
    // treat them as a set
    banana.asSet();

    // pull out the contents from a list of Optionals, skipping absent ones
    Iterable<Optional<String>> lotsOfOptionals = ImmutableList.of(banana, noBanana, mightBeAbsent);
    Iterable<String> onlyBananas = Optional.presentInstances(lotsOfOptionals);

    assertThat(ImmutableList.copyOf(onlyBananas), is(ImmutableList.of("banana")));

  }
}
