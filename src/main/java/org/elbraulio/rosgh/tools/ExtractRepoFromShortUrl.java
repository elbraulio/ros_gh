package org.elbraulio.rosgh.tools;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class ExtractRepoFromShortUrl {
    private final String repoUrl;

    public ExtractRepoFromShortUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String repoFullName() {
        String[] info = this.repoUrl.split("/");
        String ownerLogin = info[3];
        String repoName = info[4].substring(0, info[4].length() - 4);
        return ownerLogin + "/" + repoName;
    }
}
