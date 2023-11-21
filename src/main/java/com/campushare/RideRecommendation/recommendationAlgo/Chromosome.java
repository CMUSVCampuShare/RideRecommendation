//package com.campushare.RideRecommendation.geneticAlgorithm;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class Chromosome {
//    private List<String> userIds;
//    private double fitness = -1;
//
//    public Chromosome(int chromosomeLength) {
//        this.userIds = new ArrayList<>();
//        for (int i = 0; i < chromosomeLength; i++) {
//            this.userIds.add(generateRandomUserId());
//        }
//    }
//
//    public double calculateFitness() {
//        // Placeholder for fitness calculation
//        // Replace with actual logic based on user details
//        this.fitness = new Random().nextDouble(); // Assign random fitness
//        return this.fitness;
//    }
//
//    private String generateRandomUserId() {
//        // Placeholder for generating a random user ID
//        // Replace with actual logic
//        return "User" + new Random().nextInt(100); // Random user ID
//    }
//
//    public List<String> getUserIds() {
//        return this.userIds;
//    }
//
//    public void setUserIds(List<String> userIds) {
//        this.userIds = userIds;
//    }
//
//    public String getGene(int index) {
//        return this.userIds.get(index);
//    }
//
//    public void setGene(int index, String gene) {
//        this.userIds.set(index, gene);
//    }
//
//    public int getChromosomeLength() {
//        return this.userIds.size();
//    }
//}
