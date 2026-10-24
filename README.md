<h1 align="center">🎬 🎟️ MOVIE TICKET AND SEAT MANAGEMENT 🍿 🎥</h1>

<h3 align="center">📋 Đặt vé xem phim và quản lý ghế theo suất chiếu</h3>

<div align="center">
  <img src="https://img.shields.io/badge/JAVA-OOP-E98B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java OOP">
  <img src="https://img.shields.io/badge/ARCHITECTURE-LAYERED-4A86C5?style=for-the-badge" alt="Architecture">
  <img src="https://img.shields.io/badge/STORAGE-FILE-2E8B57?style=for-the-badge&logo=files&logoColor=white" alt="Storage">
  <img src="https://img.shields.io/badge/FOCUS-POLYMORPHISM-D9534F?style=for-the-badge" alt="Focus">
  <br>
  <img src="https://img.shields.io/badge/JDK-8-5382A1?style=for-the-badge&logo=openjdk&logoColor=white" alt="JDK 8">
  <img src="https://img.shields.io/badge/IDE-NETBEANS_17-1B6AC6?style=for-the-badge&logo=apachenetbeanside&logoColor=white" alt="NetBeans 17">
  <img src="https://img.shields.io/badge/PRO192-FPT_UNIVERSITY-E8702A?style=for-the-badge" alt="FPT University">
</div>

<br>

> 🏫 **PRO192 – OOP with Java – FPT University** · Lớp SE21B · Học kỳ FA26
>
> 📚 **Chủ đề:** P03 – Đặt vé xem phim và quản lý ghế theo suất chiếu
>
> 📖 Tài liệu này mô tả bài toán, phạm vi, chức năng, quy tắc nghiệp vụ, thiết kế OOP (đa hình `Seat`), cách build và chạy chương trình console.

---


## 👥 Danh sách thành viên nhóm

| Vai trò | Họ và tên | MSSV | Vị trí | Nhiệm vụ |
|:-------:|-----------|:----:|:------:|----------|
| 👑 Leader | Nguyễn Hoàng Khang | `QE200039` | `TV2` | Kiểm tra danh sách ghế, báo giá |
| 🧑‍💻 Member | Nguyễn Phát Đạt | `QE210206` | `TV1` | Ghế, sơ đồ phòng, tra cứu suất |
| 🧑‍💻 Member | Cao Trần Duy Phương | `QE210239` | `TV3` | Tạo lần đặt, sinh mã vé, tra cứu vé |
| 🧑‍💻 Member | Nguyễn Thành Chí | `QE190166` | `TV4` | Hủy vé, hoàn tiền, thống kê |
| 🧑‍💻 Member | Phan Lý Trung | `QE210104` | `TV5` | Lưu/nạp file, khôi phục, menu |

## 📚 Mục lục

| # | Chủ đề |
|:---:|:---|
| 1 | 🌐 [Tổng quan hệ thống](#-tổng-quan-hệ-thống) |
| 2 | 🎭 [Actor của hệ thống](#-actor-của-hệ-thống) |
| 3 | 🔄 [Luồng nghiệp vụ chính](#-luồng-nghiệp-vụ-chính) |
| 4 | 📏 [Quy tắc nghiệp vụ](#-quy-tắc-nghiệp-vụ) |
| 5 | 🗺️ [Use Case Diagram](#️-use-case-diagram) |
| 6 | 🧩 [Class Diagram](#-class-diagram) |
| 7 | 🔗 [Quan hệ giữa các Class](#-quan-hệ-giữa-các-class) |
| 8 | 🎯 [Đa hình và Dynamic Dispatch](#-đa-hình-và-dynamic-dispatch) |
| 9 | 🗄️ [Tầng lưu trữ dữ liệu](#️-tầng-lưu-trữ-dữ-liệu) |
| 10 | 📦 [Dữ liệu mẫu](#-dữ-liệu-mẫu) |
| 11 | ✅ [Kịch bản nghiệm thu](#-kịch-bản-nghiệm-thu) |
| 12 | 🏗️ [Kiến trúc tổng thể](#️-kiến-trúc-tổng-thể) |
| 13 | 📁 [Cấu trúc thư mục và file dữ liệu](#-cấu-trúc-thư-mục-và-file-dữ-liệu) |
| 14 | 🚀 [Hướng dẫn build và chạy](#-hướng-dẫn-build-và-chạy) |
| 15 | 🧪 [Kiểm thử](#-kiểm-thử) |
| 16 | 🤖 [Minh bạch AI và giới hạn còn lại](#-minh-bạch-ai-và-giới-hạn-còn-lại) |
| 17 | 🏁 [Kết luận](#-kết-luận) |

## 🌐 Tổng quan hệ thống

> 🎯 **Mục tiêu:** Chương trình console giúp nhân viên một rạp phim nhỏ **xem suất chiếu, kiểm tra ghế trống, đặt vé và hủy vé**, đồng thời bảo đảm **không bán hai vé còn hiệu lực cho cùng một ghế trong cùng một suất chiếu**.

### 📌 Phạm vi hệ thống

| Hạng mục | ✅ Có trong hệ thống | 🚫 Ngoài phạm vi |
|:---|:---|:---|
| 🏢 **Rạp / phòng chiếu** | Một rạp, một phòng **4 hàng × 5 ghế** (A1–D5, 20 ghế) | Nhiều rạp, nhiều phòng |
| 💺 **Loại ghế** | **Standard** (hàng A, B, C) và **VIP** (hàng D) | Thêm loại ghế khác |
| 🎞️ **Suất chiếu** | Danh sách nhỏ suất chiếu mẫu (SC01, SC02) | Quản lý lịch chiếu động |
| 💰 **Giá vé** | Giá cố định theo loại ghế | Giá theo ngày lễ, giảm giá |
| 🖥️ **Giao diện** | Console (menu văn bản) | GUI, web |
| 🗄️ **Dữ liệu** | Lưu/nạp bằng file | Cơ sở dữ liệu |
| 🔐 **Người dùng** | Nhân viên quầy vé | Đăng nhập, thanh toán thật |

### ⚙️ Chức năng bắt buộc

| # | Chức năng | Mô tả chi tiết |
|:---:|:---|:---|
| 1 | 🎞️ **Tra cứu suất chiếu** | Liệt kê mã suất, tên phim, giờ chiếu, số ghế trống; chọn suất theo mã |
| 2 | 💺 **Sơ đồ ghế** | Hiển thị mã ghế, loại ghế, trạng thái trống/đã đặt theo từng suất, có chú thích ký hiệu |
| 3 | 🎟️ **Đặt vé** | Nhập tên người đặt + nhiều ghế, kiểm tra toàn bộ, báo giá, chỉ tạo vé khi xác nhận |
| 4 | 🔍 **Tra cứu vé** | Tìm theo mã vé: người đặt, mã lần đặt, suất, ghế, đơn giá lúc đặt, trạng thái |
| 5 | ❌ **Hủy vé** | Xác nhận hủy, giải phóng ghế đúng suất, hoàn tiền mô phỏng 100% |
| 6 | 📊 **Thống kê** | Vé còn hiệu lực, ghế trống, tổng tiền, tổng hoàn, số tiền còn lại |
| 7 | 💾 **Lưu và nạp file** | Giữ dữ liệu suất, ghế, đặt vé, trạng thái hủy qua các lần chạy |