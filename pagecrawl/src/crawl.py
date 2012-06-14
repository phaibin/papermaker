#!/usr/bin/python
# -*- coding:utf-8 -*-

from inns.fetch import Hotels as InnsHotels
from jje.fetch import Hotels as JJEHotels

city = '1100'
ih = InnsHotels(city)
hp, totals = ih.extract()
print totals

jh = JJEHotels('北京')
hp, totals = jh.extract()
print totals