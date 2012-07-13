#!/usr/bin/env python2.7
# -*- coding:utf-8 -*-

import unittest
from core.fetch import INNS
from core.fetch import JJE

class TestFetch(unittest.TestCase):
    def setUp(self):
        unittest.TestCase.setUp(self)
        self.cityDicts = {'1100':'北京', '1200':'天津', '3100':'上海', '4201':'武汉', '5000':'重庆', '6101':'西安', '4101':'郑州', '3201':'南京', '3202':'无锡', '4401':'广州', '5301':'昆明', '4403':'深圳', '4602':'三亚', '3205':'苏州', '3301':'杭州', '4301':'长沙'}
    
    def tearDown(self):
        unittest.TestCase.tearDown(self)
    
    def test_inns_fetch(self):
        inns = INNS('4401')
        print inns.extract()
#        print inns._fetchPage(inns._makeUrl(1))
    
    def test_jje_fetch(self):
        jje = JJE('广州')
        print jje.extract()
#        print jje._fetchPage(jje._makeUrl(1))

if __name__=='__main__':
    unittest.main()
