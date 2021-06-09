package org.donghyun.mreview.repository;


import org.donghyun.mreview.entity.Member;
import org.donghyun.mreview.entity.Movie;
import org.donghyun.mreview.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //attributePaths 어떤 연관 관계를 가져올거야?
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    //ㄴ영화과 이미지를 물고 있을 경우 같이 양방향을 이용할때...PK위주의 설계에서 많이 쓰임, 1:1관계에선 Fetch가 유용
    List<Review> findByMovie(Movie movie);

    @Query("select r from Review r where r.movie.mno = :mno")
    List<Review> findWithMovie(Long mno);

    @Modifying //한꺼번에 삭제할 때
    @Query("delete from Review r where r.member = :member") //하나씩 삭제할 때
    void deleteByMember(Member member);


    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    Page<Review> findAll(Pageable pageable);
}
