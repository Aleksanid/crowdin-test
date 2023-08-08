package ua.aleksanid.utility;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegExUtilities {

    public static String convertWildcardToRegEx(String wildcardPattern) {
        return Arrays
                .stream(wildcardPattern.split("\\*"))
                .map(Pattern::quote)
                .collect(Collectors.joining(".*"));
    }
}
