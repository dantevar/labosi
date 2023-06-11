import math
import random
from typing import List
from neuralNet import neuralNet
import numpy as np


def selectParents(population:List[neuralNet]):

    parents = [None, None]
    sum_fit = sum(jedinka.fit for jedinka in population)
    for i in range(2):
        random_val = random.random()
        total_len=0
        for jedinka in population:
            total_len+= jedinka.fit/sum_fit
            if total_len >= random_val :
                parents[i]=jedinka
                break

        if i == 1 and parents[1] == parents[0]:
            i -= 1

    return parents[0],parents[1]


def cross_and_mutate(parent1:neuralNet, parent2:neuralNet, p:float, k:float):
    new_matrices = []
    new_biases = []
    for i in range(len(parent1.matrices)):

        new_layer_matrix = (np.add(parent1.matrices[i], parent2.matrices[i]) / 2) + random.gauss(0,k) if random.random() <= p\
        else (np.add(parent1.matrices[i], parent2.matrices[i]) / 2)

        new_layer_bias= (np.add(parent1.biases[i], parent2.biases[i]) / 2) + random.gauss(0,k) if random.random() <= p \
        else (np.add(parent1.biases[i], parent2.biases[i]) / 2)

        new_matrices.append(new_layer_matrix)
        new_biases.append(new_layer_bias)
    return neuralNet(new_matrices, new_biases)

def run(population:List[neuralNet], dataset:[],popsize:int, elitism:int, iter:int, p:float, k:float):

    #prva iteracija
    for jedinka in population:
        jedinka.calculate_error(dataset)
    population.sort(key=lambda jedinka:jedinka.fit, reverse=True)

    for i in range(2,iter+1):
        if(i % 2000 == 0):
            print("[Train error @{}]: {}".format(i, population[0].err))

        new_population = population[:elitism]

        while(len(new_population) < popsize):
            parent1,parent2 = selectParents(population)
            child = cross_and_mutate(parent1, parent2, p, k)
            child.calculate_error(dataset)
            new_population.append(child)

        population = sorted(new_population, key=lambda jedinka:jedinka.fit, reverse=True)

    return population[0]