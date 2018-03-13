package com.amosannn.service;

import com.amosannn.model.Collection;
import java.util.List;
import java.util.Map;

public interface CollectionService {

  Integer addCollection(Collection collection, Integer userId);

  Map<String, Object> getCollectionContent(Integer collectionId, Integer userId);

  List<Collection> listCreatingCollection(Integer userId);

  Boolean collectAnswer(Integer collectionId, Integer answerId);

  Boolean followCollection(Integer userId, Integer collectionId);

  Boolean unfollowCollection(Integer userId, Integer collectionId);

}
