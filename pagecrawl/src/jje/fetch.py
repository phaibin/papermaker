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
    
    def extractHotels(self, soup):
        return [{'id':item['id'], 'name':item['name']} for item in json.load(StringIO(soup.find('textarea', attrs={'id':'hotelMapInfo'}).getText()))]
    
    def extractPrices(self, soup):
        prices = []
        result = json.load(StringIO(soup.find('textarea', attrs={'id':'hotelRoomInfo'}).getText())).get('result')
        for idkey, value in result.items():
            p = {'id':idkey }
            for item in value:
                p[item['roomName']] = item['minAveragePrice'] 
            prices.append(p)
        return prices
                        
    def makeUrl(self, page):
        return 'http://www.jinjiang.com/hotel/query?hotelTypes=&brands=JJINN&checkinDate='+str(self.checkinDate)+'&checkoutDate='+str(self.checkoutDate)+'&cityName='+self.city+'&queryWords=&roomCount=1&pagination.page='+str(page)

    def extract(self):
        (soup, hotels, prices) = self.fetchPage(self.makeUrl(1))
        hp = [dict(x.items()+y.items()) for (x,y) in zip(hotels, prices)]
        (totals, pages) = self.extractPages(soup)
        for j in [i for i in range(2, pages+1) if pages>1]:
            (soup, hotels, prices) = self.fetchPage(self.makeUrl(j))
            hp.extend([dict(x.items()+y.items()) for (x,y) in zip(hotels, prices)])
        return hp, totals

    def fetchPage(self, url):
        print url
        opener = urllib2.build_opener();
        chunk = opener.open(url).read()
        soup = BeautifulSoup(chunk)
        return (soup, self.extractHotels(soup), self.extractPrices(soup))

