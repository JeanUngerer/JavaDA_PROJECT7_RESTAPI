package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dtos.CurvePointDTO;
import com.nnk.springboot.dtos.CurvePointDTO;
import com.nnk.springboot.mappers.CurvePointMapper;
import com.nnk.springboot.service.CurvePointService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CurveController {
    // TODO: Inject Curve Point service

    CurvePointMapper curvePointMapper;

    CurvePointService curvePointService;

    /**
     * get method to get all Bids
     * @param model Model
     * @param user Principal
     * @return template
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model, Principal user) {
        addCurvePointToModel(model);
        addUsernameToModel(model, user.getName());
        return "curvePoint/list";
    }

    /**
     * get method to show curvePoint form
     * @param curvePoint CurvePointDTO
     * @return template
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePointDTO curvePoint) {
        return "curvePoint/add";
    }

    /**
     * post method to add new curvePoint
     * @param curvePoint CurvePointDTO
     * @param result BindingResult
     * @param model Model
     * @return add curvePoint list in database
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointDTO curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePointService.createCurvePoint(curvePoint);
            addCurvePointToModel(model);
             log.info("CurvePoint created. Id=" + curvePoint.getId());
            return "redirect:/curvePoint/list";
        }
         log.info("Error during CurvePoint creation. CurvePoint was not created");
        return "curvePoint/add";
    }

    /**
     * get method to show curvePoint update form
     * @param id
     * @param model
     * @return curvePoint update form
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePointDTO curvePoint = curvePointMapper.modelToDto(curvePointService.findCurvePointById(id));

        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * post method to update curvePoint by id
     * @param id Integer
     * @param curvePoint CurvePointDTO
     * @param result BindingResult
     * @param model Model
     * @return template
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePointDTO curvePoint, BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
             log.info("Error during update of CurvePoint (Id=" + id + "). Not updated");
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        curvePointService.updateCurvePoint(curvePoint);
        addCurvePointToModel(model);
         log.info("CurvePoint (Id=" + id + ") was updated");
        return "redirect:/curvePoint/list";
    }

    /**
     * delete method to delete curvePoint by id
     * @param id Integer
     * @param model Model
     * @return template
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        curvePointService.deleteCurvePoint(id);
         log.info("CurvePoint (Id=" + id + ") was deleted");
        return "redirect:/curvePoint/list";
    }

    /**
     * delete method to delete curvePoint by id
     * @param model Model
     * @return add attribute listCurvePoint to model
     */
    private Model addCurvePointToModel(Model model) {
        return model.addAttribute("listCurvePoint", curvePointMapper.modelsToDtos(curvePointService.findAllCurvePoint()));
    }

    /**
     * delete method to delete curvePoint by id
     * @param model Model
     * @return adds attribute remoteUser to model
     */
    private Model addUsernameToModel(Model model, String username) {
        return model.addAttribute("remoteUser", username);
    }}
