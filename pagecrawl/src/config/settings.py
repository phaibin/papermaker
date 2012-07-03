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
cityDicts = {'1100':'北京', '1200':'天津', '3100':'上海', '4201':'武汉', '5000':'重庆', '6101':'西安', '4101':'郑州', '3201':'南京', '3202':'无锡', '4401':'广州', '5301':'昆明', '4403':'深圳', '4602':'三亚', '3205':'苏州', '3301':'杭州', '4301':'长沙'}

web.template.Template.globals['config'] = config
web.template.Template.globals['render'] = render
web.template.Template.globals['cityDicts'] = cityDicts
