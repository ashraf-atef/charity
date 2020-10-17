package org.revo.charity;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

//@SpringBootTest
class CharityApplicationTests {

    @Test
    void contextLoads() {
        Car car = new Car();
        car.setMake("Good manufacturer");
        Model model = new Model();
        model.setName("Model 3");
        car.setModel(model);
        car.setYearOfProduction(2014);

        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression("model.name");

        EvaluationContext context = new StandardEvaluationContext(car);
        String result = expression.getValue(context, String.class);
        System.out.println(result);
    }

}

@Getter
@Setter
class Car {
    private String make;
    private Model model;
    private Integer yearOfProduction;
}
@Getter
@Setter
class Model{
    private String name;
}
