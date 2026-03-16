package com.bus.timetable.model;

public enum DayType {
    WEEKDAY("평일"),
    SATURDAY("토요일"),
    HOLIDAY("일요일·공휴일");

    private final String label;

    DayType(String label) { this.label = label; }

    public String getLabel() { return label; }
}
