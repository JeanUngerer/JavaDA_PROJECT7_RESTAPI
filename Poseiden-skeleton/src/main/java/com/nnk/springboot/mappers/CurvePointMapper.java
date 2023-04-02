package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dtos.CurvePointDTO;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.models.CurvePointModel;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
        typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CurvePointMapper {


    CurvePointDTO modelToDto(CurvePointModel model);

    List<CurvePointDTO> modelsToDtos(List<CurvePointModel> models);

    CurvePointModel dtoToModel(CurvePointDTO dto);

    List<CurvePointModel> dtosToModels(List<CurvePointDTO> dtos);

    CurvePoint modelToEntity(CurvePointModel model);

    List<CurvePoint> modelsToEntities(List<CurvePointModel> models);

    CurvePointModel entityToModel(CurvePoint entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCurvePointModelFromDto(CurvePointDTO dto, @MappingTarget CurvePointModel model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
