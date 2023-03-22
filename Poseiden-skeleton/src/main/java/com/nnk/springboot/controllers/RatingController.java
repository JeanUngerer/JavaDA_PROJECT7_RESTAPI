package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dtos.RatingDTO;
import com.nnk.springboot.mappers.RatingMapper;
import com.nnk.springboot.service.RatingService;
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
public class RatingController {
    // TODO: Inject Rating service

    RatingMapper ratingMapper;

    RatingService ratingService;

    /**
     * get method to get all Bids
     * @param model Model
     * @param user Principal
     * @return template
     */
    @RequestMapping("/rating/list")
    public String home(Model model, Principal user) {
        addRatingToModel(model);
        addUsernameToModel(model, user.getName());
        return "rating/list";
    }

    /**
     * get method to show rating form
     * @param rating RatingDTO
     * @return template
     */
    @GetMapping("/rating/add")
    public String addRatingForm(RatingDTO rating) {
        return "rating/add";
    }

    /**
     * post method to add new rating
     * @param rating RatingDTO
     * @param result BindingResult
     * @param model Model
     * @return add rating list in database
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid RatingDTO rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ratingService.createRating(rating);
            addRatingToModel(model);
            log.info("Rating created. Id=" + rating.getId());
            return "redirect:/rating/list";
        }
        log.info("Error during Rating creation. Rating was not created");
        return "rating/add";
    }

    /**
     * get method to show rating update form
     * @param id
     * @param model
     * @return rating update form
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RatingDTO rating = ratingMapper.modelToDto(ratingService.findRatingById(id));

        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * post method to update rating by id
     * @param id Integer
     * @param rating RatingDTO
     * @param result BindingResult
     * @param model Model
     * @return template
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDTO rating, BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            log.info("Error during update of Rating (Id=" + id + "). Not updated");
            return "rating/update";
        }
        rating.setId(id);
        ratingService.updateRating(rating);
        addRatingToModel(model);
        log.info("Rating (Id=" + id + ") was updated");
        return "redirect:/rating/list";
    }

    /**
     * delete method to delete rating by id
     * @param id Integer
     * @param model Model
     * @return template
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        log.info("Rating (Id=" + id + ") was deleted");
        return "redirect:/rating/list";
    }

    /**
     * delete method to delete rating by id
     * @param model Model
     * @return add attribute listRating to model
     */
    private Model addRatingToModel(Model model) {
        return model.addAttribute("listRating", ratingMapper.modelsToDtos(ratingService.findAllRating()));
    }

    /**
     * delete method to delete rating by id
     * @param model Model
     * @return adds attribute remoteUser to model
     */
    private Model addUsernameToModel(Model model, String username) {
        return model.addAttribute("remoteUser", username);
    }
}
