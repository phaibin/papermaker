#!/usr/bin/python
# -*- coding:utf-8 -*-

import os
from core.fetch import JJE as JJEHotels
from core.fetch import INNS as InnsHotels
from core import compare
import json
import datetime

today = datetime.date.today()

def save(content, fileName, *pathSep):
    path = os.sep.join(pathSep)
    if not os.path.isdir(path):
        os.makedirs(path)
    with open(os.sep.join([path,fileName]), 'w') as f: 
        json.dump(content, f)


def fetchCityPages(citys, root):
    for city, cityName in citys.items():
        try:
            inns, innsTotals = InnsHotels(city).extract()
#            save(inns, city+'.txt', root,today.strftime('%Y%m%d'),'inns' )
            
            jje, jjeTotals = JJEHotels(cityName).extract()
#            save(jje, city+'.txt', root,today.strftime('%Y%m%d'),'jje' )
            
            print cityName,innsTotals, jjeTotals
            
            diffHotel = compare.compareHotels(inns, jje)
            deffPrice = compare.comparePrices(inns, jje)

        except:
            print 'error in', city, cityName
    
#fetchCityPages({'1200':'天津'},'d:/pagedata')
#fetchCityPages(cityDicts, 'd:')

#cityDicts = {'1100':'北京', '1200':'天津', '3100':'上海', '4201':'武汉', '5000':'重庆', '6101':'西安', '4101':'郑州', '3201':'南京', '3202':'无锡', '4401':'广州', '5301':'昆明', '4403':'深圳', '4602':'三亚', '3205':'苏州', '3301':'杭州', '4301':'长沙'}
#fetchCityPages(cityDicts, '/home/xuhaixiang/var/pagedata')