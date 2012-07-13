#!/usr/bin/env python2.7
# -*- coding:utf-8 -*-

def compareCities(innsCities, jjeCities):
    '''Compare cities in inns&jje
    return format. 
    {'innswithcode':inns with code[(code,name),(...)], 'inns':inns cities, 'jje':jje cities, 'intersection':intersection cities, 'innsOnly': city only in inns, 'jjeOnly': city only in jje, 'innsTotal':total in inns, 'jjeTotal':total in jje}
    '''
    innscitySet = set([city[1].rstrip('市') for city in innsCities])
    jjeCitySet = set(jjeCities)
    innsDiffjje = innscitySet.difference(jjeCitySet)
    jjeDiffinns = jjeCitySet.difference(innscitySet)
    return {'innswithcode': innsCities, 'inns':list(innscitySet), 'jje':list(jjeCitySet),'intersection':list(innscitySet.intersection(jjeCitySet)), 'innsOnly':list(innsDiffjje), 'jjeOnly':list(jjeDiffinns), 'innsTotal':len(innsCities), 'jjeTotal':len(jjeCities)}

def compareHotels(innsHotels, jjeHotels):
    '''Compare hotels in each city
    return format
    {'inns':inns hotels, 'jje':jje hotels, 'innsOnly': hotels only in inns, 'jjeOnly': hotels only in jje, 'innsTotal':total in inns, 'jjeTotal':total in jje}
    '''
    innsHotelSet = set([h['name'] for h in innsHotels])
    jjeHotelSet = set(h['name'].lstrip('锦江之星') for h in jjeHotels)
    innsDiffjje = innsHotelSet.difference(jjeHotelSet)
    jjeDiffinns = jjeHotelSet.difference(innsHotelSet)
    return {'inns':list(innsHotelSet), 'jje':list(jjeHotelSet), 'innsOnly':list(innsDiffjje), 'jjeOnly':list(jjeDiffinns), 'innsTotal':len(innsHotels), 'jjeTotal':len(jjeHotels)}

def comparePrices(innsHotels, jjeHotels):
    '''Compare room type& price for every hotel in one city
    return format. 
    [{'name':hotelName, 'innsOnly':inns only room type, 'jjeOnly':jje only room type, 'priceDiff':price different room type}, {...}] for every city
    '''
    innsHotelSet = set([h['name'] for h in innsHotels])
    jjeHotelSet = set(h['name'].lstrip('锦江之星') for h in jjeHotels)
    innsIntesection = innsHotelSet.intersection(jjeHotelSet)
    result = []
    for d in [{'name':name} for name in innsIntesection]:
        innsHotel = [x for x in innsHotels if x['name'] == d['name']].pop()
        jjeHotel = [x for x in jjeHotels if x['name'].lstrip('锦江之星') == d['name']].pop()
        (priceDiff, innsDiff, jjeDiff) = _compareHotelPrice(innsHotel, jjeHotel)
#        print '======', d['name'], '==', '+'.join(priceDiff), '--', '-'.join(innsDiff), '||', '*'.join(jjeDiff)
        result.append({'name': d['name'], 'priceDiff':list(priceDiff), 'innsOnly':list(innsDiff), 'jjeOnly':list(jjeDiff)})
    return result

def _compareHotelPrice(innsHotel, jjeHotel):
    innsRoomSet = set(innsHotel['rooms'])
    jjeRoomSet = set(jjeHotel['rooms'])
    intesection = innsRoomSet.intersection(jjeRoomSet)
    priceDiff = set([name for name in intesection if innsHotel[name] != jjeHotel[name]])
    return priceDiff, innsRoomSet.difference(jjeRoomSet), jjeRoomSet.difference(innsRoomSet)
    
#def _getInnsCities():
#    return InnsHotels('')._extractCities()
#    
#def _getJJECities():
#    return JJEHotels('')._extractCities()
#
#def _getInnsHotels():
#    return InnsHotels('1100').extract()[0]
#
#def _getJJEHotels():
#    return JJEHotels('北京').extract()[0]

#innsHotels = _getInnsHotels() 
#jjeHotels = _getJJEHotels()
#c = compareHotels(innsHotels, jjeHotels)
#print 'only in inns', ' '.join(c['innsDiff'])
#print 'only in jje', ' '.join(c['jjeDiff'])
#
#d = comparePrices(innsHotels, jjeHotels)
#for t in d:
#    print t['name'], '|', ' '.join(t['innsDiff']),  '|',' '.join(t['jjeDiff']),  '|',' '.join(t['priceDiff']), '|' 
##print len(d['innsDiff']), ' '.join(d['innsDiff']), d['innsTotal'], d['jjeTotal']
