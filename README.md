# ros_gh
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/elbraulio/ros_gh/blob/master/LICENSE)  [![Build Status](https://travis-ci.org/elbraulio/ros_gh.svg?branch=master)](https://travis-ci.org/elbraulio/ros_gh) [![](https://jitpack.io/v/elbraulio/ros_gh.svg)](https://jitpack.io/#elbraulio/ros_gh) [![](https://img.shields.io/badge/javadocs-ok-green.svg)](https://jitpack.io/com/elbraulio/ros_gh/latest/javadoc/) [![codecov](https://codecov.io/gh/elbraulio/ros_gh/branch/master/graph/badge.svg)](https://codecov.io/gh/elbraulio/ros_gh) [![codebeat  badge](https://codebeat.co/badges/509ed37d-0128-4ca3-9dfb-33e861b5e1e3)](https://codebeat.co/projects/github-com-elbraulio-ros_gh-master)

**ros_gh** is a tool that apply algorithms to recommend users to answers a given question . To achieve it there are three steps: collect data from github and ROS Answers, match identities and apply algorithms.

# How to use

#### maven

```xml
<dependency>
    <groupId>com.elbraulio</groupId>
    <artifactId>ros_gh</artifactId>
    <version>{version}</version>
</dependency>
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```



#### gradle

```groovy
dependencies {
        implementation 'com.elbraulio:ros_gh:{version}'
}
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```



# Collect data from Github and Ros Answers

**ros_gh** provides tools for fetching data from GitHub and ROS Answers. You can get them separately.

## Github

Here we use [jcabi-github](https://github.com/jcabi/jcabi-github) to get info from Github and [CanRequest](https://github.com/elbraulio/ros_gh/blob/master/src/test/java/org/elbraulio/rosgh/tools/CanRequestTest.java) to handle Github's API rate limit. Here are the steps for collecting data from Github:

1. get a [token](https://github.com/settings/tokens) from your Github account.
2. choose a [distribution file](https://github.com/elbraulio/ros_gh/tree/master/src/test/java/resources/github) to extract information from its package repositories.
3. use the script below to collect the data. In this example we fetch data from indigo distribution. This repository [includes these files](https://github.com/elbraulio/ros_gh/tree/master/src/test/resources/github) as Json files, all of them were from [this repo](https://github.com/ros/rosdistro) and can be used.

```java
@Test
public void ghInfo() throws InterruptedException, IOException {
    final String token = "secret_token";
    final String path = "src/test/java/resources/github/indigo.json";
    final Github github = new RtGithub(token);
    final CanRequest canRequest = new CanRequest(60);
    for (RosPackage rosPackage : new FromJsonFile(path).repoList()) {
        if (!rosPackage.source().isEmpty()) {
            final GhRepo ghRepo = rosPackage.asRepo(github);
            canRequest.waitForRate();
            final GhUser ghUser = new FetchGhUser(
                github, ghRepo.owner()
            ).ghUser();
            canRequest.waitForRate();
            final List<GhColaborator> colaborators = new Colaborators(
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

## Ros Answers

Here we use [jsoup](https://jsoup.org) as scraper. For getting all the user info, including questions and answers, first you might want to get all user profiles then all questions. 

### User profiles

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

### Questions, answers and comments

ROS Answers is supported by askbot, so it has an [API](https://github.com/ASKBOT/askbot-devel/blob/master/askbot/doc/source/api.rst) that can be used to read question's content but it doesn't provide any information about answers content. Therefore we also use scraper that read DOM pages to get information about answers.

```java
@Test
public void rosQuestions() throws IOException {
    final Iterator<JsonArray> iterable = new IterateApiQuestionPage();
    while (iterable.hasNext()) {
        final JsonArray questionArray = iterable.next();
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

# Implementing your own Algorithm
## Access to data
working on it ...

## Extending the base class
We provide some useful tools for researches  like pre made `sql` queries or basic health checks. The only thing you have to do is to extend some **Abstract**  classes.  For example, here we have a pseudo-implementation of a recommendation algorithm DevRec  proposed by Zhang et al in [this publication](https://www.semanticscholar.org/paper/DevRec%3A-A-Developer-Recommendation-System-for-Open-Zhang-Wang/019dab303f95a4eae9e408dbee7ac0d7b9917249).
```java
class Devrec extends AbstractAlgorithm {
    // Devrec initialization ...
    /**
    * Here we execute the algorithm and get the results. 
	  */
    @Override
    protected List<Aspirant> feed(Question question) {
        List<Aspirant> aspirants = new LinkedList();
        // use DB to get all users
        for(User user : DB.getAllUsers()) {
            final Topic topic = question.topic();
            // calculate KA from Tuu for a specific topic
            Number ka = new Ka(this.topicsRelation, topic);
            // get the project related to the question's topic
            final Project project = this.topicProjectRelation.get(topic);
            // calculate DA from a specific project
            Number da = new Da(this.projectsRelation, project);
            aspirants.add(new DevrecAspirant(ka.double(), da.double(), user));
        }
        // return all users without and specific order 😮
        return aspirants;
    }
}
```
You might be wandering about why the aspirants are returned unordered … Well it is for relieve you to do that. You only need to call `devrec.aspirants()` and you will get aspirants sorted by its rank. How does it work? It’s easy, `DevrecAspirant`was implemented from another interface `Aspirant`, see this code:
```java
class DevrecAspirant implements Aspirant {

    // useful and important things ...

    /**
    * easy 😎 ...
    */
    @Override
    public double rank() {
        return this.ka * 0.75 + this.da * 0.25;
    }
}
```
Now you see how we sort your data before we give it  back to you. All of these abstract classes and interfaces that you will extend or implement will help you to focus on the only important thing to you: the Algorithm’s implementation  🤓.

## Health Checks
working on it ...
### Resolve
Working on it …
