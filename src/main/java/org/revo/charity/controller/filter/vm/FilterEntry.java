package org.revo.charity.controller.filter.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterEntry {
    private String field;
    private Condition condition = Condition.EQUAL;
    private Object value;

    @Override
    public String toString() {
        return "{" +
            "f='" + field + '\'' +
            ", c=" + condition +
            ", v=" + value +
            '}';
    }
}
