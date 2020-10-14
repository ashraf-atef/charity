package org.revo.charity.controller.filter.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

import static java.util.Collections.singletonList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria implements Serializable {
    private CustomPageable pageable = new CustomPageable();
    private Operator operator = new Operator();

    @Override
    public String toString() {
        return "{" +
            "p=" + pageable +
            ", o=" + operator +
            '}';
    }

    public static boolean hasKey(Operator operator, String filed) {
        if (operator.getFilter().stream().anyMatch(v -> v.getField().equals(filed))) {
            return true;
        }
        for (Operator o : operator.getOperators()) {
            return hasKey(o, filed);
        }
        return false;
    }

    public static boolean hasKeyAndValue(Operator operator, String filed) {
        if (operator.getFilter().stream().anyMatch(v -> v.getField().equals(filed) && v.getValue() != null)) {
            return true;
        }
        for (Operator o : operator.getOperators()) {
            return hasKey(o, filed);
        }
        return false;
    }

    public static FilterEntry getKey(Operator operator, String filed) {
        Optional<FilterEntry> first = operator.getFilter().stream().filter(v -> v.getField().equals(filed)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        for (Operator o : operator.getOperators()) {
            return getKey(o, filed);
        }
        return null;
    }

    public static void updateKey(SearchCriteria searchCriteria, String filed, Function<Object, Object> objectFunction) {
        if (hasKey(searchCriteria.getOperator(), filed)) {
            FilterEntry key = getKey(searchCriteria.getOperator(), filed);
            key.setValue(objectFunction.apply(key.getValue()));
        }

    }


    public static Operator appendKey(Operator operator, OperatorType operatorType, FilterEntry filterEntry) {
        return new Operator(operatorType, singletonList(operator), new ArrayList<>(singletonList(filterEntry)));
    }

    public static void appendKey(SearchCriteria searchCriteria, OperatorType operatorType, FilterEntry filterEntry) {
        if (searchCriteria.getOperator().getOperatorType() == operatorType) {
            List<FilterEntry> entries = new ArrayList<>(searchCriteria.getOperator().getFilter());
            entries.add(filterEntry);
            searchCriteria.getOperator().setFilter(entries);
        } else {
            searchCriteria.setOperator(new Operator(operatorType, singletonList(searchCriteria.getOperator()), new ArrayList<>(singletonList(filterEntry))));
        }
    }
}
