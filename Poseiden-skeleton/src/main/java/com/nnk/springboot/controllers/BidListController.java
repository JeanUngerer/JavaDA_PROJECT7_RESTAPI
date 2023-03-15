package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListDTO;
import com.nnk.springboot.mappers.BidListMapper;
import com.nnk.springboot.service.BidListService;
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
public class BidListController {
    // TODO: Inject Bid service

    BidListService bidListService;

    BidListMapper bidListMapper;

    @RequestMapping("/bidList/list")
    public String home(Model model, Principal user) {
        addBidListToModel(model);
        addUsernameToModel(model, user.getName());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListDTO bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.createBidList(bid);
            addBidListToModel(model);
            log.info("BidList created. Id=" + bid.getBidListId());
            return "redirect:/bidList/list";
        }
        log.info("Error during BidList creation. BidList was not created");
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidListDTO bidList = bidListMapper.modelToDto(bidListService.findBidListById(id));
                
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDTO bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("Error during update of BidList (Id=" + id + "). Not updated");
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListService.updateBidList(bidList);
        addBidListToModel(model);
        log.info("BidList (Id=" + id + ") was updated");
        return "redirect:/bidList/list";
    }


    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        log.info("BidList (Id=" + id + ") was deleted");
        return "redirect:/bidList/list";
    }


    private Model addBidListToModel(Model model) {
        return model.addAttribute("listBidList", bidListMapper.modelsToDtos(bidListService.findAllBidList()));
    }

    private Model addUsernameToModel(Model model, String username) {
        return model.addAttribute("remoteUser", username);
    }
}
