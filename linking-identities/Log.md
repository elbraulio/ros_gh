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

- https://github.com/chaoss/grimoirelab-perceval

- [Bitergia Analytics](https://bitergia.com/customers/red-hat.html)


21/06

- Added `discourse-users.html`: html table of [all users of ROS Discourse](https://discourse.ros.org/u?period=all) (2609 users)
- It has many pairs 'username', 'Real Name', so It may help for Identity Matching