# ros_gh
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/elbraulio/ros_gh/blob/master/LICENSE)  [![Build Status](https://travis-ci.org/elbraulio/ros_gh.svg?branch=master)](https://travis-ci.org/elbraulio/ros_gh)  [![codecov](https://codecov.io/gh/elbraulio/ros_gh/branch/master/graph/badge.svg)](https://codecov.io/gh/elbraulio/ros_gh) [![codebeat  badge](https://codebeat.co/badges/509ed37d-0128-4ca3-9dfb-33e861b5e1e3)](https://codebeat.co/projects/github-com-elbraulio-ros_gh-master)

**ros_gh** is a tool that matches identities between [ROS Answers](https://answers.ros.org/users/) and Github accounts then apply algorithms to recommend users to answers a given question . To achieve this there are three steps: collect data from github and ROS Answers, match identities and apply algorithms.

# Collect data from Github and Ros Answers

**ros_gh** provides tools for fetching data from GitHub and ROS Answers.

### Github

Here we use [jcabi-github](https://github.com/jcabi/jcabi-github) to get info from Github and [CanRequest](https://github.com/elbraulio/ros_gh/blob/master/src/test/java/tools/CanRequestTest.java) to handle API Github's rate limit. This are the steps for collecting data from Github:

1. get a [token](https://github.com/settings/tokens) from your Github account.
2. choose a [distribution file](https://github.com/elbraulio/ros_gh/tree/master/src/test/java/resources/github) to extract information from its package repositories.
3. Use the script below to collect the data. In this example we fetch data from indigo distribution. This repository [includes this files](https://github.com/elbraulio/ros_gh/tree/master/src/test/java/resources/github) as Json files, all of them were from [this repo](https://github.com/ros/rosdistro) and can be used.

```java
@Test
public void ghInfo() throws InterruptedException, IOException {
    final String token = "secret_token";
    final String path = "src/test/java/resources/github/indigo.json";
    final Github github = new RtGithub(token);
    final CanRequest canRequest = new CanRequest(60);
    for (RosPackage rosPackage : new FromJsonFile(path).repoList()) {
        if (!rosPackage.source().isEmpty()) {
            GhRepo ghRepo = rosPackage.asRepo(github);
            canRequest.waitForRate();
            GhUser ghUser = new FetchGhUser(
                github, ghRepo.owner()
            ).ghUser();
            canRequest.waitForRate();
            List<GhColaborator> colaborators = new Colaborators(
                ghRepo.fullName(), canRequest, github
            ).colaboratorList();
            System.out.println("repo: " + ghRepo.name());
            System.out.println("owner: " + ghUser.login());
            System.out.println("colaborator: " + colaborators.size());
            System.out.println("-----------------------------------");
        }
    }
}
```

### Ros Answers





