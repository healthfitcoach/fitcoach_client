package com.fitcoach.client.model.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "program_reservation")
public class ProgramReservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "program_id")
  private Long programId;

  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "reservation_date")
  private LocalDate reservationDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private ReservationStatus status;

  public enum ReservationStatus {
    CONFIRMED, CANCELLED
  }

  public ProgramReservation() {}

  public ProgramReservation(Long programId, Long memberId,
      LocalDate reservationDate, ReservationStatus status) {
    this.programId = programId;
    this.memberId = memberId;
    this.reservationDate = reservationDate;
    this.status = status;
  }

  public Long getId() { return id; }
  public Long getProgramId() { return programId; }
  public Long getMemberId() { return memberId; }
  public LocalDate getReservationDate() { return reservationDate; }
  public ReservationStatus getStatus() { return status; }
  public void setStatus(ReservationStatus status) { this.status = status; }
}
