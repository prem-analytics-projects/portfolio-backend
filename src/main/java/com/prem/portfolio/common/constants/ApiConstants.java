package com.prem.portfolio.common.constants;

public final class ApiConstants {

    private ApiConstants() {
    }

    public static final String API_BASE_PATH = "/api/v1";

    public static final String AUTH_BASE_PATH = API_BASE_PATH + "/auth";

    public static final String USER_BASE_PATH = API_BASE_PATH + "/users";

    public static final String ADMIN_BASE_PATH = API_BASE_PATH + "/admin";

    public static final String DEFAULT_ROLE = "USER";
}