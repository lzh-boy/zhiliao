package com.amosannn.web;

import com.amosannn.model.Answer;
import com.amosannn.service.AnswerService;
import com.amosannn.service.UserService;
import com.amosannn.util.RedisKey;
import com.amosannn.util.ResponseResult;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/zhiliao")
public class AnswerController {

  @Autowired
  private AnswerService answerService;
  @Autowired
  private UserService userService;

  /**
   * 回答问题
   * @param map
   * @param request
   * @return
   */
  @RequestMapping("/answer")
  public ResponseResult<Integer> answer(@RequestBody Map<String, Object> map,
      HttpServletRequest request) {
    Integer userId = userService.getUserIdFromRedis(request);
    Map<String, Object> answerMap = (Map<String, Object>) map.get("answer");

    Answer answer = new Answer();
    answer.setAnswerContent(String.valueOf(answerMap.get("answerContent")));
    answer.setUserId(Integer.parseInt(answerMap.get("userId") + ""));
    answer.setQuestionId(Integer.parseInt(answerMap.get("questionId") + ""));

    // 插入成功的行数
    Integer insertRowsCount = answerService.answer(answer, userId);
    return ResponseResult.createSuccessResult("回答成功！", answer.getAnswerId());
  }

  /**
   * 给某回答点赞
   */
  @RequestMapping("/likeAnswer")
  public ResponseResult<Boolean> likeAnswer(@RequestBody Map<String, Object> map,
      HttpServletRequest request) {
    Integer answerId = Integer.parseInt(map.get("answerId") + "");
    Integer userId = userService.getUserIdFromRedis(request);
    answerService.likeAnswer(answerId, userId);
    return ResponseResult.createSuccessResult("点赞成功",true);
  }

  /**
   * 取消给某回答的点赞
   */
  @RequestMapping("/unlikeAnswer")
  public ResponseResult<Boolean> unlikeAnswer(@RequestBody Map<String, Object> map,
      HttpServletRequest request) {
    Integer answerId = Integer.parseInt(map.get("answerId") + "");
    Integer userId = userService.getUserIdFromRedis(request);
    answerService.unlikeAnswer(answerId, userId);
    return ResponseResult.createSuccessResult("取消点赞成功",true);
  }

}
