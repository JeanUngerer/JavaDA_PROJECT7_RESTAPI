package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dtos.TradeDTO;
import com.nnk.springboot.dtos.TradeDTO;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.models.TradeModel;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
        typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TradeMapper {

    TradeDTO modelToDto(TradeModel model);

    List<TradeDTO> modelsToDtos(List<TradeModel> models);

    TradeModel dtoToModel(TradeDTO dto);

    List<TradeModel> dtosToModels(List<TradeDTO> dtos);

    Trade modelToEntity(TradeModel model);

    List<Trade> modelsToEntities(List<TradeModel> models);

    TradeModel entityToModel(Trade entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTradeModelFromDto(TradeDTO dto, @MappingTarget TradeModel model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
