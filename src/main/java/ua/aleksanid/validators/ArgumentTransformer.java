package ua.aleksanid.validators;

import ua.aleksanid.models.UploadArguments;

import java.text.MessageFormat;

public class ArgumentTransformer {

    private static final int PROJECT_ID_ARGUMENT_INDEX = 0;
    private static final int API_TOKEN_ARGUMENT_INDEX = 1;
    private static final int FILE_PATTERN_ARGUMENT_INDEX = 2;

    private static final int EXPECTED_ARGUMENTS_SIZE = 3;

    public static UploadArguments extractUploadArguments(String[] consoleArguments) throws RuntimeException {
        if (consoleArguments.length != EXPECTED_ARGUMENTS_SIZE) {
            throw new RuntimeException(
                    MessageFormat.format(
                            "Arguments number mismatch. Execution requires exactly {0} arguments." +
                                    " Supplied {1} arguments. " +
                                    "Please provide: Project ID, PAT, File Pattern as arguments",
                            EXPECTED_ARGUMENTS_SIZE, consoleArguments.length));
        }

        long projectId;
        try {
            projectId = Long.parseLong(consoleArguments[PROJECT_ID_ARGUMENT_INDEX]);
        } catch (NumberFormatException numberFormatException) {
            throw new RuntimeException(consoleArguments[PROJECT_ID_ARGUMENT_INDEX] + " is not valid project Id.");
        }

        return new UploadArguments(
                projectId,
                consoleArguments[API_TOKEN_ARGUMENT_INDEX],
                consoleArguments[FILE_PATTERN_ARGUMENT_INDEX]
        );
    }
}
