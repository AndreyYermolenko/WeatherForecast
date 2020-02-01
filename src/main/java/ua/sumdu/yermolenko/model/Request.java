package ua.sumdu.yermolenko.model;

import lombok.Data;

@Data
public class Request {
    private String type;
    private String query;
    private String language;
    private String unit;
}
