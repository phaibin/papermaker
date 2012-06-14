#!/usr/bin/python
# -*- coding:utf-8 -*-

import os
from fetch import JJE as JJEHotels
from fetch import INNS as InnsHotels
import json
import datetime

def save(content, fileName, *pathSep):
    path = os.sep.join(pathSep)
    if not os.path.isdir(path):
        os.makedirs(path)
    os.chdir(path)
    f = open(fileName, 'w')
    json.dump(content, f)
    f.close()

today = datetime.date.today()

cityDicts = {'1100':'北京', '1200':'天津', '3100':'上海', '4201':'武汉', '5000':'重庆', '6101':'西安', '4101':'郑州', '3201':'南京', '3202':'无锡', '4401':'广州', '5301':'昆明', '4403':'深圳', '4602':'三亚', '3205':'苏州', '3301':'杭州', '4301':'长沙'}
for city, cityName in cityDicts.items():
    try:
        hp, innsTotals = InnsHotels(city).extract()
        save(hp, city+'.'+str(innsTotals)+'.txt', 'd:','b','inns', today.strftime('%Y%m%d') )
        
        hp, jjeTotals = JJEHotels(cityName).extract()
        save(hp, city+'.'+str(jjeTotals)+'.txt', 'd:','b','jje', today.strftime('%Y%m%d') )
        print cityName,innsTotals, jjeTotals
    except:
        print 'error in', city, cityName
    

