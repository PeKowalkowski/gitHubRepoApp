package com.example.githubRepo.githubRepository.models;

public class GitHubBranchModel {
    private String name;
    private String lastCommitSha;

    public GitHubBranchModel() {

    }

    public GitHubBranchModel(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastCommitSha() {
        return lastCommitSha;
    }

    public void setLastCommitSha(String lastCommitSha) {
        this.lastCommitSha = lastCommitSha;
    }

    @Override
    public String toString() {
        return "GitHubBranch{" +
                "name='" + name + '\'' +
                ", lastCommitSha='" + lastCommitSha + '\'' +
                '}';
    }
}
