package com.nnk.springboot.dtos;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {

    Integer id;
    String moodysRating;
    String sandRating;
    String fitchRating;
    Integer orderNumber;
}
