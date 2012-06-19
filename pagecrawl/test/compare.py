#!/usr/bin/python
# -*- coding:utf-8 -*-

import os
import json
import logging
import datetime

logging.basicConfig(filename='pagecrawl.log', level=logging.DEBUG)
today = datetime.date.today()

def openFileToJson(fileName, *pathSep):
    path = os.sep.join(pathSep)
    os.chdir(path)
    f = open(fileName, 'r')
    result = json.load(f)
    f.close()
    return result

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

def compareCitys(citys):
    a = []
    for city, cityName in citys.items():
        inns = openFileToJson(city+'.txt', 'd:',today.strftime('%Y%m%d'),'inns')
        jje = openFileToJson(city+'.txt', 'd:',today.strftime('%Y%m%d'),'jje')
        a.append(compareResult(inns, jje))
    return a

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

