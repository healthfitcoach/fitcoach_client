package com.fitcoach.client.model.notice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notice")
public class Notice {

  @Id
  @Column(name = "notice_id")
  private String noticeId;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @Column(name = "category")
  private String category;

  @Column(name = "write_date")
  private LocalDate writeDate;

  @Column(name = "attachment")
  private String attachment;

  @Column(name = "read_by_members")  // DB에는 쉼표 구분 문자열로 저장
  private String readByMembersRaw;

  @Transient  // Java 편의용 List — DB 저장 시 readByMembersRaw 사용
  private List<String> readByMembers;

  public Notice() {  // JPA 필수 no-arg 생성자
    this.readByMembers = new ArrayList<>();
  }

  public Notice(String noticeId, String title, String content, String category,
      LocalDate writeDate, String attachment) {
    this.noticeId = noticeId;
    this.title = title;
    this.content = content;
    this.category = category;
    this.writeDate = writeDate;
    this.attachment = attachment;
    this.readByMembersRaw = null;
    this.readByMembers = new ArrayList<>();
  }

  public boolean init() { return true; }

  public void listAll() {}

  public void getDetail() {}

  public void markAsRead(String memberId) {
    if (!readByMembers.contains(memberId)) {
      readByMembers.add(memberId);
    }
  }

  public boolean isReadBy(String memberId) {
    return readByMembers.contains(memberId);
  }

  // Getters
  public String getNoticeId() { return noticeId; }
  public String getTitle() { return title; }
  public String getContent() { return content; }
  public String getCategory() { return category; }
  public LocalDate getWriteDate() { return writeDate; }
  public String getAttachment() { return attachment; }
  public String getReadByMembersRaw() { return readByMembersRaw; }
  public void setReadByMembersRaw(String raw) { this.readByMembersRaw = raw; }
  public List<String> getReadByMembers() { return readByMembers; }
}
