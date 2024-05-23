package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

  private final RuleNameRepository ruleNameRepository;

  @Autowired
  public RuleNameService(RuleNameRepository ruleNameRepository) {
    this.ruleNameRepository = ruleNameRepository;
    Logger.info("RuleNameService initialized");
  }

  public List<RuleName> findAll() {
    List<RuleName> ruleNames = ruleNameRepository.findAll();
    Logger.info("Retrieved all rule names, count: {}", ruleNames.size());
    return ruleNames;
  }

  public Optional<RuleName> findById(Long id) {
    Optional<RuleName> ruleName = ruleNameRepository.findById(Math.toIntExact(id));
    if (ruleName.isPresent()) {
      Logger.info("Found rule name with ID: {}", id);
    } else {
      Logger.warn("No rule name found with ID: {}", id);
    }
    return ruleName;
  }

  public RuleName save(RuleName ruleName) {
    Logger.info("Saving rule name: {}", ruleName.getName());
    RuleName savedRuleName = ruleNameRepository.save(ruleName);
    Logger.info("Rule name saved successfully with ID: {}", savedRuleName.getId());
    return savedRuleName;
  }

  public void delete(Long id) {
    if (ruleNameRepository.existsById(Math.toIntExact(id))) {
      ruleNameRepository.deleteById(Math.toIntExact(id));
      Logger.info("Deleted rule name with ID: {}", id);
    } else {
      Logger.warn("Attempted to delete non-existing rule name with ID: {}", id);
    }
  }
}
