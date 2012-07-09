#! /usr/bin/env python
# -*- coding:utf-8 -*-

import unittest

class TestOs(unittest.TestCase):
    def testmerge(self):
        a = [{'id':1,'name':'a'}, {'id':2,'name':'b'}]
        b = [{'id':1, 'room':'c','price':44},{'room':'d','price':22}]
        c = [dict(x.items() + y.items()) for (x,y) in zip(a,b)]
        self.assertEqual(2, len(c))
        self.assertEqual(4, len(c[0]))
        self.assertEqual('c', c[0]['room'])
    
    def testloop(self):
        pages = 3
        t = 0
        for j in [i for i in range(2, pages+1) if pages>1]:
            t += 1
        self.assertEqual(2, t)

    def testSetUnion(self):
        a = ['1100','1200','1300','1400']
        b = ['1100', '1500', '1600']
        seta = set(a)
        setb = set(b)
        unionab = set(['1100','1200','1300','1400', '1500', '1600'])
        self.assertEqual(unionab, seta.union(setb))
        adiffb = set(['1400', '1300', '1200'])
        self.assertEqual(adiffb, seta.difference(setb))
        bdiffa = set(['1600', '1500'])
        self.assertEqual(bdiffa, setb.difference(seta))
        intersectionab = set(['1100'])
        self.assertEqual(intersectionab, seta.intersection(setb))
        
    def testU(self):
        a = set(['泰安','象山县','滨州','淮南','镇江','泰州','成都','新昌县','荆州','太原','通化','莆田','大连'])
        b = set(['台州','天水','清远','莆田','北京','锦州'])
        self.assertEqual('台州锦州北京天水清远', ''.join(b.difference(a)))
        
if __name__=='__main__':
    unittest.main()
