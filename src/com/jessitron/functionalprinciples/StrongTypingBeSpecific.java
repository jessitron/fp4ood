package com.jessitron.functionalprinciples;

public class StrongTypingBeSpecific {
  
  public boolean validateUser(User user) {
    EmailAddress email = user.getEmailAddress();
    // exercise business logic
    return true;    
  }

  // Do this with more specificity
  public boolean validate(HasEmailAddress anythingWithAnEmail) {
    EmailAddress email = anythingWithAnEmail.getEmailAddress();
    // exercise business logic
    return true;
  }
  
  interface HasEmailAddress {
    EmailAddress getEmailAddress();
  }
  
  class User implements HasEmailAddress {
    private EmailAddress login;

    public EmailAddress getEmailAddress()
    {
      return login;
    }
  }
}
