#!/usr/bin/python
# -*- coding:utf-8 -*-

import web

render = web.template.render('templates/', cache=False)

web.config.debug = True

config = web.storage(
    email='sean.xu@jinjiang.com',
    site_name = '网页比较',
    site_desc = '',
    static = '/static'
)

data_root = '/home/xuhaixiang/var/pagedata'

web.template.Template.globals['config'] = config
web.template.Template.globals['render'] = render
