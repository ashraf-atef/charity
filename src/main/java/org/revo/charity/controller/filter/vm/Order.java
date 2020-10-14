package org.revo.charity.controller.filter.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private org.springframework.data.domain.Sort.Direction direction;
    private String property;
}
