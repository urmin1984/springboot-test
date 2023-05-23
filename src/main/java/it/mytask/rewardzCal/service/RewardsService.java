package it.mytask.rewardzCal.service;

import it.mytask.rewardzCal.model.Customer;
import it.mytask.rewardzCal.model.Transaction;
import it.mytask.rewardzCal.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardsService {
  @Autowired
  private CustomerRepository customerRepository;

  public List<Map<String, Object>> calculateRewardPointsByMonth() {
    List<Map<String, Object>> rewardsByMonth = new ArrayList<>();

    for (int month = 1; month <= 3; month++) {
      Map<String, Object> rewardMap = new HashMap<>();
      rewardMap.put("month", month);
      List<Map<String, Object>> customerRewards = new ArrayList<>();

      List<Customer> customers = customerRepository.findAll();
      for (Customer customer : customers) {
        List<Transaction> transactions = customer.getTransactionsForMonth(month);
        int totalRewardPoints = calculateTotalRewardPoints(transactions);
        Map<String, Object> customerReward = new HashMap<>();
        customerReward.put("customerId", customer.getId());
        customerReward.put("rewardPoints", totalRewardPoints);
        customerRewards.add(customerReward);
      }

      rewardMap.put("customerRewards", customerRewards);
      rewardsByMonth.add(rewardMap);
    }

    return rewardsByMonth;
  }

  private int calculateTotalRewardPoints(List<Transaction> transactions) {
    int totalRewardPoints = 0;

    for (Transaction transaction : transactions) {
      int amount = transaction.getAmount();
      if (amount > 100) {
        totalRewardPoints += (2 * (amount - 100)) + 50; // 2 points per dollar over $100 + 50 points for the amount between $50 and $100
      } else if (amount > 50) {
        totalRewardPoints += (amount - 50); // 1 point per dollar between $50 and $100
      }
    }

    return totalRewardPoints;
  }
}
