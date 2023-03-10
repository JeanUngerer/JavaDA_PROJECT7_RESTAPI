package com.nnk.springboot.service;

import com.nnk.springboot.dtos.CurvePointDTO;
import com.nnk.springboot.exception.ExceptionHandler;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.mappers.CurvePointMapper;
import com.nnk.springboot.models.CurvePointModel;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointService {

    CurvePointMapper curvePointMapper;

    CurvePointRepository curvePointRepository;

    public List<CurvePointModel> findAllCurvePoint() {
        try {
            log.info("findAllCurvePoint");
            List<CurvePointModel> curvePointList = new ArrayList<CurvePointModel>();
            curvePointRepository.findAll().forEach(ct -> curvePointList.add(curvePointMapper.entityToModel(ct)));
            return  curvePointList;
        } catch (Exception e) {
            log.error("We could not find all curvePoint: " + e.getMessage());
            throw new ExceptionHandler("We could not find your curvePoints");
        }
    }

    public CurvePointModel findCurvePointById(Integer id) {
        try {
            log.info("findCurvePointById - id: " + id.toString());
            CurvePointModel curvePoint = curvePointMapper.entityToModel(curvePointRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We didn't find your curvePoint")));
            return curvePoint;
        } catch (Exception e) {
            log.error("We could not find all curvePoint: " + e.getMessage());
            throw new ExceptionHandler("We could not find your curvePoint");
        }
    }

    public CurvePointModel createCurvePoint(CurvePointDTO dto) {
        try {
            log.info("createCurvePoint");
            CurvePointModel curvePoint = curvePointMapper.dtoToModel(dto);
            curvePointRepository.save(curvePointMapper.modelToEntity(curvePoint));
            return curvePoint;
        } catch (Exception e) {
            log.error("Couldn't curvePoint user: " + e.getMessage());
            throw new ExceptionHandler("We could not create your curvePoint");
        }
    }
    public CurvePointModel updateCurvePoint(CurvePointDTO dto) {
        try {
            log.info("updateCurvePointModel - id: " + dto.getId().toString());
            CurvePointModel curvePoint = curvePointMapper.entityToModel(curvePointRepository.findById(dto.getId()).orElseThrow(()
                    -> new ExceptionHandler("We could not find your curvePoint")));
            curvePointMapper.updateCurvePointModelFromDto(dto, curvePoint, new CycleAvoidingMappingContext());
            curvePointRepository.save(curvePointMapper.modelToEntity(curvePoint));
            return curvePoint;
        } catch (Exception e) {
            log.error("Couldn't update user: " + e.getMessage());
            throw new ExceptionHandler("We could not update your curvePoint");
        }
    }

    public String deleteCurvePoint(Integer id) {
        try {
            log.info("deleteCurvePointModel - id: " + id.toString());
            CurvePointModel curvePoint = curvePointMapper.entityToModel(curvePointRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We could not find your curvePoint")));
            curvePointRepository.delete(curvePointMapper.modelToEntity(curvePoint));
            return "CurvePointModel deleted";
        } catch (Exception e) {
            log.error("Couldn't delete curvePoint: " + e.getMessage());
            throw new ExceptionHandler("We could not delete your curvePoint");
        }
    }
}
