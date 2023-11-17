package refrigerator.back.mybookmark.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.mybookmark.application.service.RecipeBookmarkModifyHandler;
import refrigerator.back.mybookmark.exception.MyBookmarkExceptionType;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MyBookmarkTest {

    @Mock RecipeBookmarkModifyHandler handler;

    @Test
    @DisplayName("북마크 최초 생성 테스트")
    void create(){
        // given
        Long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 11, 1, 1);
        BDDMockito.doNothing().when(handler).added(recipeId);
        // when
        MyBookmark myBookmark = MyBookmark.create("memberId", recipeId, createDateTime, handler);
        // then
        assertEquals(false, myBookmark.getDeleted());
    }

    @Test
    @DisplayName("북마크 추가 성공 테스트 -> 삭제했던 북마크를 다시 추가하려고 할 때")
    void addSuccessTest() {
        // given
        Long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 11, 1, 1);
        BDDMockito.doNothing().when(handler).added(recipeId);
        // when
        MyBookmark myBookmark = MyBookmark.createForTest("memberId", recipeId, true, createDateTime);
        myBookmark.add(handler);
        // then
        assertEquals(false, myBookmark.getDeleted());
    }

    @Test
    @DisplayName("북마크 추가 실패 테스트 -> 이미 추가된 북마크를 다시 북마크하려고 할 때")
    void addFailTest() {
        // given
        Long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 11, 1, 1);
        MyBookmark myBookmark = MyBookmark.createForTest("memberId", recipeId, false, createDateTime);
        // when
        assertThrows(BusinessException.class, () -> {
            try{
                myBookmark.add(handler);
            } catch (BusinessException e){
                assertEquals(MyBookmarkExceptionType.ALREADY_ADD_BOOKMARK, e.getBasicExceptionType());
                throw e;
            }
        });
    }

    @Test
    @DisplayName("북마크 삭제 성공 테스트")
    void deleted() {
        // given
        Long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 11, 1, 1);
        MyBookmark myBookmark = MyBookmark.createForTest("memberId", recipeId, false, createDateTime);
        BDDMockito.doNothing().when(handler).deleted(recipeId);
        // when
        myBookmark.deleted(handler);
        // then
        assertEquals(true, myBookmark.getDeleted());
    }

    @Test
    @DisplayName("북마크 삭제 실패 테스트 -> 이미 삭제된 테스트를 삭제하려고 할 때")
    void deletedFailTest() {
        // given
        Long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 11, 1, 1);
        MyBookmark myBookmark = MyBookmark.createForTest("memberId", recipeId, true, createDateTime);
        // when
        assertThrows(BusinessException.class, () -> {
            try{
                myBookmark.deleted(handler);
            } catch (BusinessException e){
                assertEquals(MyBookmarkExceptionType.ALREADY_DELETE_BOOKMARK, e.getBasicExceptionType());
                throw e;
            }
        });
    }
}