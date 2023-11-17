package refrigerator.back.global.s3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.global.s3.S3ImageConvertAdapter;
import refrigerator.back.global.s3.S3ImageHandler;

/**
 * s3 의 Root Bucket 의 이미지를 다루는 imageConvert
 */
@Configuration
public class RootImageConvertConfig {

    private final S3ImageHandler s3ImageHandler;
    private final String bucketName;

    public RootImageConvertConfig(S3ImageHandler s3ImageHandler,
                                  @Value("${cloud.aws.bucket.root}") String bucketName) {
        this.s3ImageHandler = s3ImageHandler;
        this.bucketName = bucketName;
    }

    @Bean(name = "rootImageUrlConvert")
    @Primary
    public ImageUrlConvert rootImageUrlConvert(){
        return new S3ImageConvertAdapter(s3ImageHandler, bucketName);
    }
}
