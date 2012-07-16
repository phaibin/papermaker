#!/usr/bin/env python
# -*- coding:utf-8 -*-

import os
from core.fetch import JJE as JJEHotels
from core.fetch import INNS as InnsHotels
from core import compare
import json
import datetime

today = datetime.date.today()

def save(content, fileName, *pathSep):
    _save(content, fileName, *pathSep)

def _save(content, fileName, *pathSep):
    path = os.sep.join(pathSep)
    if not os.path.isdir(path):
        os.makedirs(path)
    with open(os.sep.join([path,fileName]), 'w') as f: 
        json.dump(content, f)


def _fetchCityPages(citys, root, ignoreCompare=False, ignoreJJE=False):
    for city, cityName in citys:
        try:
            print cityName
            
            inns, innsTotals = InnsHotels(city).extract()
            _save(inns, city+'.json', root,today.strftime('%Y%m%d'),'inns' )
            print 'innstotal:', innsTotals
            
            if not ignoreJJE:
                jje, jjeTotals = JJEHotels(cityName).extract()
                _save(jje, city+'.json', root,today.strftime('%Y%m%d'),'jje' )
                print 'jjetotal:', jjeTotals
            
            if not ignoreCompare:
                diffHotel = compare.compareHotels(inns, jje)
                deffPrice = compare.comparePrices(inns, jje)
                _save(diffHotel, city+'.json', root,today.strftime('%Y%m%d'), 'hotel' )
                _save(deffPrice, city+'.json', root,today.strftime('%Y%m%d'), 'prices' )
        except Exception, err:
            print 'error in', city, cityName, err
    
def do(root = '/home/xuhaixiang/var/pagedata'):
    print 'do fetch....'
    innsCities = InnsHotels('').fetchCities()
    jjeCities = JJEHotels('').fetchCities()
    a = compare.compareCities(innsCities, jjeCities)
    _save(a, 'cities.json', root,today.strftime('%Y%m%d'))
    
    cities = [(cityInfo[2], cityInfo[1].rstrip('市')) for cityInfo in innsCities]
    citiesInterSection = [city for city in cities if city[1] in a['intersection']]
    _fetchCityPages(citiesInterSection, root)
    
    citiesInnsOnly = [city for city in cities if city[1] in a['innsOnly']]
    _fetchCityPages(citiesInnsOnly, root, ignoreCompare=True, ignoreJJE=True)

#cityDicts = {'1100':'北京', '1200':'天津', '3100':'上海', '4201':'武汉', '5000':'重庆', '6101':'西安', '4101':'郑州', '3201':'南京', '3202':'无锡', '4401':'广州', '5301':'昆明', '4403':'深圳', '4602':'三亚', '3205':'苏州', '3301':'杭州', '4301':'长沙'}
#cityDicts = [('3205','苏州')]
#_fetchCityPages(cityDicts, '/home/xuhaixiang/var/pagedata')

