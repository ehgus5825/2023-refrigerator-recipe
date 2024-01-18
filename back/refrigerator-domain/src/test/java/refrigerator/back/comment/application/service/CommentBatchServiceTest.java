package refrigerator.back.comment.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.comment.application.domain.entity.Comment;
import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;
import refrigerator.back.comment.application.domain.entity.CommentRecord;
import refrigerator.back.comment.application.port.batch.DeleteCommentBatchPort;
import refrigerator.back.comment.application.port.batch.FindCommentHeartPeopleForDeletePort;
import refrigerator.back.comment.application.port.batch.FindDeletedCommentPort;
import refrigerator.back.global.common.RandomUUID;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentBatchServiceTest {

    @InjectMocks CommentBatchService commentBatchService;
    @Mock DeleteCommentBatchPort deleteCommentBatchPort;
    @Mock FindDeletedCommentPort findDeletedCommentPort;
    @Mock FindCommentHeartPeopleForDeletePort findCommentHeartPeopleForDeletePort;

    @Test
    @DisplayName("댓글 삭제 배치 테스트")
    void deleteCommentTest(){

        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        Comment comment = Comment.builder()
                .recipeId(1L)
                .writerId("email123@gmail.com")
                .commentRecord(new CommentRecord(now, true, false))
                .content("aaaaaa")
                .build();

        RandomUUID uuid = () -> "12345678";
        CommentHeartPeople commentHeartPeople = new CommentHeartPeople(
                uuid.getUUID().substring(0, 8), comment.getCommentId(), "email123@gmail.com");

        List<CommentHeartPeople> commentHeartPeopleList = new ArrayList<>();
        commentHeartPeopleList.add(commentHeartPeople);

        given(findDeletedCommentPort.findDeletedComment())
                .willReturn(List.of(1L));

        given(findCommentHeartPeopleForDeletePort.findByCommentID(1L))
                .willReturn(commentHeartPeopleList);

        willDoNothing().given(deleteCommentBatchPort).deleteCommentHeartPeople(commentHeartPeople);

        given(deleteCommentBatchPort.deleteCommentHeart())
                .willReturn(1L);

        given(deleteCommentBatchPort.deleteComment())
                .willReturn(1L);

        commentBatchService.deleteComment();

        verify(findDeletedCommentPort, times(1)).findDeletedComment();
        verify(findCommentHeartPeopleForDeletePort, times(1)).findByCommentID(1L);
        verify(deleteCommentBatchPort, times(1)).deleteCommentHeartPeople(commentHeartPeople);
        verify(deleteCommentBatchPort, times(1)).deleteCommentHeart();
        verify(deleteCommentBatchPort, times(1)).deleteComment();
    }
}