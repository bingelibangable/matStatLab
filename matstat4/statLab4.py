#2,576 × 2,576 = 6,635776
#a = 0.995
#e = 0.001
import random
import math
N = 33013
T = 8760
m = 4

def startSim(spareParstList:list):
    timeList = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
    x = list()
    listOfParts = [4,2,6,2]
    lambdaList = [40 * 10**-6, 10 * 10**-6, 80 * 10**-6, 30 * 10**-6]
    invalidations = 0
    for i in range(N):
        shift = 0
        for k in range(m):
            for l in range(listOfParts[k]):
                timeList[shift + l] = -math.log(random.random())/lambdaList[k]
            for l in range(spareParstList[k]):
                index_min = timeList.index(min(timeList[shift:shift + listOfParts[k]]))
                timeList[index_min] = timeList[index_min] - math.log(random.random())/lambdaList[k]
            shift = shift + listOfParts[k]
        if (not isValid(timeList)):
            invalidations = invalidations + 1
    print('invalidations = ' + (str)(invalidations))
    print(1 - invalidations / N)

def isValid(x:list):
    return ((x[0] > T and x[1] > T) or (x[2] > T and x[3] > T)) and (x[4] > T and x[5] > T) and ((x[6] > T and x[7] > T) or (x[8] > T and x[9] > T) or (x[10] > T and x[11] > T)) and (x[12] > T or x[13] > T)

spareParstList = [4,2,8,2]#3241
for f in range(4):
        spareParstList[f] = spareParstList[f] - 1
        print("list of details: " + (str)(spareParstList))
        startSim(spareParstList)
        spareParstList[f] = spareParstList[f] + 1
print("list of details: " + (str)(spareParstList))
startSim(spareParstList)
