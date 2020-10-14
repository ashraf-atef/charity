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
public class Operator {
    private OperatorType operatorType = OperatorType.AND;
    private List<Operator> operators = new ArrayList<>();
    private List<FilterEntry> filter = new ArrayList<>();

    @Override
    public String toString() {
        return "{" +
            "ot=" + operatorType +
            ", o=" + operators +
            ", f=" + filter +
            '}';
    }
}
