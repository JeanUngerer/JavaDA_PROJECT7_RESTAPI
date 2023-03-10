package com.nnk.springboot.service;

import com.nnk.springboot.dtos.TradeDTO;
import com.nnk.springboot.exception.ExceptionHandler;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.mappers.TradeMapper;
import com.nnk.springboot.models.TradeModel;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeService {

    TradeMapper tradeMapper;

    TradeRepository tradeRepository;

    public List<TradeModel> findAllTrade() {
        try {
            log.info("findAllTrade");
            List<TradeModel> tradeList = new ArrayList<TradeModel>();
            tradeRepository.findAll().forEach(ct -> tradeList.add(tradeMapper.entityToModel(ct)));
            return  tradeList;
        } catch (Exception e) {
            log.error("We could not find all trade: " + e.getMessage());
            throw new ExceptionHandler("We could not find your trades");
        }
    }

    public TradeModel findTradeById(Integer id) {
        try {
            log.info("findTradeById - id: " + id.toString());
            TradeModel trade = tradeMapper.entityToModel(tradeRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We didn't find your trade")));
            return trade;
        } catch (Exception e) {
            log.error("We could not find all trade: " + e.getMessage());
            throw new ExceptionHandler("We could not find your trade");
        }
    }

    public TradeModel createTrade(TradeDTO dto) {
        try {
            log.info("createTrade");
            TradeModel trade = tradeMapper.dtoToModel(dto);
            tradeRepository.save(tradeMapper.modelToEntity(trade));
            return trade;
        } catch (Exception e) {
            log.error("Couldn't trade user: " + e.getMessage());
            throw new ExceptionHandler("We could not create your trade");
        }
    }
    public TradeModel updateTrade(TradeDTO dto) {
        try {
            log.info("updateTradeModel - id: " + dto.getTradeId().toString());
            TradeModel trade = tradeMapper.entityToModel(tradeRepository.findById(dto.getTradeId()).orElseThrow(()
                    -> new ExceptionHandler("We could not find your trade")));
            tradeMapper.updateTradeModelFromDto(dto, trade, new CycleAvoidingMappingContext());
            tradeRepository.save(tradeMapper.modelToEntity(trade));
            return trade;
        } catch (Exception e) {
            log.error("Couldn't update user: " + e.getMessage());
            throw new ExceptionHandler("We could not update your trade");
        }
    }

    public String deleteTrade(Integer id) {
        try {
            log.info("deleteTradeModel - id: " + id.toString());
            TradeModel trade = tradeMapper.entityToModel(tradeRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We could not find your trade")));
            tradeRepository.delete(tradeMapper.modelToEntity(trade));
            return "TradeModel deleted";
        } catch (Exception e) {
            log.error("Couldn't delete trade: " + e.getMessage());
            throw new ExceptionHandler("We could not delete your trade");
        }
    }
}
