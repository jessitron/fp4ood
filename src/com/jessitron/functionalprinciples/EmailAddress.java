package com.jessitron.functionalprinciples;

import static com.google.common.base.Preconditions.checkNotNull;

public class EmailAddress {

  public final String stringValue;

  public EmailAddress(String value) {
    checkNotNull(value);
    stringValue = value;
  }

  public static EmailAddress emailAddress(String value) {
    return new EmailAddress(value);
  }

  @Override
  public String toString() {
    return "EmailAddress{" + stringValue + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EmailAddress that = (EmailAddress) o;

    if (stringValue != null ? !stringValue.equals(that.stringValue) : that.stringValue != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return stringValue != null ? stringValue.hashCode() : 0;
  }
}
