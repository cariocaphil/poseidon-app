package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleTests {

  @Autowired
  private RuleNameRepository ruleNameRepository;

  @Test
  public void ruleTest() {
    // Initialize a new RuleName object with all fields
    RuleName rule = new RuleName();
    rule.setName("Rule Name");
    rule.setDescription("Description");
    rule.setJson("Json content");
    rule.setTemplate("Template content");
    rule.setSqlStr("SQL statement");
    rule.setSqlPart("SQL part");

    // Save
    rule = ruleNameRepository.save(rule);
    Assert.assertNotNull(rule.getId());
    Assert.assertEquals("Rule Name", rule.getName());

    // Update
    rule.setName("Rule Name Update");
    rule = ruleNameRepository.save(rule);
    Assert.assertEquals("Rule Name Update", rule.getName());

    // Find
    List<RuleName> listResult = ruleNameRepository.findAll();
    Assert.assertFalse(listResult.isEmpty());

    // Delete
    Long id = rule.getId();
    ruleNameRepository.delete(rule);
    Optional<RuleName> ruleList = ruleNameRepository.findById(Math.toIntExact(id));
    Assert.assertFalse(ruleList.isPresent());
  }
}
