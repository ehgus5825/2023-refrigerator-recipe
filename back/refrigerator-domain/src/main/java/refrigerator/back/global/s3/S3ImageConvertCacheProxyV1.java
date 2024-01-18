//package refrigerator.back.global.s3;
//
//import lombok.RequiredArgsConstructor;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@RequiredArgsConstructor
//public class S3ImageConvertCacheProxyV1 implements ImageUrlConvert {
//
//    private final ImageUrlConvert target;
//    private final Map<String, String> cache = new ConcurrentHashMap<>();
//
//    @Override
//    public String getUrl(String fileName) {
//        if (fileName.startsWith("IMAGE_INGREDIENT")){
//            String cacheData = cache.get(fileName);
//            if (cacheData == null){
//                String url = target.getUrl(fileName);
//                cache.put(fileName, url);
//                return url;
//            }
//            return cacheData;
//        } else{
//            return target.getUrl(fileName);
//        }
//    }
//}