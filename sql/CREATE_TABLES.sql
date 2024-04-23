USE [master]
GO

DROP DATABASE IF EXISTS [AllAboard]
GO

CREATE DATABASE [AllAboard]
GO

USE [AllAboard]
GO

CREATE TABLE [Admin] (
  [admin_id]			INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [email]				VARCHAR(255) NOT NULL,
  [created_by]			VARCHAR(255) DEFAULT 'SYSTEM',
  [date_created]		DATETIME NOT NULL,
  [date_updated]		DATETIME NOT NULL
);

CREATE TABLE [Station] (
  [station_id]			INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [station_name]		VARCHAR(255) NOT NULL,
  [created_by]			VARCHAR(255) DEFAULT 'SYSTEM',
  [date_created]		DATETIME NOT NULL,
  [date_updated]		DATETIME NOT NULL
);

CREATE TABLE [Train] (
  [train_id]			INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [train_name]			VARCHAR(255) NOT NULL,
  [source_station]		INT NOT NULL,
  [destination_station] INT NOT NULL,
  [travel_date]			DATE NOT NULL,
  [departure_time]		TIME NOT NULL,
  [created_by]			VARCHAR(255) DEFAULT 'SYSTEM',
  [date_created]		DATETIME NOT NULL,
  [date_updated]		DATETIME NOT NULL,
  FOREIGN KEY ([source_station]) REFERENCES [Station]([station_id]),
  FOREIGN KEY ([destination_station]) REFERENCES [Station]([station_id])
);

CREATE TABLE [PeakTimes] (
  [peak_time_id]				INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [start_time]					TIME NOT NULL,
  [end_time]					TIME NOT NULL,
  [price_increase_percentage]	FLOAT NOT NULL,
  [created_by]					VARCHAR(255) DEFAULT 'SYSTEM',
  [date_created]				DATETIME NOT NULL,
  [date_updated]				DATETIME NOT NULL
)

CREATE TABLE [TrainPeakTime] (
  [train_peak_time_id]			INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [train_id]					INT NOT NULL,
  [peak_time_id]				INT DEFAULT NULL NULL,
  [date_created]				DATETIME NOT NULL,
  [date_updated]				DATETIME NOT NULL,
  FOREIGN KEY ([train_id]) REFERENCES [Train]([train_id])
)

CREATE TABLE [TrainClassType] (
  [class_type_id]	INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [class_type_name] VARCHAR(255) NOT NULL,
  [created_by]		VARCHAR(255) DEFAULT 'SYSTEM',
  [date_created]	DATETIME NOT NULL,
  [date_updated]	DATETIME NOT NULL
);

CREATE TABLE [TrainClass] (
  [class_id]		INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [train_id]		INT NOT NULL,
  [class_type_id]	INT NOT NULL,
  [capacity]		INT NOT NULL,
  [base_price]		DECIMAL(19,2) NOT NULL,
  [date_created]	DATETIME NOT NULL,
  [date_updated]	DATETIME NOT NULL,
  FOREIGN KEY ([train_id]) REFERENCES [Train]([train_id]),
  FOREIGN KEY ([class_type_id]) REFERENCES TrainClassType ([class_type_id])
);

CREATE TABLE [SeatType] (
  [seat_type_id]	INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [seat_type_name]	VARCHAR(255) NOT NULL,
  [created_by]		VARCHAR(255) DEFAULT 'SYSTEM',
  [date_created]	DATETIME NOT NULL,
  [date_updated]	DATETIME NOT NULL
);

CREATE TABLE [Seat] (
  [seat_id]			INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [train_id]		INT NOT NULL,
  [class_id]		INT NOT NULL,
  [seat_type_id]	INT NOT NULL,
  [seat_number]		VARCHAR(255) NOT NULL,
  [is_booked]		BIT DEFAULT 0 NOT NULL,
  [seat_price]		DECIMAL(19,2) NULL,
  [date_created]	DATETIME NOT NULL,
  [date_updated]	DATETIME NOT NULL,
  FOREIGN KEY ([train_id]) REFERENCES Train([train_id]),
  FOREIGN KEY ([class_id]) REFERENCES TrainClass([class_id]),
  FOREIGN KEY ([seat_type_id]) REFERENCES SeatType([seat_type_id])
);

CREATE TABLE [Booking] (
  [booking_id]			INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [train_id]			INT NOT NULL,
  [user_email]			VARCHAR(255) NOT NULL,
  [booking_date]		DATETIME NOT NULL,
  [ticket_price]		DECIMAL(19,2) NOT NULL,
  [date_created]		DATETIME NOT NULL,
  [date_updated]		DATETIME NOT NULL,
  FOREIGN KEY ([train_id]) REFERENCES Train([train_id]),
);

CREATE TABLE [Passenger] (
  [passenger_id]		INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [booking_id]			INT NOT NULL,
  [seat_id]				INT NOT NULL,
  [passenger_name]		VARCHAR(255) NOT NULL,
  [age]					INT NOT NULL,
  [date_created]		DATETIME NOT NULL,
  [date_updated]		DATETIME NOT NULL,
  FOREIGN KEY ([booking_id]) REFERENCES Booking([booking_id]),
  FOREIGN KEY ([seat_id]) REFERENCES Seat([seat_id])
);

DROP PROCEDURE IF EXISTS [AddSeats];
GO

CREATE PROCEDURE [AddSeats] (
		@train_id INT
	)
AS
BEGIN
	DECLARE @CounterClass INT = 1;
	WHILE (@CounterClass <= (SELECT COUNT([class_type_id]) FROM [TrainClassType]))
	BEGIN 
		DECLARE @CounterSeat INT = 1;
		DECLARE @Counter INT = 1;
		WHILE (@Counter <= (SELECT [capacity] FROM [TrainClass] WHERE [train_id] = @train_id AND [class_type_id] = @CounterClass))
		BEGIN
			DECLARE @SeatCharNumber VARCHAR(255) = (SELECT SUBSTRING([class_type_name], 1, 1) FROM [TrainClassType] WHERE [class_type_id] = @CounterClass) + CONVERT(VARCHAR, @Counter);
			INSERT INTO [Seat] ([train_id], [class_id], [seat_type_id], [seat_number], [date_created], [date_updated])
				VALUES (@train_id, @CounterClass, @CounterSeat, @SeatCharNumber, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
			SET @Counter = @Counter + 1

			IF (@CounterSeat >= (SELECT COUNT([seat_type_id]) FROM [SeatType]))
				SET @CounterSeat = 1
			ELSE 
				SET @CounterSeat = @CounterSeat + 1
		END
		SET @CounterClass = @CounterClass + 1
	END 
END
GO

