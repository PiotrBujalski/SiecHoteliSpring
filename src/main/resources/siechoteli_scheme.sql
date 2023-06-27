-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Cze 26, 2023 at 11:40 PM
-- Wersja serwera: 10.4.28-MariaDB
-- Wersja PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `siechoteli`
--


--
-- Procedury
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_expired_reservations` ()   DELETE FROM reservations WHERE end_date < NOW();

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `hotel`
--

CREATE TABLE `hotel` (
  `hotelid` int(11) NOT NULL,
  `city` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hotel`
--

INSERT INTO `hotel` (`hotelid`, `city`, `name`) VALUES
(1, 'Tokyo', 'Hotel A'),
(2, 'Berlin', 'Hotel B'),
(3, 'Paris', 'Hotel C'),
(4, 'Rome', 'Hotel D');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `hotel_seq`
--

CREATE TABLE `hotel_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hotel_seq`
--

INSERT INTO `hotel_seq` (`next_val`) VALUES
(5);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reservations`
--

CREATE TABLE `reservations` (
  `reservationid` int(11) NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `roomid` int(11) NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `userid` int(11) NOT NULL,
  `hotelid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Wyzwalacze `reservations`
--

CREATE TRIGGER `tr_rezerwacje` AFTER DELETE ON `reservations` FOR EACH ROW INSERT INTO `Reservations_history` (id, start_date, end_date, userID, hotelID, roomID) VALUES (OLD.reservationid ,OLD.start_date, OLD.end_date, OLD.userid, OLD.hotelid, OLD.roomid);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reservations_history`
--

CREATE TABLE `reservations_history` (
  `id` int(11) NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `userID` int(11) NOT NULL,
  `hotelID` int(11) NOT NULL,
  `roomID` int(11) NOT NULL,
  `reservationid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservations_history`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reservations_seq`
--

CREATE TABLE `reservations_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservations_seq`
--

INSERT INTO `reservations_seq` (`next_val`) VALUES
(204);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `room`
--

CREATE TABLE `room` (
  `roomid` int(11) NOT NULL,
  `availability` bit(1) NOT NULL,
  `price` double NOT NULL,
  `type` varchar(45) NOT NULL,
  `hotelid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`roomid`, `availability`, `price`, `type`, `hotelid`) VALUES
(1, b'0', 100, 'trzygwiazdkowy', 2),
(2, b'1', 80, 'standard', 3),
(3, b'1', 150, 'deluxe', 1),
(4, b'1', 150, 'trzygwiazdkowy', 2),
(5, b'0', 150, 'standard', 2),
(6, b'1', 250, 'deluxe', 4);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `room_seq`
--

CREATE TABLE `room_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room_seq`
--

INSERT INTO `room_seq` (`next_val`) VALUES
(101);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `login`, `password`, `role`) VALUES
(1, 'admin', '$2a$10$5EW9eWesuoieMy563Oan/eOAq6YftaQwBfkx1G4yLF4OR8mW7sPeS', 'ADMIN'),
(2, 'user', '$2a$10$nAZbuSLo7yMuoq5R2ynPoO.pTeTBsEX.dx5gEjF4zfuR5.5YE425q', 'ADMIN'),
(3, 'user123', '$2a$10$Xd/zbDPljEFua1wloA7iuOEY6E4sQ8CvzWg7XPble.qyT.U3Wm5By', 'ADMIN');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users_seq`
--

CREATE TABLE `users_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users_seq`
--

INSERT INTO `users_seq` (`next_val`) VALUES
(4);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`hotelid`);

--
-- Indeksy dla tabeli `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`reservationid`),
  ADD KEY `FKauhl5rm94sjecp0xgubilq54m` (`hotelid`);

--
-- Indeksy dla tabeli `reservations_history`
--
ALTER TABLE `reservations_history`
  ADD KEY `FK29uk7dlocyj3pqyeytn0okuo6` (`hotelID`);

--
-- Indeksy dla tabeli `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`roomid`),
  ADD KEY `FK7bt2oc7b3h1cqba9crblkx1c4` (`hotelid`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `FKauhl5rm94sjecp0xgubilq54m` FOREIGN KEY (`hotelid`) REFERENCES `hotel` (`hotelid`);

--
-- Constraints for table `reservations_history`
--
ALTER TABLE `reservations_history`
  ADD CONSTRAINT `FK29uk7dlocyj3pqyeytn0okuo6` FOREIGN KEY (`hotelID`) REFERENCES `hotel` (`hotelid`);

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FK7bt2oc7b3h1cqba9crblkx1c4` FOREIGN KEY (`hotelid`) REFERENCES `hotel` (`hotelid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
