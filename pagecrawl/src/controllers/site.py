#!/usr/bin/python
# -*- coding:utf-8 -*-

import web
import os
from config import settings
import core.crawl as cc

render = settings.render

class Index:
    def GET(self):
        listDir = os.listdir(settings.data_root)
        return render.index(listDir)

class DiffView:
    def GET(self, date):
        return render.diff(cc.getAllDiff(settings.data_root, date))
    
class DiffDetailView:
    def GET(self, date):
        return render.diffDetail(cc.getAllDiff(settings.data_root, date))
    
class Start:
    def GET(self):
        cc.fetchCityPages(settings.cityDicts, settings.data_root)
        return render.index([])    


