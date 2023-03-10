package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListDTO;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.models.BidListModel;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
        typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BidListMapper {


    BidListDTO modelToDto(BidListModel model);

    List<BidListDTO> modelsToDtos(List<BidListModel> models);

    BidListModel dtoToModel(BidListDTO dto);

    List<BidListModel> dtosToModels(List<BidListDTO> dtos);

    BidList modelToEntity(BidListModel model);

    List<BidList> modelsToEntities(List<BidListModel> models);

    BidListModel entityToModel(BidList entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBidListModelFromDto(BidListDTO dto, @MappingTarget BidListModel model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
