import plotly
import plotly.express as px
import re
import random
import math

def drawCorr(f):
    for n in range (8):
        f.readline()
    string = f.readline().split()
    values = list(map(float, string))
    print(values)
    xval = list()
    for n in range(len(values)):
        xval.append(n + 1)
    print(xval)
    fig = px.bar(values)
    fig.show()

def mExp(n:int, lis:list):
    sum = 0
    for i in range(n):
        sum = sum + lis[n]
    return sum / n

def disp(n:int, mathExp:float, lis:list):
    sum = 0
    for i in range(n):
        sum = math.pow(lis[i] - mathExp, 2)
    return sum / n

def corr(n:int, mathExp:float, lis:list, f:int):
    numSum = 0
    denSum = 0
    for i in range(f):
        numSum = numSum + (lis[i] - mathExp) * (lis[i+f]-mathExp)
    for i in range(n):
        sum = math.pow(lis[i] - mathExp, 2)
    return numSum / denSum

f = open('out.txt', 'w')
i = 0
for i in range(10000):
    data.append(random.random())
i = 10
while (i < 10001):
    f.write("Набор № " + (str)(i))
    mathExp = mExp(i, data);
    f.write("Мат. ожидание = ");
    f.write(mathExp)
    dispers = disp(i, mathExp, data);
    f.write("Дисперсия = ")
    f.write(dispers)
    f.write("среднее квадратическое отклонение = ")
    f.write(math.sqrt(dispers))
    shift = i;
    f.write("Корреляция")
    for k in range(shift):
        f.write(corr(i,mathExp,data,k))
    i = i * 10

f = open('out.txt', 'r')
for i in range(4):
    drawCorr(f)
print(f.readline())
string = f.readline()
xval = list()
xval.append(-2)
while(not (string == '')):
    xval.append(float(string))
    string = f.readline()
xval.append(2)
xval.sort()
yval = list()
yval.append(0)
for n in range(10000):
    yval.append(n/10000)
yval.append(1)
fig = px.line(x=xval, y=yval)
fig.show()
yval.remove(0)
yval.remove(1)
xval.remove(2)
xval.remove(-2)
fig = px.histogram(xval, histnorm='probability density')
fig.show()
f.close()
