package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dtos.RatingDTO;
import com.nnk.springboot.mappers.RatingMapper;
import com.nnk.springboot.models.RatingModel;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private RatingMapper ratingMapper;

	@Test
	public void ratingTest() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		rating = ratingRepository.save(rating);
		assertNotNull(rating.getId());
		assertTrue(rating.getOrderNumber() == 10);

		// Update
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
		assertTrue(rating.getOrderNumber() == 20);

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		assertFalse(ratingList.isPresent());
	}

	@Test
	public void ratingCreateService(){
		Rating entity = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		RatingDTO dto = ratingMapper.modelToDto(ratingMapper.entityToModel(entity));

		RatingModel model = ratingService.createRating(dto);

		assertNotNull(model);
		assertEquals("Moodys Rating", model.getMoodysRating());
		assertEquals("Sand PRating", model.getSandRating());
		assertEquals("Fitch Rating", model.getFitchRating());
	}

	@Test
	public void ratingUpdateService(){
		Rating entity = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		RatingDTO dto = ratingMapper.modelToDto(ratingMapper.entityToModel(entity));

		RatingModel model = ratingService.createRating(dto);
		model.setMoodysRating("Moodys Ratinggg");
		model.setSandRating("Sand PRatingggg");
		model = ratingService.updateRating(ratingMapper.modelToDto(model));

		assertNotNull(model);
		assertEquals("Moodys Ratinggg", model.getMoodysRating());
		assertEquals("Sand PRatingggg", model.getSandRating());
		assertEquals("Fitch Rating", model.getFitchRating());
	}

	@Test
	public void ratingFindAllService(){
		Rating entity = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		RatingDTO dto = ratingMapper.modelToDto(ratingMapper.entityToModel(entity));

		RatingModel model = ratingService.createRating(dto);

		List<RatingModel> list = ratingService.findAllRating();

		Integer len = list.size();
		assertNotNull(list);
		assertNotEquals(Optional.of(0), len);
	}

	@Test
	public void ratingFindByIdService(){
		Rating entity = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		RatingDTO dto = ratingMapper.modelToDto(ratingMapper.entityToModel(entity));

		RatingModel model = ratingService.createRating(dto);

		RatingModel found = ratingService.findRatingById(model.getId());

		assertNotNull(found);
		assertEquals(found.getId(), model.getId());
		assertEquals("Moodys Rating", found.getMoodysRating());
		assertEquals("Sand PRating", found.getSandRating());
		assertEquals("Fitch Rating", found.getFitchRating());
	}

	@Test
	public void ratingDeleteService(){
		Rating entity = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		RatingDTO dto = ratingMapper.modelToDto(ratingMapper.entityToModel(entity));

		RatingModel model = ratingService.createRating(dto);

		String message = ratingService.deleteRating(model.getId());


		assertNotNull(message);
		assertEquals(message, "RatingModel deleted");

	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////  Controller
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




	@Test
	public void ratingAddAPI() throws Exception {

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/rating/add");

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}



	@Test
	public void ratingUpdateAPI() throws Exception {

		Rating bid = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		bid = ratingRepository.save(bid);
		Integer id = bid.getId();

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/rating/update/"+id);

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(view().name("rating/update"));

		ratingRepository.deleteById(id);

	}


	@Test
	public void ratingDeleteAPI() throws Exception {

		Rating bid = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		bid = ratingRepository.save(bid);
		Integer id = bid.getId();

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/rating/delete/"+id);

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print());

		Optional<Rating> rating = ratingRepository.findById(id);
		assertFalse(rating.isPresent());
	}
}
