-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 17, 2019 at 10:34 AM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db`
--

-- --------------------------------------------------------

--
-- Table structure for table `light`
--

CREATE TABLE `light` (
  `date` datetime NOT NULL DEFAULT current_timestamp(),
  `lightstatus` text NOT NULL,
  `intensity` double NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `light`
--

INSERT INTO `light` (`date`, `lightstatus`, `intensity`, `id`) VALUES
('2019-11-13 00:00:00', 'On', 10.5, 1),
('2019-11-13 20:21:29', 'Off', 100.51, 2),
('2019-11-13 20:28:11', 'On', 50, 3),
('2019-11-14 20:21:21', 'ON', 16, 5),
('2019-11-14 23:21:21', 'Off', 70, 6),
('2019-11-15 23:21:21', 'Off', 70, 7),
('2019-11-16 23:21:21', 'Off', 20, 8),
('2019-11-17 23:21:21', 'Off', 30, 9),
('2019-11-18 23:21:21', 'Off', 50, 10),
('2019-11-19 23:21:21', 'Off', 60, 11),
('2019-11-20 23:21:21', 'Off', 40, 12),
('2019-11-14 00:00:00', 'On', 10.2, 13),
('2019-11-14 00:00:00', 'On', 10.2, 14),
('2019-11-14 17:27:46', 'on', 60, 15),
('2019-11-14 17:27:50', 'on', 60, 16);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `light`
--
ALTER TABLE `light`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `light`
--
ALTER TABLE `light`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
