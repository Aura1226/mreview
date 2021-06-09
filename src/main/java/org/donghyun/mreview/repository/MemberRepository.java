package org.donghyun.mreview.repository;

import org.donghyun.mreview.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
