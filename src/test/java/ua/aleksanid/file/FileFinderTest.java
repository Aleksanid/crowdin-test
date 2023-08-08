package ua.aleksanid.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileFinderTest {

    private static final String TEST_DIR = "test-directory";
    @TempDir
    Path tempDir;

    private Path testDir;

    @BeforeEach
    void setUp() throws IOException {
        testDir = tempDir.resolve(TEST_DIR);
        Files.createDirectory(testDir);
        createTestFiles();
    }

    private void createTestFiles() throws IOException {
        Files.createFile(testDir.resolve("sample.txt"));
        Files.createFile(testDir.resolve("file-1.log"));
        Files.createFile(testDir.resolve("file-2.log"));
        Files.createFile(testDir.resolve("file-3.log"));
        Path nestedDirectory = Files.createDirectory(tempDir.resolve("directory"));
        Files.createFile(nestedDirectory.resolve("inner-file.log"));
    }

    @Test
    void testFindFilesByPatternWithPatternMatchingNoFiles() {
        String pattern = "*.xcl";
        List<Path> matchedFiles = FileFinder.findFilesByPattern(pattern, testDir);

        assertTrue(matchedFiles.isEmpty());
    }

    @Test
    void testFindFilesByPatternWithPatternMatchingOneFile() {
        String pattern = "*.txt";

        List<Path> matchedFiles = FileFinder.findFilesByPattern(pattern, testDir);

        assertEquals(1, matchedFiles.size());
        assertEquals("sample.txt", matchedFiles.get(0).getFileName().toString());
    }

    @Test
    void testFindFilesByPatternWithPatternMatchingMultipleFiles() {
        String pattern = "file*.log";

        List<Path> matchedFiles = FileFinder.findFilesByPattern(pattern, testDir);

        assertEquals(3, matchedFiles.size());
        assertEquals("file-1.log", matchedFiles.get(0).getFileName().toString());
        assertEquals("file-2.log", matchedFiles.get(1).getFileName().toString());
        assertEquals("file-3.log", matchedFiles.get(2).getFileName().toString());
    }

    @Test
    void testFindFilesByPatternWithPatternMatchingOnlyNestedFiles() {
        String pattern = "inner-file.log";

        List<Path> matchedFiles = FileFinder.findFilesByPattern(pattern, testDir);

        assertEquals(0, matchedFiles.size());
    }
}