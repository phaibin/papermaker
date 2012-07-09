#!/usr/bin/python
# -*- coding:utf-8 -*-
import unittest
from core.fetch import INNS as InnsHotels
from lxml import etree

class TestInnsExtract(unittest.TestCase):
    def setUp(self):
        unittest.TestCase.setUp(self)
        with open('inns.html', 'r') as f:
            self.tree = etree.parse(f, etree.HTMLParser())
        self.hotels = InnsHotels('3100')
        self.soup = None

    def test_extract_page(self):
        (total, pages) = self.hotels._extractPages(self.tree)
        self.assertEqual((40,8), (total,pages))

    def test_extract_hotels(self):
        hotels = self.hotels._extractHotels(self.tree)
        self.assertEqual(5, len(hotels))
        self.assertEqual('北京国际展览中心店', hotels[0]['name'])
        self.assertEqual('0622', hotels[0]['id'])
        self.assertIsNotNone(hotels[0]['address'])
        self.assertIsNotNone(hotels[0]['bigScore'])
        self.assertIsNotNone(hotels[0]['telephone'])
        self.assertIsNotNone(hotels[0]['intro'])

    def test_extract_prices(self):
        prices = self.hotels._extractPrices(self.tree)
        self.assertEqual(5, len(prices))
        hotelPrice = prices[0]
        self.assertEqual(269, hotelPrice[u'商务房A'])

if __name__=='__main__':
    unittest.main()
