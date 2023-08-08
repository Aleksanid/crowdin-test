package ua.aleksanid.file;

import ua.aleksanid.file.visitors.RegExFileVisitor;
import ua.aleksanid.utility.RegExUtilities;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;

public class FileFinder {
    public static List<Path> findFilesByPattern(String wildcardPattern, Path inPath) {
        RegExFileVisitor regExFileVisitor = new RegExFileVisitor(RegExUtilities.convertWildcardToRegEx(wildcardPattern));

        try {
            Files.walkFileTree(inPath, EnumSet.noneOf(FileVisitOption.class), 1, regExFileVisitor);
        } catch (IOException e) {
            throw new RuntimeException("Error while finding files matching a pattern", e);
        }

        return regExFileVisitor.getMatchedFiles();
    }
}
