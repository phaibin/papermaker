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
        tbxing = [x for x in soup.find_all('table', attrs={'class':'tbFang'}) ]
        for t in tbxing:
            t = t.findNextSibling('table', attrs={'class':'tbXing'})
            room = {}
            try :
                t = t.table
                t.tr.decompose()
                for tr in [tr for tr in list(t.children) if isinstance(tr, bs4.element.Tag)]:
                        td_tag = list(tr.children)
                        room[td_tag[0].a.getText()] = int(td_tag[3].getText())
                        if not room.get('id'):
                            if td_tag[4].input.get('onclick'):
                                match = re.findall('\d+', td_tag[4].input.get('onclick'))
                                room['id'] = match[0]
            except:
                print 'error get'
            result.append(room)
        return result
                        
    def makeUrl(self, page):
        return 'http://www.jinjianginns.com/resv/resv1----'+self.city+'------'+str(self.checkinDate)+'---'+str(self.checkoutDate)+'----00-----'+str(page)+'.html'
    
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
