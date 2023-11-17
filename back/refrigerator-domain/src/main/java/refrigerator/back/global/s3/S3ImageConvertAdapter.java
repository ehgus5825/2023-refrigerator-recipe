package refrigerator.back.global.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class S3ImageConvertAdapter implements ImageUrlConvert{

    private final S3ImageHandler s3ImageHandler;
    private final String bucketName;

    @Override
    public String getUrl(String fileName) {
        if (StringUtils.hasText(fileName)){
            return s3ImageHandler.getUrl(bucketName, fileName).toString();
        }
        return null;
    }
}
