package refrigerator.server.api.authentication.application.usecase;

import javax.servlet.http.Cookie;

public interface RestrictAccessUseCase {
    Cookie restrictAccessToTokens(Cookie[] cookies);
}
