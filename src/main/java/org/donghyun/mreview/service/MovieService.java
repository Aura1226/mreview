package org.donghyun.mreview.service;


import org.donghyun.mreview.dto.PageRequestDTO;
import org.donghyun.mreview.dto.PageResultDTO;
import org.donghyun.mreview.entity.Movie;
import org.donghyun.mreview.entity.MovieImage;
import org.donghyun.mreview.dto.MovieDTO;
import org.donghyun.mreview.dto.MovieImageDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    Long register(MovieDTO movieDTO); //서비스 계층은  DTO나 Entity클래스로 설계?

    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    MovieDTO getMovie(Long mno);

    default Map<String, Object> dtoToEntity(MovieDTO movieDTO){

        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();

        entityMap.put("movie", movie);

        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        if(imageDTOList != null && imageDTOList.size() > 0 ) { //MovieImageDTO 처리

            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO ->{

                MovieImage movieImage = MovieImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(movie)
                        .build();
                return movieImage;
            }).collect(Collectors.toList());

            //첫번째 이미지 = 대표이미지
            movieImageList.get(0).confirmProfile(true);

            entityMap.put("imgList", movieImageList);
        }

        return entityMap;
    }



    default MovieDTO entitiesToDTO(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt){
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder().imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());



        return movieDTO;

    }

}
