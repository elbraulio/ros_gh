# ros_gh
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/elbraulio/ros_gh/blob/master/LICENSE)  [![Build Status](https://travis-ci.org/elbraulio/ros_gh.svg?branch=master)](https://travis-ci.org/elbraulio/ros_gh)  [![codecov](https://codecov.io/gh/elbraulio/ros_gh/branch/master/graph/badge.svg)](https://codecov.io/gh/elbraulio/ros_gh) [![codebeat  badge](https://codebeat.co/badges/509ed37d-0128-4ca3-9dfb-33e861b5e1e3)](https://codebeat.co/projects/github-com-elbraulio-ros_gh-master)

**ros_gh** is a tool that matches identities between [ROS Answers](https://answers.ros.org/users/) and Github accounts then apply algorithms to recommend users to answers a given question . To achieve it there are three steps: collect data from github and ROS Answers, match identities and apply algorithms.

## Install

For now we don't have releases 🙄, so if you want to use these tools you'll need to clone this repo and install its maven dependencies:

```shell
git clone https://github.com/elbraulio/ros_gh.git
mvn verify
```

# Collect data from Github and Ros Answers

**ros_gh** provides tools for fetching data from GitHub and ROS Answers. You can get them separately.

### Github

Here we use [jcabi-github](https://github.com/jcabi/jcabi-github) to get info from Github and [CanRequest](https://github.com/elbraulio/ros_gh/blob/master/src/test/java/tools/CanRequestTest.java) to handle API Github's rate limit. Here are the steps for collecting data from Github:

1. get a [token](https://github.com/settings/tokens) from your Github account.
2. choose a [distribution file](https://github.com/elbraulio/ros_gh/tree/master/src/test/java/resources/github) to extract information from its package repositories.
3. Use the script below to collect the data. In this example we fetch data from indigo distribution. This repository [includes these files](https://github.com/elbraulio/ros_gh/tree/master/src/test/java/resources/github) as Json files, all of them were from [this repo](https://github.com/ros/rosdistro) and can be used.

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

Here we use [jsoup](https://jsoup.org) as scraper. For getting all the user info, including questions and answers, first you might want to get all user profiles then all questions. 

#### User profiles

```java
@Test
public void rosUserProfile() throws IOException {
    final String url = "https://answers.ros.org";
    final Document usersPage = Jsoup.connect(url + "/users/").get();
    final int initialPage = 1;
    final int lastPage = new LastRosUserPage(usersPage).value();
    final Iterator<String> usersLinks = new IteratePagedContent<>(
        new IterateDomPages(
            new RosUserPagedDom(),
            initialPage,
            lastPage,
            new IterateByUserLinks()
        )
    );
    while (usersLinks.hasNext()) {
        final String userLink = usersLinks.next();
        System.out.println(
            new RosDomUser(Jsoup.connect(root + userLink).get())
        );
    }
}
```

#### Questions, answers and comments

ROS Answers is supported by askbot, so it has an [API](https://github.com/ASKBOT/askbot-devel/blob/master/askbot/doc/source/api.rst) that can be used to read Questions content but it doesn't provide any information about answers content. Therefore we also use scraper that read DOM pages to get information about answers.

```java
@Test
public void rosQuestions() throws IOException {
    final Iterator<JsonArray> iterable = new IterateApiQuestionPage();
    while (iterable.hasNext()) {
        JsonArray questionArray = iterable.next();
        for (int i = 0; i < questionArray.size(); i++) {
            final ApiRosQuestion questionApi = new DefaultApiRosQuestion(
                questionArray.getJsonObject(i)
            );
            final RosDomQuestion questionDom = new DefaultRosDomQuestion(
                questionApi.id()
            );
            System.out.println("From API (title): " + questionApi.title());
            System.out.println("From API (url): " + questionApi.url());
            System.out.println("From DOM (votes): " + questionDom.votes());
        }
    }
}
```







