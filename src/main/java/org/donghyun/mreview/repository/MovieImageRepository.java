package org.donghyun.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.donghyun.mreview.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {


}
