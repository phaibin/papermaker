#! /usr/bin/env python
# -*- coding:utf-8 -*-
          
import unittest
import datetime 
import re
from bs4 import BeautifulSoup
import os

class TestOs(unittest.TestCase):
    def test_env(self):
        self.assertEquals('utf-8', os.environ["PYTHONIOENCODING"])
    
class TestDateTime(unittest.TestCase):
    def test_today(self):
        today = datetime.date.today()
        td = datetime.timedelta(days=1)
        tomorrow = today + td
        
class TestStr(unittest.TestCase):
    def test_parse_str(self):
        string = '共有 82 条信息   每页 5 条   共 17 页'
        match = re.findall('\d+', string)
        self.assertTrue(match)
        self.assertEqual(3, len(match))
        self.assertEqual('82', match[0])
        self.assertEqual('5', match[1])
        self.assertEqual('17', match[2])

    def test_parse_str_2(self):
        string = '/CityInnHotel/jChainHotelInfo----0783---3100---2012-06-13---2012-06-14.html'
        match = re.findall('\d+', string)
        self.assertTrue(match)
        self.assertEqual('0783', match[0])

    def test_parse_str_3(self):
        string = "javascript:resv('0783','0001')"
        match = re.findall('\d+', string)
        self.assertTrue(match)
        self.assertEqual('0783', match[0])


class TestBs4(unittest.TestCase):
    def test_parse_soup(self):
        string = '''<div class="pageSys"><div class="qTextSort"><span class="sort-bg">排序方式</span> <a href="  Resv1----1100------2012-06-12---2012-06-13----11-----1.html" id="pU">价格↓</a><a href=" Resv1----1100------2012-06-12---2012-06-13----10-----1.html" id="pD">价格↑</a><a href=" Resv1----1100------2012-06-12---2012-06-13----21-----1.html" id="cU">用户评分</a></div>共有 40 条信息   每页 5 条   共 8 页 <span class="pageCurrent">1</span> <a href=" Resv1----1100------2012-06-12---2012-06-13----00-----2.html">2</a> <a href=" Resv1----1100------2012-06-12---2012-06-13----00-----3.html">3</a> <a href=" Resv1----1100------2012-06-12---2012-06-13----00-----4.html">4</a> <a href=" Resv1----1100------2012-06-12---2012-06-13----00-----5.html">5</a> <a href=" Resv1----1100------2012-06-12---2012-06-13----00-----6.html">6</a> <a href=" Resv1----1100------2012-06-12---2012-06-13----00-----7.html">7</a> <a href=" Resv1----1100------2012-06-12---2012-06-13----00-----8.html">8</a> <a href=" Resv1----1100------2012-06-12---2012-06-13----00-----2.html">下一页 &gt;</a></div>'''
        soup = BeautifulSoup(string)
        find = soup.find('div', attrs={'class':'qTextSort'})
        find.decompose()
        span = soup.find('span')
        span.decompose()
        ahrefs = soup.findAll('a')
        for ele in ahrefs:
            ele.decompose()
        text = soup.getText()
        match = re.findall('\d+', text)
        (total, size, pages) = (int(match[0]),int(match[1]), int(match[2]))
        self.assertEqual(40, total)
        self.assertEqual(5, size)
        self.assertEqual(8, pages)

