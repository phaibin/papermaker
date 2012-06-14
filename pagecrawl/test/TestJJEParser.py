#!/usr/bin/python
# -*- coding:utf-8 -*-
import unittest
from bs4 import BeautifulSoup
from fetch import JJE as JJEHotels

class TestJJEExtract(unittest.TestCase):
    
    def setUp(self):
        unittest.TestCase.setUp(self)
        f = open('jje.html', 'r')
        content = f.read()
        soup = BeautifulSoup(content)
        self.soup = soup
        self.hotels = JJEHotels('上海')
        
    def test_extract_page(self):
        (total, pages) = self.hotels.extractPages(self.soup)
        self.assertEqual((74,8), (total, pages))

    def test_extract_hotels(self):
        hotels = self.hotels.extractHotels(self.soup)
        self.assertEqual(10, len(hotels))
        dict = hotels[0]
        self.assertEqual(704, dict['id'])
        self.assertEqual('锦江之星上海虹梅南路店', dict['name'])

    def test_extract_prices(self):
        prices = self.hotels.extractPrices(self.soup)
        self.assertEqual(10, len(prices))
        dict = prices[0]
        self.assertEqual(206, dict[u'双人房A'])
        self.assertEqual('142', dict['id'])
