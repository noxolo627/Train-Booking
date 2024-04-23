USE [master]
GO

USE [AllAboard]
GO

-- insert peak times 
MERGE [PeakTimes] AS TARGET 
USING (VALUES
	  ('06:00', '08:30', '0.2'),
	  ('15:00', '18:00', '0.2')
	)
  AS SOURCE ([start_time], [end_time], [price_increase_percentage])
  ON (TARGET.start_time = SOURCE.start_time AND TARGET.end_time = SOURCE.end_time AND TARGET.price_increase_percentage = SOURCE.price_increase_percentage)
  WHEN MATCHED THEN 
	UPDATE SET start_time = SOURCE.start_time, end_time = SOURCE.end_time, [price_increase_percentage] = SOURCE.price_increase_percentage, [date_updated] = CURRENT_TIMESTAMP
  WHEN NOT MATCHED BY TARGET THEN
	INSERT ([start_time], [end_time], [price_increase_percentage], [date_created], [date_updated])
	VALUES (SOURCE.start_time, SOURCE.end_time, SOURCE.price_increase_percentage, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

SELECT * FROM [PeakTimes]
GO


-- Inserting stations
MERGE [Station] AS TARGET 
USING (VALUES
	  ('Hatfield'),
	  ('Pretoria'),
	  ('Centurion'),
	  ('Midrand'),
	  ('Marlboro'),
	  ('Sandton'),
	  ('Rosebank'),
	  ('Park'),
	  ('Rhodesfield'),
	  ('O.R. Tambo')
	)
  AS SOURCE ([station_name])
  ON (TARGET.[station_name] = SOURCE.[station_name])
  WHEN MATCHED THEN 
	UPDATE SET [station_name] = SOURCE.station_name, [date_updated] = CURRENT_TIMESTAMP
  WHEN NOT MATCHED BY TARGET THEN
	INSERT ([station_name], [date_created], [date_updated])
	VALUES (SOURCE.station_name, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

SELECT * FROM [Station]
GO


-- Inserting trains
MERGE [Train] AS TARGET 
USING (VALUES
	  ('Toon Express', (SELECT [station_id] FROM [Station] WHERE [station_name] = 'Centurion'), (SELECT [station_id] FROM [Station] WHERE [station_name] = 'Park'), '2023-07-17', '09:00:00'),
	  ('Cartoonville Chugger', (SELECT [station_id] FROM [Station] WHERE [station_name] = 'Pretoria'), (SELECT [station_id] FROM [Station] WHERE [station_name] = 'Rhodesfield'), '2023-07-16', '10:30:00'),
	  ('Looney Line Express', (SELECT [station_id] FROM [Station] WHERE [station_name] = 'Centurion'), (SELECT [station_id] FROM [Station] WHERE [station_name] = 'Park'), '2023-07-25', '07:00:00')
	)
  AS SOURCE ([train_name], [source_station], [destination_station], [travel_date], [departure_time])
  ON (TARGET.train_name = SOURCE.train_name AND TARGET.source_station = SOURCE.source_station AND TARGET.destination_station = SOURCE.destination_station)
  WHEN MATCHED THEN 
	UPDATE SET [travel_date] = SOURCE.travel_date, [departure_time] = SOURCE.departure_time, [date_updated] = CURRENT_TIMESTAMP
  WHEN NOT MATCHED BY TARGET THEN
	INSERT ([train_name], [source_station], [destination_station], [travel_date], [departure_time], [date_created], [date_updated])
	VALUES (SOURCE.train_name, SOURCE.source_station, SOURCE.destination_station, SOURCE.travel_date, SOURCE.departure_time, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

SELECT * FROM [Train]
GO


-- inserting train peak times 
MERGE [TrainPeakTime] AS TARGET 
USING (VALUES
	  ((SELECT [train_id] FROM [Train] WHERE [train_name] = 'Toon Express'), NULL),
	  ((SELECT [train_id] FROM [Train] WHERE [train_name] = 'Cartoonville Chugger'), NULL),
	  ((SELECT [train_id] FROM [Train] WHERE [train_name] = 'Looney Line Express'), 1)
	)
  AS SOURCE ([train_id], [peak_time_id])
  ON (TARGET.train_id = SOURCE.train_id AND TARGET.peak_time_id = SOURCE.peak_time_id)
  WHEN MATCHED THEN 
	UPDATE SET [peak_time_id] = SOURCE.peak_time_id, [date_updated] = CURRENT_TIMESTAMP
  WHEN NOT MATCHED BY TARGET THEN
	INSERT ([train_id], [peak_time_id], [date_created], [date_updated])
	VALUES (SOURCE.train_id, SOURCE.peak_time_id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

SELECT * FROM [TrainPeakTime]
GO


-- Inserting train class types
MERGE [TrainClassType] AS TARGET 
USING (VALUES
	  ('Economy'),
	  ('Business'),
	  ('Sleeper')
	)
  AS SOURCE ([class_type_name])
  ON (TARGET.class_type_name = SOURCE.class_type_name)
  WHEN MATCHED THEN 
	UPDATE SET class_type_name = SOURCE.class_type_name, [date_updated] = CURRENT_TIMESTAMP
  WHEN NOT MATCHED BY TARGET THEN
	INSERT ([class_type_name], [date_created], [date_updated])
	VALUES (SOURCE.class_type_name, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

SELECT * FROM [TrainClassType]
GO



-- Inserting train classes
MERGE [TrainClass] AS TARGET 
USING (VALUES
		((SELECT [class_type_id] FROM [TrainClassType] WHERE [class_type_name] = 'Economy'), 6, 100.30, (SELECT [train_id] FROM [Train] WHERE [train_name] = 'Toon Express')),
		((SELECT [class_type_id] FROM [TrainClassType] WHERE [class_type_name] = 'Business'), 6, 200.00, (SELECT [train_id] FROM [Train] WHERE [train_name] = 'Toon Express')),
		((SELECT [class_type_id] FROM [TrainClassType] WHERE [class_type_name] = 'Sleeper'), 6, 150.00, (SELECT [train_id] FROM [Train] WHERE [train_name] = 'Toon Express')),
		((SELECT [class_type_id] FROM [TrainClassType] WHERE [class_type_name] = 'Economy'), 10, 100.30, (SELECT [train_id] FROM [Train] WHERE [train_name] = 'Cartoonville Chugger')),
		((SELECT [class_type_id] FROM [TrainClassType] WHERE [class_type_name] = 'Business'), 10, 200.00, (SELECT [train_id] FROM [Train] WHERE [train_name] = 'Cartoonville Chugger')),
		((SELECT [class_type_id] FROM [TrainClassType] WHERE [class_type_name] = 'Sleeper'), 10, 150.00, (SELECT [train_id] FROM [Train] WHERE [train_name] = 'Cartoonville Chugger')),
		((SELECT [class_type_id] FROM [TrainClassType] WHERE [class_type_name] = 'Economy'), 30, 100.30, (SELECT [train_id] FROM [Train] WHERE [train_name] = 'Looney Line Express')),
		((SELECT [class_type_id] FROM [TrainClassType] WHERE [class_type_name] = 'Business'), 30, 200.00, (SELECT [train_id] FROM [Train] WHERE [train_name] = 'Looney Line Express')),
		((SELECT [class_type_id] FROM [TrainClassType] WHERE [class_type_name] = 'Sleeper'), 30, 150.00, (SELECT [train_id] FROM [Train] WHERE [train_name] = 'Looney Line Express'))
	)
  AS SOURCE ([class_type_id], [capacity], [base_price],  [train_id])
  ON (TARGET.class_type_id = SOURCE.class_type_id AND TARGET.train_id = SOURCE.train_id)
  WHEN MATCHED THEN 
	UPDATE SET [capacity] = SOURCE.capacity, [base_price] = SOURCE.base_price, [date_updated] = CURRENT_TIMESTAMP
  WHEN NOT MATCHED BY TARGET THEN
	INSERT ([class_type_id], [capacity], [base_price], [train_id], [date_created], [date_updated])
	VALUES (SOURCE.class_type_id, SOURCE.capacity, SOURCE.base_price, SOURCE.train_id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

SELECT * FROM [TrainClass]
GO


-- Inserting seat types
MERGE [SeatType] AS TARGET 
USING (VALUES
		('Window Seat'),
		('Aisle Seat'),
		('Middle Seat')
	)
  AS SOURCE ([seat_type_name])
  ON (TARGET.seat_type_name = SOURCE.seat_type_name)
  WHEN MATCHED THEN 
	UPDATE SET seat_type_name = SOURCE.seat_type_name, [date_updated] = CURRENT_TIMESTAMP
  WHEN NOT MATCHED BY TARGET THEN
	INSERT ([seat_type_name], [date_created], [date_updated])
	VALUES (SOURCE.seat_type_name, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

SELECT * FROM [SeatType]
GO


-- Inserting seats 
EXEC [AddSeats] @train_id = 1
EXEC [AddSeats] @train_id = 2
EXEC [AddSeats] @train_id = 3

SELECT * FROM [Seat]
GO 


-- insert admins 
MERGE [Admin] AS TARGET 
USING (VALUES
	  ('raaga@bbd.co.za'),
	  ('tlholo@bbd.co.za'),
	  ('lehlohonolo@bbd.co.za'),
	  ('noxolo@bbd.co.za'),
	  ('joaquim@bbd.co.za')
	)
  AS SOURCE ([email])
  ON (TARGET.email = SOURCE.email)
  WHEN MATCHED THEN 
	UPDATE SET email = SOURCE.email, [date_updated] = CURRENT_TIMESTAMP
  WHEN NOT MATCHED BY TARGET THEN
	INSERT ([email], [date_created], [date_updated])
	VALUES (SOURCE.email, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

SELECT * FROM [Admin]
GO


-- insert Booking and Passengers 
DECLARE @train_id INT = (SELECT [train_id] FROM [Train] WHERE [train_name] = 'Cartoonville Chugger');

DECLARE @booking_id INT;

INSERT INTO Booking (booking_date, [train_id], ticket_price, [user_email], [date_created], [date_updated])
VALUES (GETDATE(), @train_id, 100.00, 'peter_@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 

SET @booking_id = SCOPE_IDENTITY();

-- Inserting passenger
DECLARE @seat_id_1 INT = (SELECT seat_id FROM Seat WHERE seat_number = 'B3' AND [train_id] = @train_id); 
DECLARE @seat_id_2 INT = (SELECT seat_id FROM Seat WHERE seat_number = 'B4' AND [train_id] = @train_id); 

INSERT INTO Passenger (booking_id, seat_id, passenger_name, age, [date_created], [date_updated]) VALUES 
	(@booking_id, @seat_id_1, 'John', 42, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(@booking_id, @seat_id_2, 'Mary', 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 

UPDATE Seat SET is_booked = 1, seat_price = 50.50 WHERE seat_id = @seat_id_1;
UPDATE Seat SET is_booked = 1, seat_price = 49.50 WHERE seat_id = @seat_id_2;

SELECT * FROM [Booking]
SELECT * FROM [Passenger]
SELECT * FROM [Seat]
GO
