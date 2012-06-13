#!/usr/bin/python
# -*- coding:utf-8 -*-

from inns.fetch import Hotels as InnsHotels
from jje.fetch import Hotels as JJEHotels

city = '1100'
ih = InnsHotels(city)
ih.extractAndStore()

jh = JJEHotels('上海')
jh.extractAndStore()