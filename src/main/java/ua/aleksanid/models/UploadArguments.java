package ua.aleksanid.models;

public class UploadArguments {
    private Long projectId;
    private String apiToken;
    private String filePattern;

    public UploadArguments(Long projectId, String apiToken, String filePattern) {
        this.projectId = projectId;
        this.apiToken = apiToken;
        this.filePattern = filePattern;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getFilePattern() {
        return filePattern;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }
}
