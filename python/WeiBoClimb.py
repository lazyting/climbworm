# coding=utf-8

from lxml import etree
import requests
from selenium import webdriver
import time
from selenium.webdriver.common.keys import Keys  # 输入框回车
from selenium.webdriver.common.by import By  # 与下面的2个都是等待时要用到
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import NoSuchElementException, TimeoutException  # 异常处理


class climb():
    def __init__(self):
        self.url = 'https://weibo.com/1642592432/J7RlsgaVK?ref=feedsdk&type=comment#_rnd1592900367769'
        chrome_driver = 'F:\chromedriver.exe'  # chromedriver的文件位置
        self.driver = webdriver.Chrome(executable_path=chrome_driver)
        # self.wait = WebDriverWait(self.driver, 10)
        # self.driver.add_cookie(cookies)
        self.headers = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36'
        self.driver.get(self.url)
        time.sleep(10)  # 刚进入页面会有一层跳转，等它跳完再处理 sina vistor system
        # print(self.driver.page_source)
        self.commentlen = int(
            str(self.driver.find_elements(By.XPATH, "//span[@node-type='comment_btn_text']/span/em")[1].text))
        self.turnlen = int(
            str(self.driver.find_elements(By.XPATH, "//span[@node-type='forward_btn_text']/span/em")[1].text))
        print(self.commentlen)
        print(self.turnlen)

    '''
    获取所有的评论
    '''
    def getallcomment(self):
        listboxlen = self.getcommentlistsize(True)
        print(listboxlen)
        while listboxlen < self.commentlen:
            self.scroll()
            self.driver.find_element(By.XPATH, '//a[@action-type="click_more_comment"]').click()
            listboxlen = self.getcommentlistsize(False)
        return self.driver.find_elements(By.XPATH, '//div[@class="list_li S_line1 clearfix"]')

    def getcommentlistsize(self, sleepflag: bool):
        if sleepflag:
            time.sleep(5)
        self.driver.get(self.url)
        listboxlen = len(self.driver.find_elements(By.XPATH, '//div[@class="list_li S_line1 clearfix"]'))
        return listboxlen

    def getpagesource(self):
        return self.driver.page_source

    def scroll(self):
        for i in range(0,10):
            self.driver.execute_script("var a = window.innerHeight;window.scrollBy(0,a);")
            time.sleep(0.5)

        # html = driver.execute_script("return document.documentElement.outerHTML") #通过 driver获取整个页面的代码
        # html = driver.page_source # 获取网页源码
        # js = "var q=document.getElementsByClassName('list_box')[0].scrollTop = 10000"
        # driver.execute_script(js)
        # commons = driver.find_element_by_class_name('list_li S_line1 clearfix')
        # print(commons)
        # listboxs = driver.find_elements(By.XPATH, '//div[@class="list_li S_line1 clearfix"]')
        # print(len(listboxs))
        # # 获取当前窗口的内容可视区域
        # inner_height = driver.execute_script("var a = window.innerHeight;return a;")
        # print("当前窗口的内容可视区域-高度：", inner_height)
        #
        # # 获取当前整个html页面的body高度。
        # body_height = driver.execute_script("var a = document.body.scrollHeight;return a;")
        # print("当前整个html页面的body-高度:", body_height)
        # scrolled_height = 0
        # new_body_height = body_height  # 当前整个html页面的body高度
        # old_body_height = 0
        # break_flag = False
        # while new_body_height != old_body_height:
        #     distance = int((new_body_height - scrolled_height) / (inner_height * 0.5)) + 1
        #     for i in range(distance):
        #         # 滚动距离为 窗口内容可视区域的百分之50.可灵活配置哦！
        #         driver.execute_script("var a = window.innerHeight;window.scrollBy(0,a*0.5);")
        #         # 滚动一次，页面内容会更新一部分。在滚动之后，查找当前页面是否包含了它。如果没有，继续滚动。如果有，退出。
        #         try:
        #             WebDriverWait(driver, 10).until(EC.visibility_of_element_located(lo))
        #         except:
        #             pass
        #         else:
        #             print("找到啦！！！")
        #             driver.find_element(*lo).click()
        #             break_flag = True  # 终止for循环
        #             break
        #     if break_flag is True:  # 终止While循环
        #         break
        #         # time.sleep(3)
        #     # 更新滚动
        #     old_body_height = new_body_height
        #     scrolled_height = new_body_height
        #     new_body_height = driver.execute_script("var a = document.body.scrollHeight;return a;")
        #     print("老 - 当前整个html页面的body-高度:", old_body_height)
        #     print("新 - 当前整个html页面的body-高度:", new_body_height)
        # for single in listboxs:
        #     print(single.text)
        # b.quit()
        # 页面分段加载，鼠标悬浮在某元素上
        # listbox = driver.find_element(By.XPATH, '//div[@node-type="feed_list"]')
        # listbox = driver.find_elements(By.XPATH, '//div[@node-type="comment_list"]')
        # listbox = driver.find_elements(By.XPATH, '//div[@class="list_li S_line1 clearfix"]')
        # while True:
        #     js = "var q=document.documentElement.scrollTop=10000"  # documentElement表示获取body节点元素
        #     driver.execute_script(js)
        #     listboxs = driver.find_elements(By.XPATH, '//div[@class="list_li S_line1 clearfix"]')
        #     for single in listboxs:
        #         print(single.text)
        # ActionChains(driver).move_to_element(listbox).perform()
        # element = WebDriverWait(driver, 10).until(
        #     EC.presence_of_element_located((By.XPATH, "//div[@class='list_li S_line1 clearfix']"))
        # )


if __name__ == '__main__':
    wb = climb()
    wb.getallcomment()
    wb.driver.quit()
