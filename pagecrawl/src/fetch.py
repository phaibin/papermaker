#!/usr/bin/python
# -*- coding:utf-8 -*-

import datetime
import urllib2
import bs4
from bs4 import BeautifulSoup
import json
from StringIO import StringIO
import re

class Page:
    def __init__(self, city, checkinDate = datetime.date.today(), timedelta = datetime.timedelta(days=1)):
        self.city = city
        self.checkinDate = checkinDate
        self.checkoutDate = checkinDate + timedelta
    
    def extract(self):
        (soup, hotels, prices) = self.fetchPage(self.makeUrl(1))
        hp = [dict(x.items()+y.items()) for (x,y) in zip(hotels, prices)]
        (totals, pages) = self.extractPages(soup)
        for j in [i for i in range(2, pages+1) if pages>1]:
            (soup, hotels, prices) = self.fetchPage(self.makeUrl(j))
            hp.extend([dict(x.items()+y.items()) for (x,y) in zip(hotels, prices)])
        return hp, totals

    def fetchPage(self, url):
        soup = BeautifulSoup(urllib2.build_opener().open(url).read())
        return (soup, self.extractHotels(soup), self.extractPrices(soup))

class INNS(Page):
    def extractPages(self, soup):
        try:
            match = re.findall('\d+', soup.find('div', attrs={'class':'pageSys'}).getText())
            return (int(match[0]), int(match[2]))
        except:
            ts = soup.find_all('table', attrs={'class':'mlist'})
            return (len(ts), 1)
   
    
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
                pass
#               print 'error get'
            result.append(room)
        return result
                        
    def makeUrl(self, page):
        return 'http://www.jinjianginns.com/resv/resv1----'+self.city+'------'+str(self.checkinDate)+'---'+str(self.checkoutDate)+'----00-----'+str(page)+'.html'
    

class JJE(Page):
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

