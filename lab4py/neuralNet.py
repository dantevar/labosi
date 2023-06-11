import math
import random
import numpy as np

mean = 0
std_dev = 0.01

class neuralNet:

    # input -hiddens - output
    def __init__(self, matrices,biases):
        self.matrices = matrices
        self.biases = biases

    def new(layers_dimension:[]):
        matrices = []
        biases = []
        for i in range (1 ,len(layers_dimension)) :
            previous_dimension = layers_dimension[i-1]
            current_dimension = layers_dimension[i]

            layer_weight = np.array( [[random.gauss(mean, std_dev) for _ in range(previous_dimension)] for _ in range(current_dimension)] )
            layer_bias = np.array( [random.gauss(mean, std_dev) for _ in range(current_dimension)] )

            matrices.append(layer_weight)
            biases.append(layer_bias)
        return neuralNet(matrices, biases)

    def evaluate(self, input:[]):

        layer_input = np.array( input )
        layer_output = np.array( [] )
        n = len(self.matrices)
        for i in range(n):

            layer_matrix:[] = self.matrices[i]
            layer_bias:[] = self.biases[i]

            mul_output = np.matmul(layer_matrix, layer_input)

            layer_output = np.add(mul_output, layer_bias)

            if i != n-1:
                layer_output = 1 / (1 + np.exp(-layer_output))
                layer_input = layer_output

        return layer_output[0]

    def calculate_error(self, dataset:[]):
        err = 0
        n = len(dataset)
        for entry in dataset:
            float_entry = [float(x) for x in entry]
            real_y = float_entry[-1]
            eval_y = self.evaluate(float_entry[:-1])
            err += pow(real_y-eval_y, 2)
        err = err/n
        fit = 1/err

        self.err = err
        self.fit = fit
        return self.err
