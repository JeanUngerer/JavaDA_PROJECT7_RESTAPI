package com.nnk.springboot.models;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RatingModel {

    Integer id;
    String moodysRating;
    String sandRating;
    String fitchRating;
    Integer orderNumber;
}
