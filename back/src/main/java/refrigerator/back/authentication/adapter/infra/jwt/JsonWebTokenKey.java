package refrigerator.back.authentication.adapter.infra.jwt;

public class JsonWebTokenKey {
    public static final String AUTHORITIES_KEY = "auth";
    public static final String BEARER_TYPE = "Bearer";
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1800 * 1000; // 30분
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 43200L * 60 * 1000; // 30일
}
