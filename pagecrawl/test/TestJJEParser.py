#!/usr/bin/python
# -*- coding:utf-8 -*-
import unittest
from core.fetch import JJE as JJEHotels
from lxml import etree

class TestJJEExtract(unittest.TestCase):
    
    def setUp(self):
        unittest.TestCase.setUp(self)
        with open('jje.html', 'r') as f:
            self.tree = etree.parse(f, etree.HTMLParser())
        self.hotels = JJEHotels('上海')
        self.soup = None
        
    def test_extract_page(self):
        (total, pages) = self.hotels._extractPages(self.tree)
        self.assertEqual((74,8), (total, pages))

    def test_extract_hotels(self):
        hotels = self.hotels._extractHotels(self.tree)
        self.assertEqual(10, len(hotels))
        self.assertEqual(110, hotels[0]['id'])
        self.assertEqual('锦江之星上海新国际博览中心店', hotels[0]['name'])

    def test_extract_prices(self):
        prices = self.hotels._extractPrices(self.tree)
        self.assertEqual(10, len(prices))
        self.assertEqual(305, prices[0][u'标准房A'])
        self.assertEqual('110', prices[0]['id'])

if __name__=='__main__':
    unittest.main()
