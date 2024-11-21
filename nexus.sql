-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 21, 2024 at 09:28 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nexus`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'Business network'),
(2, 'Hub Switch'),
(3, 'Network card');

-- --------------------------------------------------------

--
-- Table structure for table `distributors`
--

CREATE TABLE `distributors` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `distributors`
--

INSERT INTO `distributors` (`id`, `address`, `name`, `phone`) VALUES
(1, 'Địa chỉ 1', 'Nhà phân phối 1', '0841234567'),
(2, 'Địa chỉ 2', 'Nhà phân phối 2', '0842345678'),
(3, 'Địa chỉ 3', 'Nhà phân phối 3', '0843456789'),
(4, 'Địa chỉ 4', 'Nhà phân phối 4', '0844567890'),
(5, 'Địa chỉ 5', 'Nhà phân phối 5', '0845678901');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `order_date` datetime(6) NOT NULL,
  `status` enum('CANCELLED','DELIVERED','PENDING','PROCESSING','SHIPPED') NOT NULL,
  `total_amount` decimal(38,2) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `order_date`, `status`, `total_amount`, `user_id`) VALUES
(1, '2024-11-16 20:46:38.000000', 'PENDING', '220.00', 24),
(2, '2024-11-16 21:10:39.000000', 'PENDING', '0.00', 21),
(3, '2024-11-17 21:57:24.000000', 'PENDING', '110.00', 21),
(4, '2024-11-20 22:58:06.000000', 'PENDING', '451.50', 4),
(5, '2024-11-20 23:01:58.000000', 'PENDING', '150.50', 4),
(6, '2024-11-20 23:02:21.000000', 'PENDING', '150.50', 4),
(7, '2024-11-20 23:10:17.000000', 'CANCELLED', '150.50', 4),
(8, '2024-11-21 15:20:59.000000', 'PENDING', '301.00', 3);

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`id`, `price`, `quantity`, `order_id`, `product_id`) VALUES
(1, '110.00', 2, 1, 1),
(2, '110.00', 1, 3, 1),
(3, '150.50', 3, 4, 2),
(4, '150.50', 1, 5, 2),
(5, '150.50', 1, 6, 2),
(6, '150.50', 1, 7, 2),
(7, '150.50', 2, 8, 2);

-- --------------------------------------------------------

--
-- Table structure for table `permissions`
--

CREATE TABLE `permissions` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `detail` longtext DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `distributor_id` bigint(20) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `description`, `detail`, `name`, `price`, `distributor_id`, `status`, `image`) VALUES
(1, 'Mô tả sản phẩm 1', 'Chi tiết sản phẩm 1', 'Sản phẩm 1', '110.00', 1, b'0', 'Screenshot_49.png'),
(2, 'Mô tả sản phẩm 2', 'Chi tiết sản phẩm 2', 'Sản phẩm 2', '150.50', 1, b'0', NULL),
(3, 'Mô tả sản phẩm 3', 'Chi tiết sản phẩm 3', 'Sản phẩm 3', '200.00', 2, b'0', NULL),
(4, 'Mô tả sản phẩm 4', 'Chi tiết sản phẩm 4', 'Sản phẩm 4', '250.75', 2, b'0', NULL),
(5, 'Mô tả sản phẩm 5', 'Chi tiết sản phẩm 5', 'Sản phẩm 5', '300.00', 3, b'0', NULL),
(6, 'Mô tả sản phẩm 6', 'Chi tiết sản phẩm 6', 'Sản phẩm 6', '350.25', 3, b'0', NULL),
(7, 'Mô tả sản phẩm 7', 'Chi tiết sản phẩm 7', 'Sản phẩm 7', '400.00', 4, b'0', NULL),
(8, 'Mô tả sản phẩm 8', 'Chi tiết sản phẩm 8', 'Sản phẩm 8', '450.50', 4, b'0', NULL),
(9, 'Mô tả sản phẩm 9', 'Chi tiết sản phẩm 9', 'Sản phẩm 9', '500.00', 5, b'0', NULL),
(10, 'Mô tả sản phẩm 10', 'Chi tiết sản phẩm 10', 'Sản phẩm 10', '550.75', 5, b'0', NULL),
(12, 'ádsa', 'ádasdas', 'Product1', '12.00', 2, b'0', 'Screenshot_41.png'),
(13, 'Original box, full accessories from the manufacturer Router Power supply Ethernet cable Quick installation guide 24-month genuine warranty at CellphoneS, 1 for 1 exchange within 15 days if there is a hardware error from the manufacturer', 'Equipped with dual band: speed 867 Mbps - 5 GHz and 300 Mbps - 2.4 GHz MU-MIMO technology connects multiple devices and reduces latency 4 antennas and Beamforming technology increase coverage Parental control function for children\'s network access Supports Router, Access Point and Range Extender modes', 'TP-Link Archer C54 Dual Band AC1200 Wifi Router', '20.00', 1, b'1', 'h_nh_5.png');

-- --------------------------------------------------------

--
-- Table structure for table `product_categories`
--

CREATE TABLE `product_categories` (
  `product_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `product_categories`
