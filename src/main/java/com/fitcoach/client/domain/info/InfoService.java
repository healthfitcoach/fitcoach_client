package com.fitcoach.client.domain.info;

import com.fitcoach.client.domain.info.repository.ApparatusRepository;
import com.fitcoach.client.domain.info.repository.ExerciseMethodRepository;
import com.fitcoach.client.domain.info.repository.NoticeRepository;
import com.fitcoach.client.global.exception.CustomException;
import com.fitcoach.client.global.exception.ErrorCode;
import com.fitcoach.client.model.equipment.Apparatus;
import com.fitcoach.client.model.equipment.ExerciseMethod;
import com.fitcoach.client.model.notice.Notice;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InfoService {

  private final ApparatusRepository apparatusRepository;
  private final ExerciseMethodRepository exerciseMethodRepository;
  private final NoticeRepository noticeRepository;

  public InfoService(ApparatusRepository apparatusRepository,
      ExerciseMethodRepository exerciseMethodRepository,
      NoticeRepository noticeRepository) {
    this.apparatusRepository = apparatusRepository;
    this.exerciseMethodRepository = exerciseMethodRepository;
    this.noticeRepository = noticeRepository;
  }

  @Transactional(readOnly = true)
  public List<Apparatus> getAllApparatus() {
    return apparatusRepository.findAll();
  }

  @Transactional(readOnly = true)
  public List<Apparatus> searchApparatus(String keyword) {
    return apparatusRepository.findByNameContainingOrCategoryContaining(keyword, keyword);
  }

  @Transactional(readOnly = true)
  public Apparatus getApparatusById(Long apparatusId) {
    return apparatusRepository.findById(apparatusId)
        .orElseThrow(() -> new CustomException(ErrorCode.APPARATUS_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public List<ExerciseMethod> getExerciseMethodsByEquipmentId(String equipmentId) {
    return exerciseMethodRepository.findByEquipmentId(equipmentId);
  }

  @Transactional(readOnly = true)
  public List<Notice> getAllNotices() {
    return noticeRepository.findAllByOrderByCreatedDateDesc();
  }

  @Transactional(readOnly = true)
  public Notice getNoticeById(Long noticeId) {
    return noticeRepository.findById(noticeId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
  }
}
