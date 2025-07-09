package com.wesleyadiel.valeapi.enums;

public enum TableName {
    USER("vale.user");
    
    private final String value;

    TableName(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
