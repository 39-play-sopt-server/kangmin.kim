package org.sopt.repository;

import org.sopt.domain.Member;
import org.sopt.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = "member")
    Page<Post> findAll(Pageable pageable);

    void deleteAllByMember(Member member);
}