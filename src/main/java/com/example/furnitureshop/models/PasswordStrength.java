package com.example.furnitureshop.models;

public class PasswordStrength {
    private boolean isThereUppercase = false;
    private boolean isThereLowercase = false;
    private boolean isThereNumber = false;
    private boolean isThereSpecialChar = false;

    public PasswordStrength() {
    }

    public PasswordStrength(boolean isThereUppercase, boolean isThereLowercase, boolean isThereNumber, boolean isThereSpecialChar) {
        this.isThereUppercase = isThereUppercase;
        this.isThereLowercase = isThereLowercase;
        this.isThereNumber = isThereNumber;
        this.isThereSpecialChar = isThereSpecialChar;
    }

    public boolean getIsThereUppercase() {
        return isThereUppercase;
    }

    public void setIsThereUppercase(boolean thereUppercase) {
        isThereUppercase = thereUppercase;
    }

    public boolean getIsThereLowercase() {
        return isThereLowercase;
    }

    public void setIsThereLowercase(boolean thereLowercase) {
        isThereLowercase = thereLowercase;
    }

    public boolean getIsThereNumber() {
        return isThereNumber;
    }

    public void setIsThereNumber(boolean thereNumber) {
        isThereNumber = thereNumber;
    }

    public boolean getIsThereSpecialChar() {
        return isThereSpecialChar;
    }

    public void setIsThereSpecialChar(boolean thereSpecialChar) {
        isThereSpecialChar = thereSpecialChar;
    }
}
