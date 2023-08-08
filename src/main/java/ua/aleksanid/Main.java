package ua.aleksanid;

import ua.aleksanid.runner.FileUploadRunner;

public class Main {
    public static void main(String[] args) {
        System.out.println("[Execution started]");
        try {
            FileUploadRunner.runUploadFilesFromWorkingDirectory(args);
        } catch (RuntimeException runtimeException) {
            System.err.println("[ERROR]: " + runtimeException.getMessage());
            System.err.println("Debug trace: ");
            runtimeException.printStackTrace(System.err);
        }
        System.out.println("[Execution finished]");
    }
}