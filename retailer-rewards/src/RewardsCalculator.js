import React, { useState, useEffect } from 'react';

function RewardsCalculator() {
    const [transactions, setTransactions] = useState([]);

    useEffect(() => {
        // Fetching from Spring Boot API
        fetch('http://localhost:8080/api/transactions')
            .then(response => response.json())
            .then(data => setTransactions(data.transactions)); // Assuming your Spring Boot API sends data in this format
    }, []);


    function calculateRewardPoints(amount) {
        let points = 0;
        if (amount > 100) {
            points += (amount - 100) * 2;
            points += 50; // 1 point for each dollar from $50 to $100
        } else if (amount > 50) {
            points += (amount - 50);
        }
        return Math.floor(points); // Ensure points are whole numbers
    }


    const getCustomerTotalPoints = (customer) => {
        return transactions
            .filter(transaction => transaction.customer === customer)
            .reduce((acc, curr) => acc + calculateRewardPoints(curr.amount), 0);
    };

    const uniqueCustomers = [...new Set(transactions.map(t => t.customer))];

    return (
        <div>
            <h1>Reward Points</h1>
            <ul>
                {uniqueCustomers.map(customer => (
                    <li key={customer}>
                        {customer} - {getCustomerTotalPoints(customer)} points
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default RewardsCalculator;
