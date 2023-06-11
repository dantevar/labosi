import csv
import genAlg
from neuralNet import neuralNet
import sys

def readFile(path):
    with open(path, 'r') as file:
        reader = csv.reader(file)
        dataset = list(reader)
        dataset.pop(0)
        return dataset

trainPath = "sine_train.txt"
testPath = "sine_test.txt"
dimensions = [1,5,1]
elitism = 1
popsize = 10
p = 0.1
k = 0.1
iter = 2000
nn = ""

args = sys.argv
for i in range (len(args)):
    if args[i] == "--train" : trainPath = args[i+1]
    if args[i] == "--test": testPath = args[i+1]
    if args[i] == "--nn" : nn = args[i+1]
    if args[i] == "--elitism": elitism = int(args[i+1])
    if args[i] == "--popsize": popsize = int(args[i+1])
    if args[i] == "--p" : p = float(args[i+1])
    if args[i] == "--K": k = float(args[i+1])
    if args[i] == "--iter": iter = int(args[i+1])

train_dataset = readFile(trainPath)
input_dimension = len(train_dataset[0])-1

if nn == "5s": dimensions = [input_dimension,5,1]
if nn == "5s5s": dimensions = [input_dimension,5,5,1]
if nn == "20s": dimensions = [input_dimension,20,1]

population = [neuralNet.new(dimensions) for _ in range(popsize)]

best_jedinka = genAlg.run(population,train_dataset, popsize, elitism, iter, p, k)

test_dataset = readFile(testPath)
err = best_jedinka.calculate_error(test_dataset)

print("[Test error]: "+str(err))