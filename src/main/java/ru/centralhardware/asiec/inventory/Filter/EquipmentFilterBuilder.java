package ru.centralhardware.asiec.inventory.Filter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.centralhardware.asiec.inventory.Entity.Enum.AttributeType;
import ru.centralhardware.asiec.inventory.Service.AttributeService;
import ru.centralhardware.asiec.inventory.SpringContext;
import ru.centralhardware.asiec.inventory.Web.Dto.FilterRequest;

import java.util.ArrayList;
import java.util.List;

public class EquipmentFilterBuilder {

    /**
     * construct ${@link EquipmentFilter} from JSON string
     * @param filter json string contains filter request
     * @return constructed instance of  ${@link EquipmentFilter}
     * @throws ParseException if JSON invalid
     */
    public static EquipmentFilter of(String filter) throws ParseException {
        if (filter == null || filter.isEmpty()) return new EquipmentFilter(List.of());

        List<FilterRequest> filterRequest = new ArrayList<>();
        JSONObject object = (JSONObject) new JSONParser().parse(filter);
        object.forEach((k,v) -> {
            if (v instanceof JSONArray && k.equals("responsible")){
                ((JSONArray) v).forEach(it -> filterRequest.add(new FilterRequest(
                        ValueType.STRING,
                        (String) k,
                        (String) k,
                        "=",
                        (String) it
                )));
                return;
            }

            if (!(v instanceof JSONObject)) return;

            ((JSONObject) v).forEach((key,value) -> {
                AttributeType type = SpringContext.getBean(AttributeService.class).getAttributeType((String) key);
                if (type == null) return;

                switch (type){
                    case STRING -> {
                        if (value instanceof JSONArray){
                            ((JSONArray) value).forEach(it -> filterRequest.add(new FilterRequest(
                                    ValueType.STRING,
                                    (String) k,
                                    (String) key,
                                    "=",
                                    (String) it
                            )));
                        } else {
                            filterRequest.add(new FilterRequest(
                                    ValueType.STRING,
                                    (String) k,
                                    (String) key,
                                    "=",
                                    (String) value
                            ));
                        }
                    }
                    case NUMBER -> {
                        if (value instanceof JSONArray){
                            ((JSONArray) value).forEach(it -> filterRequest.add(new FilterRequest(
                                    ValueType.NUMBER,
                                    (String) k,
                                    (String) key,
                                    "=",
                                    (String) it
                            )));
                        } else {
                            filterRequest.add(new FilterRequest(
                                    ValueType.NUMBER,
                                    (String) k,
                                    (String) key,
                                    "=",
                                    (String) value
                            ));
                        }
                    }
                    case RANGE -> {
                        filterRequest.add(new FilterRequest(
                                ValueType.NUMBER,
                                (String) k,
                                (String) key,
                                "<",
                                ((JSONArray)value).get(0).toString()
                        ));
                        filterRequest.add(new FilterRequest(
                                ValueType.NUMBER,
                                (String) k,
                                (String) key,
                                ">",
                                ((JSONArray)value).get(1).toString()
                        ));
                    }
                }
            });
        });

        return new EquipmentFilter(filterRequest);
    }

}
