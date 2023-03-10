package com.nnk.springboot.service;

import com.nnk.springboot.dtos.RuleNameDTO;
import com.nnk.springboot.exception.ExceptionHandler;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.mappers.RuleNameMapper;
import com.nnk.springboot.models.RuleNameModel;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class RuleNameService {

    RuleNameMapper ruleNameMapper;

    RuleNameRepository ruleNameRepository;

    public List<RuleNameModel> findAllRuleName() {
        try {
            log.info("findAllRuleName");
            List<RuleNameModel> ruleNameList = new ArrayList<RuleNameModel>();
            ruleNameRepository.findAll().forEach(ct -> ruleNameList.add(ruleNameMapper.entityToModel(ct)));
            return  ruleNameList;
        } catch (Exception e) {
            log.error("We could not find all ruleName: " + e.getMessage());
            throw new ExceptionHandler("We could not find your ruleNames");
        }
    }

    public RuleNameModel findRuleNameById(Integer id) {
        try {
            log.info("findRuleNameById - id: " + id.toString());
            RuleNameModel ruleName = ruleNameMapper.entityToModel(ruleNameRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We didn't find your ruleName")));
            return ruleName;
        } catch (Exception e) {
            log.error("We could not find all ruleName: " + e.getMessage());
            throw new ExceptionHandler("We could not find your ruleName");
        }
    }

    public RuleNameModel createRuleName(RuleNameDTO dto) {
        try {
            log.info("createRuleName");
            RuleNameModel ruleName = ruleNameMapper.dtoToModel(dto);
            ruleNameRepository.save(ruleNameMapper.modelToEntity(ruleName));
            return ruleName;
        } catch (Exception e) {
            log.error("Couldn't ruleName user: " + e.getMessage());
            throw new ExceptionHandler("We could not create your ruleName");
        }
    }
    public RuleNameModel updateRuleName(RuleNameDTO dto) {
        try {
            log.info("updateRuleNameModel - id: " + dto.getId().toString());
            RuleNameModel ruleName = ruleNameMapper.entityToModel(ruleNameRepository.findById(dto.getId()).orElseThrow(()
                    -> new ExceptionHandler("We could not find your ruleName")));
            ruleNameMapper.updateRuleNameModelFromDto(dto, ruleName, new CycleAvoidingMappingContext());
            ruleNameRepository.save(ruleNameMapper.modelToEntity(ruleName));
            return ruleName;
        } catch (Exception e) {
            log.error("Couldn't update user: " + e.getMessage());
            throw new ExceptionHandler("We could not update your ruleName");
        }
    }

    public String deleteRuleName(Integer id) {
        try {
            log.info("deleteRuleNameModel - id: " + id.toString());
            RuleNameModel ruleName = ruleNameMapper.entityToModel(ruleNameRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We could not find your ruleName")));
            ruleNameRepository.delete(ruleNameMapper.modelToEntity(ruleName));
            return "RuleNameModel deleted";
        } catch (Exception e) {
            log.error("Couldn't delete ruleName: " + e.getMessage());
            throw new ExceptionHandler("We could not delete your ruleName");
        }
    }
}
