#!/usr/bin/env python2.7
# -*- coding:utf-8 -*-

import web
import os
from config import settings
import json
import core.crawl as cc

render = settings.render

def _load(fileName, *pathSep):
    path = os.sep.join(pathSep)
    if not os.path.isdir(path):
        os.makedirs(path)
    with open(os.sep.join([path,fileName]), 'r') as f: 
        return json.load(f, encoding='utf-8')

def _citiesLoad(date):
    a = _load('cities.json', settings.data_root, date)
    inns = a['innswithcode']
    cities = [(cityInfo[2], cityInfo[1].rstrip(u'市')) for cityInfo in inns]
    citiesInterSection = [city for city in cities if city[1] in a['intersection']]
    return cities, citiesInterSection

class Index:
    def GET(self):
        listDir = os.listdir(settings.data_root)
        listDir.reverse()
        return render.index(listDir)

class Start:
    def GET(self):
        cc.do(settings.data_root)
        return web.seeother('/')    

class City:
    def GET(self, date):
        cities = _load('cities.json', settings.data_root, date)
        return render.city(cities)

class InnsCityOnly:
    def GET(self, date):
        cities = _load('cities.json', settings.data_root, date)
        return render.innscityonly(cities)

class JJECityOnly:
    def GET(self, date):
        cities = _load('cities.json', settings.data_root, date)
        return render.jjecityonly(cities)

class Hotel:
    def GET(self, date):
        (cities, citiesInterSection) = _citiesLoad(date)
        diffHotels = []
        for city in citiesInterSection:
            diffHotels.append({'city':city[1], 'data':_load(city[0]+'.json', settings.data_root, date,'hotel' )})
            
        return render.hotel(cities, diffHotels)
    
class InnsHotelOnly:
    def GET(self, date):
        (cities, citiesInterSection) = _citiesLoad(date)
        diffHotels = []
        for city in citiesInterSection:
            diffHotels.append(_load(city[0]+'.json', settings.data_root, date,'hotel' ))
        return render.hotel(cities, diffHotels)
    
class JJEHotelOnly:
    def GET(self, date):
        (cities, citiesInterSection) = _citiesLoad(date)
        diffHotels = []
        for city in citiesInterSection:
            diffHotels.append(_load(city[0]+'.json', settings.data_root, date,'hotel' ))
        return render.hotel(cities, diffHotels)

class Price:
    def GET(self, date):
        (cities, citiesInterSection) = _citiesLoad(date)
        diffPrices = []
        for city in citiesInterSection:
            diffPrices.append({'city':city[1], 'data':_load(city[0]+'.json', settings.data_root, date,'prices' )})
        return render.price(cities, diffPrices)

class InnsPriceOnly:
    def GET(self, date):
        (cities, citiesInterSection) = _citiesLoad(date)
        diffPrices = []
        for city in citiesInterSection:
            diffPrices.append(_load(city[0]+'.json', settings.data_root, date,'prices' ))
        return render.innspriceonly(cities, diffPrices)

class JJEPriceOnly:
    def GET(self, date):
        (cities, citiesInterSection) = _citiesLoad(date)
        diffPrices = []
        for city in citiesInterSection:
            diffPrices.append(_load(city[0]+'.json', settings.data_root, date,'prices' ))
        return render.innspriceonly(cities, diffPrices)

