package ua.aleksanid.file;

import ua.aleksanid.file.visitors.RegExFileVisitor;
import ua.aleksanid.utility.RegExUtilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileFinder {
    public static List<Path> findFilesByPattern(String wildcardPattern) {
        RegExFileVisitor regExFileVisitor = new RegExFileVisitor(RegExUtilities.convertWildcardToRegEx(wildcardPattern));

        try {
            Files.walkFileTree(Paths.get(""), regExFileVisitor);
        } catch (IOException e) {
            throw new RuntimeException("Error while finding files matching a pattern", e);
        }

        return regExFileVisitor.getMatchedFiles();
    }
}
