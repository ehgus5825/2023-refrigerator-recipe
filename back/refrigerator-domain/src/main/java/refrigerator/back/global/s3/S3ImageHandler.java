package refrigerator.back.global.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.S3Exception;

import java.net.URL;
import java.util.List;

@Component
@Slf4j
public class S3ImageHandler {

    private final AmazonS3Client amazonS3Client;

    public S3ImageHandler(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    /**
     * 이미지 url 생성 함수
     * @param fileName 파일명
     * @return URL 객체, 순수 Url 을 추출하기 위해선 toString() 사용
     */
    public URL getUrl(String bucketName, String fileName){
        ListObjectsV2Request request = createRequest(bucketName, fileName);
        return amazonS3Client.getUrl(bucketName, getKey(request));
    }

    /**
     * s3 image key 값을 가져오는 함수
     * @param request s3 객체(Object) 요청
     * @return s3 image key, <폴더경로>/<파일명>
     * ex : ingredient/apple.png
     */
    private String getKey(ListObjectsV2Request request) {
        List<String> prefixes = amazonS3Client.listObjectsV2(request).getCommonPrefixes();
        if (prefixes.size() != 1){
            log.info("[s3] image name duplication or not found : {}", prefixes);
            throw new S3Exception(S3ExceptionType.DUPLICATE_FILE_NAME);
        }
        return prefixes.get(0);
    }

    /**
     * s3 객체 요청 함수 생성, 파일명은 key 의 가장 마지막 부분에 위치하기 때문에 ListObjectsV2Request 의 구분 문자를 파일 명으로 지정
     * @param fileName 파일명
     * @return s3 객체(Object) 요청 객체
     */
    private ListObjectsV2Request createRequest(String bucketName, String fileName){
        ListObjectsV2Request request = new ListObjectsV2Request();
        request.setDelimiter(fileName);
        request.setBucketName(bucketName);
        return request;
    }
}
