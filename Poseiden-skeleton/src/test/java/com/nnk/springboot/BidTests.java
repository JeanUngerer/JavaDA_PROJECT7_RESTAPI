package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListDTO;
import com.nnk.springboot.mappers.BidListMapper;
import com.nnk.springboot.models.BidListModel;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BidTests {


	@Autowired
	MockMvc mockMvc;
	@Autowired
	private BidListRepository bidListRepository;

	@Autowired
	private BidListService bidListService;

	@Autowired
	private BidListMapper bidListMapper;

	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		bid = bidListRepository.save(bid);
		assertNotNull(bid.getBidListId());
		assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////  Service
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void bidListCreateService(){
		BidList entity = new BidList("Account Test", "Type Test", 10d);
		BidListDTO dto = bidListMapper.modelToDto(bidListMapper.entityToModel(entity));

		BidListModel model = bidListService.createBidList(dto);

		assertNotNull(model);
		assertEquals(model.getBidQuantity(), 10d, 10d);
		assertEquals(model.getAccount(), "Account Test");
		assertEquals(model.getType(), "Type Test");
	}

	@Test
	public void bidListUpdateService(){
		BidList entity = new BidList("Account Test", "Type Test", 10d);
		BidListDTO dto = bidListMapper.modelToDto(bidListMapper.entityToModel(entity));

		BidListModel model = bidListService.createBidList(dto);
		model.setBid(20d);
		model.setType("Type2 Test");
		model = bidListService.updateBidList(bidListMapper.modelToDto(model));

		assertNotNull(model);
		assertEquals(model.getBidQuantity(), 20d, 20d);
		assertEquals(model.getAccount(), "Account Test");
		assertEquals(model.getType(), "Type2 Test");
	}

	@Test
	public void bidListFindAllService(){
		BidList entity = new BidList("Account Test", "Type Test", 10d);
		BidListDTO dto = bidListMapper.modelToDto(bidListMapper.entityToModel(entity));

		BidListModel model = bidListService.createBidList(dto);

		List<BidListModel> list = bidListService.findAllBidList();

		Integer len = list.size();
		assertNotNull(list);
		assertNotEquals(Optional.of(0), len);
	}

	@Test
	public void bidListFindByIdService(){
		BidList entity = new BidList("Account Test", "Type Test", 10d);
		BidListDTO dto = bidListMapper.modelToDto(bidListMapper.entityToModel(entity));

		BidListModel model = bidListService.createBidList(dto);

		BidListModel found = bidListService.findBidListById(model.getBidListId());

		assertNotNull(found);
		assertEquals(found.getBidListId(), model.getBidListId());
		assertEquals(found.getBidQuantity(), 10d, 10d);
		assertEquals(found.getAccount(), "Account Test");
		assertEquals(found.getType(), "Type Test");
	}

	@Test
	public void bidListDeleteService(){
		BidList entity = new BidList("Account Test", "Type Test", 10d);
		BidListDTO dto = bidListMapper.modelToDto(bidListMapper.entityToModel(entity));

		BidListModel model = bidListService.createBidList(dto);

		String message = bidListService.deleteBidList(model.getBidListId());


		assertNotNull(message);
		assertEquals(message, "BidListModel deleted");

	}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////  Controller
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




	@Test
	public void bidListAddAPI() throws Exception {

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/bidList/add");

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}



	@Test
	public void bidListUpdateAPI() throws Exception {

		BidList bid = new BidList("Account Test", "Type Test", 10d);
		bid = bidListRepository.save(bid);
		Integer id = bid.getBidListId();

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/bidList/update/"+id);

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/update"));

		bidListRepository.deleteById(id);

	}


	@Test
	public void bidListDeleteAPI() throws Exception {

		BidList bid = new BidList("Account Test", "Type Test", 10d);
		bid = bidListRepository.save(bid);
		Integer id = bid.getBidListId();

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/bidList/delete/"+id);

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print());

		Optional<BidList> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}





}
