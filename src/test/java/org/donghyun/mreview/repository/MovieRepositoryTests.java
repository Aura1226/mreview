package org.donghyun.mreview.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.donghyun.mreview.entity.Movie;
import org.donghyun.mreview.entity.MovieImage;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieImageRepository imageRepository;

    @Test
    @Transactional
    @Commit //Test는 ...이 안되니까 Commit
    public void insertMovies(){

        //1부터 100번까지 forEach로 뽑아준다
        IntStream.rangeClosed(1, 100).forEach(i -> {

            Movie movie = Movie.builder().title("Movie" + i).build();

            //번호 추출
            movieRepository.save(movie); //한번에 처리하려면 번호가 있어야하니까 save해서 따준다.

            //최대 5개의 영화 이미지
            int count = (int)(Math.random() * 5) + 1;

            for (int j = 0; j < count; j++){

                //첫번째 이미지
                boolean profile = j == 0;

                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                                .movie(movie)
                        .imgName("test" + j + ".jpg")
                        .path("2021/04/26")
                        .profile(profile)
                        .build();

                imageRepository.save(movieImage);
            }
        });
    }

    @Test
    public void testList1(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);

        result.getContent().forEach(arr -> {log.info(Arrays.toString(arr));
        });
    }

    @Test
    public void testWithAll(){

        Long mno = 201L;

        List<Object[]> result = movieRepository.getMovieWithAll(mno);

        for (Object[] objects : result){
            log.info(Arrays.toString(objects));
        }//end for
    }
}
