# Linking identities from GitHub and ROS Answers

## GitHub

1. Cloned all repositories indexed into `/{hydro,indigo,jade,kinetic,lunar}/distribution.yaml` in [ros/rosdistro](http://gihtub.com/ros/rosdistro)

2. Generated logs to obtain all authors of commits in all cloned repositories: `git log --pretty=format"%aN,%aE"`

3. Generated a unique list with all author names and emails from all the gitlogs: `sort -u path_to_logs/*.gitlog > authors.csv`, 6199 authors.

4. For linking identities, we use Fril (v 2.1.5)

   Too basic, moving to `recordlinkage` from python, [info here](http://recordlinkage.readthedocs.io/en/latest/installation.html)
