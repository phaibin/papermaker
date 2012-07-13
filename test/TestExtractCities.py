#!/usr/bin/env python
# -*- coding:utf-8 -*-
import unittest
from core.fetch import JJE as JJEHotels
from core.fetch import INNS as InnsHotels
import socket

class TestExtraCities(unittest.TestCase):
    def test_getInnsCities(self):
        hotels = InnsHotels('3100')
        cities = hotels._extractCities()
        self.assertEqual(161, len(cities))
        
    def test_getJJECities(self):
        hotels = JJEHotels('上海')
        cities = hotels._extractCities()
        self.assertEqual(118, len(cities))
        
origGetAddrInfo = socket.getaddrinfo

def getAddrInfoWrapper(host, port, family=0, socktype=0, proto=0, flags=0):
    return origGetAddrInfo(host, port, socket.AF_INET, socktype, proto, flags)

socket.getaddrinfo = getAddrInfoWrapper

if __name__=='__main__':
    unittest.main()
