package com.nnk.springboot.service;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListDTO;
import com.nnk.springboot.exception.ExceptionHandler;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.mappers.BidListMapper;
import com.nnk.springboot.models.BidListModel;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class BidListService {

    BidListMapper bidListMapper;

    BidListRepository bidListRepository;

    /**
     * Get all BidList objects from database
     * @return List<BidListModel> list of all entities found in database
     * @throws Exception Bad Request on error
     */
    public List<BidListModel> findAllBidList() {
        try {
            log.info("findAllBidList");
            List<@Valid BidListModel> bidListList = new ArrayList<BidListModel>();
            bidListRepository.findAll().forEach(ct -> bidListList.add(bidListMapper.entityToModel(ct)));
            return  bidListList;
        } catch (Exception e) {
            log.error("We could not find all bidList: " + e.getMessage());
            throw new ExceptionHandler("We could not find your bidLists");
        }
    }

    /**
     * Get a BidList object from database by ID
     * @param id the Id of the object to find
     * @return BidListModel the bidList with correct Id, if any.
     * @throws  Exception Bad Request on error
     */
    public BidListModel findBidListById(Integer id) {
        try {
            log.info("findBidListById - id: " + id.toString());
            @Valid BidListModel bidList = bidListMapper.entityToModel(bidListRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We didn't find your bidList")));
            return bidList;
        } catch (Exception e) {
            log.error("We could not find all bidList: " + e.getMessage());
            throw new ExceptionHandler("We could not find your bidList");
        }
    }

    /**
     * Save a BidList object into database.
     * @param dto the BidList to save or update into database
     * @return BidListModel the saved BidList
     * @throws  Exception Bad Request on error
     */
    public BidListModel createBidList(@Valid BidListDTO dto) {
        try {
            log.info("createBidList");
            @Valid BidListModel bidList = bidListMapper.dtoToModel(dto);
            BidList entity = bidListRepository.save(bidListMapper.modelToEntity(bidList));
            return bidListMapper.entityToModel(entity);
        } catch (Exception e) {
            log.error("Couldn't bidList user: " + e.getMessage());
            throw new ExceptionHandler("We could not create your bidList");
        }
    }

    /**
     * Update a BidList object into database.
     * @param dto the BidList to save or update into database
     * @return BidListModel the saved BidList
     * @throws  Exception Bad Request on error
     */
    public BidListModel updateBidList(@Valid BidListDTO dto) {
        try {
            log.info("updateBidListModel - id: " + dto.getBidListId().toString());
            @Valid BidListModel bidList = bidListMapper.entityToModel(bidListRepository.findById(dto.getBidListId()).orElseThrow(()
                    -> new ExceptionHandler("We could not find your bidList")));
            bidListMapper.updateBidListModelFromDto(dto, bidList, new CycleAvoidingMappingContext());
            BidList entity = bidListRepository.save(bidListMapper.modelToEntity(bidList));
            return bidListMapper.entityToModel(entity);
        } catch (Exception e) {
            log.error("Couldn't update user: " + e.getMessage());
            throw new ExceptionHandler("We could not update your bidList");
        }
    }

    /**
     * Delete a BidList object from database by ID
     * @param id the Id of the object to delete
     * @return String deleted messsage.
     * @throws Exception Bad Request on error
     */
    public String deleteBidList(Integer id) {
        try {
            log.info("deleteBidListModel - id: " + id.toString());
            @Valid BidListModel bidList = bidListMapper.entityToModel(bidListRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We could not find your bidList")));
            bidListRepository.delete(bidListMapper.modelToEntity(bidList));
            return "BidListModel deleted";
        } catch (Exception e) {
            log.error("Couldn't delete bidList: " + e.getMessage());
            throw new ExceptionHandler("We could not delete your bidList");
        }
    }
}
