#!/usr/bin/python
# -*- coding:utf-8 -*-

pre_fix = 'controllers.'

urls = (
    '/',                    pre_fix + 'site.Index',
    '/start',                    pre_fix + 'site.Start',
    '/date/(\d+)',          pre_fix + 'site.DiffView',
    '/date/(\d+)/(\w+)',     pre_fix + 'site.CityDiffView'
)
