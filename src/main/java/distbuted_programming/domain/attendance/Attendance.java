package distbuted_programming.domain.attendance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Attendance {

  private String attendanceId;
  private String memberId;
  private String attendanceDateTime;
  private String authMethod;

  private static final List<Attendance> attendanceList = new ArrayList<>();
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public Attendance() {}

  public Attendance(String attendanceId, String memberId,
      String attendanceDateTime, String authMethod) {
    this.attendanceId = attendanceId;
    this.memberId = memberId;
    this.attendanceDateTime = attendanceDateTime;
    this.authMethod = authMethod;
  }

  public Attendance checkIn(String memberId) {
    Attendance a = new Attendance(
        UUID.randomUUID().toString(),
        memberId,
        LocalDateTime.now().format(FORMATTER),
        "QR");
    attendanceList.add(a);
    return a;
  }

  public Attendance get() {
    return this;
  }

  public String getAttendanceId() { return attendanceId; }
  public String getMemberId() { return memberId; }
  public String getAttendanceDateTime() { return attendanceDateTime; }
  public String getAuthMethod() { return authMethod; }

  public static List<Attendance> getAll() { return attendanceList; }

  public static Attendance findLatestByMemberId(String memberId) {
    Attendance latest = null;
    for (Attendance a : attendanceList) {
      if (a.memberId.equals(memberId)) {
        latest = a;
      }
    }
    return latest;
  }

  public static List<Attendance> findAllByMemberId(String memberId) {
    List<Attendance> result = new ArrayList<>();
    for (Attendance a : attendanceList) {
      if (a.memberId.equals(memberId)) {
        result.add(a);
      }
    }
    return result;
  }

  public static boolean hasTodayAttendance(String memberId) {
    String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    for (Attendance a : attendanceList) {
      if (a.memberId.equals(memberId) && a.attendanceDateTime.startsWith(today)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "출석일시: " + attendanceDateTime + " | 인증방법: " + authMethod;
  }
}
