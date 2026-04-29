package distbuted_programming.domain.notice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Notice {

  private String noticeId;
  private String title;
  private String content;
  private String category;
  private LocalDate createdDate;
  private String attachment;
  private boolean isRead;

  private static final List<Notice> notices = new ArrayList<>();

  public Notice() {}

  public Notice(String noticeId, String title, String content, String category,
      LocalDate createdDate, String attachment) {
    this.noticeId = noticeId;
    this.title = title;
    this.content = content;
    this.category = category;
    this.createdDate = createdDate;
    this.attachment = attachment;
    this.isRead = false;
  }

  public List<Notice> getList() {
    return notices;
  }

  public Notice getDetail(String noticeId) {
    for (Notice n : notices) {
      if (n.noticeId.equals(noticeId)) {
        return n;
      }
    }
    return null;
  }

  public void markAsRead(String noticeId) {
    for (Notice n : notices) {
      if (n.noticeId.equals(noticeId)) {
        n.isRead = true;
        return;
      }
    }
  }

  public String getNoticeId() { return noticeId; }
  public String getTitle() { return title; }
  public String getContent() { return content; }
  public String getCategory() { return category; }
  public LocalDate getCreatedDate() { return createdDate; }
  public String getAttachment() { return attachment; }
  public boolean isRead() { return isRead; }

  public static List<Notice> getAll() { return notices; }
  public static void add(Notice n) { notices.add(n); }

  @Override
  public String toString() {
    return "[" + category + "] " + title + " (" + createdDate + ")"
        + (isRead ? " [읽음]" : " [안읽음]");
  }
}
