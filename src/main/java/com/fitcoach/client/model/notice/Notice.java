package com.fitcoach.client.model.notice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "notice")
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @Column(name = "category")
  private String category;

  @Column(name = "created_date")
  private LocalDate createdDate;

  @Column(name = "attachment")
  private String attachment;

  @Column(name = "author_employee_id")
  private Long authorEmployeeId;

  public Notice() {}

  public Notice(String title, String content, String category,
      LocalDate createdDate, String attachment, Long authorEmployeeId) {
    this.title = title;
    this.content = content;
    this.category = category;
    this.createdDate = createdDate;
    this.attachment = attachment;
    this.authorEmployeeId = authorEmployeeId;
  }

  public Long getId() { return id; }
  public String getTitle() { return title; }
  public String getContent() { return content; }
  public String getCategory() { return category; }
  public LocalDate getCreatedDate() { return createdDate; }
  public String getAttachment() { return attachment; }
  public Long getAuthorEmployeeId() { return authorEmployeeId; }
}
