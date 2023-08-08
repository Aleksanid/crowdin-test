package ua.aleksanid.crowdin;

import com.crowdin.client.Client;
import com.crowdin.client.core.http.exceptions.HttpBadRequestException;
import com.crowdin.client.core.http.exceptions.HttpException;
import com.crowdin.client.core.model.Credentials;
import com.crowdin.client.sourcefiles.model.AddFileRequest;
import com.crowdin.client.sourcefiles.model.FileInfo;
import com.crowdin.client.storage.model.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class CrowdinClient {

    private final Client client;

    public CrowdinClient(String token, String organization) {
        Credentials credentials = new Credentials(token, organization);
        this.client = new Client(credentials);
    }

    public Storage addStorageFile(Path filePath) {
        return withCrowdinExceptionHandler(() -> {
            try {
                return client.getStorageApi().addStorage(filePath.getFileName().toString(), Files.newInputStream(filePath.toFile().toPath())).getData();
            } catch (IOException e) {
                throw new RuntimeException("Exception while reading file " + filePath.getFileName().toString() + " for upload to the storage", e);
            }
        });
    }

    public FileInfo addSourceFile(Long projectId, Storage storage) {
        AddFileRequest addFileRequest = new AddFileRequest();
        addFileRequest.setName(storage.getFileName());
        addFileRequest.setStorageId(storage.getId());

        return withCrowdinExceptionHandler(() -> client.getSourceFilesApi().addFile(projectId, addFileRequest).getData());
    }

    private <T> T withCrowdinExceptionHandler(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (HttpException httpException) {
            throw new RuntimeException("Error from Crowdin API. Status: " + httpException.getError().code + ". Message: " + httpException.getError().message);
        } catch (HttpBadRequestException httpBadRequestException) {
            throw new RuntimeException("Error from Crowdin API. Bad Request. Next errors supplies: " + httpBadRequestException
                    .getErrors()
                    .stream()
                    .map(HttpBadRequestException.ErrorHolder::getError)
                    .flatMap((HttpBadRequestException.ErrorKey errorKey) -> errorKey.getErrors().stream())
                    .map(HttpBadRequestException.Error::getMessage)
                    .collect(Collectors.joining(", ")));
        }
    }
}
