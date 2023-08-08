package ua.aleksanid.validators;

import ua.aleksanid.models.UploadArguments;

public class ArgumentValidator {

    private static final int PROJECT_ID_ARGUMENT_INDEX = 0;
    private static final int API_TOKEN_ARGUMENT_INDEx = 1;
    private static final int FILE_PATTERN_ARGUMENT_INDEX = 2;

    private static final int MIN_ARGUMENTS_SIZE = 3;

    static UploadArguments extractUploadArguments(String[] consoleArguments){
        if(consoleArguments.length < MIN_ARGUMENTS_SIZE){
            throw new IllegalArgumentException("One or more arguments are missing. Execution requires " + MIN_ARGUMENTS_SIZE + "arguments");
        }
        return new UploadArguments(
                consoleArguments[PROJECT_ID_ARGUMENT_INDEX],
                consoleArguments[API_TOKEN_ARGUMENT_INDEx],
                consoleArguments[FILE_PATTERN_ARGUMENT_INDEX]
        );
    }
}
