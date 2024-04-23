package io.swagger.repository;

import io.swagger.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "SELECT DISTINCT " +
            "B.booking_id AS 'booking_id'" +
            ", B.train_id AS 'train_id'" +
            ", T.train_name AS 'train_name' " +
            ", S1.station_name AS 'source_station'" +
            ", S2.station_name AS 'destination_station' " +
            ", T.travel_date AS 'travel_date' " +
            ", T.departure_time AS 'departure_time' " +
            ", B.booking_date AS 'booking_date' " +
            ", B.ticket_price AS 'ticket_price' " +
            ", B.user_email AS 'user_email' " +
            ", TCT.class_type_name AS 'train_class' " +
            //", P.passenger_id AS 'passenger_id' " +
            //", P.passenger_name AS 'passenger_name' " +
            //", P.age AS 'passenger_age' " +
            //", S.seat_number AS  " +
            "FROM [Booking] as B " +
            "JOIN [Train] AS T ON T.train_id = B.train_id " +
            "JOIN [Station] AS S1 ON T.source_station = S1.station_id " +
            "JOIN [Station] AS S2 ON T.destination_station = S2.station_id " +
            "LEFT JOIN Passenger P ON P.booking_id = B.booking_id " +
            "LEFT JOIN Seat AS S ON P.seat_id = S.seat_id " +
            "LEFT JOIN TrainClass AS TC ON S.class_id = TC.class_id " +
            "LEFT JOIN TrainClassType AS TCT ON TC.class_type_id = TCT.class_type_id " +
            "WHERE B.booking_id = :bookingId", nativeQuery = true)
    Optional<Booking> findBookingByBookingId(
            @Param("bookingId") Integer bookingId);

    boolean existsByTrainId(Integer trainId);

    @Query(value = "SELECT DISTINCT " +
            "B.booking_id AS 'booking_id'" +
            ", B.train_id AS 'train_id'" +
            ", T.train_name AS 'train_name' " +
            ", S1.station_name AS 'source_station'" +
            ", S2.station_name AS 'destination_station' " +
            ", T.travel_date AS 'travel_date' " +
            ", T.departure_time AS 'departure_time' " +
            ", B.booking_date AS 'booking_date' " +
            ", B.ticket_price AS 'ticket_price' " +
            ", B.user_email AS 'user_email' " +
            ", TCT.class_type_name AS 'train_class' " +
            //", P.passenger_id AS 'passenger_id' " +
            //", P.passenger_name AS 'passenger_name' " +
            //", P.age AS 'passenger_age' " +
            //", S.seat_number AS  " +
            "FROM [Booking] as B " +
            "JOIN [Train] AS T ON T.train_id = B.train_id " +
            "JOIN [Station] AS S1 ON T.source_station = S1.station_id " +
            "JOIN [Station] AS S2 ON T.destination_station = S2.station_id " +
            "LEFT JOIN Passenger P ON P.booking_id = B.booking_id " +
            "LEFT JOIN Seat AS S ON P.seat_id = S.seat_id " +
            "LEFT JOIN TrainClass AS TC ON S.class_id = TC.class_id " +
            "LEFT JOIN TrainClassType AS TCT ON TC.class_type_id = TCT.class_type_id " +
            "WHERE B.user_email = :userEmail", nativeQuery = true)
    Optional<List<Booking>> findByUserEmail(
            @Param("userEmail") String user_email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Booking (booking_date,train_id, ticket_price,user_email, date_created, date_updated) " +
            "VALUES (:bookingDate, :trainId, :ticketPrice, :userEmail, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)", nativeQuery = true)
    void insertBooking(
            @Param("bookingDate") Date bookingDate,
            @Param("trainId") Integer trainId,
            @Param("ticketPrice") BigDecimal ticketPrice,
            @Param("userEmail") String userEmail);

    @Query(value = "SELECT MAX(booking_id) FROM Booking", nativeQuery = true)
    Integer findMaxBookingId();
}