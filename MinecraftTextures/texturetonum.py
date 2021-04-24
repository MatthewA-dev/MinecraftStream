import os
from PIL import Image
from json import JSONEncoder
from scipy.cluster import vq

def conv256to10(n):    
    n.reverse()
    number = 0
    for i, num in enumerate(n):
        number += 256**i * num
    return number

os.chdir("MinecraftTextures/TEXTURES")
fs = os.listdir()
dic = []
for f in fs:
    image = Image.open(f)
    pixels = []
    if("CORAL" in f):
        continue
    try:
        if(len(list(image.getpixel((0,0)))) != 4):
            continue
    except(TypeError):
        continue
    for x in range(image.width):
        for y in range(image.height):
            g = list(image.getpixel((x,y)))[:-1]
            pixels.append(float(conv256to10(g)))
    d = {}
    d["id"] = f[:-4]
    d["num"] = vq.kmeans2(pixels, 10)[0][0]
    dic.append(d)

js = JSONEncoder()
f = open("../txts.json","w")
f.write(js.encode(dic))
f.flush()
f.close()