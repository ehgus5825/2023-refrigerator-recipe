package refrigerator.server.security.user;


import java.util.Map;

// TODO : NPE 를 방지하기 위한 조치 생각하기
public interface LoginMethodCall<T> {
    T call(Map<String, Object> attributes, String targetKey);
}
