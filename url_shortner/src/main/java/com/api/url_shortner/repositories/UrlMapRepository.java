package com.api.url_shortner.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.api.url_shortner.collections.UrlMap;

public interface UrlMapRepository extends MongoRepository<UrlMap, String>{
    Optional<UrlMap>  findByShortUrl(String shortUrl);
    Optional<UrlMap>  findByUrl(String url);
    boolean existsByUrl(String url);
    boolean existsByShortUrl(String shortUrl);
    
    public long count();
    public UrlMap findTopByOrderByNumberOfAccessesDesc();
    public UrlMap findTopByOrderByNumberOfCreationsRequestsDesc();
    public UrlMap findTopByOrderByCreationDateDesc();
    public UrlMap findTopByOrderByLastAccessDateDesc();
    public UrlMap findTopByOrderByNumberOfAccessesAsc();
    public UrlMap findTopByOrderByCreationDateAsc();
    public UrlMap findTopByOrderByLastAccessDateAsc();
}
