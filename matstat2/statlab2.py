import random
import math
import matplotlib.pyplot as plt
from scipy import interpolate

def getUnif(a:int, b:int):
	return (b-a) * random.random() + a

def getP(j: int, size: int, lis: list):
	temp = sorted(lis)
	k = 0
	sumk = 0
	r_values = list()
	while j != size:
		if(temp[k] == j):
			k = k + 1
			sumk = sumk + 1
		else:
			j = j + 1
			r_values.append(sumk/10000)
			#print(sumk)
			sumk = 0
			continue
	return r_values

def MandD(lis: list):
	temp = list()
	for i in lis:
	  temp.append(i**2)
	M = sum(lis)/10000
	print("M = " + (str) (M))
	D = sum(temp)/10000 - M**2
	print("D = " + (str) (D))


def getPlotP(r_values: list, size: int):
	hr_values = [0, 0]
	hv_values = [0]
	sumkk = 0
	for i in range(size):
		sumkk = sumkk + r_values[i]
		hr_values.append(sumkk)
		hr_values.append(sumkk)
		hv_values.append(i + 1)
		hv_values.append(i + 1)
	hv_values.append(size)
	return [hr_values, hv_values]

def IRNBIN(prob: float, N: int):
	#print(0.5**10)
	p0 = (1 - prob)**N
	p = list()
	p.append(p0)
	for i in range(N+1):
		p0 = p0 * (((N-i)/(i+1))*(prob/(1-prob)))
		p.append(p0)
	#print(p)
	#print(sum(p))
	m = 0
	M = random.random()
	while not M < 0:
		M = M - p[m]
		m += 1
	return m

def IRNBNL(prob: float, N: int):
	xi = 0
	for i in range(N):
		alpha = random.random()
		if alpha <= prob:
			xi +=1
	return xi

def getGeomP(prob: float):
	alpha = random.random()
	p = prob
	i = 1
	while (alpha - p > 0):
		alpha = alpha - p
		p = p * (1-prob)
		i = i + 1
	return i

def getGeomP2(prob: float):
	alpha = random.random()
	i = 1
	while(alpha > prob):
		alpha = random.random()
		i = i + 1
	return i

def getGeomP3(prob: float):
	alpha = random.random()
	return int(math.log(alpha)/math.log(1-prob)) + 1

def getPiosson(mu: int):
	alpha = random.random()
	p = math.exp(-mu)
	i = 1
	while (alpha - p > 0):
		alpha = alpha - p
		p = p * mu/i
		i = i + 1
	return i - 1

def getPiosson2(mu: int):
	alpha = random.random()
	p = alpha
	i = 1
	while (p >= math.exp(-mu)):
		alpha = random.random()
		p = p * alpha
		i = i + 1
	return i - 1

def signCriterion(first: list, second: list, size: int):
	signs = list()
	for i in range(size):
		if (not (first[i] == second[i])):
			print((str)(i + 1) + ' = ' + (str) (first[i]) + '|' + (str)(i + 1) + ' = ' + (str) (second[i]))
			temp = first[i] - second[i]
			if(temp < 0):
				signs.append('-')
			else:
				signs.append('+')
	signStr = ''
	for i in range (len(signs)):
		signStr = signStr + signs[i]
	print(signStr)
	print(sum(1 if x == '-' else 0 for x in signs))
	print(sum(1 if x == '+' else 0 for x in signs))
	if (min(sum(1 if x == '-' else 0 for x in signs), sum(1 if x == '+' else 0 for x in signs)) > 18):
		print('norm')
	else:
		print('nenorm')

#[0,1,2,3,4,5,6,7,8,9]
piossonList = list()
piossonList2 = list()
for i in range(10000):
	piossonList.append(getPiosson(10))
	piossonList2.append(getPiosson2(10))
MandD(piossonList)
MandD(piossonList2)

lust = list()
lest = list()
firstList = list()
secondList = list()
unifList = list()

for i in range(50):
	firstList.append(IRNBIN(0.25, 20))
	secondList.append(getPiosson(4))

signCriterion(firstList, secondList, 50)
for i in range(10000):
	unifList.append((int)(getUnif(1,100)))
MandD(unifList)
unifPlotX = list()
unifPlot = getP(1,99,unifList)
for i in range(len(unifPlot)):
	unifPlotX.append(i)
plt.bar(unifPlotX, unifPlot)
plt.show()
plt.plot(unifPlotX,unifPlot)
ax = plt.gca()
ax.set_xlim([0, 100])
ax.set_ylim([0, 0.1])
plt.show()
plt.plot(getPlotP(unifPlot, len(unifPlot))[1], getPlotP(unifPlot, len(unifPlot))[0])
plt.show()
for i in range(10000):
	lust.append(IRNBIN(0.5, 10))
