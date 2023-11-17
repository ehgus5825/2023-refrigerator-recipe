package refrigerator.back.global.s3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import refrigerator.back.global.exception.S3Exception;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        initializers = ConfigDataApplicationContextInitializer.class,
        classes = S3TestConfiguration.class)
@Slf4j
class S3ImageHandlerTest {

    @Autowired S3ImageHandler s3ImageHandler;
    @Value("${cloud.aws.bucket.root}") String bucketName;

    @Test
    @DisplayName("이미지 url 생성 성공 테스트")
    void getUrl() {
        // given
        String fileName = "FXh69KNUUAE06-5.jpeg";
        // when
        URL url = s3ImageHandler.getUrl(bucketName, fileName);
        // then
        assertEquals("/test_image/" + fileName, url.getFile());
    }

    @Test
    @DisplayName("이미지 url 생성 실패 테스트 -> 동일한 파일명의 이미지가 다른 폴더 내에 있을 때")
    void getUrlFailTest() {
        // given
        String fileName = "IMG＿20220516＿184954＿811.jpg";
        // when
        assertThrows(S3Exception.class, () -> {
            try{
                s3ImageHandler.getUrl(bucketName, fileName);
            }catch (S3Exception e){
                assertEquals(S3ExceptionType.DUPLICATE_FILE_NAME, e.getBasicExceptionType());
                throw e;
            }
        });
    }


}