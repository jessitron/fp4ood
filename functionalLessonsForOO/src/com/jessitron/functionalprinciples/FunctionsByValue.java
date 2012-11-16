package com.jessitron.functionalprinciples;

import static com.google.common.collect.Lists.newArrayList;
import static com.jessitron.functionalprinciples.User.user;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

public class FunctionsByValue {

    // Sort a list of Users by first name.


    private final List<User> userList = ImmutableList.of(user("Onorio"), user("Joe"), user("Linda"));
    private final List<User> sortedResults = ImmutableList.of(user("Joe"), user("Linda"), user("Onorio"));
    private final List<User> reverseSortedResults = ImmutableList.of(user("Onorio"), user("Linda"), user("Joe"));

    @Test
    public void guavaOrdering() {
        final Function<User, String> GET_FIRST_NAME_FUNCTION = new Function<User, String>() {
            @Override
            public String apply(User user) {
                return user.firstName;
            }
        };

        final Ordering<User> firstNameOrdering = Ordering.natural().onResultOf(GET_FIRST_NAME_FUNCTION);
        List<User> sortedList = firstNameOrdering.sortedCopy(userList);

        assertThat(sortedList, is(sortedResults));

        List<User> reverseSortedList = firstNameOrdering.reverse().sortedCopy(userList);

        assertThat(reverseSortedList, is(reverseSortedResults));

    }

    @Test
    public void javaSort() {
        Comparator<User> firstNameComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.firstName.compareTo(o2.firstName);
            }
        };

        List<User> myUserList = newArrayList(userList);
        Collections.sort(myUserList, firstNameComparator);
        assertThat(myUserList, is(sortedResults));

        Comparator<User> reverseFirstNameComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.firstName.compareTo(o1.firstName);
            }
        };

        Collections.sort(myUserList, reverseFirstNameComparator);
        assertThat(myUserList, is(reverseSortedResults));

    }

    // Java 8 will look like:
    // List<User> sortedList = userList.sort(comparing(u -> u.firstName));
    // List<User> reverseSortedList = userList.sort(comparing(u -> u.firstName).reverseOrder());

    /* Scala looks like:

    case class User(val firstName : String)

    val userList = List(User("Onorio"), User("Joe"), User("Linda"))

    val sortedList = userList.sortBy(u ->u.firstName)
    val reverseSortedList = userList.sortBy(u ->u.firstName).reverse

    // or, if you want to define an Ordering:

    val o = Ordering.by( (u => u.firstName) : (User => String))

    val sortedList = userList.sorted(o);
    val reverseSortedList = userList.sorted(o.reverse)

     */
}
