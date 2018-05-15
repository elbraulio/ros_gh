# ros_gh
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/elbraulio/ros_gh/blob/master/LICENSE)  [![Build Status](https://travis-ci.org/elbraulio/ros_gh.svg?branch=master)](https://travis-ci.org/elbraulio/ros_gh)  [![codecov](https://codecov.io/gh/elbraulio/ros_gh/branch/master/graph/badge.svg)](https://codecov.io/gh/elbraulio/ros_gh)

**ros_gh** is a tool that matches identities between [ROS Answers](https://answers.ros.org/users/) and Github accounts. 

# How this works
#### Testing SQLite db
This project includes a script to create a SQLite data base for testing. To create it, just open your terminal in the project's folder, then follow this 3 steps: 
1. `$ cd src/test/java/resources/sqlite`
2. `$ sqlite3 test.db`
3. `sqlite> .read model`

