import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Notice {

    private String noticeId;
    private String title;
    private String content;
    private String category;
    private LocalDate writeDate;
    private String attachment;
    private List<String> readByMembers;

    public Notice(String noticeId, String title, String content, String category,
                  LocalDate writeDate, String attachment) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.writeDate = writeDate;
        this.attachment = attachment;
        this.readByMembers = new ArrayList<>();
    }

    public boolean init() {
        return true;
    }

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
    public List<String> getReadByMembers() { return readByMembers; }
}
