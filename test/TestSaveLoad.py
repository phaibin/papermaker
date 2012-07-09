#! /usr/bin/env python
# -*- coding:utf-8 -*-

import unittest
import core.crawl as cc
import os
import shutil
import json

class TestSaveLoad(unittest.TestCase):
    def setUp(self):
        unittest.TestCase.setUp(self)
        self.root = os.path.dirname(__file__)
        self.tmpFolderName = 'vardata'
        self.fileName = 'test.json'
    
    def tearDown(self):
        unittest.TestCase.tearDown(self)
        shutil.rmtree(os.sep.join([self.root, self.tmpFolderName]))
    
    def test_save_empty(self):
        cc.save([], self.fileName, self.root, self.tmpFolderName)
        with open(os.sep.join([self.root, self.tmpFolderName, self.fileName]), 'r') as f:
            self.assertEqual(0, len(json.load(f)))
            
    def test_save_simple(self):
        cc.save([1,2], self.fileName, self.root, self.tmpFolderName)
        with open(os.sep.join([self.root, self.tmpFolderName, self.fileName]), 'r') as f:
            self.assertEqual(2, len(json.load(f)))


if __name__=='__main__':
    unittest.main()
