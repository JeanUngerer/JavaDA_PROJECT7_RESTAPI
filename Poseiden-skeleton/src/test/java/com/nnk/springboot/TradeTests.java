package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dtos.TradeDTO;
import com.nnk.springboot.mappers.TradeMapper;
import com.nnk.springboot.models.TradeModel;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class TradeTests {


	@Autowired
	MockMvc mockMvc;
	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private TradeService tradeService;

	@Autowired
	private TradeMapper tradeMapper;

	@Test
	public void tradeTest() {
		Trade trade = new Trade("Trade Account", "Type");

		// Save
		trade = tradeRepository.save(trade);
		assertNotNull(trade.getTradeId());
		assertTrue(trade.getAccount().equals("Trade Account"));

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		assertTrue(trade.getAccount().equals("Trade Account Update"));

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		assertFalse(tradeList.isPresent());
	}

	@Test
	public void tradeCreateService(){
		Trade entity = new Trade("Trade Account", "Type");
		TradeDTO dto = tradeMapper.modelToDto(tradeMapper.entityToModel(entity));

		TradeModel model = tradeService.createTrade(dto);

		assertNotNull(model);
		assertEquals("Trade Account", model.getAccount());
		assertEquals("Type", model.getType());
	}

	@Test
	public void tradeUpdateService(){
		Trade entity = new Trade("Trade Account", "Type");
		TradeDTO dto = tradeMapper.modelToDto(tradeMapper.entityToModel(entity));

		TradeModel model = tradeService.createTrade(dto);
		model.setAccount("Trade Account22");
		model.setType("Type22");
		model = tradeService.updateTrade(tradeMapper.modelToDto(model));

		assertNotNull(model);
		assertEquals("Trade Account22", model.getAccount());
		assertEquals("Type22", model.getType());
	}

	@Test
	public void tradeFindAllService(){
		Trade entity = new Trade("Trade Account", "Type");
		TradeDTO dto = tradeMapper.modelToDto(tradeMapper.entityToModel(entity));

		TradeModel model = tradeService.createTrade(dto);

		List<TradeModel> list = tradeService.findAllTrade();

		Integer len = list.size();
		assertNotNull(list);
		assertNotEquals(Optional.of(0), len);
	}

	@Test
	public void tradeFindByIdService(){
		Trade entity = new Trade("Trade Account", "Type");
		TradeDTO dto = tradeMapper.modelToDto(tradeMapper.entityToModel(entity));

		TradeModel model = tradeService.createTrade(dto);

		TradeModel found = tradeService.findTradeById(model.getTradeId());

		assertNotNull(found);
		assertEquals(found.getTradeId(), model.getTradeId());
		assertEquals("Trade Account", found.getAccount());
		assertEquals("Type", found.getType());
	}

	@Test
	public void tradeDeleteService(){
		Trade entity = new Trade("Trade Account", "Type");
		TradeDTO dto = tradeMapper.modelToDto(tradeMapper.entityToModel(entity));

		TradeModel model = tradeService.createTrade(dto);

		String message = tradeService.deleteTrade(model.getTradeId());


		assertNotNull(message);
		assertEquals(message, "TradeModel deleted");

	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////  Controller
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




	@Test
	public void tradeAddAPI() throws Exception {

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/trade/add");

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}



	@Test
	public void tradeUpdateAPI() throws Exception {

		Trade bid = new Trade("Trade Account", "Type");
		bid = tradeRepository.save(bid);
		Integer id = bid.getTradeId();

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/trade/update/"+id);

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(view().name("trade/update"));

		tradeRepository.deleteById(id);

	}


	@Test
	public void tradeDeleteAPI() throws Exception {

		Trade bid = new Trade("Trade Account", "Type");
		bid = tradeRepository.save(bid);
		Integer id = bid.getTradeId();

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/trade/delete/"+id);

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print());

		Optional<Trade> trade = tradeRepository.findById(id);
		assertFalse(trade.isPresent());
	}
}