--

INSERT INTO `product_categories` (`product_id`, `category_id`) VALUES
(12, 1),
(12, 3),
(1, 1),
(1, 2),
(1, 3),
(13, 3);

-- --------------------------------------------------------

--
-- Table structure for table `product_images`
--

CREATE TABLE `product_images` (
  `id` bigint(20) NOT NULL,
  `image_path` varchar(255) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `id` bigint(20) NOT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `created_at` date NOT NULL,
  `rating` int(11) NOT NULL,
  `updated_at` date DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_CLIENT');

-- --------------------------------------------------------

--
-- Table structure for table `spring_session`
--

CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `spring_session`
--

INSERT INTO `spring_session` (`PRIMARY_ID`, `SESSION_ID`, `CREATION_TIME`, `LAST_ACCESS_TIME`, `MAX_INACTIVE_INTERVAL`, `EXPIRY_TIME`, `PRINCIPAL_NAME`) VALUES
('ed4479fb-0723-405e-95dc-3eb2898c89e8', 'd37c3002-142d-48c4-b52b-c5453b82a8e0', 1732177172540, 1732177417453, 43200, 1732220617453, 'admin@domain.com');

-- --------------------------------------------------------

--
-- Table structure for table `spring_session_attributes`
--

CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `spring_session_attributes`
--

INSERT INTO `spring_session_attributes` (`SESSION_PRIMARY_ID`, `ATTRIBUTE_NAME`, `ATTRIBUTE_BYTES`) VALUES
('ed4479fb-0723-405e-95dc-3eb2898c89e8', 'scopedTarget.cartService', 0xaced0005737200266e617375786a6176612e7765626e657875732e736572766963652e436172745365727669636500000000000000010200014c00046361727474001f4c6e617375786a6176612f7765626e657875732f6d6f64656c2f436172743b78707372001d6e617375786a6176612e7765626e657875732e6d6f64656c2e4361727400000000000000010200014c00056974656d737400104c6a6176612f7574696c2f4c6973743b7870737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078),
('ed4479fb-0723-405e-95dc-3eb2898c89e8', 'SPRING_SECURITY_CONTEXT', 0xaced00057372003d6f72672e737072696e676672616d65776f726b2e73656375726974792e636f72652e636f6e746578742e5365637572697479436f6e74657874496d706c000000000000026c0200014c000e61757468656e7469636174696f6e7400324c6f72672f737072696e676672616d65776f726b2f73656375726974792f636f72652f41757468656e7469636174696f6e3b78707372004f6f72672e737072696e676672616d65776f726b2e73656375726974792e61757468656e7469636174696f6e2e557365726e616d6550617373776f726441757468656e7469636174696f6e546f6b656e000000000000026c0200024c000b63726564656e7469616c737400124c6a6176612f6c616e672f4f626a6563743b4c00097072696e636970616c71007e0004787200476f72672e737072696e676672616d65776f726b2e73656375726974792e61757468656e7469636174696f6e2e416273747261637441757468656e7469636174696f6e546f6b656ed3aa287e6e47640e0200035a000d61757468656e746963617465644c000b617574686f7269746965737400164c6a6176612f7574696c2f436f6c6c656374696f6e3b4c000764657461696c7371007e0004787001737200266a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c654c697374fc0f2531b5ec8e100200014c00046c6973747400104c6a6176612f7574696c2f4c6973743b7872002c6a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c65436f6c6c656374696f6e19420080cb5ef71e0200014c00016371007e00067870737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000007704000000007871007e000d737200486f72672e737072696e676672616d65776f726b2e73656375726974792e7765622e61757468656e7469636174696f6e2e57656241757468656e7469636174696f6e44657461696c73000000000000026c0200024c000d72656d6f7465416464726573737400124c6a6176612f6c616e672f537472696e673b4c000973657373696f6e496471007e000f787074000f303a303a303a303a303a303a303a317070737200326f72672e737072696e676672616d65776f726b2e73656375726974792e636f72652e7573657264657461696c732e55736572000000000000026c0200075a00116163636f756e744e6f6e457870697265645a00106163636f756e744e6f6e4c6f636b65645a001563726564656e7469616c734e6f6e457870697265645a0007656e61626c65644c000b617574686f72697469657374000f4c6a6176612f7574696c2f5365743b4c000870617373776f726471007e000f4c0008757365726e616d6571007e000f787001010101737200256a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c65536574801d92d18f9b80550200007871007e000a737200116a6176612e7574696c2e54726565536574dd98509395ed875b0300007870737200466f72672e737072696e676672616d65776f726b2e73656375726974792e636f72652e7573657264657461696c732e5573657224417574686f72697479436f6d70617261746f72000000000000026c0200007870770400000000787074001061646d696e40646f6d61696e2e636f6d);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `datecreate` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `address`, `datecreate`, `email`, `full_name`, `note`, `password`, `phone`, `image`, `role`) VALUES
(3, 'adnew', NULL, 'admin@domain.com', 'Đặng Văn Đạt', '603', '$2a$10$.kOKI/ae56NvO/xhRPJGK.JXx2OCbnMFhLMXsMYPszqsWVGV9/C1y', '0862442297', NULL, NULL),
(4, 'đâs', NULL, 'dangdat@gmail.com', 'Đặng Văn Đạt', 'ádasda', '$2a$10$NaRMtLRhebdJPcWOq1tWmOQnO6xbsy39HzB.CiSlIj/drgdkwAQye', '0862442297', NULL, NULL),
(5, NULL, NULL, 'contact@holaweb.net', 'Đặng Văn Đạt', NULL, '$2a$10$KIbKpJbbD46XYLNgl/dZeO1HlFGGbfGc9rrlZB/syXRut0Wn7ZHy.', NULL, NULL, NULL),
(9, NULL, NULL, 'datclient@gmail.com', 'Đặng Văn Đạt', NULL, '$2a$10$i/Ym0lozxkActuQHzz3DeO5cdfsNtROXFNdcXWHhlGGP2txf9L1ru', NULL, NULL, NULL),
(10, NULL, '2024-10-30 15:34:36.000000', 'checkuser@gmail.com', 'Đặng Văn Đạt', NULL, '$2a$10$V8RfmvhUbsfwRDdA3bQLPeSZziDf4lCUGIeP9YEdn9HH41.MfjH1S', NULL, NULL, NULL),
(11, 'sáda', '2024-10-31 09:59:34.000000', 'dattest@gmail.com', 'Đặng Văn Đạt', '123213', '$2a$10$A306jea2NsoEFoSVisTBgO31Xld7AXMJU7z430mgcg2klXkd7RqYi', '0862442297', 'Screenshot_53.png', NULL),
(15, 'Đông Mỹ Thanh Trì Hà Nội', '2024-11-03 19:17:08.000000', 'dang1@gmail.com', 'dat', NULL, '$2a$10$H.IT7h0XSzrPmBcEEfNjpeS8a7j44I0mMu1wVhpoBeY29jNcpu5cy', '0862442297', NULL, NULL),
(18, '1231', '2024-11-03 19:56:10.000000', 'admin1@domain.com', 'dat', '1231', '$2a$10$lQbq1YV7qG6OjhLgYmuGO.4wQvtN7O2VRcEkp5rLmQveI0kjw7Do2', '0862442297', 'Screenshot_47.png', NULL),
(19, 'sdadsadadas', '2024-11-04 08:44:32.000000', 'tesstss@gmail.com', 'dat', 'asdasdsada', '$2a$10$bMb/MpkcI89yNotqPRNqzOgwAwd.Up3DUVAGKTyHiY08aq5QaKHDa', '0862442297', 'Screenshot_49.png', NULL),
(20, '123213', '2024-11-04 08:47:52.000000', 'tesstss1@gmal.com', 'dat', 'sdds', '$2a$10$M9Ufcy/.TflBvgEekWlYVeL1qRlE75BXLYKZRAnZBdRQq3cCEX4Z6', '0862442297', 'Screenshot_48.png', NULL),
(21, 'đâs', '2024-11-04 08:51:37.000000', 'testss@gmail.com', 'dat', 'asda', '$2a$10$M.aVndTsMKCFhNEUBNKcsuEUODAfv9n.kuQtWVbHYppzCD.n3ybty', '0862442297', 'Screenshot_325.png', NULL),
(22, 'asdas', '2024-11-04 08:59:28.000000', 'dangtest@gmail.com', 'dat', 'zxc', '$2a$10$Jd/C6rqGkJrJxC4SpKIWfOBFS.vsWjx/jh1qAagSx83zLRM8PNDWW', '0862442297', 'Screenshot_47.png', NULL),
(24, 'Đông Mỹ Thanh Trì Hà Nội', '2024-11-16 20:46:38.000000', 'datclient@gmail.com', 'Đặng Văn Đạt', 'Created when ordered', '$2a$10$zqNzU0p3citcFOjOGD/tf.2JywJGA/YgHtdConyM98yj35WNu3kum', '0862442297', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_permissions`
--

CREATE TABLE `user_permissions` (
  `user_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(5, 1),
(9, 2),
(10, 2),
(11, 1),
(15, 2),
(18, 1),
(19, 1),
(20, 1),
(21, 1),
(22, 1),
(24, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `distributors`
--
ALTER TABLE `distributors`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  ADD KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`);

--
-- Indexes for table `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK19jivlhf28nbt9h47nkisw0di` (`distributor_id`);

--
-- Indexes for table `product_categories`
--
ALTER TABLE `product_categories`
  ADD KEY `FKd112rx0alycddsms029iifrih` (`category_id`),
  ADD KEY `FKlda9rad6s180ha3dl1ncsp8n7` (`product_id`);

--
-- Indexes for table `product_images`
--
ALTER TABLE `product_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqnq71xsohugpqwf3c9gxmsuy` (`product_id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpl51cejpw4gy5swfar8br9ngi` (`product_id`),
  ADD KEY `FKcgy7qjc1r99dp117y9en6lxye` (`user_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `spring_session`
--
ALTER TABLE `spring_session`
  ADD PRIMARY KEY (`PRIMARY_ID`),
  ADD UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  ADD KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  ADD KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`);

--
-- Indexes for table `spring_session_attributes`
--
ALTER TABLE `spring_session_attributes`
  ADD PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_permissions`
--
ALTER TABLE `user_permissions`
  ADD KEY `FKq4qlrabt4s0etm9tfkoqfuib1` (`permission_id`),
  ADD KEY `FKkowxl8b2bngrxd1gafh13005u` (`user_id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `FKt7e7djp752sqn6w22i6ocqy6q` (`role_id`),
  ADD KEY `FKj345gk1bovqvfame88rcx7yyx` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `distributors`
--
ALTER TABLE `distributors`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `permissions`
--
ALTER TABLE `permissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `product_images`
--
ALTER TABLE `product_images`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FK19jivlhf28nbt9h47nkisw0di` FOREIGN KEY (`distributor_id`) REFERENCES `distributors` (`id`);

--
-- Constraints for table `product_categories`
--
ALTER TABLE `product_categories`
  ADD CONSTRAINT `FKd112rx0alycddsms029iifrih` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `FKlda9rad6s180ha3dl1ncsp8n7` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `product_images`
--
ALTER TABLE `product_images`
  ADD CONSTRAINT `FKqnq71xsohugpqwf3c9gxmsuy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKpl51cejpw4gy5swfar8br9ngi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `spring_session_attributes`
--
ALTER TABLE `spring_session_attributes`
  ADD CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE;

--
-- Constraints for table `user_permissions`
--
ALTER TABLE `user_permissions`
  ADD CONSTRAINT `FKkowxl8b2bngrxd1gafh13005u` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKq4qlrabt4s0etm9tfkoqfuib1` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKt7e7djp752sqn6w22i6ocqy6q` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
