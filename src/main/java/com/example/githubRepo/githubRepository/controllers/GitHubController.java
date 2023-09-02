package com.example.githubRepo.githubRepository.controllers;
import com.example.githubRepo.githubRepository.models.GitHubModel;
import com.example.githubRepo.githubRepository.services.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private final GitHubService gitHubService;


    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/repositories/{username}")
    public ResponseEntity<?> getUserRepositories(@PathVariable("username") String username,
                                                 @RequestHeader("Accept") String acceptHeader) {
        try {
            if (acceptHeader.equals("application/xml")) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                        "The XML format is not supported");
            }

            List<GitHubModel> repositories = gitHubService.getRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getReason());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }



}
