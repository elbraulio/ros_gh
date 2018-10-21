#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
    Linking GitHub and ROS Answers users database
'''

__author__ = "Pablo Estefó"
__email__ = "pestefo@dcc.uchile.cl"


import pandas as pd
import numpy as np
import sqlite3
from sklearn.metrics.pairwise import cosine_similarity
from sets import Set

conn = sqlite3.connect("../data/v1.db")

repo_commit_distribution_query = pd.read_sql_query("""
select full_name, nb_commits
from 
(select gh_repo_id, count(gh_repo_id) as nb_commits
from gh_commits
group by gh_repo_id) as count_comm
join gh_repo on count_comm.gh_repo_id = gh_repo.id
                            """, conn)

print repo_commit_distribution_query
