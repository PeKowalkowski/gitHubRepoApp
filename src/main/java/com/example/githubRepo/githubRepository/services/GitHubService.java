package com.example.githubRepo.githubRepository.services;
import com.example.githubRepo.githubRepository.models.GitHubBranchModel;
import com.example.githubRepo.githubRepository.models.GitHubCommitModel;
import com.example.githubRepo.githubRepository.models.GitHubModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final WebClient webClient;
    private final String githubToken;
    private Map<String, List<GitHubBranchModel>> branchCache = new HashMap<>();

    @Autowired
    public GitHubService(WebClient.Builder webClientBuilder, @Value("${github.api.token}") String githubToken) {
        this.webClient = webClientBuilder.baseUrl("https://api.github.com").build();
        this.githubToken = githubToken;
    }

    private String getRepo(GitHubModel repository) {
        return repository.getName();
    }

    public List<GitHubModel> getRepositories(String username) {
        try {
            ResponseEntity<List<GitHubModel>> response = webClient.get()
                    .uri("https://api.github.com/users/{username}/repos", username)
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + githubToken)
                    .retrieve()
                    .toEntityList(GitHubModel.class)
                    .block();

            List<GitHubModel> repositories = response.getBody().stream()
                    .filter(repository -> !repository.getFork())
                    .collect(Collectors.toList());

            if (repositories.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik nie istnieje na GitHub.");
            }

            repositories.forEach(repository -> {
                String repoName = getRepo(repository);
                try {
                    repository.setBranches(getBranches(username, repoName));
                    repository.setOwnerLogin(username);
                } catch (Exception ex) {
                    throw new RuntimeException("Error during retrieving repository data: " + ex.getMessage());
                }
            });

            return repositories;
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("User '" + username + "' don't exist.");
        }
    }

    private List<GitHubBranchModel> getBranches(String username, String repoName) {
        try {
            ResponseEntity<List<GitHubBranchModel>> response = webClient.get()
                    .uri("https://api.github.com/repos/{owner}/{repo}/branches", username, repoName)
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + githubToken)
                    .retrieve()
                    .toEntityList(GitHubBranchModel.class)
                    .block();

            List<GitHubBranchModel> branches = response.getBody();

            branches.forEach(branch -> {
                try {
                    ResponseEntity<List<GitHubCommitModel>> commitsResponse = webClient.get()
                            .uri("https://api.github.com/repos/{owner}/{repo}/commits", username, repoName)
                            .header("Accept", "application/json")
                            .header("Authorization", "Bearer " + githubToken)
                            .retrieve()
                            .toEntityList(GitHubCommitModel.class)
                            .block();

                    List<GitHubCommitModel> commits = commitsResponse.getBody();

                    if (!commits.isEmpty()) {
                        branch.setLastCommitSha(commits.get(0).getSha());
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("Error during retrieving branch data: " + ex.getMessage());
                }
            });
            return branches;
        } catch (Exception ex) {
            throw new RuntimeException("Error during retrieving branch data: " + ex.getMessage());
        }
    }
}

