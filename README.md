# ros_gh
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/elbraulio/ros_gh/blob/master/LICENSE)  [![Build Status](https://travis-ci.org/elbraulio/ros_gh.svg?branch=master)](https://travis-ci.org/elbraulio/ros_gh)  [![codecov](https://codecov.io/gh/elbraulio/ros_gh/branch/master/graph/badge.svg)](https://codecov.io/gh/elbraulio/ros_gh) [![codebeat  badge](https://codebeat.co/badges/509ed37d-0128-4ca3-9dfb-33e861b5e1e3)](https://codebeat.co/projects/github-com-elbraulio-ros_gh-master)

**ros_gh** is a tool that matches identities between [ROS Answers](https://answers.ros.org/users/) and Github accounts. 



#### Log

18/06

- Created a DB model for ROS Answers
- Discovered that RA has a (limited) [API](https://askbot.org/doc/api.html)
  - Question: https://answers.ros.org/api/v1/questions/294052/
  - User: https://answers.ros.org/api/v1/users/28038/
- Tasks:
  - Adapt scrapper to accelerate scrapping using the API
  - Populate the DB schema
  - Experiment with [Gitana](http://gitanadocs.getforge.io/)