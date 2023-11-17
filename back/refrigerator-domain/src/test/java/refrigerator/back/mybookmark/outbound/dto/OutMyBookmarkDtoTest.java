package refrigerator.back.mybookmark.outbound.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OutMyBookmarkDtoTest {

    @Test
    @DisplayName("북마크 전체 개수 조회 Dto -> 전체 개수가 1 이상 일 때")
    void getNumberExistNumber() {
        OutMyBookmarkNumberDto number = new OutMyBookmarkNumberDto(10L);
        int result = number.getNumber();
        assertEquals(10, result);
    }

    @Test
    @DisplayName("북마크 전체 개수 조회 Dto -> 전체 개수가 0 일 때")
    void getNumberNotExistNumber() {
        OutMyBookmarkNumberDto number = new OutMyBookmarkNumberDto(null);
        int result = number.getNumber();
        assertEquals(0, result);
    }
}