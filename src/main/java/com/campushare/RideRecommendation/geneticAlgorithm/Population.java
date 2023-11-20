//package com.campushare.RideRecommendation.geneticAlgorithm;
//
//public class Population {
//    private Chromosome[] chromosomes;
//    private double populationFitness = -1;
//
//    public Population(int populationSize, int chromosomeLength) {
//        this.chromosomes = new Chromosome[populationSize];
//        for (int i = 0; i < populationSize; i++) {
//            this.chromosomes[i] = new Chromosome(chromosomeLength);
//        }
//    }
//
//    public Chromosome getFittest(int offset) {
//        java.util.Arrays.sort(this.chromosomes, (chromosome1, chromosome2) -> {
//            if (chromosome1.calculateFitness() > chromosome2.calculateFitness()) {
//                return -1;
//            } else if (chromosome1.calculateFitness() < chromosome2.calculateFitness()) {
//                return 1;
//            }
//            return 0;
//        });
//
//        return this.chromosomes[offset];
//    }
//
//    public Chromosome[] getChromosomes() {
//        return this.chromosomes;
//    }
//
//    public void setChromosomes(Chromosome[] chromosomes) {
//        this.chromosomes = chromosomes;
//    }
//
//    public Chromosome getChromosome(int index) {
//        return this.chromosomes[index];
//    }
//
//    public void setChromosome(int index, Chromosome chromosome) {
//        this.chromosomes[index] = chromosome;
//    }
//
//    public int size() {
//        return this.chromosomes.length;
//    }
//
//    public void setPopulationFitness(double fitness) {
//        this.populationFitness = fitness;
//    }
//
//    public double getPopulationFitness() {
//        return this.populationFitness;
//    }
//}
