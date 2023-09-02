package com.example.githubRepo.githubRepository.models;

import java.util.List;

public class GitHubModel {
    private String name;
    private String ownerLogin;
    private List<GitHubBranchModel> branches;
    private boolean fork;

    public GitHubModel() {
    }

    public GitHubModel( String ownerLogin, List<GitHubBranchModel> branches, boolean fork, String name) {
        this.ownerLogin = ownerLogin;
        this.branches = branches;
        this.fork = fork;
        this.name = name;
    }




    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<GitHubBranchModel> getBranches() {
        return branches;
    }

    public void setBranches(List<GitHubBranchModel> branches) {
        this.branches = branches;
    }

    public boolean getFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public String getName() { // Dodaj getter
        return name;
    }

    public void setName(String name) { // Dodaj setter
        this.name = name;
    }

    @Override
    public String toString() {
        return "GitHubModel{" +
                ", ownerLogin='" + ownerLogin + '\'' +
                ", branches=" + branches +
                ", fork=" + fork +
                ", name='" + name + '\'' +
                '}';
    }
}
