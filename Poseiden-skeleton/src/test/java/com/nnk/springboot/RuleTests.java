package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dtos.RuleNameDTO;
import com.nnk.springboot.mappers.RuleNameMapper;
import com.nnk.springboot.models.RuleNameModel;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private RuleNameService ruleNameService;

	@Autowired
	private RuleNameMapper ruleNameMapper;

	@Test
	public void ruleTest() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = ruleNameRepository.save(rule);
		assertNotNull(rule.getId());
		assertTrue(rule.getName().equals("Rule Name"));

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
		assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		assertFalse(ruleList.isPresent());
	}

	@Test
	public void ruleNameCreateService(){
		RuleName entity = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		RuleNameDTO dto = ruleNameMapper.modelToDto(ruleNameMapper.entityToModel(entity));

		RuleNameModel model = ruleNameService.createRuleName(dto);

		assertNotNull(model);
		assertEquals("Rule Name", model.getName());
		assertEquals("Description", model.getDescription());
		assertEquals("Json", model.getJson());
		assertEquals("Template", model.getTemplate());
	}

	@Test
	public void ruleNameUpdateService(){
		RuleName entity = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		RuleNameDTO dto = ruleNameMapper.modelToDto(ruleNameMapper.entityToModel(entity));

		RuleNameModel model = ruleNameService.createRuleName(dto);
		model.setName("Rule Name22");
		model.setDescription("Description22");
		model = ruleNameService.updateRuleName(ruleNameMapper.modelToDto(model));

		assertNotNull(model);
		assertEquals("Rule Name22", model.getName());
		assertEquals("Description22", model.getDescription());
		assertEquals("Json", model.getJson());
		assertEquals("Template", model.getTemplate());
	}

	@Test
	public void ruleNameFindAllService(){
		RuleName entity = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		RuleNameDTO dto = ruleNameMapper.modelToDto(ruleNameMapper.entityToModel(entity));

		RuleNameModel model = ruleNameService.createRuleName(dto);

		List<RuleNameModel> list = ruleNameService.findAllRuleName();

		Integer len = list.size();
		assertNotNull(list);
		assertNotEquals(Optional.of(0), len);
	}

	@Test
	public void ruleNameFindByIdService(){
		RuleName entity = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		RuleNameDTO dto = ruleNameMapper.modelToDto(ruleNameMapper.entityToModel(entity));

		RuleNameModel model = ruleNameService.createRuleName(dto);

		RuleNameModel found = ruleNameService.findRuleNameById(model.getId());

		assertNotNull(found);
		assertEquals(found.getId(), model.getId());
		assertEquals("Rule Name", found.getName());
		assertEquals("Description", found.getDescription());
		assertEquals("Json", found.getJson());
		assertEquals("Template", found.getTemplate());
	}

	@Test
	public void ruleNameDeleteService(){
		RuleName entity = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		RuleNameDTO dto = ruleNameMapper.modelToDto(ruleNameMapper.entityToModel(entity));

		RuleNameModel model = ruleNameService.createRuleName(dto);

		String message = ruleNameService.deleteRuleName(model.getId());


		assertNotNull(message);
		assertEquals(message, "RuleNameModel deleted");

	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////  Controller
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




	@Test
	public void ruleNameAddAPI() throws Exception {

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/ruleName/add");

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}



	@Test
	public void ruleNameUpdateAPI() throws Exception {

		RuleName bid = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		bid = ruleNameRepository.save(bid);
		Integer id = bid.getId();

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/ruleName/update/"+id);

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/update"));

		ruleNameRepository.deleteById(id);

	}


	@Test
	public void ruleNameDeleteAPI() throws Exception {

		RuleName bid = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		bid = ruleNameRepository.save(bid);
		Integer id = bid.getId();

		RequestBuilder echoUserReq = MockMvcRequestBuilders.get("/ruleName/delete/"+id);

		this.mockMvc.perform(echoUserReq)
				.andDo(MockMvcResultHandlers.print());

		Optional<RuleName> ruleName = ruleNameRepository.findById(id);
		assertFalse(ruleName.isPresent());
	}
}
