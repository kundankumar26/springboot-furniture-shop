package com.example.furnitureshop.models;

public class FirstLastUsernameValid {
    boolean isFirstNameValid = true;
    boolean isLastNameValid = true;
    boolean isUserNameValid = true;

    public FirstLastUsernameValid(){

    }

    public FirstLastUsernameValid(boolean isFirstNameValid, boolean isLastNameValid, boolean isUserNameValid) {
        this.isFirstNameValid = isFirstNameValid;
        this.isLastNameValid = isLastNameValid;
        this.isUserNameValid = isUserNameValid;
    }

    public boolean isFirstNameValid() {
        return isFirstNameValid;
    }

    public void setFirstNameValid(boolean firstNameValid) {
        isFirstNameValid = firstNameValid;
    }

    public boolean isLastNameValid() {
        return isLastNameValid;
    }

    public void setLastNameValid(boolean lastNameValid) {
        isLastNameValid = lastNameValid;
    }

    public boolean isUserNameValid() {
        return isUserNameValid;
    }

    public void setUserNameValid(boolean userNameValid) {
        isUserNameValid = userNameValid;
    }
}
