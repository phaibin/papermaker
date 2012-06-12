import datetime
import urllib2
from bs4 import BeautifulSoup
import re
import os

class CityPage:
    '''Get all search result of specify city in jjinns
    '''
    def __init__(self, city, checkinDate = datetime.date.today(), timedelta = datetime.timedelta(days=1)):
        self.city = city
        self.checkinDate = checkinDate
        self.checkoutDate = checkinDate + timedelta
        

    def extractPages(self, soup):
        pageElement = soup.find('div', attrs={'class':'pageSys'})
        pageElement.div.decompose()
        pageElement.span.decompose()
        pageElement.a.decompose()
        text = pageElement.getText()
        match = re.findall('\d+', text)
        return (int(match[0]),int(match[1]), int(match[2]))
   
    
    def store(self, chunk):
        pass
    
    
    def extractAndStore(self):
        print self.city, self.checkinDate, self.checkoutDate
        url = 'http://www.jinjianginns.com/resv/resv1----'+self.city+'------'+str(self.checkinDate)+'---'+str(self.checkoutDate)+'----00-----1.html'
        opener = urllib2.build_opener();
        chunk = opener.open(url).read()
        self.store(chunk)
        soup = BeautifulSoup(chunk)
        (totals, size, pages) = self.extractPages(soup)
        print totals, size, pages

