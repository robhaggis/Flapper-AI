package com.haggis.graphics;

public class NN {

	public final int[] NETWORK_LAYER_SIZES;
	public final int INPUT_SIZE, OUTPUT_SIZE;
	public final int NETWORK_SIZE;

	private double[][] output;
	private double[][][] weights;
	private double[][] bias;

	private double[][] error;
	private double[][] output_derivative;

	public NN(int... NETWORK_LAYER_SIZES) {
		this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
		INPUT_SIZE = NETWORK_LAYER_SIZES[0];
		NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
		OUTPUT_SIZE = NETWORK_LAYER_SIZES[NETWORK_SIZE - 1];

		this.output = new double[NETWORK_SIZE][];
		this.weights = new double[NETWORK_SIZE][][];
		this.bias = new double[NETWORK_SIZE][];

		this.error = new double[NETWORK_SIZE][];
		this.output_derivative = new double[NETWORK_SIZE][];

		for (int i = 0; i < NETWORK_SIZE; i++) {
			this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
			this.error[i] = new double[NETWORK_LAYER_SIZES[i]];
			this.output_derivative[i] = new double[NETWORK_LAYER_SIZES[i]];

			this.bias[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], -1, 1);

			if (i > 0) {
				this.weights[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], NETWORK_LAYER_SIZES[i - 1],
						-1, 1);
			}
		}
	}

	public double[] calculate(double... input) {
		if (input.length != this.INPUT_SIZE) {
			return null;
		}

		this.output[0] = input;
		for (int layer = 1; layer < NETWORK_SIZE; layer++) {
			for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {

				double sum = 0;
				for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
					sum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];
				}
				sum += bias[layer][neuron];
				output[layer][neuron] = sigmoid(sum);
				output_derivative[layer][neuron] = output[layer][neuron] * (1 - output[layer][neuron]);

			}
		}

		return output[NETWORK_SIZE - 1];
	}

	public void train(double[] input, double[] target, double eta) {
		if (input.length != INPUT_SIZE || target.length != OUTPUT_SIZE) {
			return;
		}

		calculate(input);
		backPropError(target);
		updateWeights(eta);

	}

	public void backPropError(double[] target) {
		for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[NETWORK_SIZE - 1]; neuron++) {
			error[NETWORK_SIZE
					- 1][neuron] = (output[NETWORK_SIZE - 1][neuron] - target[neuron])
					* output_derivative[NETWORK_SIZE - 1][neuron];
		}
		
		
		for(int layer = NETWORK_SIZE-2; layer > 0; layer--) {
			for(int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {
				double sum = 0;
				for(int nextNeuron = 0; nextNeuron < NETWORK_LAYER_SIZES[layer+1];nextNeuron++) {
					sum += weights[layer+1][nextNeuron][neuron] * error[layer+1][nextNeuron];
				}
				this.error[layer][neuron] = sum * output_derivative[layer][neuron];
			}
		}
	}
	

	public void updateWeights(double eta) {
		for(int layer=1; layer < NETWORK_SIZE; layer++) {
			for(int neuron = 0;neuron <NETWORK_LAYER_SIZES[layer];neuron++) {
				for(int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer-1];prevNeuron++) {
					double delta = -eta * output[layer-1][prevNeuron] * error[layer][neuron];
					weights[layer][neuron][prevNeuron] += delta;
				}
				double delta = -eta *error[layer][neuron]; 
				bias[layer][neuron] += delta;
			}
		}
	}

	private double sigmoid(double x) {
		return 1d / (1 + Math.exp(-x));
	}

}