package org.revo.charity.controller.filter.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sort {
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        return "{" +
                "o=" + orders +
                '}';
    }
}
