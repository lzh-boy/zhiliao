package com.amosannn.service.impl;

import com.amosannn.mapper.CommentDao;
import com.amosannn.mapper.UserDao;
import com.amosannn.model.AnswerComment;
import com.amosannn.model.QuestionComment;
import com.amosannn.model.User;
import com.amosannn.service.CommentService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  CommentDao commentDao;
  @Autowired
  UserDao userDao;

  /**
   * 评论回答
   * @param answerId
   * @param commentContent
   * @param userId
   * @return
   */
  @Override
  public AnswerComment commentAnswer(Integer answerId, String commentContent, Integer userId) {
    AnswerComment comment = new AnswerComment();
    comment.setAnswerId(answerId);
    comment.setAnswerCommentContent(commentContent);
    comment.setUserId(userId);
    comment.setCreateTime(System.currentTimeMillis());
    comment.setLikedCount(0);

    Integer successRowCount = commentDao.insertAnswerComment(comment);
//    comment.setAnswerCommentId(answerCommentId);

    User user = userDao.selectUserInfoByUserId(userId);
    comment.setUser(user);

    return comment;
  }

  /**
   * 回复回答下的评论
   * @param reqMap
   * @param userId
   * @return
   */
  @Override
  public AnswerComment replyAnswerComment(Map<String, Object> reqMap, Integer userId) {
    AnswerComment answerComment = new AnswerComment();
//    answerComment.setAnswerCommentId(Integer.parseInt(reqMap.get("answerCommentId") + ""));
    answerComment.setAnswerCommentContent(String.valueOf(reqMap.get("answerCommentContent")));
    answerComment.setAnswerId(Integer.parseInt(reqMap.get("answerId") + ""));
    answerComment.setAtUserId(Integer.parseInt(reqMap.get("atUserId") + ""));
    answerComment.setAtUserName(String.valueOf(reqMap.get("atUserName")));

    answerComment.setLikedCount(0);
    answerComment.setCreateTime(System.currentTimeMillis());
    answerComment.setUserId(userId);

    Integer successRowCount = commentDao.insertAnswerCommentReply(answerComment);
//    answerComment.setAnswerCommentId(answerCommentId);

    User user = userDao.selectUserInfoByUserId(userId);
    answerComment.setUser(user);

    return answerComment;
  }

  /**
   * 评论问题
   * @param questionId
   * @param commentContent
   * @param userId
   * @return
   */
  @Override
  public QuestionComment commentQuestion(Integer questionId, String commentContent, Integer userId) {
    QuestionComment comment = new QuestionComment();
    comment.setQuestionId(questionId);
    comment.setQuestionCommentContent(commentContent);
    comment.setUserId(userId);

    comment.setLikedCount(0);
    comment.setCreateTime(System.currentTimeMillis());

    commentDao.insertQuestionComment(comment);
    User user = userDao.selectUserInfoByUserId(userId);
    comment.setUser(user);

    return comment;
  }

  /**
   * 回复问题下的评论
   * @param reqMap
   * @param userId
   * @return
   */
  @Override
  public QuestionComment replyQuestionComment(Map<String, Object> reqMap, Integer userId) {
    QuestionComment questionComment = new QuestionComment();
    questionComment.setQuestionCommentContent(String.valueOf(reqMap.get("questionCommentContent")));
    questionComment.setQuestionId(Integer.parseInt(reqMap.get("questionId") + ""));
    questionComment.setAtUserId(Integer.parseInt(reqMap.get("atUserId") + ""));
    questionComment.setAtUserName(String.valueOf(reqMap.get("atUserName")));

    questionComment.setLikedCount(0);
    questionComment.setCreateTime(System.currentTimeMillis());
    questionComment.setUserId(userId);

    commentDao.insertQuestionCommentReply(questionComment);
    User user = userDao.selectUserInfoByUserId(userId);
    questionComment.setUser(user);

    return questionComment;
  }

}