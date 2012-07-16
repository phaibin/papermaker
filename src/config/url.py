#!/usr/bin/env python
# -*- coding:utf-8 -*-

pre_fix = 'controllers.'

urls = (
    '/',                    pre_fix + 'site.Index',
    '/start',                    pre_fix + 'site.Start',
    '/hotel/(\d+)',          pre_fix + 'site.Hotel',
    '/hotel/inns/(\d+)',          pre_fix + 'site.InnsHotelOnly',
    '/hotel/jje/(\d+)',          pre_fix + 'site.JJEHotelOnly',
    '/city/(\d+)',     pre_fix + 'site.City',
    '/city/inns/(\d+)',     pre_fix + 'site.InnsCityOnly',
    '/city/jje/(\d+)',     pre_fix + 'site.JJECityOnly',
    '/price/(\d+)',     pre_fix + 'site.Price',
    '/price/inns/(\d+)',     pre_fix + 'site.InnsPriceOnly',
    '/price/jje/(\d+)',     pre_fix + 'site.JJEPriceOnly',
)
