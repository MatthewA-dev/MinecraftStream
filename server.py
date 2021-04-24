from PIL import Image
from PIL import ImageGrab
import threading
from json import load
from time import time
import requests
from bisect import bisect_left

url = "http://localhost:5000"
x = 160
y = 90

def conv256to10(num : tuple):
    num = list(num)[::-1]
    number = 0
    for i, x in enumerate(num):
        number += 256 ** i * x
    return number

js = load(fp = open("txts.json"))
js = sorted(js, key = lambda i: i['num'])
jskeys = [x["num"] for x in js]

class sender (threading.Thread):
    def __init__(self, x,y):
        threading.Thread.__init__(self)
        self.img = ImageGrab.grab().resize((x,y))
    def run(self):
        data = []
        headers = {
            "Content-Type" : "application/octet-stream",
            "Size": f"{self.img.width}:{self.img.height}"
        }
        for y in range(self.img.height):
            for x in range(self.img.width):
                # YES ITS A MESS, NO I DONT CARE
                # essentially we need to get the pixel at each thing of the image, then 
                # we convert it to a number, as every color can be represented as a base 256 number
                # then we get the index of the smallest material
                data.append(self.smallestMat(conv256to10(self.img.getpixel((x,y)))))
        requests.post(url=url,data=",".join(data), headers=headers)
    def smallestMat(self, num):
        pos = bisect_left(jskeys, num)
        return str(pos)
while True:
    s = sender(x = x,y = x)
    s.run()