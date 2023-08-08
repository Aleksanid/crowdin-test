package ua.aleksanid.utility;

public class RegExUtilities {

    public static String convertWildcardToRegEx(String wildcardPattern) {
        return "\\Q" + wildcardPattern.replace("*", "\\E.*\\Q") + "\\E";
    }
}
