#!/usr/bin/env python2.7
# -*- coding:utf-8 -*-

import unittest
import urllib2
import urllib
from urlparse import urlparse
from lxml import etree
from StringIO import StringIO
import re

class TestUrlGetPost(unittest.TestCase):
    def test_GetJinjiangHome(self):
        req = urllib2.Request('http://www.jinjiang.com/')
        try:
            response = urllib2.urlopen(req)
            parser = etree.HTMLParser()
            tree = etree.parse(response, parser)
            title = tree.xpath('/html/head/title')[0].text
            self.assertEqual(u'锦江旅行⁺-酒店预订,旅游度假,汽车租赁,机票预订,锦江e卡通,4S', title)
        except urllib2.HTTPError, ex:
            print 'code %s message %s reason %s' % (ex.code, ex.message, ex.reason)
            print ex

    def test_parser(self):
        p = urlparse("http://www.jinjiang.com/hotle/query")
        self.assertEqual('www.jinjiang.com', p.netloc)
        self.assertEqual('http', p.scheme)
        
        
    def test_PostJinjingHotelCity(self):
        req = self.makeRequest('http://www.jinjiang.com/hotel/queryHotelCity')
        data = {'cityName':''}
        encoded_data = urllib.urlencode(data)
        req.add_data(encoded_data)
        response = urllib2.urlopen(req)
        content = response.read()
        tree = etree.parse(StringIO(content))
        cityNames = tree.xpath('/citiesDto/cities/name')
        self.assertEquals(125, len(cityNames))
            
    def makeRequest(self, url):
#        httpHandler = urllib2.HTTPHandler(debuglevel=1)
#        httpsHandler = urllib2.HTTPSHandler(debuglevel=1)
#        opener = urllib2.build_opener(httpHandler,httpsHandler)
#        urllib2.install_opener(opener)
        req = urllib2.Request(url)
        return req
    
    def test_GetInnsCity(self):
        req = self.makeRequest('http://www.jinjianginns.com/js/jjcity.js')
        response = urllib2.urlopen(req)
        content = response.read()
        cr = StringIO(content)
        for line in cr.readlines():
            if line.lstrip().startswith('var Cities '):
                pattern = '\'(.+)\''
                p = re.compile(pattern)
                match = p.search(line)
                content = match._partition(1)
                cityList = content.split('|')
                cgl = _partition(cityList, 3)
                self.assertEqual(161, len(cgl))
                break

def _partition(lst, n):
    return zip(*[lst[i::n] for i in range(n)]) 

def group2(lst, n):
    for i in range(0, len(lst), n):
        val = lst[i:i+n]
        if len(val) == n:
            yield tuple(val)

if __name__=='__main__':
    unittest.main()
