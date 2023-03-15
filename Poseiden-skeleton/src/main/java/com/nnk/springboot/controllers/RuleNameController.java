package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dtos.RuleNameDTO;
import com.nnk.springboot.mappers.RuleNameMapper;
import com.nnk.springboot.service.RuleNameService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RuleNameController {
    // TODO: Inject RuleName service

    RuleNameMapper ruleNameMapper;

    RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model, Principal user) {
        addRuleNameToModel(model);
        addUsernameToModel(model, user.getName());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleNameForm(RuleName ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleNameDTO ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleNameService.createRuleName(ruleName);
            addRuleNameToModel(model);
            log.info("RuleName created. Id=" + ruleName.getId());
            return "redirect:/ruleName/list";
        }
        log.info("Error during RuleName creation. RuleName was not created");
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleNameDTO ruleName = ruleNameMapper.modelToDto(ruleNameService.findRuleNameById(id));

        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDTO ruleName, BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            log.info("Error during update of RuleName (Id=" + id + "). Not updated");
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameService.updateRuleName(ruleName);
        addRuleNameToModel(model);
        log.info("RuleName (Id=" + id + ") was updated");
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        log.info("RuleName (Id=" + id + ") was deleted");
        return "redirect:/ruleName/list";
    }

    private Model addRuleNameToModel(Model model) {
        return model.addAttribute("listRuleName", ruleNameMapper.modelsToDtos(ruleNameService.findAllRuleName()));
    }

    private Model addUsernameToModel(Model model, String username) {
        return model.addAttribute("remoteUser", username);
    }
}
