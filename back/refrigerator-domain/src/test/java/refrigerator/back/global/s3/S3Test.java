package refrigerator.back.global.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import refrigerator.back.global.s3.config.S3Config;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class, classes = S3Config.class)
@Slf4j
@Disabled
class S3Test {

    @Autowired AmazonS3Client amazonS3Client;
    private final String bucketName = "refrigerator-image";

    @Test
    @DisplayName("파일 이름으로 key 값 가져오기")
    void getFolderNameByBucket(){
        ListObjectsV2Request request = new ListObjectsV2Request();
        request.setDelimiter("IMAGE_INGREDIENT_BEAN_CURD.png");
        request.setBucketName(bucketName);
        List<String> commonPrefixes = amazonS3Client.listObjectsV2(request).getCommonPrefixes();
        log.info("commonPrefixes={}", commonPrefixes);
    }

    @Test
    void amazonS3Client() {
        String path = "path";
        String url = amazonS3Client.getUrl(bucketName, path).toString();
        log.info("url={}", url);
    }
}