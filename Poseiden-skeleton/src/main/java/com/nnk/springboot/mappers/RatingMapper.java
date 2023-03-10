package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dtos.RatingDTO;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.models.RatingModel;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
        typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RatingMapper {


    RatingDTO modelToDto(RatingModel model);

    List<RatingDTO> modelsToDtos(List<RatingModel> models);

    RatingModel dtoToModel(RatingDTO dto);

    List<RatingModel> dtosToModels(List<RatingDTO> dtos);

    Rating modelToEntity(RatingModel model);

    List<Rating> modelsToEntities(List<RatingModel> models);

    RatingModel entityToModel(Rating entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRatingModelFromDto(RatingDTO dto, @MappingTarget RatingModel model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
