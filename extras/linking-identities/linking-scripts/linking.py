#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
    Linking GitHub and ROS Answers users database
'''

__author__ = "Pablo Estefó"
__email__ = "pestefo@dcc.uchile.cl"


import pandas as pd
import numpy as np
import recordlinkage as rl


def main():
    data_dir = '../'
    gh_file = 'gh_authors.csv'
    ra_file = 'ra_authors.csv'

    df_gh = pd.read_csv(data_dir + gh_file)
    df_ra = pd.read_csv(data_dir + ra_file)

    df_gh = df_gh.str.lower()
    df_ra = df_ra.str.lower()

    # username = lambda x: re.compile("(.+)\@").search(x).group(1)


if __name__ == "__main__":

    main()


# def username(x):
#     if np.isnan(x):
#         return '@@nan'
#     p = re.compile("(.+)\@").search(x)
#         if p == None:
#             return ''
#     return p.group(1)
