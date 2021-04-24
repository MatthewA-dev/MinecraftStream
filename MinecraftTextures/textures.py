import os
import shutil


f = open("MinecraftTextures/textures.txt","r")
content = f.read()
f.close()
content = content.split(", ")
content = [ITEM for ITEM in content if "LEGACY" not in ITEM]
new = [ITEM.replace("_","").lower() for ITEM in content]
ids = dict(zip(new,content))



os.chdir("./MinecraftTextures/block")
textures = os.listdir()
textures = [ITEM for ITEM in textures if ".json" not in ITEM]
texturesnew = [ITEM[:-4].replace("_","") for ITEM in textures]
fileids = dict(zip(texturesnew,textures))



for texture in texturesnew:
    if(texture[:-4] == "side"):
        texture = texture[:-4]
    if(texture in new):
        shutil.copy(f"./{fileids[texture]}",f"../TEXTURES/{ids[texture]}.png")