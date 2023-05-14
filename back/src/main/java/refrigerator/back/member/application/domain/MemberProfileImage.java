package refrigerator.back.member.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum MemberProfileImage {
    PROFILE_IMAGE_ONE("IMG_9709.JPG"),
    PROFILE_IMAGE_TWO("IMG_9706.JPG"),
    PROFILE_IMAGE_THREE("IMG_9707.JPG"),
    PROFILE_IMAGE_FOUR("IMG_9708.JPG"),
    PROFILE_IMAGE_FIVE("IMG_9705.JPG"),
    PROFILE_NOT_SELECT("")
    ;

    private String name;

    protected static MemberProfileImage pickUp(int random){
        MemberProfileImage[] result = MemberProfileImage.values();
        return result[random];
    }

    public static MemberProfileImage findImageByName(String name){
        return Arrays.stream(MemberProfileImage.values())
                .filter(image -> image.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_PROFILE_IMAGE));
    }
}
