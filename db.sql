create schema hdv;

use hdv;

CREATE TABLE `class` (
  `id` int(12) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `code` varchar(64) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `class`
--

INSERT INTO `class` (`id`, `name`, `code`, `created_at`, `updated_at`) VALUES
(1, 'CNTT11-01', '6D1', '2023-03-21 18:42:28', '2023-03-23 02:36:06'),
(2, 'CNTT11-02', '6D2', '2023-03-21 18:42:28', '2023-03-23 02:36:06'),
(3, 'CNTT11-03', '6D3', '2023-03-21 18:42:28', '2023-03-23 02:36:06'),
(4, 'CNTT11-04', '7D1', '2023-03-21 18:42:28', '2023-03-23 02:36:06'),
(5, 'CNTT13-05', '7D2', '2023-03-21 18:42:28', '2023-03-23 02:36:06'),
(6, 'CNTT13-06', '7D3', '2023-03-21 18:42:28', '2023-03-23 02:36:06'),
(7, 'CNTT13-07', '8D1', '2023-03-21 18:42:28', '2023-03-23 02:36:06'),
(8, 'CNTT13-02', '6D', '2023-03-21 18:42:28', '2023-03-23 02:36:06'),
(9, 'CNTT13-01', '6B', '2023-03-21 18:42:11', '2023-03-23 02:35:51'),
(10, 'Lớp 6C', '6C', '2023-03-21 18:42:20', '2023-03-21 18:42:20');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `classrooms`
--

CREATE TABLE `classrooms` (
  `id` int(12) NOT NULL,
  `name` varchar(64) NOT NULL,
  `status` int(1) NOT NULL DEFAULT 0,
  `location` varchar(512) DEFAULT NULL,
  `image` varchar(256) DEFAULT NULL,
  `start_time` varchar(64) DEFAULT NULL,
  `end_time` varchar(64) DEFAULT NULL,
  `owner` int(12) DEFAULT NULL,
  `note` varchar(521) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `classrooms`
--

INSERT INTO `classrooms` (`id`, `name`, `status`, `location`, `image`, `start_time`, `end_time`, `owner`, `note`, `created_at`, `updated_at`) VALUES
(1, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 1, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(2, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 2, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(3, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', NULL, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(4, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', NULL, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(5, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', NULL, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(6, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 3, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(7, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 4, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(8, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 5, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(9, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 6, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(10, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 1, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(11, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 2, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(12, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 3, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(13, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 1, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(14, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 3, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(15, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 2, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(16, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 1, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(17, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 5, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(18, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', NULL, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(19, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 1, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(20, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 8, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(21, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 8, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(22, 'Phòng 702', 1, 'Tầng 7 toà H1', '/classRooms/641c133147afd.png', '08:00:00', '17:00:00', 8, 'Phòng vi tính', '2023-03-22 20:58:36', '2023-03-26 19:24:11'),
(23, 'Phòng 701', 0, 'Tầng 7 toà H1', '/classRooms/641c0ef4dc217.png', '00:00:00', '23:59:59', NULL, 'Phòng thí nghiệm sinh học', '2023-03-22 20:58:36', '2023-04-01 01:15:47'),
(24, 'Phòng 704', 1, 'Tầng 7 toà H2', '/classRooms/641c133147afd.png', '13:00:00', '17:00:00', 8, 'Phòng trưng bày', '2023-03-22 20:58:36', '2023-04-01 01:14:45');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2014_10_12_000000_create_users_table', 1),
(2, '2014_10_12_100000_create_password_resets_table', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `password_resets`
--

CREATE TABLE `password_resets` (
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `students`
--

CREATE TABLE `students` (
  `id` int(12) NOT NULL,
  `name` varchar(256) NOT NULL,
  `birthday` text NOT NULL,
  `email` varchar(256) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `student_code` varchar(256) NOT NULL,
  `class` int(12) NOT NULL,
  `image` varchar(256),
  `gender` int(1) NOT NULL DEFAULT 0,
  `ability_to_borrow` int(1) DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `students`
--

INSERT INTO `students` (`id`, `name`, `birthday`, `email`, `phone`, `student_code`, `class`, `image`, `gender`, `ability_to_borrow`, `created_at`, `updated_at`) VALUES
(1, 'Trần Văn A', '17/01/2001', 'linhnguyen1@gmail.com', '0963852741', 'HS0010', 8, '/students/641bd64a6a6b4.jpg', 2, 1, '2023-03-22 21:31:32', '2023-03-22 21:31:32'),
(2, 'Trần Văn B', '17/01/2001', 'linhnguyen2@gmail.com', '0963852742', 'HS0021', 8, '/students/641bd64a6a6b4.jpg', 2, 1, '2023-03-22 21:31:32', '2023-03-22 21:31:32'),
(3, 'Trần Văn C', '17/01/2001', 'linhnguyen3@gmail.com', '0963852743', 'HS0031', 8, '/students/641bd64a6a6b4.jpg', 2, 1, '2023-03-22 21:31:32', '2023-03-22 21:31:32'),
(4, 'Trần Văn D', '17/01/2001', 'linhnguyen4@gmail.com', '0963852744', 'HS004', 8, '/students/641bd64a6a6b4.jpg', 2, 1, '2023-03-22 21:31:32', '2023-03-22 21:31:32'),
(5, 'Trần Văn E', '17/01/2001', 'linhnguyen5@gmail.com', '0963852745', 'HS005', 8, '/students/641bd64a6a6b4.jpg', 2, 1, '2023-03-22 21:31:32', '2023-03-22 21:31:32'),
(6, 'Trần Văn F', '17/01/2001', 'linhnguyen6@gmail.com', '0963852746', 'HS006', 8, '/students/641bd64a6a6b4.jpg', 2, 1, '2023-03-22 21:31:32', '2023-03-22 21:31:32'),
(7, 'Trần Thị Thu Thuỷ', '17/01/2001', 'thuthuy@gmail.com', '0963852747', 'HS001', 8, '/students/641bd62406d24.jpg', 2, 1, '2023-03-22 21:31:32', '2023-03-22 21:31:32'),
(8, 'Nguyễn Thị Linh', '12/07/2001', 'linhnguyen@gmail.com', '0963852749', 'HS002', 9, '/students/641bd64a6a6b4.jpg', 2, 1, '2023-03-22 21:32:10', '2023-03-22 21:32:10'),
(9, 'Bùi Văn An', '01/10/2001', 'anbui@gmail.com', '0963852711', 'HS003', 8, '/students/641bd66fa84ad.jpg', 1, 1, '2023-03-22 21:32:47', '2023-03-22 21:32:47');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(12) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` int(1) NOT NULL DEFAULT 0,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `role`, `email_verified_at`, `password`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'A', 'linhnguyen1@gmail.com', 0, NULL, '$2y$10$7DHUaZ8OVSfuSLHpVwoKue1nmEMIYZr/idho/vvoj0qIXvvq5b4e6', NULL, '2023-03-22 21:38:06', '2023-03-22 21:38:06'),
(2, 'B', 'linhnguyen2@gmail.com', 0, NULL, '$2y$10$7DHUaZ8OVSfuSLHpVwoKue1nmEMIYZr/idho/vvoj0qIXvvq5b4e6', NULL, '2023-03-22 21:38:06', '2023-03-22 21:38:06'),
(4, 'C', 'linhnguyen3@gmail.com', 0, NULL, '$2y$10$7DHUaZ8OVSfuSLHpVwoKue1nmEMIYZr/idho/vvoj0qIXvvq5b4e6', NULL, '2023-03-22 21:38:06', '2023-03-22 21:38:06'),
(6, 'D', 'linhnguyen4@gmail.com', 0, NULL, '$2y$10$7DHUaZ8OVSfuSLHpVwoKue1nmEMIYZr/idho/vvoj0qIXvvq5b4e6', NULL, '2023-03-22 21:38:06', '2023-03-22 21:38:06'),
(7, 'E', 'linhnguyen5@gmail.com', 0, NULL, '$2y$10$7DHUaZ8OVSfuSLHpVwoKue1nmEMIYZr/idho/vvoj0qIXvvq5b4e6', NULL, '2023-03-22 21:38:06', '2023-03-22 21:38:06'),
(8, 'F', 'linhnguyen6@gmail.com', 0, NULL, '$2y$10$7DHUaZ8OVSfuSLHpVwoKue1nmEMIYZr/idho/vvoj0qIXvvq5b4e6', NULL, '2023-03-22 21:38:06', '2023-03-22 21:38:06'),
(4, 'Linh Nguyễn', 'linhnguyen@gmail.com', 0, NULL, '$2y$10$7DHUaZ8OVSfuSLHpVwoKue1nmEMIYZr/idho/vvoj0qIXvvq5b4e6', NULL, '2023-03-22 21:38:06', '2023-03-22 21:38:06'),
(5, 'Quản trị viên', 'admin@gmail.com', 1, NULL, '$2y$10$7DHUaZ8OVSfuSLHpVwoKue1nmEMIYZr/idho/vvoj0qIXvvq5b4e6', NULL, '2023-03-26 18:42:27', '2023-03-26 18:42:27');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `classrooms`
--
ALTER TABLE `classrooms`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);


-- Chỉ mục cho bảng `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `class`
--
ALTER TABLE `class`
  MODIFY `id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `classrooms`
--
ALTER TABLE `classrooms`
  MODIFY `id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `students`
--
ALTER TABLE `students`
  MODIFY `id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;
