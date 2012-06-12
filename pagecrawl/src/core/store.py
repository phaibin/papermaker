#!/usr/bin/python
# -*- coding:utf-8 -*-

import os

def save(content, *pathSep):
    path = os.sep.join(pathSep)
    os.makedirs(path)
    

save('abc', 'c','b','c')