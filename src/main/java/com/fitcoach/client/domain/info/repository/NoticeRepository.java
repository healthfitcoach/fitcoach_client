package com.fitcoach.client.domain.info.repository;

import com.fitcoach.client.model.notice.Notice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  List<Notice> findAllByOrderByCreatedDateDesc();
}
