package com.spring.insta.repository;

import com.spring.insta.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    void deleteAllByIdIn(List<Long> ids);

    List<Tag> findByNameContaining(String name);

    @Query("select count(t.name), t.name from Tag t where t.name like %:name% group by t.name")
    List findByTagName(@Param("name") String name);
}
