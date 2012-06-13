#!/usr/bin/python
# -*- coding:utf-8 -*-

import datetime
import urllib2
from bs4 import BeautifulSoup
import json
from StringIO import StringIO

class Hotels:
    '''Get all search result of specify city in jje.
    '''
    def __init__(self, city, checkinDate = datetime.date.today(), timedelta = datetime.timedelta(days=1)):
        self.city = city
        self.checkinDate = checkinDate
        self.checkoutDate = checkinDate + timedelta
        

    def extractPages(self, soup):
        find = soup.find('input', attrs={'id':'totalPage'})
        pages = find['value']
        find = soup.find('div', attrs={'class':'rightUpSectionTitle'})
        total = list(find.children)[-3].getText()
        return (int(total), int(pages))
    
    def store(self, chunk):
        pass
    
    
    def extractHotels(self, soup):
        find = soup.find('textarea', attrs={'id':'hotelMapInfo'})
        decode = json.load(StringIO(find.getText()))
        hotels = []
        for item in decode:
            hotels.append({'id':item['id'], 'name':item['name']})
        return hotels
    
    def extractPrices(self, soup):
        prices = []
        find = soup.find('textarea', attrs={'id':'hotelRoomInfo'})
        decode = json.load(StringIO(find.getText()))
        result = decode['result']
        for id, value in result.items():
            dict = {'id':id }
            for item in value:
                dict[item['roomName']] = item['minAveragePrice'] 
            prices.append(dict)
        return prices
                        
    def extractAndStore(self):
        print self.city, self.checkinDate, self.checkoutDate
        url = 'http://www.jinjiang.com/hotel/query?hotelTypes=&brands=JJINN&checkinDate=2012-06-14&checkoutDate=2012-06-15&cityName=上海&queryWords=&roomCount=1&pagination.page=1'
        opener = urllib2.build_opener();
        chunk = opener.open(url).read()
        self.store(chunk)
        soup = BeautifulSoup(chunk)

