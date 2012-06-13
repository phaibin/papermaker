#!/usr/bin/python
# -*- coding:utf-8 -*-

import datetime
import urllib2
from bs4 import BeautifulSoup
import bs4
import re

class Hotels:
    '''Get all search result of specify city in jjinns
    '''
    def __init__(self, city, checkinDate = datetime.date.today(), timedelta = datetime.timedelta(days=1)):
        self.city = city
        self.checkinDate = checkinDate
        self.checkoutDate = checkinDate + timedelta
        

    def extractPages(self, soup):
        text = soup.find('div', attrs={'class':'pageSys'}).getText()
        match = re.findall('\d+', text)
        return (int(match[0]), int(match[2]))
   
    
    def store(self, chunk):
        pass
    
    
    def extractHotels(self, soup):
        result = []
        for t in soup.find_all('table', attrs={'class':'mlist'}):
            a_tag = t.tr.td.a
            innName = a_tag.getText()
            link = a_tag.get('href')
            match = re.findall('\d+', link)
            result.append({'id':match[0], 'name':innName})
        return result
    
    def extractPrices(self, soup):
        result = []
        for t in [t for t in soup.find_all('table', attrs={'class':'tbXing'}) if not t.table]:
            t.tr.decompose()
            room = {}
            for tr in [tr for tr in list(t.children) if isinstance(tr, bs4.element.Tag)]:
                    tr_tag = list(tr.children)
                    room[tr_tag[0].a.getText()] = int(tr_tag[3].getText())
                    if not room.get('id'):
                        if tr_tag[4].input.get('onclick'):
                            match = re.findall('\d+', tr_tag[4].input.get('onclick'))
                            room['id'] = match[0]
            result.append(room)
        return result
                        
    def extractAndStore(self):
        print self.city, self.checkinDate, self.checkoutDate
        url = 'http://www.jinjianginns.com/resv/resv1----'+self.city+'------'+str(self.checkinDate)+'---'+str(self.checkoutDate)+'----00-----1.html'
        opener = urllib2.build_opener();
        chunk = opener.open(url).read()
        self.store(chunk)
        soup = BeautifulSoup(chunk)
        (totals, pages) = self.extractPages(soup)
        hotels = self.extractHotels(soup)
        prices = self.extractPrices(soup)
        print totals, pages, hotels, prices