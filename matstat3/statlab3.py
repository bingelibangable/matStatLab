import random
import math
import matplotlib.pyplot as plt
from scipy import interpolate
import numpy as np

def getUnif(a:int, b:int):
    alpha = random.random()
    return (b-a) * alpha + a

def MandD(lis: list):
	temp = list()
	for i in lis:
	  temp.append(i**2)
	M = sum(lis)/10000
	print("M = " + (str) (M))
	D = sum(temp)/10000 - M**2
	print("D = " + (str) (D))

def getExp(beta: float):
	return -beta * math.log(random.random())

def getP(a:int, b:int, source:list, size:int):
	temp = sorted(source)
	k = 0
	sumk = 0
	j = a + 1
	r_values = list()
	while j != b:
		if(temp[k] <= j):
			k = k + 1
			sumk = sumk + 1
		else:
			j = j + 1
			r_values.append(sumk/size)
			sumk = 0
			#continue
	for i in range(size - k):
		sumk = sumk + 1
	r_values.append(sumk/size)
	return r_values

def getPlotP(source:list, size:int, length:int):
	listY = list()
	listX = list()
	temp = sorted(source)
	for i in range(size):
		listY.append(i / size)
		listX.append(temp[i])
	return [listX, listY]

def getXi(N:int):
	result = 0
	for i in range(N):
		result = result + getStand2()**2
	return result

def getStand1():
	sum = 0
	for i in range(12):
		alpha = random.random()
		sum = sum + alpha
	return sum - 6

def getStand2():
	alpha = random.random()
	beta = random.random()
	return math.sqrt(-2*math.log(beta)) * math.cos(2*math.pi*(alpha))

def getStud(N:int):
	return getStand2() / (math.sqrt(getXi(N)/N))

unifList = list()
for i in range(10000):
	unifList.append(getUnif(0,10))

task = list()
for i in range(20):
	task.append(np.random.normal(3,2))
	print(task[i])
m = sum(task)/20
tempTask = list()
for i in range(20):
	tempTask.append((task[i] - m)**2)
gDisp = sum(tempTask)/20
disp = math.sqrt(20/19 * gDisp)
print(disp)
interval = 1.7291*disp/math.sqrt(20)
print("interval: %.2f" % (m - interval) + " < m < %.2f" % (m + interval))
MandD(unifList)
plt.bar(['0-1','1-2','2-3','3-4','4-5','5-6','6-7','7-8','8-9','9-10'], getP(0,10,unifList,10000))
plt.show()
poly = interpolate.KroghInterpolator([0,1,2,3,4,5,6,7,8,9], getP(0,10,unifList,10000))
Y = poly([0,1,2,3,4,5,6,7,8,9])
plt.plot([0,1,2,3,4,5,6,7,8,9],Y)
ax = plt.gca()
ax.set_xlim([0, 10])
ax.set_ylim([0, 0.2])
plt.show()
X, Y = getPlotP(unifList, 10000, 10)
plt.plot(X, Y)
plt.show()

standList = list()
standList1 = list()
for i in range(10000):
	standList.append(getStand1())
	standList1.append(getStand2())
MandD(standList)
MandD(standList1)
temp = getP(((int)(min(standList1))), ((int)(max(standList1))), standList1, 10000)
X.clear()
for i in range(len(temp)):
	X.append((str)((int)(min(standList1)) + i) + ' - ' + ((str)((int)(min(standList1)) + i + 1)))
plt.bar(X, temp)
plt.show()
X.clear()
X.append(len(temp)/2*-1)
for i in range(len(temp) - 1):
	X.append(temp[0] + i)
poly = interpolate.KroghInterpolator(X, temp)
Y = poly(X)
plt.plot(X,Y)
ax = plt.gca()
ax.set_xlim([X[0], X[len(X) - 1]])
ax.set_ylim([0, 0.7])
plt.show()
X.clear()
X, Y = getPlotP(standList1, 10000, 8)
plt.plot(X, Y)
plt.show()
temp = getP(((int)(min(standList))), ((int)(max(standList))), standList, 10000)
X.clear()
for i in range(len(temp)):
	X.append((str)((int)(min(standList)) + i) + ' - ' + ((str)((int)(min(standList)) + i + 1)))
plt.bar(X, temp)
plt.show()
X.clear()
X.append(len(temp)/2*-1)
for i in range(len(temp) - 1):
	X.append(temp[0] + i)
poly = interpolate.KroghInterpolator(X, temp)
Y = poly(X)
plt.plot(X,Y)
ax = plt.gca()
ax.set_xlim([X[0], X[len(X) - 1]])
ax.set_ylim([0, 0.7])
plt.show()
X.clear()
X, Y = getPlotP(standList, 10000, 8)
plt.plot(X, Y)
plt.show()
expList = list()
print('expList')
for i in range(10000):
	expList.append(getExp(1))
MandD(expList)
temp = getP(0,(int)(max(expList)),expList,10000)
X.clear()
for i in range(len(temp)):
	X.append((str)((int)(min(expList)) + i) + ' - ' + ((str)((int)(min(expList)) + i + 1)))
plt.bar(X, temp)
plt.show()
X.clear()
for i in range((int)(max(expList))):
	X.append(i)
poly = interpolate.KroghInterpolator(X, temp)
Y = poly(X)
plt.plot(X,Y)
ax = plt.gca()
ax.set_xlim([X[0], X[len(X) - 1]])
ax.set_ylim([0, 0.7])
plt.show()
X, Y = getPlotP(expList, 10000, (int)(max(expList)))
plt.plot(X,Y)
plt.show()
listXi = list()
for i in range(10000):
	listXi.append(getXi(10))
MandD(listXi)
supressedMax = (int)(max(listXi))
temp = getP(0,supressedMax,listXi,10000)
X.clear()

for i in range(supressedMax):
	X.append((str)(i) + ' - ' + (str)(i + 1))
plt.bar(X, temp)
plt.show()
X.clear()
for i in range(supressedMax):
	X.append(i)
poly = interpolate.KroghInterpolator(X, temp)
Y = poly(X)
plt.plot(X,Y)
ax = plt.gca()
ax.set_xlim([X[0], X[len(X) - 1]])
ax.set_ylim([0, 0.13])
plt.show()
X, Y = getPlotP(listXi, 10000, (int)(max(listXi)))
plt.plot(X,Y)
plt.show()
studList = list()
for i in range(10000):
	studList.append(getStud(10))
MandD(studList)
temp = getP((int)(min(studList)),(int)(max(studList)),studList,10000)
X.clear()
for i in range(len(temp)):
	X.append((str)((int)(min(studList) + i)) + ' - ' + (str)((int)(min(studList) + i + 1)))
plt.bar(X, temp)
plt.show()
X.clear()
for i in range(len(temp)):
	X.append(i)
poly = interpolate.KroghInterpolator(X, temp)
Y = poly(X)
plt.plot(X,Y)
ax = plt.gca()
ax.set_xlim([X[0], X[len(X) - 1]])
ax.set_ylim([0, 0.5])
plt.show()
X, Y = getPlotP(studList, 10000, (int)(max(studList)))
plt.plot(X,Y)
plt.show()

#X, Y = getPlotP(standList1, 10000, (int)(max(standList1)))
#plt.plot(X, Y)
#plt.show()

