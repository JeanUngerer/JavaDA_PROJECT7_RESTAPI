package com.nnk.springboot.models;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RuleNameModel {

    Integer id;
    String name;
    String description;
    String json;
    String template;
    String sqlStr;
    String sqlPart;

}
