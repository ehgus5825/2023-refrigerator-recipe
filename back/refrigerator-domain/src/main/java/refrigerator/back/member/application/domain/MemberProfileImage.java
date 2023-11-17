package refrigerator.back.member.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MemberProfileImage {
    PROFILE_IMAGE_ONE("IMG_9709.JPG"),
    PROFILE_IMAGE_TWO("IMG_9706.JPG"),
    PROFILE_IMAGE_THREE("IMG_9707.JPG"),
    PROFILE_IMAGE_FOUR("IMG_9708.JPG"),
    PROFILE_IMAGE_FIVE("IMG_9705.JPG")
    ;

    private final String name;

    public static MemberProfileImage pickUp(int number){
        if (number < 0 || number >= MemberProfileImage.values().length){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_PROFILE_IMAGE);
        }
        return MemberProfileImage.values()[number];
    }

    public static MemberProfileImage findImageByName(String name){
        return Arrays.stream(MemberProfileImage.values())
                .filter(image -> image.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_PROFILE_IMAGE));
    }

}
