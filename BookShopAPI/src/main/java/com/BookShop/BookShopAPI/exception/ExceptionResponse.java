package com.BookShop.BookShopAPI.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    private long exceptionTimeStamp;
    private String exceptionMessage;
    private String customMessage;
    private int code;
}
