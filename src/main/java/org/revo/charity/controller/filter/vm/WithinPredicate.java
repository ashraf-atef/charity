package org.revo.charity.controller.filter.vm;

import com.vividsolutions.jts.geom.Point;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.Renderable;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;

import javax.persistence.criteria.Expression;
import java.io.Serializable;

public class WithinPredicate extends AbstractSimplePredicate implements Serializable {
    private final Expression<Point> matchExpression;
    private final Point area;
    private final Double range;

    public WithinPredicate(CriteriaBuilderImpl criteriaBuilder, Expression<Point> matchExpression, Point area, Double range) {
        super(criteriaBuilder);
        this.matchExpression = matchExpression;
        this.area = area;
        this.range = range;
    }


    @Override
    public String render(boolean isNegated, RenderingContext renderingContext) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(" ST_DWithin(")
            .append(((Renderable) this.matchExpression).render(renderingContext))
            .append(", ").append(" ST_MakePoint( ").append(area.getX()).append(",").append(area.getY()).append(" ) ")
            .append(", ")
            .append(this.range)
            .append(") ");
        return buffer.toString();
    }

    @Override
    public void registerParameters(ParameterRegistry registry) {

    }
}
