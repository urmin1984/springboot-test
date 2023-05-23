package it.mytask.rewardzCal.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private List<Transaction> transactions;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  public List<Transaction> getTransactionsForMonth(int month) {
    List<Transaction> transactionsForMonth = new ArrayList<>();

    for (Transaction transaction : transactions) {
      if (transaction.getMonth() == month) {
        transactionsForMonth.add(transaction);
      }
    }

    return transactionsForMonth;
  }
}