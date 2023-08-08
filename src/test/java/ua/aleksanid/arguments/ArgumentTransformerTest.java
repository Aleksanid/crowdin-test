package ua.aleksanid.arguments;

import org.junit.jupiter.api.Test;
import ua.aleksanid.models.UploadArguments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArgumentTransformerTest {
    @Test
    void testExtractUploadArgumentsWithValidArguments() {
        String[] consoleArguments = {"123", "apiToken123", "*.txt"};
        UploadArguments uploadArguments = ArgumentTransformer.extractUploadArguments(consoleArguments);

        assertEquals(123, uploadArguments.getProjectId());
        assertEquals("apiToken123", uploadArguments.getApiToken());
        assertEquals("*.txt", uploadArguments.getFilePattern());
    }

    @Test
    void testExtractUploadArgumentsWithInvalidArgumentsSize() {
        String[] consoleArguments = {"123", "apiToken123"};
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> ArgumentTransformer.extractUploadArguments(consoleArguments),
                "Expected RuntimeException due to invalid arguments size"
        );

        assertEquals("Arguments number mismatch. " +
                        "Execution requires exactly 3 arguments. " +
                        "Supplied 2 arguments. " +
                        "Please provide: Project ID, PAT, File Pattern as arguments",
                exception.getMessage());
    }

    @Test
    void testExtractUploadArgumentsWithProjectIdNotNumber() {
        String[] consoleArguments = {"invalidId", "apiToken123", "*.txt"};
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> ArgumentTransformer.extractUploadArguments(consoleArguments),
                "Expected RuntimeException due to invalid project ID"
        );

        assertEquals("invalidId is not valid project Id.", exception.getMessage());
    }
}