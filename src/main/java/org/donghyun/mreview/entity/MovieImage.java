package org.donghyun.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
@Table(name = "gallery")
public class MovieImage { //Movie와 라이프 사이클이 같다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;

    private String imgName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY) //이거 하면 위에 @ToString에 exclude 해준다
    private Movie movie;

    private boolean profile;

    //첫번째째 사진 대표 이미지
    public void confirmProfile(boolean value){
        this.profile = value;
    }


}
