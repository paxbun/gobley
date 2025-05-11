import React from "react";
import useGitHubStargazersCount from "./useGitHubStargazersCount";

export default function GithubStargazersCountButton({
  repository,
}: {
  repository: string;
}): React.ReactNode {
  const stargazersCount = useGitHubStargazersCount(repository);
  return (
    <a
      href={`https://github.com/${repository}`}
      target="_blank"
      className="navbar__item navbar__link header-github-link"
      aria-label="GitHub repository"
    >
      <span className="header-github-link-stats header-github-link-repo-name">
        gobley/gobley
      </span>
      <span className="header-github-link-stats header-github-link-stargazers">
        {" ☆ "}
        {stargazersCount ?? "…"}
      </span>
    </a>
  );
}
