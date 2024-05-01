package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

  private final RuleNameRepository ruleNameRepository;

  @Autowired
  public RuleNameService(RuleNameRepository ruleNameRepository) {
    this.ruleNameRepository = ruleNameRepository;
  }

  public List<RuleName> findAll() {
    return ruleNameRepository.findAll();
  }

  public Optional<RuleName> findById(Long id) {
    return ruleNameRepository.findById(Math.toIntExact(id));
  }

  public RuleName save(RuleName ruleName) {
    return ruleNameRepository.save(ruleName);
  }

  public void delete(Long id) {
    ruleNameRepository.deleteById(Math.toIntExact(id));
  }
}
