package com.jessitron.functionalprinciples;

import static com.google.common.base.Preconditions.checkNotNull;

public class User {
    public final String firstName;

    private User(String firstName) {
        this.firstName = firstName;
    }

    public static User user(String firstName) {
        checkNotNull(firstName);
        return new User(firstName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return firstName != null ? firstName.hashCode() : 0;
    }
}