package com.retailer.reward.service;

import com.retailer.reward.model.Transaction;
import com.retailer.reward.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardService {

    @Autowired
    private TransactionRepository transactionRepository;

    public int calculatePoints(double amount) {
        if (amount <= 50) {
            return 0;
        } else if (amount <= 100) {
            return (int) (amount - 50);
        } else {
            return (int) (50 + (amount - 100) * 2);
        }
    }

    public Map<String, Integer> getRewardsForCustomer(Long customerId) {
        List<Transaction> transactions = transactionRepository.findByCustomer(customerId);

        int totalPoints = 0;
        Map<String, Integer> monthlyPoints = new HashMap<>();

        for (Transaction transaction : transactions) {
            int points = calculatePoints(transaction.getAmount());
            totalPoints += points;

            String month = transaction.getDate().getMonth().toString();
            monthlyPoints.put(month, monthlyPoints.getOrDefault(month, 0) + points);
        }

        monthlyPoints.put("TOTAL", totalPoints);
        return monthlyPoints;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}