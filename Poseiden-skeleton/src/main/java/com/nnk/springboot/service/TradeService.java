package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
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

    /**
     * Get all Trade objects from database
     * @return List<TradeModel> list of all entities found in database
     * @throws Exception Bad Request on error
     */
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

    /**
     * Get a Trade object from database by ID
     * @param id the Id of the object to find
     * @return TradeModel the trade with correct Id, if any.
     * @throws  Exception Bad Request on error
     */
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

    /**
     * Save a Trade object into database.
     * @param dto the Trade to save or update into database
     * @return TradeModel the saved Trade
     * @throws  Exception Bad Request on error
     */
    public TradeModel createTrade(TradeDTO dto) {
        try {
            log.info("createTrade");
            TradeModel trade = tradeMapper.dtoToModel(dto);
            Trade entity = tradeRepository.save(tradeMapper.modelToEntity(trade));
            return tradeMapper.entityToModel(entity);
        } catch (Exception e) {
            log.error("Couldn't trade user: " + e.getMessage());
            throw new ExceptionHandler("We could not create your trade");
        }
    }

    /**
     * Update a Trade object into database.
     * @param dto the Trade to save or update into database
     * @return TradeModel the saved Trade
     * @throws  Exception Bad Request on error
     */
    public TradeModel updateTrade(TradeDTO dto) {
        try {
            log.info("updateTradeModel - id: " + dto.getTradeId().toString());
            TradeModel trade = tradeMapper.entityToModel(tradeRepository.findById(dto.getTradeId()).orElseThrow(()
                    -> new ExceptionHandler("We could not find your trade")));
            tradeMapper.updateTradeModelFromDto(dto, trade, new CycleAvoidingMappingContext());
            Trade entity = tradeRepository.save(tradeMapper.modelToEntity(trade));
            return tradeMapper.entityToModel(entity);
        } catch (Exception e) {
            log.error("Couldn't update user: " + e.getMessage());
            throw new ExceptionHandler("We could not update your trade");
        }
    }

    /**
     * Delete a Trade object from database by ID
     * @param id the Id of the object to delete
     * @return String deleted messsage.
     * @throws Exception Bad Request on error
     */
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
