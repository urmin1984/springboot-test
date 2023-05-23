package it.mytask.rewardzCal.controller;

import it.mytask.rewardzCal.service.RewardsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rewards")
public class RewardsController {
  @Autowired
  private RewardsService rewardsService;

  @GetMapping("/calculate")
  public List<Map<String, Object>> calculateRewardPointsByMonth() {
    return rewardsService.calculateRewardPointsByMonth();
  }

}
