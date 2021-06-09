package org.donghyun.mreview.repository;

import org.donghyun.mreview.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    //MySQL은 이대로 하면 에러가 난다. select 뒤에 mi를 min(mi.inum)해준다. => 퍼포먼스가 떨어진다
    //그래서 group by m를 where mi.profile = true 해준다
    @Query("select m, mi, avg(coalesce( r.grade, 0 )), count(r) from Movie m " +
            "left join MovieImage mi on mi.movie = m " +
            "left join Review r on r.movie = m" +
            " where mi.profile = true group by m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r)" +
            "from Movie m " +
            "left join MovieImage mi on mi.movie = m " +
            "left join Review r on r.movie = m " +
            "where m.mno = :mno group by mi")

    List<Object[]> getMovieWithAll(@Param("mno") Long mno);


}
