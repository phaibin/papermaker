#!/usr/bin/python
# -*- coding:utf-8 -*-
import unittest
from core.fetch import JJE as JJEHotels
from core.fetch import INNS as InnsHotels

class TestExtraCities(unittest.TestCase):
    def test_getInnsCities(self):
        hotels = InnsHotels('3100')
        cities = hotels._extractCities()
        self.assertEqual(161, len(cities))
        
    def test_getJJECities(self):
        hotels = JJEHotels('sh')
        cities = hotels._extractCities()
        self.assertEqual(125, len(cities))
        
if __name__=='__main__':
    unittest.main()
