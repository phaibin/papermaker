#!/usr/bin/python
# -*- coding:utf-8 -*-

import os
from core.fetch import JJE as JJEHotels
from core.fetch import INNS as InnsHotels
import json
import datetime

today = datetime.date.today()

def compareHotelPrice(hotelResult, innsHotel, jjeHotel):
    for name, value in innsHotel.items():
        found = False
        if name == 'name' or name == 'id':
            continue
        for ename, evalue in jjeHotel.items():
            if name.strip() == ename.strip():
                found = True
                if value == evalue:
                    hotelResult[name.strip()] = 'match'
                else:
                    hotelResult[name.strip()] = 'notMatch,'+str(value)+','+str(evalue)
        if not found:
            hotelResult[name] = 'notFound'


def compareResult(inns, jje):
    results = []
    for innsHotel in inns:
        found = False
        hotelResult = {'name':innsHotel['name']}
        for jjeHotel in jje:
            jjeHotelName = jjeHotel['name']
            if jjeHotelName.find('百时快捷') == -1:
                jjeHotelName = jjeHotelName[4:]
            if innsHotel['name'] == jjeHotelName:
                found = True
                hotelResult[innsHotel['name']] = 'found'
                compareHotelPrice(hotelResult, innsHotel, jjeHotel)
                jje.remove(jjeHotel)
        if not found:
            hotelResult[innsHotel['name']] = 'notFound'
        results.append(hotelResult)
    return results


def save(content, fileName, *pathSep):
    path = os.sep.join(pathSep)
    if not os.path.isdir(path):
        os.makedirs(path)
    os.chdir(path)
    f = open(fileName, 'w')
    json.dump(content, f)
    f.close()

#cityDicts = {'1100':'北京', '1200':'天津', '3100':'上海', '4201':'武汉', '5000':'重庆', '6101':'西安', '4101':'郑州', '3201':'南京', '3202':'无锡', '4401':'广州', '5301':'昆明', '4403':'深圳', '4602':'三亚', '3205':'苏州', '3301':'杭州', '4301':'长沙'}

def fetchCityPages(citys, root):
    for city, cityName in citys.items():
        try:
            inns, innsTotals = InnsHotels(city).extract()
            save(inns, city+'.txt', root,today.strftime('%Y%m%d'),'inns' )
            
            jje, jjeTotals = JJEHotels(cityName).extract()
            save(jje, city+'.txt', root,today.strftime('%Y%m%d'),'jje' )
            
            print cityName,innsTotals, jjeTotals
            
            diff = compareResult(inns, jje)
            save(diff, city+'.txt', root,today.strftime('%Y%m%d'),'diff' )
        except:
            print 'error in', city, cityName
    
def getAllDiff(root, date):
    path = os.sep.join([root, date, 'diff'])
    if not os.path.isdir(path):
        raise Exception('not found')
    results = []
    for file in os.listdir(path):
        print file
        f = open(path+os.sep+file, 'r')
        city = json.load(f)
        results.append(city)
    return results
        
diffDatas = getAllDiff('d:/pagedata', '20120619')
for cityData in diffDatas:
    for hotelData in cityData:
        for name, value in hotelData.items():
            print name , value
#fetchCityPages({'1200':'天津'})
#fetchCityPages(cityDicts, 'd:')
