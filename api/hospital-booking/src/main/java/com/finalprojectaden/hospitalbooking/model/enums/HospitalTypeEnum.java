package com.finalprojectaden.hospitalbooking.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum  HospitalTypeEnum {

    GOVERNOMENT,
    PRIVATE;

    @JsonValue
    public int toValue(){
        return this.ordinal();
    }

}
