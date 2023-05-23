package it.mytask.rewardzCal;

import it.mytask.rewardzCal.model.Customer;
import it.mytask.rewardzCal.model.Transaction;
import it.mytask.rewardzCal.repository.CustomerRepository;
import it.mytask.rewardzCal.service.RewardsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RewardsServiceTest {
  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private RewardsService rewardsService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCalculateRewardPointsByMonth() {
    Customer customer1 = new Customer();
    customer1.setId(1L);
    customer1.setName("John");

    Customer customer2 = new Customer();
    customer2.setId(2L);
    customer2.setName("Jane");

    Transaction transaction1 = new Transaction();
    transaction1.setAmount(120);
    transaction1.setMonth(1);
    transaction1.setCustomer(customer1);

    Transaction transaction2 = new Transaction();
    transaction2.setAmount(80);
    transaction2.setMonth(1);
    transaction2.setCustomer(customer2);

    Transaction transaction3 = new Transaction();
    transaction3.setAmount(70);
    transaction3.setMonth(2);
    transaction3.setCustomer(customer1);

    Transaction transaction4 = new Transaction();
    transaction4.setAmount(150);
    transaction4.setMonth(2);
    transaction4.setCustomer(customer2);

    when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));
    when(customerRepository.findById(1L)).thenReturn(java.util.Optional.of(customer1));
    when(customerRepository.findById(2L)).thenReturn(java.util.Optional.of(customer2));
    when(customerRepository.getOne(1L)).thenReturn(customer1);
    when(customerRepository.getOne(2L)).thenReturn(customer2);

    customer1.setTransactions(Arrays.asList(transaction1, transaction3));
    customer2.setTransactions(Arrays.asList(transaction2, transaction4));

    List<Map<String, Object>> rewardsByMonth = rewardsService.calculateRewardPointsByMonth();

    assertEquals(3, rewardsByMonth.size());

    Map<String, Object> rewardsMonth1 = rewardsByMonth.get(0);
    assertEquals(1, rewardsMonth1.get("month"));

    List<Map<String, Object>> customerRewardsMonth1 = (List<Map<String, Object>>) rewardsMonth1.get("customerRewards");
    assertEquals(2, customerRewardsMonth1.size());

    Map<String, Object> customerReward1Month1 = customerRewardsMonth1.get(0);
    assertEquals(1L, customerReward1Month1.get("customerId"));
    assertEquals(90, customerReward1Month1.get("rewardPoints"));

    Map<String, Object> customerReward2Month1 = customerRewardsMonth1.get(1);
    assertEquals(2L, customerReward2Month1.get("customerId"));
    assertEquals(30, customerReward2Month1.get("rewardPoints"));

  }
}

