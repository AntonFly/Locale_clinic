package com.police.openam;

import lombok.Data;

@Data
public class RegistrationInfo {

    private String type;
    private Status status;

    @Data
    public static class Status{

        private boolean success;

    }

}
