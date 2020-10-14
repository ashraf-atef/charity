package org.revo.charity.controller.filter;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.revo.charity.controller.filter.vm.*;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SpecificationUtil {
    public static <T, V extends Specification<T>> Optional<Specification<T>> andSpecification(Stream<V> criteria) {
        Iterator<V> itr = criteria.iterator();
        if (itr.hasNext()) {
            Specification<T> spec = Specification.where(itr.next());
            while (itr.hasNext()) {
                spec = spec.and(itr.next());
            }
            return Optional.of(spec);
        }
        return Optional.empty();
    }

    public static <T, V extends Specification<T>> Optional<Specification<T>> orSpecification(Stream<V> criteria) {
        Iterator<V> itr = criteria.iterator();
        if (itr.hasNext()) {
            Specification<T> spec = Specification.where(itr.next());
            while (itr.hasNext()) {
                spec = spec.or(itr.next());
            }
            return Optional.of(spec);
        }
        return Optional.empty();
    }


    public static <T, Y extends Comparable<? super Y>> Specification<T> getDefaultSpecification(FilterEntry it, Class<T> tClass) {
        Class<Y> yClass = null;
        return (root, cq, cb) -> {
            if (it.getField() == null || it.getField().isEmpty() || it.getValue() == null || it.getCondition() == null)
                return null;
            if (it.getCondition() == Condition.MATCH) {
                Map.Entry<Path<String>, String> expressions = getExpressionsPathToValueString(root, it, tClass);
                return cb.equal(cb.trim(expressions.getKey()), expressions.getValue());
            }
            if (it.getCondition() == Condition.NOT_MATCH) {
                Map.Entry<Path<String>, String> expressions = getExpressionsPathToValueString(root, it, tClass);
                return cb.notEqual(cb.trim(expressions.getKey()), expressions.getValue());
            }
            if (it.getCondition() == Condition.LIKE) {
                Map.Entry<Path<String>, String> expressions = getExpressionsPathToValueString(root, it, tClass);
                return cb.like(cb.lower(expressions.getKey()), "%" + expressions.getValue().toLowerCase() + "%");
            }
            if (it.getCondition() == Condition.NOT_LIKE) {
                Map.Entry<Path<String>, String> expressions = getExpressionsPathToValueString(root, it, tClass);
                return cb.notLike(cb.lower(expressions.getKey()), "%" + expressions.getValue().toLowerCase() + "%");
            }
            if (it.getCondition() == Condition.EQUAL) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.equal(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.NOT_EQUAL) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.notEqual(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.GREATER_THAN) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.greaterThan(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.GREATER_THAN_EQUAL) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.greaterThanOrEqualTo(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.LESS_THAN) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.lessThan(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.LESS_THAN_EQUAL) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.greaterThanOrEqualTo(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.BETWEEN) {
                List<Object> value = (List<Object>) it.getValue();
                FilterEntry min = new FilterEntry(it.getField(), it.getCondition(), value.get(0));
                FilterEntry max = new FilterEntry(it.getField(), it.getCondition(), value.get(1));
                    Map.Entry<Path<Y>, Y> minExpression = getExpressionsPathToValue(root, min, tClass, yClass);
                    Map.Entry<Path<Y>, Y> maxExpression = getExpressionsPathToValue(root, max, tClass, yClass);
                    return cb.and(cb.greaterThanOrEqualTo(minExpression.getKey(), minExpression.getValue()), cb.lessThanOrEqualTo(maxExpression.getKey(), maxExpression.getValue()));
            }
            if (it.getCondition() == Condition.EQUAL_DATE) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.equal(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.NOT_EQUAL_DATE) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.notEqual(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.GREATER_THAN_DATE) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.greaterThan(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.GREATER_THAN_EQUAL_DATE) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.greaterThanOrEqualTo(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.LESS_THAN_DATE) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.lessThan(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.LESS_THAN_EQUAL_DATE) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.lessThanOrEqualTo(expressions.getKey(), expressions.getValue());
            }
            if (it.getCondition() == Condition.IN) {
                return cb.or(((List<Object>) it.getValue()).stream().map(itt -> {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, new FilterEntry(it.getField(), it.getCondition(), itt), tClass, yClass);
                    return cb.equal(expressions.getKey(), expressions.getValue());
                }).toArray(Predicate[]::new));
            }
            if (it.getCondition() == Condition.NOT_IN) {
                return cb.or(((List<Object>) it.getValue()).stream().map(itt -> {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, new FilterEntry(it.getField(), it.getCondition(), itt), tClass, yClass);
                    return cb.notEqual(expressions.getKey(), expressions.getValue());
                }).toArray(Predicate[]::new));
            }
            if (it.getCondition() == Condition.MEMBER) {
                return cb.and(((List<Object>) it.getValue()).stream().map(itt -> {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, new FilterEntry(it.getField(), it.getCondition(), itt), tClass, yClass);
                    return cb.equal(expressions.getKey(), expressions.getValue());
                }).toArray(Predicate[]::new));
            }
            if (it.getCondition() == Condition.NOT_MEMBER) {
                return cb.and(((List<Object>) it.getValue()).stream().map(itt -> {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, new FilterEntry(it.getField(), it.getCondition(), itt), tClass, yClass);
                    return cb.notEqual(expressions.getKey(), expressions.getValue());
                }).toArray(Predicate[]::new));
            }
            if (it.getCondition() == Condition.IS_NULL) {
                    Map.Entry<Path<Y>, Y> expressions = getExpressionsPathToValue(root, it, tClass, yClass);
                    return cb.isNull(expressions.getKey());
            }
            if (it.getCondition() == Condition.WITHIN) {
                List<Double> value = (List<Double>) it.getValue();
                Map.Entry<Path<Point>, Point> expressions = getExpressionsPathToValuePoint(root, it, tClass);
                return cb.equal(new WithinPredicate(((CriteriaBuilderImpl) cb), expressions.getKey(), expressions.getValue(), value.get(2)), true);
            }
            return null;
        };
    }

    public static <T> Class getType(String field, Class<T> tClass) {
        try {
            List<String> strings = Arrays.asList(field.split("\\."));
            Field declaredField = tClass.getDeclaredField(strings.get(0));
            if (strings.size() > 1) {
                if (Collection.class.isAssignableFrom(declaredField.getType())) {
                    return getType(strings.stream().skip(1).collect(Collectors.joining(".")), (Class<T>) ((ParameterizedTypeImpl) declaredField.getGenericType()).getActualTypeArguments()[0]);
                }
                return getType(strings.stream().skip(1).collect(Collectors.joining(".")), declaredField.getType());
            }
            return declaredField.getType();
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /*
    need to be handled
    "class java.sql.Timestamp",
    "class java.time.LocalTime",
    "class java.time.Instant",
    */
    private static <T, Y extends Comparable<? super Y>> Map.Entry<Path<Y>, Y> getExpressionsPathToValue(Root<T> root, FilterEntry it, Class<T> tClass, Class<Y> yClass) {
        Class<Y> fieldType = getType(it.getField(), tClass);
        if (Long.class.equals(fieldType)) {
            return new AbstractMap.SimpleEntry<>(getPath(root, it.getField(), tClass, fieldType), ((Y) Long.valueOf(it.getValue().toString())));
        }
        if (Integer.class.equals(fieldType) || int.class.equals(fieldType)) {
            return new AbstractMap.SimpleEntry<>(getPath(root, it.getField(), tClass, fieldType), ((Y) Integer.valueOf(it.getValue().toString())));
        }
        if (Boolean.class.equals(fieldType) || boolean.class.equals(fieldType)) {
            return new AbstractMap.SimpleEntry<>(getPath(root, it.getField(), tClass, fieldType), ((Y) Boolean.valueOf(it.getValue().toString())));
        }
        if (ZonedDateTime.class.equals(fieldType)) {
            return new AbstractMap.SimpleEntry<>(getPath(root, it.getField(), tClass, fieldType), ((Y) ZonedDateTime.parse(it.getValue().toString())));
        }
        if (fieldType != null && fieldType.isEnum()) {
            return new AbstractMap.SimpleEntry<>(getPath(root, it.getField(), tClass, fieldType), ((Y) Enum.valueOf(((Class) fieldType), it.getValue().toString())));
        }
        return new AbstractMap.SimpleEntry<>(getPath(root, it.getField(), tClass, fieldType), ((Y) String.valueOf(it.getValue()).trim()));
    }

//    private static <T, Y extends Comparable<? super Y>> Map.Entry<Path<Y>, Path<Y>> getExpressionsPathToPath(Root<T> root, FilterEntry it, Class<T> tClass, Class<Y> yClass) {
//        return new AbstractMap.SimpleEntry<>(getPath(root, it.getField(), tClass, yClass), getPath(root, it.getValue().toString(), tClass, yClass));
//    }

    private static <T> Map.Entry<Path<String>, String> getExpressionsPathToValueString(Root<T> root, FilterEntry it, Class<T> tClass) {
        return new AbstractMap.SimpleEntry<>(getPath(root, it.getField(), tClass, String.class), String.valueOf(it.getValue()).trim());
    }

    private static <T> Map.Entry<Path<Point>, Point> getExpressionsPathToValuePoint(Root<T> root, FilterEntry it, Class<T> tClass) {
        List<Double> value = (List<Double>) it.getValue();
        Point point = new GeometryFactory().createPoint(new Coordinate(value.get(0), value.get(1)));
        return new AbstractMap.SimpleEntry<>(getPath(root, it.getField(), tClass, Point.class), point);
    }

    private static <T> boolean isCollection(String field, Class<T> tClass) {
        return Arrays.stream(tClass.getDeclaredFields()).filter(it -> it.getName().equals(field)).map(Field::getType).findFirst()
                .map(Collection.class::isAssignableFrom).
                        orElse(false);
        //        @TODO check this commented line
        //                        orElseThrow(() -> new Exception(field + " not exist on " + tClass.getSimpleName()));
    }

    private static <T, R> Path<R> getPath(Root<T> root, String field, Class<T> tClass, Class<R> rClass) {
        String[] split = field.split("\\.");
        Path<R> path = isCollection(split[0], tClass) ? root.join(split[0], JoinType.LEFT) : root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            path = path.get(split[i]);
        }
        return path;
    }

    public static <T> Specification<T> getSpecification(Operator operator, Class<T> tClass) {
        Stream<Specification<T>> stream = Stream.concat(
                operator.getFilter().stream()
                        .map(rr -> getDefaultSpecification(rr, tClass)),
                operator.getOperators().stream().map(it -> getSpecification(it, tClass))).filter(Objects::nonNull);
        if (operator.getOperatorType() == OperatorType.AND)
            return andSpecification(stream).orElse(null);
        else
            return orSpecification(stream).orElse(null);
    }

    public static <T> Page<T> findAllBySpecification(EntityManager entityManager, SearchCriteria searchCriteria, Class<T> tClass) {
        Specification<T> specification = getSpecification(searchCriteria.getOperator(), tClass);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        Long count = getCount(entityManager, criteriaBuilder, specification, tClass);
        List<T> data = count > 0 ? getData(entityManager, criteriaBuilder, specification, searchCriteria, tClass) : new ArrayList<>();
        return new CustomPage<>(data, searchCriteria.getPageable(), count);
    }

    public static <T> Long getCount(EntityManager entityManager, CriteriaBuilder criteriaBuilder, Specification<T> specification, Class<T> tClass) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(tClass);
        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(specification.toPredicate(root, criteriaQuery, criteriaBuilder));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public static <T> List<T> getData(EntityManager entityManager, CriteriaBuilder criteriaBuilder, Specification<T> specification, SearchCriteria searchCriteria, Class<T> tClass) {
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> root = criteriaQuery.from(tClass);
        criteriaQuery.where(specification.toPredicate(root, criteriaQuery, criteriaBuilder));
        if (searchCriteria.getPageable().getSort() != null)
            criteriaQuery.orderBy(StreamSupport.stream(searchCriteria.getPageable().getSort().spliterator(), false)
                    .map(it -> {
                        Path<Object> path = getPath(root, it.getProperty(), tClass, Object.class);
                        return it.isAscending() ? criteriaBuilder.asc(path) : criteriaBuilder.desc(path);
                    })
                    .collect(Collectors.toList()));
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(searchCriteria.getPageable().getPageNumber() * searchCriteria.getPageable().getPageSize());
        query.setMaxResults(searchCriteria.getPageable().getPageSize());
        return query.getResultList();
    }
}
