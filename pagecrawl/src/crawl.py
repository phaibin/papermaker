#!/usr/bin/python
# -*- coding:utf-8 -*-

import os
from fetch import JJE as JJEHotels
from fetch import INNS as InnsHotels

city = '1100'
ih = InnsHotels(city)
hp, totals = ih.extract()
print totals

jh = JJEHotels('北京')
hp, totals = jh.extract()
print totals

def save(content, *pathSep):
    path = os.sep.join(pathSep)
    os.makedirs(path)
    

save('abc', 'c','b','c')