#!/usr/bin/python
# -*- coding:utf-8 -*-
import unittest
from bs4 import BeautifulSoup
from fetch import INNS as InnsHotels

class TestInnsExtract(unittest.TestCase):
    def setUp(self):
        unittest.TestCase.setUp(self)
        f = open('inns.html', 'r')
        content = f.read()
        soup = BeautifulSoup(content)
        self.soup = soup
        self.hotels = InnsHotels('3100')

    def test_extract_page(self):
        (total, pages) = self.hotels.extractPages(self.soup)
        self.assertEqual((40,8), (total,pages))

    def test_extract_hotels(self):
        hotels = self.hotels.extractHotels(self.soup)
        self.assertEqual(5, len(hotels))
        self.assertEqual('北京国际展览中心店', hotels[0]['name'])
        self.assertEqual('0622', hotels[0]['id'])

    def test_extract_prices(self):
        prices = self.hotels.extractPrices(self.soup)
        self.assertEqual(5, len(prices))
        hotelPrice = prices[0]
        self.assertEqual(269, hotelPrice[u'商务房A'])
