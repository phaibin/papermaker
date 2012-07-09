#! /usr/bin/env python
# -*- coding:utf-8 -*-

import unittest
from lxml import etree

class TestLxmlParser(unittest.TestCase):
    def test_parse_content_with_subelement(self):
        introStr = '''<div>锦江之星动物园店位于北京市<a href="http://www.jinjianginns.com/resv/resv1----1100---45-------00-----1.html" title="西城区锦江之星连锁酒店">西城区</a><a href="http://www.jinjianginns.com/resv/resv1----1100---141-------00-----1.html" title="西直门连锁酒店预订">西直门</a>繁华商业区，紧邻北京展览馆，西距动物园400米，西邻首都体育馆和国家图书馆，东邻北京火车站北站，距离地铁4号线动物园站和地铁2号、13号线西直门站步行仅需10分钟。南邻金融街商圈和各大部委，附近是嘉茂商城、华堂商场、西直门长途客运站。周边医疗机构、餐饮娱乐、文化和商务设施一应俱全。酒店地理位置优越，乘坐公交和地铁可直达北京站、西客站、北京南站、<a href="http://www.jinjianginns.com/resv/resv1----1100---428-------00-----1.html" title="首都机场连锁酒店预定">首都机场</a>，交通十分便利。</div>'''
        tree = etree.fromstring(introStr)
        first = '''锦江之星动物园店位于北京市西城区西直门繁华商业区，紧邻北京展览馆，西距动物园400米，西邻首都体育馆和国家图书馆，东邻北京火车站北站，距离地铁4号线动物园站和地铁2号、13号线西直门站步行仅需10分钟。南邻金融街商圈和各大部委，附近是嘉茂商城、华堂商场、西直门长途客运站。周边医疗机构、餐饮娱乐、文化和商务设施一应俱全。酒店地理位置优越，乘坐公交和地铁可直达北京站、西客站、北京南站、首都机场，交通十分便利。'''
        second = ''.join(tree.itertext())
        self.assertEqual(first, second)

    def test_parse_content_without_subelement(self):
        introStr = '''<div>锦江之星北京奥体中心店坐落于著名的北京亚运村、奥运商圈内，环境是理想的进行商务、会议、休闲、娱乐等活动的地区，另外还有各种风格的餐饮汇聚此地。，酒店周围交通十分便捷，商业活动繁华。有著名的鸟巢、水立方、奥体中心、亚运村、奥运村、国际会议中心近在咫尺（可步行前往）。另外中日医院、安贞医院、妇产等医院距离均较近（均可步行）。还有各大银行分布周围。</div>'''
        tree = etree.fromstring(introStr)
        first = '''锦江之星北京奥体中心店坐落于著名的北京亚运村、奥运商圈内，环境是理想的进行商务、会议、休闲、娱乐等活动的地区，另外还有各种风格的餐饮汇聚此地。，酒店周围交通十分便捷，商业活动繁华。有著名的鸟巢、水立方、奥体中心、亚运村、奥运村、国际会议中心近在咫尺（可步行前往）。另外中日医院、安贞医院、妇产等医院距离均较近（均可步行）。还有各大银行分布周围。'''
        second = ''.join(tree.itertext())
        self.assertEqual(first, second)
        
    def test_or(self):
        t = 'a' or None
        self.assertEqual('a', t)
        t = 'a' or 'b'
        self.assertEqual('a', t)
        t = None or 'a'
        self.assertEqual('a', t)
        
    def test_and(self):
        t = 'a' and None
        self.assertEqual(None, t)
        t = None and 'a'
        self.assertEqual(None, t)