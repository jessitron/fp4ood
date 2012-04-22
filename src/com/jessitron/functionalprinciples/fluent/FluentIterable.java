package com.jessitron.functionalprinciples.fluent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.Iterator;

public final class FluentIterable<T> implements Iterable<T> {

  private final Iterable<T> iter;

  private FluentIterable(final Iterable<T> iterable) {
    Preconditions.checkArgument(iterable != null, "iter cannot be null");
    this.iter = iterable;
  }

  public static <T> FluentIterable<T> take(final Iterable<T> iterable) {
    return new FluentIterable<T>(iterable);
  }

  @Override
  public Iterator<T> iterator() {
    return iter.iterator();
  }


  public FluentIterable<T> filterBy(final Predicate<? super T> predicate) {
    return new FluentIterable<T>(Iterables.filter(iter, predicate));
  }


  public <U> FluentIterable<U> transformWith(final Function<? super T, ? extends U> function) {
    return new FluentIterable<U>(Iterables.transform(iter, function));
  }


  public ImmutableList<T> asImmutableList() {
    return ImmutableList.copyOf(iter);
  }

  public FluentIterable<T> limit(int howMany) {
    return new FluentIterable<T>(Iterables.limit(iter, howMany));
  }
}
