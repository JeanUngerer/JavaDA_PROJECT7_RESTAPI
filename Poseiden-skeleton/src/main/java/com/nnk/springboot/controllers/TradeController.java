package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dtos.TradeDTO;
import com.nnk.springboot.mappers.TradeMapper;
import com.nnk.springboot.service.TradeService;
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
public class TradeController {
    // TODO: Inject Trade service

    TradeMapper tradeMapper;

    TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model, Principal user) {
        addTradeToModel(model);
        addUsernameToModel(model, user.getName());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid TradeDTO trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeService.createTrade(trade);
            addTradeToModel(model);
            log.info("Trade created. Id=" + trade.getTradeId());
            return "redirect:/trade/list";
        }
        log.info("Error during Trade creation. Trade was not created");
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        TradeDTO trade = tradeMapper.modelToDto(tradeService.findTradeById(id));

        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDTO trade, BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            log.info("Error during update of Trade (Id=" + id + "). Not updated");
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeService.updateTrade(trade);
        addTradeToModel(model);
        log.info("Trade (Id=" + id + ") was updated");
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTrade(id);
        log.info("Trade (Id=" + id + ") was deleted");
        return "redirect:/trade/list";
    }

    private Model addTradeToModel(Model model) {
        return model.addAttribute("listTrade", tradeMapper.modelsToDtos(tradeService.findAllTrade()));
    }

    private Model addUsernameToModel(Model model, String username) {
        return model.addAttribute("remoteUser", username);
    }
}
