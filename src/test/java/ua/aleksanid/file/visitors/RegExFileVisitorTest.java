package ua.aleksanid.file.visitors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegExFileVisitorTest {

    private static final String TEST_DIR = "test-directory";
    private static final String FILE_MATCHING_REGEX = "file[0-9]+.txt";
    private static final String DIRECTORY_MATCHING_REGEX = "directory";
    private static final String FILE_NOT_MATCHING_REGEX = "not_matching_regex";

    @TempDir
    Path tempDir;

    private Path testDir;
    private RegExFileVisitor fileVisitor;

    @BeforeEach
    void setUp() throws IOException {
        testDir = tempDir.resolve(TEST_DIR);
        Files.createDirectory(testDir);
        createTestFiles();
    }

    private void createTestFiles() throws IOException {
        Files.createFile(testDir.resolve("file1.txt"));
        Files.createFile(testDir.resolve("file2.txt"));
        Files.createFile(testDir.resolve("file3.txt"));
        Files.createDirectory(tempDir.resolve("directory"));
        Files.createFile(testDir.resolve("not_matching.txt"));
    }

    @Test
    void testFileVisitorMatchesOnlyCorrectFiles() throws IOException {
        fileVisitor = new RegExFileVisitor(FILE_MATCHING_REGEX);

        Files.walkFileTree(testDir, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, fileVisitor);

        List<Path> matchedFiles = fileVisitor.getMatchedFiles();
        assertEquals(3, matchedFiles.size());
        assertEquals("file1.txt", matchedFiles.get(0).getFileName().toString());
        assertEquals("file2.txt", matchedFiles.get(1).getFileName().toString());
        assertEquals("file3.txt", matchedFiles.get(2).getFileName().toString());
    }

    @Test
    void testFileVisitorDoesNotMatchesDirectories() throws IOException {
        fileVisitor = new RegExFileVisitor(DIRECTORY_MATCHING_REGEX);

        Files.walkFileTree(testDir, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, fileVisitor);

        List<Path> matchedFiles = fileVisitor.getMatchedFiles();
        assertEquals(0, matchedFiles.size());
    }

    @Test
    void testFileVisitorDoesNotMatchFilesNotMatchingRegEx() throws IOException {
        fileVisitor = new RegExFileVisitor(FILE_NOT_MATCHING_REGEX);

        Files.walkFileTree(testDir, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, fileVisitor);

        List<Path> matchedFiles = fileVisitor.getMatchedFiles();
        assertEquals(0, matchedFiles.size());
    }
}