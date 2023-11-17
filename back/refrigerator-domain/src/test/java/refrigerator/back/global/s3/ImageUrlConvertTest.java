package refrigerator.back.global.s3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import refrigerator.back.global.s3.config.RootImageConvertConfig;
import refrigerator.back.global.s3.config.S3Config;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        initializers = ConfigDataApplicationContextInitializer.class,
        classes = S3TestConfiguration.class)
@ActiveProfiles("local")
@Slf4j
class ImageUrlConvertTest {

    @Autowired ImageUrlConvert imageUrlConvert;

    @Test
    @DisplayName("연동된 s3 버킷에서 이미지 url 조회 성공 테스트")
    void getUrl() {
        String urlRegex = "https://[/da-zA-Z-]+\\.s3\\.[0-9a-zA-Z-]+\\.amazonaws\\.com+(/\\S*)?";
        Pattern pattern = Pattern.compile(urlRegex);
        String fileName = "FXh69KNUUAE06-5.jpeg";
        String url = imageUrlConvert.getUrl(fileName);
        assertTrue(pattern.matcher(url).matches());
    }
}