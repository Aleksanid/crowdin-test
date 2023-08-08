package ua.aleksanid.runner;

import com.crowdin.client.storage.model.Storage;
import ua.aleksanid.crowdin.CrowdinClient;
import ua.aleksanid.file.FileFinder;
import ua.aleksanid.models.UploadArguments;
import ua.aleksanid.validators.ArgumentTransformer;

import java.nio.file.Path;
import java.util.List;

public class FileUploadRunner {
    public static void runUploadFilesFromWorkingDirectory(String[] args){
        UploadArguments uploadArguments = ArgumentTransformer.extractUploadArguments(args);

        printArguments(uploadArguments);

        System.out.println("Searching files by pattern");
        List<Path> filesByPattern = FileFinder.findFilesByPattern(uploadArguments.getFilePattern());

        if (filesByPattern.isEmpty()) {
            System.out.println("No files matching " + uploadArguments.getFilePattern() + " found");
            return;
        }

        System.out.println("Found " + filesByPattern.size() + " files matching file pattern");

        uploadFiles(filesByPattern, uploadArguments.getProjectId(), uploadArguments.getApiToken());

        System.out.println("All files uploaded");
    }


    private static void printArguments(UploadArguments uploadArguments) {
        System.out.println("Next arguments supplied...");

        System.out.println("-- Project ID: " + uploadArguments.getProjectId());
        System.out.println("-- API Token: [REDACTED]");
        System.out.println("-- File pattern: " + uploadArguments.getFilePattern());
    }

    private static void uploadFiles(List<Path> filesByPattern, Long projectId, String apiToken) {
        CrowdinClient crowdinClient = new CrowdinClient(apiToken, null);

        for (Path file : filesByPattern) {
            System.out.println("Uploading " + file.getFileName().toString() + " to the Crowdin projectId: " + projectId);
            Storage storage = crowdinClient.addStorageFile(file);
            crowdinClient.addSourceFile(projectId, storage);
            System.out.println("Successfully uploaded " + file.getFileName().toString() + " to the project id: " + projectId);
        }
    }
}
