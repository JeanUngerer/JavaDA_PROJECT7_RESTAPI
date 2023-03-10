package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dtos.RuleNameDTO;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.models.RuleNameModel;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
        typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RuleNameMapper {

    RuleNameDTO modelToDto(RuleNameModel model);

    List<RuleNameDTO> modelsToDtos(List<RuleNameModel> models);

    RuleNameModel dtoToModel(RuleNameDTO dto);

    List<RuleNameModel> dtosToModels(List<RuleNameDTO> dtos);

    RuleName modelToEntity(RuleNameModel model);

    List<RuleName> modelsToEntities(List<RuleNameModel> models);

    RuleNameModel entityToModel(RuleName entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRuleNameModelFromDto(RuleNameDTO dto, @MappingTarget RuleNameModel model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
