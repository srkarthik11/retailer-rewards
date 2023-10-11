package com.retailer.reward.controller;

import com.retailer.reward.model.Customer;
import com.retailer.reward.model.Transaction;
import com.retailer.reward.repository.CustomerRepository;
import com.retailer.reward.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private RewardService service;

    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getAllTransactions() {
        List<Transaction> transactions = service.getAllTransactions();

        List<Map<String, Object>> responseTransactions = transactions.stream()
                .map(this::convertToResponseFormat)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Collections.singletonMap("transactions", responseTransactions));
    }

    private Map<String, Object> convertToResponseFormat(Transaction transaction) {
        Map<String, Object> responseTransaction = new HashMap<>();

        Optional<Customer> customerOpt = customerRepository.findById(transaction.getCustomer());
        String customerName = customerOpt.isPresent() ? customerOpt.get().getName() : "Unknown";

        responseTransaction.put("customer", customerName);
        responseTransaction.put("month", monthNameFromLocalDate(transaction.getDate()));
        responseTransaction.put("amount", transaction.getAmount());
        return responseTransaction;
    }



    private String monthNameFromLocalDate(LocalDate date) {
        return date.getMonth().name().toLowerCase().substring(0, 3);
    }

}

