package ua.aleksanid.file.visitors;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class RegExFileVisitor extends SimpleFileVisitor<Path> {

    private final PathMatcher pathMatcher;
    private final List<Path> matchedFiles;

    public RegExFileVisitor(String regEx) {
        this.pathMatcher = FileSystems.getDefault().getPathMatcher("regex:" + regEx);
        matchedFiles = new ArrayList<>();
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (!dir.getFileName().equals(Paths.get(""))) {
            return FileVisitResult.SKIP_SUBTREE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) {
        if (basicFileAttributes.isRegularFile() && pathMatcher.matches(path.getFileName())) {
            matchedFiles.add(path);
        }
        return FileVisitResult.CONTINUE;
    }

    public List<Path> getMatchedFiles() {
        return matchedFiles;
    }
}
