import plotly
import plotly.express as px
import re
import random

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

f = open('out.txt', 'w')
for i in range(10000):
    f.write(random.random())

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
