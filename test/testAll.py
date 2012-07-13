#!/usr/bin/env python
# -*- coding:utf-8 -*-

import unittest

def suite():
    modules_to_test = ('TestInnsParser', 'TestMergeList', 'TestJJEParser', 'TestPythonFeature', 'TestSaveLoad') # and so on
    alltests = unittest.TestSuite()
    for module in map(__import__, modules_to_test):
        alltests.addTest(unittest.findTestCases(module))
    return alltests

if __name__ == '__main__':
    unittest.main(defaultTest='suite')