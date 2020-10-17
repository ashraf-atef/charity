package org.revo.charity.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import java.io.IOException;

public class PointDeserializer extends JsonDeserializer<Point> {
    @Override
    public Point deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String[] split = jsonParser.getText().split(",");
        if (split.length == 2) {
           return new GeometryFactory().createPoint(new Coordinate(Double.parseDouble(split[0]), Double.parseDouble(split[1])));

        }
        return null;
    }
}