for i in range(10000):
	lest.append(IRNBNL(0.5, 10))
MandD(lust)
MandD(lest)
r_values = getP(1, 11, lust)
print(r_values)
sr_values = getP(0, 10, lest)
plt.bar([1,2,3,4,5,6,7,8,9,10], r_values)
plt.show()
poly = interpolate.KroghInterpolator([1,2,3,4,5,6,7,8,9,10], r_values)
Y = poly([1,2,3,4,5,6,7,8,9,10])
plt.plot([1,2,3,4,5,6,7,8,9,10],Y)
ax = plt.gca()
ax.set_xlim([0, 10])
ax.set_ylim([0, 0.5])
plt.show()
plt.bar([1,2,3,4,5,6,7,8,9,10], sr_values)
plt.show()
poly = interpolate.KroghInterpolator([0,1,2,3,4,5,6,7,8,9], sr_values)
Y = poly([0,1,2,3,4,5,6,7,8,9])
plt.plot([0,1,2,3,4,5,6,7,8,9],Y)
ax = plt.gca()
ax.set_xlim([0, 10])
ax.set_ylim([0, 0.5])
plt.show()
plt.plot(getPlotP(r_values, 10)[1], getPlotP(r_values, 10)[0])
plt.show()
plt.plot(getPlotP(sr_values, 10)[1], getPlotP(sr_values, 10)[0])
plt.show()
##geom
geomList = list()
geomList2 = list()
geomList3 = list()
geomListx = list()
for i in range(10000):
	geomList.append(getGeomP(0.5))
	geomList2.append(getGeomP2(0.5))
	geomList3.append(getGeomP3(0.5))
MandD(geomList)
MandD(geomList2)
MandD(geomList3)
geomListy = getP(1, max(geomList), geomList)
for i in range(max(geomList) - 1):
	geomListx.append(i + 1)
plt.bar(geomListx, geomListy)
plt.show()
poly = interpolate.KroghInterpolator(geomListx, geomListy)
Y = poly(geomListx)
plt.plot(geomListx,Y)
ax = plt.gca()
ax.set_xlim([0, 10])
ax.set_ylim([0, 0.7])
plt.show()
plt.plot(getPlotP(geomListy, max(geomListx))[1], getPlotP(geomListy, max(geomListx))[0])
plt.show()
geomListy = getP(1, max(geomList2), geomList2)
geomListx.clear()
for i in range(max(geomList2) - 1):
	geomListx.append(i + 1)
plt.bar(geomListx, geomListy)
plt.show()
poly = interpolate.KroghInterpolator(geomListx, geomListy)
Y = poly(geomListx)
plt.plot(geomListx,Y)
ax = plt.gca()
ax.set_xlim([0, 10])
ax.set_ylim([0, 0.7])
plt.show()
plt.plot(getPlotP(geomListy, max(geomListx))[1], getPlotP(geomListy, max(geomListx))[0])
plt.show()
geomListy = getP(1, max(geomList3), geomList3)
geomListx.clear()
for i in range(max(geomList3) - 1):
	geomListx.append(i + 1)
plt.bar(geomListx, geomListy)
plt.show()
poly = interpolate.KroghInterpolator(geomListx, geomListy)
Y = poly(geomListx)
plt.plot(geomListx,Y)
ax = plt.gca()
ax.set_xlim([0, 10])
ax.set_ylim([0, 0.7])
plt.show()
plt.plot(getPlotP(geomListy, max(geomListx))[1], getPlotP(geomListy, max(geomListx))[0])
plt.show()
geomListy = getP(1, max(piossonList), piossonList)
geomListx.clear()
for i in range(max(piossonList) - 1):
	geomListx.append(i + 1)
plt.bar(geomListx, geomListy)
plt.show()
poly = interpolate.KroghInterpolator(geomListx, geomListy)
Y = poly(geomListx)
plt.plot(geomListx,Y)
ax = plt.gca()
ax.set_xlim([0, 25])
ax.set_ylim([0, 0.2])
plt.show()
plt.plot(getPlotP(geomListy, max(geomListx))[1], getPlotP(geomListy, max(geomListx))[0])
plt.show()
geomListy = getP(1, max(piossonList2), piossonList2)
geomListx.clear()
for i in range(max(piossonList2) - 1):
	geomListx.append(i + 1)
plt.bar(geomListx, geomListy)
plt.show()
poly = interpolate.KroghInterpolator(geomListx, geomListy)
Y = poly(geomListx)
plt.plot(geomListx,Y)
ax = plt.gca()
ax.set_xlim([0, 25])
ax.set_ylim([0, 0.2])
plt.show()
plt.plot(getPlotP(geomListy, max(geomListx))[1], getPlotP(geomListy, max(geomListx))[0])
plt.show()

