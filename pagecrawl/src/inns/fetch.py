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
        pageSys = soup.find('div', attrs={'class':'pageSys'})
        pageSys.div.decompose()
        pageSys.span.decompose()
        pageSys.a.decompose()
        text = pageSys.getText()
        match = re.findall('\d+', text)
        return (int(match[0]), int(match[2]))
   
    
    def store(self, chunk):
        pass
    
    
    def extractHotels(self, soup):
        result = []
        find_all = soup.find_all('table', attrs={'class':'mlist'})
        for t in find_all:
            dict = {}
            a_tag = t.tr.td.a
            innName = a_tag.getText()
            dict['name'] = innName
            link = a_tag['href']
            match = re.findall('\d+', link)
            dict['id'] = match[0]
            result.append(dict)
        return result
    
    def extractPrices(self, soup):
        result = []
        find_all = soup.find_all('table', attrs={'class':'tbXing'})
        for t in find_all:
            if t.table:
                continue
            t.tr.decompose()
            dict = {}
            for tr in list(t.children):
                if isinstance(tr, bs4.element.Tag):
                    tr_tag = list(tr.children)
                    td0 = tr_tag[0]
                    td3 = tr_tag[3]
                    dict[td0.a.getText()] = int(td3.getText())
                    if not dict.get('id'):
                        input_tag = tr_tag[4].input
                        onclikStr = input_tag.get('onclick')
                        if onclikStr:
                            match = re.findall('\d+', onclikStr)
                            dict['id'] = match[0]
            result.append(dict)
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