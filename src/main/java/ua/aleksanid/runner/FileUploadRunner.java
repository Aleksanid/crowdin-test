package ua.aleksanid.runner;

import com.crowdin.client.storage.model.Storage;
import ua.aleksanid.crowdin.CrowdinClient;
import ua.aleksanid.file.FileFinder;
import ua.aleksanid.models.UploadArguments;
import ua.aleksanid.arguments.ArgumentTransformer;

import java.nio.file.Path;
import java.util.List;

public class FileUploadRunner {
    private final UploadArguments uploadArguments;

    public FileUploadRunner(String[] args) {
        uploadArguments = ArgumentTransformer.extractUploadArguments(args);
    }

    public void runUploadFilesFromWorkingDirectory() {
        printArguments();

        System.out.println("Searching files by pattern");
        List<Path> filesByPattern = FileFinder.findFilesByPattern(uploadArguments.getFilePattern());

        if (filesByPattern.isEmpty()) {
            System.out.println("No files matching " + uploadArguments.getFilePattern() + " found");
            return;
        }

        System.out.println("Found " + filesByPattern.size() + " files matching file pattern");

        uploadFiles(filesByPattern);

        System.out.println("All files uploaded");
    }


    private void printArguments() {
        System.out.println("Next arguments supplied...");

        System.out.println("-- Project ID: " + uploadArguments.getProjectId());
        System.out.println("-- API Token: [REDACTED]");
        System.out.println("-- File pattern: " + uploadArguments.getFilePattern());
    }

    private void uploadFiles(List<Path> filesByPattern) {
        System.out.println("Starting API client");
        CrowdinClient crowdinClient = new CrowdinClient(uploadArguments.getApiToken(), null);
        System.out.println("API client started");

        for (Path file : filesByPattern) {
            System.out.println("Uploading " + file.getFileName().toString() + " to the Crowdin projectId: " + uploadArguments.getProjectId());
            Storage storage = crowdinClient.addStorageFile(file);
            crowdinClient.addSourceFile(uploadArguments.getProjectId(), storage);
            System.out.println("Successfully uploaded " + file.getFileName().toString() + " to the project id: " + uploadArguments.getProjectId());
        }
    }
}
