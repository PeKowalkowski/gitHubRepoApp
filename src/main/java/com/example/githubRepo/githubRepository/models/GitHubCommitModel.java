package com.example.githubRepo.githubRepository.models;

public class GitHubCommitModel {
    private String sha;
    private String url;

    public GitHubCommitModel() {
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
