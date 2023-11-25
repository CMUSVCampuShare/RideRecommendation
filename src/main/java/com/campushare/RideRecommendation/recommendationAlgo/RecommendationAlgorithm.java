package com.campushare.RideRecommendation.recommendationAlgo;


import com.campushare.RideRecommendation.model.Schedule;
import com.campushare.RideRecommendation.model.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RecommendationAlgorithm  {

    private final int populationSize = 100;
    private final double mutationRate = 0.05;
    private final int maxGenerations = 100;
    private Random random = new Random();

    public List<String> generateRecommendations(String currentUserId, Schedule currentUserSchedule, String currentUserZipcode, List<User> allUsers) {
        List<List<User>> population = initializePopulation(allUsers, populationSize);

        for (int generation = 0; generation < maxGenerations; generation++) {
            List<List<User>> newPopulation = new ArrayList<>();

            for (int i = 0; i < populationSize; i++) {
                List<User> parent1 = selectParent(population, currentUserId, currentUserSchedule, currentUserZipcode);
                List<User> parent2 = selectParent(population, currentUserId, currentUserSchedule, currentUserZipcode);
                List<User> offspring = crossover(parent1, parent2);
                mutate(offspring, allUsers);
                newPopulation.add(offspring);
            }

            population = newPopulation;
        }

        List<User> bestSolution = selectBest(population, currentUserId, currentUserSchedule, currentUserZipcode);
        List<String> userIds = convertToUserIds(bestSolution);

        return userIds;
    }


    private List<List<User>> initializePopulation(List<User> allUsers, int populationSize) {
        Set<List<User>> population = new HashSet<>(); // Use a Set to ensure uniqueness
        while (population.size() < populationSize) {
            Collections.shuffle(allUsers);
            population.add(new ArrayList<>(allUsers.subList(0, Math.min(10, allUsers.size()))));
        }
        return new ArrayList<>(population);
    }


    private List<User> selectParent(List<List<User>> population, String currentUserId, Schedule currentUserSchedule, String currentUserZipcode) {
        double totalFitness = population.stream()
                .mapToDouble(individual -> calculateFitness(individual, currentUserId, currentUserSchedule, currentUserZipcode))
                .sum();
        double slice = random.nextDouble() * totalFitness;
        double total = 0;
        for (List<User> individual : population) {
            total += calculateFitness(individual, currentUserId, currentUserSchedule, currentUserZipcode);
            if (total >= slice) {
                return individual;
            }
        }
        return population.get(random.nextInt(population.size()));
    }


    private List<User> crossover(List<User> parent1, List<User> parent2) {
        List<User> offspring = new ArrayList<>();
        for (int i = 0; i < parent1.size(); i++) {
            offspring.add(random.nextBoolean() ? parent1.get(i) : parent2.get(i));
        }
        return offspring;
    }


    private void mutate(List<User> individual, List<User> allUsers) {
        for (int i = 0; i < individual.size(); i++) {
            if (random.nextDouble() < mutationRate) {
                individual.set(i, allUsers.get(random.nextInt(allUsers.size())));
            }
        }
    }


    private List<User> selectBest(List<List<User>> population, String currentUserId, Schedule currentUserSchedule, String currentUserZipcode) {
        return Collections.max(population, Comparator.comparingDouble(individual -> calculateFitness(individual, currentUserId, currentUserSchedule, currentUserZipcode)));
    }


    private double calculateFitness(List<User> recommendation, String currentUserId, Schedule currentUserSchedule, String currentUserZipcode) {
        double score = 0.0;
        double zipcodeWeight = 1;
        double scheduleWeight = 3;

        for (User user : recommendation) {
            if (user.getId().equals(currentUserId)) {
                continue;
            }

            if (user.getZipcode().equals(currentUserZipcode)) {
                score += zipcodeWeight;
            }

            if (schedulesMatch(user.getSchedule(), currentUserSchedule)) {
                score += scheduleWeight;
            }
        }

        return score/2;
    }


    private List<String> convertToUserIds(List<User> users) {
        List<String> userIds = new ArrayList<>();
        for (User user : users) {
            userIds.add(user.getId());
        }
        return userIds;
    }


    private boolean schedulesMatch(Schedule schedule1, Schedule schedule2) {
        if (schedule1 == null || schedule2 == null) {
            return false;
        }

        return schedule1.getEntryTime().equals(schedule2.getEntryTime()) ||
                schedule1.getExitTime().equals(schedule2.getExitTime());
    }

//    public List<String> generateRecommendations(String currentUserId, Schedule currentUserSchedule, String currentUserZipcode, List<User> allUsers) {
//        List<String> recommendedUserIds = new ArrayList<>();
//        for (User user : allUsers) {
//            if (!user.getId().equals(currentUserId) &&
//                    user.getZipcode().equals(currentUserZipcode) ||
//                    schedulesMatch(user.getSchedule(), currentUserSchedule)) {
//                recommendedUserIds.add(user.getId());
//            }
//        }
//        return recommendedUserIds;
//    }

}
