#!/usr/bin/python
# -*- coding:utf-8 -*-

import unittest

class TestOs(unittest.TestCase):
    def test_merge(self):
        a = [{'id':1,'name':'a'}, {'id':2,'name':'b'}]
        b = [{'id':1, 'room':'c','price':44},{'room':'d','price':22}]
        c = [dict(x.items() + y.items()) for (x,y) in zip(a,b)]
        self.assertEqual(2, len(c))
        self.assertEqual(4, len(c[0]))
        self.assertEqual('c', c[0]['room'])
    
    def test_loop(self):
        pages = 3
        for j in [i for i in range(2, pages+1) if pages>1]:
            print j
            
        
        