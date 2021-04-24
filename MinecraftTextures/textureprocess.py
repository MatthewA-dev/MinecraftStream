from PIL import Image
import os

os.chdir("MinecraftTextures/TEXTURES")

files = os.listdir()
for f in files:
    image = Image.open(f)
    if(image.height != image.width):
        image = image.crop((0,0,image.width,image.width))
    image.save(f)