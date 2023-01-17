package main.constants;

public class Constants {
    public static final int DEFAULT_LENDNG_DAYS = 60;
    public static final int EXTENSION_LENDING_DAYS_30 = 30;
    public static final int EXTENSION_LENDING_DAYS_60 = 60;

    public static final String EMAIL_REGEX = "^(.+)@(.+)$";
    public static final String ISBN_REGEX = "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
}
