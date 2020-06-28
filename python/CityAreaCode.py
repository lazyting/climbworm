# encoding=utf8
import requests
from lxml import etree


class cityAreaCode():
    def __init__(self):
        self.url = "http://www.ip33.com/area/2019.html"

    def get(self):
        page = requests.get(self.url)
        page.encoding = 'utf-8'
        _element = etree.HTML(page.text)
        divs = _element.xpath('//div[@class="ip"]')[1:]
        for div in divs:
            divtext = etree.tostring(div, encoding="utf-8", pretty_print=False).decode("utf-8")
            _element1 = etree.HTML(divtext)
            h4s = _element1.xpath('//div/h4')
            lis = _element1.xpath('//div/ul/li')
            for li in lis:
                litext = etree.tostring(li, encoding="utf-8", pretty_print=False).decode("utf-8")
                _element2 = etree.HTML(litext)
                h5s = _element2.xpath('//li/h5')
                lilis = _element2.xpath('//li/ul/li')
                for lili in lilis:
                    print('（省名称）',h4s[0].text.replace(' ','（省代码）'), '（市/区名称）', h5s[0].text.replace(' ','（市/区代码）'), "（地区名称）", lili.text.replace(' ','（地区代码）'))


if __name__ == '__main__':
    cityAreaCode = cityAreaCode()
    cityAreaCode.get()
