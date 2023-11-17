package refrigerator.server.security.config;

public class JsonWebTokenKey {
    public static final String AUTHORITIES_KEY = "auth";
    public static final String BEARER_TYPE = "Bearer";
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24; // 1일 (기본 ms)
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 43200L * 60 * 1000; // 30 일
}
