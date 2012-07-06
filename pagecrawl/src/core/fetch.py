#!/usr/bin/python
# -*- coding:utf-8 -*-

import datetime
import urllib2
import json
from StringIO import StringIO
import re
from lxml import etree
import urllib

class Page:
    def __init__(self, cityPara, checkinDate = datetime.date.today(), timedelta = datetime.timedelta(days=1)):
        self.city = cityPara
        self.checkinDate = checkinDate
        self.checkoutDate = checkinDate + timedelta
    
    def _fetchPage(self, url):
        print 'fetch url:', url, datetime.datetime.now()
        request = self._makeRequest(url)
        tree = etree.parse(request, etree.HTMLParser())
        return (tree, self._extractHotels(tree), self._extractPrices(tree))

    def extract(self):
        (tree, hotels, prices) = self._fetchPage(self._makeUrl(1))
        hp = [dict(x.items()+y.items()) for (x,y) in zip(hotels, prices)]
        (totals, pages) = self._extractPages(tree)
        for j in [i for i in range(2, pages+1) if pages>1]:
            (tree, hotels, prices) = self._fetchPage(self._makeUrl(j))
            hp.extend([dict(x.items()+y.items()) for (x,y) in zip(hotels, prices)])
        return hp, totals
    
    def fetchCities(self):
        return self._extractCities()

class INNS(Page):
    def _makeRequest(self, url):
        req = urllib2.Request(url)
        request = urllib2.urlopen(req)
        return request
    
    def _extractPages(self, tree):
        pageSys = tree.xpath('//div[@class="pageSys"]')
        try:
            match = re.findall('\d+', pageSys.pop().text)
            return (int(match[0]), int(match[2]))
        except IndexError, err:
            print 'not found page info, use mlist instead : ', err
            try:
                return (len(tree.xpath('//table[@class="mlist"]')), 1)
            except:
                return (0,1)
   
    def _extractHotels(self, tree):
        result = []
        for t in tree.xpath('//table[@class="mlist"]'):
            innNameTag = t.xpath('tr[1]/*/a[@class="innName"]').pop()
            hotelInfo = {'id':re.findall('\d+', innNameTag.get('href'))[0], 'name': innNameTag.text}
            bigScoreTable = t.xpath('tr[2]/td[1]/table').pop()#涉外
            hotelInfo['bigScore'] = bigScoreTable.xpath('td/div/p[last()]/span').pop().text
            tbFangTag = t.xpath('tr[2]/td[2]/table[@class="tbFang"]').pop()
            try:
                hotelInfo['address'] = list(tbFangTag.find('tr/td/p[2]').itertext())[1]
                hotelInfo['telephone'] = list(tbFangTag.find('tr/td/p[3]').itertext())[1]
            except:
                print hotelInfo['name'], 'parse address error, because its no low price'
                try:
                    hotelInfo['address'] = list(tbFangTag.find('tr/td/p[1]').itertext())[1]
                    hotelInfo['telephone'] = list(tbFangTag.find('tr/td/p[2]').itertext())[1]
                except:
                    print hotelInfo['name'], 'parse address error'
            try:
                introTag = tbFangTag.find('tr/td/div/div')
                if introTag is None:
                    introTag = tbFangTag.find('tr/td/div/p')
                hotelInfo['intro'] = ''.join(introTag.itertext())
            except:
                print hotelInfo['name'], 'parse intro error.'
            print hotelInfo['address'],hotelInfo['telephone'] 
            result.append(hotelInfo)
        return result
    
    def _extractPrices(self, tree):
        result = []
        for t in tree.xpath('//table[@class="mlist"]'):
            try:
                tbXing = t.xpath('tr[2]/td[2]/*/table[@class="tbXing"][last()]').pop()
                room = {'rooms':[]}
                for tr in list(tbXing.iterchildren(tag='tr'))[1:]:
                    roomName = tr.find('td[1]/a').text 
                    room['rooms'].append(roomName)
                    room[roomName] = int(tr.find('td[4]').text)
                result.append(room)
            except:
                innNameTag = t.xpath('tr[1]/*/a[@class="innName"]').pop()
                print 'no room info, ', innNameTag.text
        return result
                        
    def _extractCities(self):
        '''return format
        [(beijing, 北京, 1100),(...)]
        '''
        req = urllib2.Request('http://www.jinjianginns.com/js/jjcity.js')
        content = urllib2.urlopen(req).read()
        cr = StringIO(content)
        for line in cr.readlines():
            if line.lstrip().startswith('var Cities '):
                return self._getCities(unicode(line))
            
    def _getCities(self, line):
        return _partition(_str2List(_findMatchInLine(line, '\'(.+)\''),'|'), 3)
                            
    def _makeUrl(self, page):
        return 'http://www.jinjianginns.com/resv/resv1----'+self.city+'------'+str(self.checkinDate)+'---'+str(self.checkoutDate)+'----00-----'+str(page)+'.html'
    
class JJE(Page):
    def _makeRequest(self, url):
        req = urllib2.Request(url)
        request = urllib2.urlopen(req)
        return request

    def _extractPages(self, tree):
        pagesTag = tree.xpath('//input[@id="totalPage"]').pop()
        totalTag = tree.xpath('//html/body/div[@id="wrap"]/div[@class="bodyContent"]/div[@class="rightContent"]/div[@class="rightUpSection"]/div[@class="rightUpSectionTitle"]/span[5]/b').pop()
        return (int(totalTag.text), int(pagesTag.get('value')))
    
    def _extractHotels(self, tree):
        r = [{'id':item['id'], 'name':item['name']} for item in json.load(StringIO(tree.find('//textarea[@id="hotelMapInfo"]').text))]
        r.sort(key=lambda x : str(x.get('id')))
        return r
    
    def _extractPrices(self, tree):
        prices = []
        hriTag = tree.find('//textarea[@id="hotelRoomInfo"]')
        tagString = etree.tostring(hriTag, encoding='utf-8', method='html')
        tagString = tagString.replace('<textarea id="hotelRoomInfo" style="display:none">', '').replace('</textarea>', '').strip()
        result = json.load(StringIO(tagString)).get('result')
        for idkey, value in result.items():
            p = {'id':idkey, 'rooms':[] }
            for item in value:
                p['rooms'].append(item['roomName'])
                p[item['roomName']] = item['minAveragePrice'] 
            prices.append(p)
        prices.sort(key=lambda x : str(x.get('id')))
        return prices
    
    def _extractCities(self):
        req = urllib2.Request('http://www.jinjiang.com/hotel/queryHotelCity')
        req.add_data(urllib.urlencode({'cityName':''}))
        content = urllib2.urlopen(req).read()
        tree = etree.parse(StringIO(content))
        cityNames = tree.xpath('/citiesDto/cities/name')
        return [cn.text for cn in cityNames]        

    def _makeUrl(self, page):
        return 'http://www.jinjiang.com/hotel/query?hotelTypes=&brands=JJINN%2CBESTAY&checkinDate='+str(self.checkinDate)+'&checkoutDate='+str(self.checkoutDate)+'&cityName='+self.city+'&queryWords=&roomCount=1&pagination.page='+str(page)

def _findMatchInLine(line, pattern):
    return re.search(pattern, line).group(1)

def _str2List(line, sep):
    return line.split(sep)

def _partition(lst, n):
    return zip(*[lst[i::n] for i in range(n)]) 

def partition2(lst, n):
    for i in range(0, len(lst), n):
        val = lst[i:i+n]
        if len(val) == n:
            yield tuple(val)
