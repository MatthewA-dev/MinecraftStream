import random
from scipy import cluster

pts = []

rand = random.Random()
for x in range(rand.randint(50,100)):
    pts.append(float(rand.randint(0, 400) * rand.randint(1,x+2)))
pts.sort()
print(pts)
print(cluster.vq.kmeans2(pts, 1)[0][0])