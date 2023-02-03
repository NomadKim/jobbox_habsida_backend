package com.example.jobbox.repository;

import com.example.jobbox.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT * from events WHERE date >= :startDate AND date <= :endDate AND user_id =:id", nativeQuery = true)
    List<Event> getEventsFromTo(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("id") Long id);

    @Query(value = "SELECT * from events WHERE id = :id", nativeQuery = true)
    Event findEventById(@Param("id") Long id);

    @Query(value = "SELECT SUM(hours_worked) from events WHERE date >= :startDate AND date <= :endDate AND user_id =:id", nativeQuery = true)
    Integer hoursWorked(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("id") Long id);

    @Query(value = "SELECT SUM(daily_sum) from events WHERE date >= :startDate AND date <= :endDate AND user_id = :id", nativeQuery = true)
    Float earned(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("id") Long id);

    @Query(value = "SELECT SUM(hours_worked) from events WHERE date >= :startDate AND date <= :endDate AND user_id =:id AND is_completed = :isCompleted", nativeQuery = true)
    Integer getHoursWorkedByCompleted(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("id") Long id, @Param("isCompleted") Boolean isCompleted);

    @Query(value = "SELECT SUM(daily_sum) from events WHERE date >= :startDate AND date <= :endDate AND user_id = :id AND is_completed = :isCompleted ", nativeQuery = true)
    Float getEarnedByCompleted(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("id") Long id, @Param("isCompleted") Boolean isCompleted);

    @Query(value = "SELECT * from events WHERE template_id = :templateId AND user_id = :userId AND date >= :startDate AND date <= :endDate AND user_id =:id", nativeQuery = true)
    List<Event> getEventsByTemplateAndUserAndDate(@Param("templateId") Long templateId, @Param("userId") Long userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT SUM(daily_sum) from events WHERE date >= :startDate AND date <= :endDate AND user_id = :userId AND template_id = :templateId", nativeQuery = true)
    Float earnedByTemplate(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Long userId, @Param("templateId") Long templateId);

    @Query(value = "SELECT SUM(daily_sum) from events WHERE date >= :startDate AND date <= :endDate AND user_id =:userId AND template_id = :templateId AND is_completed = true", nativeQuery = true)
    Float getEarnedByTemplate(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Long userId, @Param("templateId") Long templateId);

    @Query(value = "SELECT SUM(daily_sum) from events WHERE date >= :startDate AND date <= :endDate AND user_id =:userId AND template_id = :templateId AND is_paid = :isPaid AND is_completed = true", nativeQuery = true)
    Float getEarnedByTemplateAndPaid(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Long userId, @Param("templateId") Long templateId, @Param("isPaid") Boolean isPaid);
    @Transactional
    @Modifying
    @Query("DELETE FROM Event e WHERE e.id = :id")
    void deleteEventById(@Param("id") Long id);
}
