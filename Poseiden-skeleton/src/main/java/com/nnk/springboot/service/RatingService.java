package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dtos.RatingDTO;
import com.nnk.springboot.exception.ExceptionHandler;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.mappers.RatingMapper;
import com.nnk.springboot.models.RatingModel;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingService {

    RatingMapper ratingMapper;

    RatingRepository ratingRepository;

    /**
     * Get all Rating objects from database
     * @return List<RatingModel> list of all entities found in database
     * @throws Exception Bad Request on error
     */
    public List<RatingModel> findAllRating() {
        try {
            log.info("findAllRating");
            List<RatingModel> ratingList = new ArrayList<RatingModel>();
            ratingRepository.findAll().forEach(ct -> ratingList.add(ratingMapper.entityToModel(ct)));
            return  ratingList;
        } catch (Exception e) {
            log.error("We could not find all rating: " + e.getMessage());
            throw new ExceptionHandler("We could not find your ratings");
        }
    }

    /**
     * Get a Rating object from database by ID
     * @param id the Id of the object to find
     * @return RatingModel the rating with correct Id, if any.
     * @throws  Exception Bad Request on error
     */
    public RatingModel findRatingById(Integer id) {
        try {
            log.info("findRatingById - id: " + id.toString());
            RatingModel rating = ratingMapper.entityToModel(ratingRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We didn't find your rating")));
            return rating;
        } catch (Exception e) {
            log.error("We could not find all rating: " + e.getMessage());
            throw new ExceptionHandler("We could not find your rating");
        }
    }

    /**
     * Save a Rating object into database.
     * @param dto the Rating to save or update into database
     * @return RatingModel the saved Rating
     * @throws  Exception Bad Request on error
     */
    public RatingModel createRating(RatingDTO dto) {
        try {
            log.info("createRating");
            RatingModel rating = ratingMapper.dtoToModel(dto);
            Rating entity = ratingRepository.save(ratingMapper.modelToEntity(rating));
            return ratingMapper.entityToModel(entity);
        } catch (Exception e) {
            log.error("Couldn't rating user: " + e.getMessage());
            throw new ExceptionHandler("We could not create your rating");
        }
    }

    /**
     * Update a Rating object into database.
     * @param dto the Rating to save or update into database
     * @return RatingModel the saved Rating
     * @throws  Exception Bad Request on error
     */
    public RatingModel updateRating(RatingDTO dto) {
        try {
            log.info("updateRatingModel - id: " + dto.getId().toString());
            RatingModel rating = ratingMapper.entityToModel(ratingRepository.findById(dto.getId()).orElseThrow(()
                    -> new ExceptionHandler("We could not find your rating")));
            ratingMapper.updateRatingModelFromDto(dto, rating, new CycleAvoidingMappingContext());
            Rating entity = ratingRepository.save(ratingMapper.modelToEntity(rating));
            return ratingMapper.entityToModel(entity);
        } catch (Exception e) {
            log.error("Couldn't update user: " + e.getMessage());
            throw new ExceptionHandler("We could not update your rating");
        }
    }

    /**
     * Delete a Rating object from database by ID
     * @param id the Id of the object to delete
     * @return String deleted messsage.
     * @throws Exception Bad Request on error
     */
    public String deleteRating(Integer id) {
        try {
            log.info("deleteRatingModel - id: " + id.toString());
            RatingModel rating = ratingMapper.entityToModel(ratingRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We could not find your rating")));
            ratingRepository.delete(ratingMapper.modelToEntity(rating));
            return "RatingModel deleted";
        } catch (Exception e) {
            log.error("Couldn't delete rating: " + e.getMessage());
            throw new ExceptionHandler("We could not delete your rating");
        }
    }
}
