package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dtos.CurvePointDTO;
import com.nnk.springboot.mappers.CurvePointMapper;
import com.nnk.springboot.models.CurvePointModel;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
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
public class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private CurvePointService curvePointService;

	@Autowired
	private CurvePointMapper curvePointMapper;

	@Test
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		assertNotNull(curvePoint.getId());
		assertTrue(curvePoint.getCurveId() == 10);

		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
		assertTrue(curvePoint.getCurveId() == 20);

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		assertFalse(curvePointList.isPresent());
	}


	@Test
	public void curvePointCreateService(){
		CurvePoint entity = new CurvePoint(10, 10d, 30d);
		CurvePointDTO dto = curvePointMapper.modelToDto(curvePointMapper.entityToModel(entity));

		CurvePointModel model = curvePointService.createCurvePoint(dto);

		assertNotNull(model);
		assertEquals(model.getTerm(), 10d, 10d);
		assertEquals(model.getValue(), 30d, 30d);
	}

	@Test
	public void curvePointUpdateService(){
		CurvePoint entity = new CurvePoint(10, 10d, 30d);
		CurvePointDTO dto = curvePointMapper.modelToDto(curvePointMapper.entityToModel(entity));

		CurvePointModel model = curvePointService.createCurvePoint(dto);
		model.setTerm(20d);
		model.setValue(40d);
		model = curvePointService.updateCurvePoint(curvePointMapper.modelToDto(model));

		assertNotNull(model);
		assertEquals(model.getTerm(), 20d, 20d);
		assertEquals(model.getValue(), 40d, 40d);
	}

	@Test
	public void curvePointFindAllService(){
		CurvePoint entity = new CurvePoint(10, 10d, 30d);
		CurvePointDTO dto = curvePointMapper.modelToDto(curvePointMapper.entityToModel(entity));

		CurvePointModel model = curvePointService.createCurvePoint(dto);

		List<CurvePointModel> list = curvePointService.findAllCurvePoint();

		Integer len = list.size();
		assertNotNull(list);
		assertNotEquals(Optional.of(0), len);
	}

	@Test
	public void curvePointFindByIdService(){
		CurvePoint entity = new CurvePoint(10, 10d, 30d);
		CurvePointDTO dto = curvePointMapper.modelToDto(curvePointMapper.entityToModel(entity));

		CurvePointModel model = curvePointService.createCurvePoint(dto);

		CurvePointModel found = curvePointService.findCurvePointById(model.getId());

		assertNotNull(found);
		assertEquals(found.getId(), model.getId());
		assertEquals(found.getTerm(), 10d, 10d);
		assertEquals(found.getValue(), 30d, 30d);
	}

	@Test
	public void curvePointDeleteService(){
		CurvePoint entity = new CurvePoint(10, 10d, 30d);
		CurvePointDTO dto = curvePointMapper.modelToDto(curvePointMapper.entityToModel(entity));

		CurvePointModel model = curvePointService.createCurvePoint(dto);

		String message = curvePointService.deleteCurvePoint(model.getId());


		assertNotNull(message);
		assertEquals(message, "CurvePointModel deleted");

	}



	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////  Controller
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




	@Test
	public void curvePointAddAPI() throws Exception {

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/curvePoint/add");

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}



	@Test
	public void curvePointUpdateAPI() throws Exception {

		CurvePoint bid = new CurvePoint(10, 10d, 30d);
		bid = curvePointRepository.save(bid);
		Integer id = bid.getId();

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/curvePoint/update/"+id);

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/update"));

		curvePointRepository.deleteById(id);

	}


	@Test
	public void curvePointDeleteAPI() throws Exception {

		CurvePoint bid = new CurvePoint(10, 10d, 30d);
		bid = curvePointRepository.save(bid);
		Integer id = bid.getId();

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/curvePoint/delete/"+id);

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print());

		Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
		assertFalse(curvePoint.isPresent());
	}

}
