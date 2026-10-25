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

## 🎭 Actor của hệ thống

Hệ thống có **2 actor**: 1 actor chính trực tiếp thao tác và 1 actor phụ là nơi lưu trữ dữ liệu.

| Actor | Vai trò |
|:---|:---|
| 🧑‍💼 **Nhân viên quầy vé** | Thao tác mọi chức năng: xem suất, xem ghế, đặt vé, tra cứu và hủy vé |
| 💾 **File lưu trữ** | Nhận dữ liệu khi lưu và cung cấp dữ liệu khi nạp lại chương trình |

### 🧑‍💼 2.1. Nhân viên quầy vé

Nhân viên quầy vé là người dùng duy nhất của chương trình console, thay mặt khách hàng thực hiện việc đặt và hủy vé.

#### ⚙️ Chức năng

- Xem danh sách **Showtime** (mã suất, tên phim, giờ chiếu, số ghế trống).
- Xem sơ đồ **Seat** của một suất (trống / đã đặt).
- Nhập tên người đặt.
- Chọn nhiều ghế trong một lần đặt.
- Xem báo giá và xác nhận đặt vé.
- Tra cứu **Ticket** theo mã vé.
- Hủy **Ticket** theo mã vé.
- Xem thống kê theo suất chiếu.

#### 📏 Quy tắc

Nhân viên chỉ được hủy vé khi:

```text
Ticket.status = ACTIVE
```

> [!WARNING]
> Vé đã hủy **không thể hủy lần hai**. Hủy một vé cũ cũng không được giải phóng ghế của vé mới đã đặt lại cùng ghế đó.

> [!IMPORTANT]
> Khi đặt vé, hệ thống phải kiểm tra **toàn bộ** danh sách ghế trước khi tạo bất kỳ vé nào. Chỉ cần một ghế sai (không tồn tại, bị lặp, hoặc đã có vé còn hiệu lực) thì **từ chối cả lần đặt**.

### 💾 2.2. File lưu trữ

File lưu trữ là nơi giữ dữ liệu giữa các lần chạy chương trình.

#### ⚙️ Chức năng

- Cung cấp dữ liệu đã lưu khi khởi động.
- Nhận và ghi lại dữ liệu khi thoát.
- Lưu suất chiếu, loại ghế, Booking, Ticket.
- Lưu trạng thái hủy và đơn giá tại lúc đặt của từng vé.

#### 🔄 Thời điểm tương tác

| Thời điểm | Hành động |
|:---|:---|
| 🟢 Khởi động | Nạp dữ liệu; nếu chưa có file thì dùng dữ liệu mẫu |
| 🔴 Thoát | Ghi toàn bộ dữ liệu hiện tại |

> [!NOTE]
> Nếu file bị hỏng, chương trình phải báo lỗi rõ ràng và **không** thay bằng dữ liệu rỗng rồi ghi đè file cũ.

### 🔄 2.3. Trạng thái của Ticket

```text
(chưa có vé)
     ↓  đặt vé và xác nhận
   ACTIVE
     ↓  hủy vé
 CANCELLED
```

Trạng thái ghế được xác định theo cặp **mã suất + mã ghế**:

| Trạng thái ghế | Điều kiện |
|:---:|:---|
| 🟩 **Trống** | Không có vé `ACTIVE` nào giữ ghế đó trong suất đó |
| 🟥 **Đã đặt** | Có đúng một vé `ACTIVE` giữ ghế đó trong suất đó |

#### 💡 Ví dụ

Giả sử có:

```text
SC01: A1 trống, A2 trống, D1 trống
An đặt A1, D1 ở SC01 → xác nhận
```

Kết quả:

```text
V1 = A1, 70.000  (ACTIVE)
V2 = D1, 100.000 (ACTIVE)
Tổng = 170.000
SC01 còn 18 ghế
```

Nếu sau đó nhân viên đặt `A2` và `A1` ở SC01:

```text
A1 đang bị V1 giữ → từ chối CẢ lần đặt
A2 vẫn trống, không có vé nào được tạo
```

Nhưng A1 ở **SC02** vẫn đặt được, vì ghế được xét theo từng suất.

### 🏛️ 2.4. Quy tắc kiến trúc

Hai logic quan trọng:

```text
validateSeats()
cancelTicket()
```

> [!IMPORTANT]
> `validateSeats()` và `cancelTicket()` phải được xử lý ở **lớp nghiệp vụ (Service)**, không đặt trong Menu. Menu chỉ nhận dữ liệu nhập và hiển thị kết quả.

## 🔄 Luồng nghiệp vụ chính

Luồng tổng quát của hệ thống:

```text
Chọn suất → Xem sơ đồ ghế → Chọn nhiều ghế → Kiểm tra toàn bộ danh sách ghế
          → Xác nhận giá → Tạo Booking + Ticket → Tra cứu hoặc hủy vé → Lưu và nạp lại dữ liệu
```

### 🧭 3.1. Các nhánh trong luồng

| Điểm quyết định | Điều kiện | Kết quả |
|:---|:---:|:---|
| 🔶 **Kiểm tra toàn bộ danh sách ghế** | ✅ Hợp lệ | Chuyển sang bước **xác nhận giá** |
| 🔶 **Kiểm tra toàn bộ danh sách ghế** | ❌ Không hợp lệ | **Từ chối cả lần đặt**, không tạo vé nào |
| 🔶 **Xác nhận giá** | ✅ Xác nhận | **Tạo Booking + Ticket** |
| 🔶 **Xác nhận giá** | ❌ Không xác nhận | **Từ chối cả lần đặt**, không giữ chỗ |

> [!IMPORTANT]
> Việc đặt vé là **all-or-nothing**: kiểm tra xong toàn bộ danh sách mới tạo vé. Chỉ cần một ghế sai, hoặc nhân viên không xác nhận giá, thì không có vé nào được tạo.

### 🎟️ 3.2. Chi tiết từng bước

| Bước | Hành động | Kết quả |
|:---:|:---|:---|
| 1 | 🎞️ **Chọn suất** | Nhân viên nhập mã suất; hệ thống kiểm tra suất tồn tại |
| 2 | 💺 **Xem sơ đồ ghế** | Hiển thị ghế trống / đã đặt của đúng suất đó |
| 3 | ✍️ **Chọn nhiều ghế** | Nhập tên người đặt (không rỗng sau `trim()`) và danh sách ghế |
| 4 | 🔶 **Kiểm tra toàn bộ danh sách ghế** | Kiểm tra hết trước, chưa tạo bất kỳ vé nào |
| 5 | 💰 **Xác nhận giá** | Báo giá từng ghế và tổng tiền; nhân viên đồng ý hoặc từ chối |
| 6 | 🎟️ **Tạo Booking + Ticket** | Sinh mã không trùng, lưu **đơn giá lúc đặt** trên từng vé |
| 7 | 🔍 **Tra cứu hoặc hủy vé** | Tra theo mã vé, hoặc hủy vé còn hiệu lực |
| 8 | 💾 **Lưu và nạp lại dữ liệu** | Ghi file khi thoát, nạp lại khi mở chương trình |

#### ✅ Điều kiện danh sách ghế hợp lệ

- Có **ít nhất 1 ghế**.
- Mã ghế nằm trong sơ đồ (**A1–D5**); nhập chữ thường vẫn được và tự chuẩn hóa thành chữ hoa.
- **Không lặp** mã ghế trong cùng một lần đặt.
- Không có ghế nào đang bị vé `ACTIVE` giữ trong suất đó.

#### 💡 Ví dụ đặt vé thành công

```text
An đặt A1, D1 ở SC01 → xác nhận giá

Booking B1
  ├── V1: A1 (Standard) = 70.000
  └── V2: D1 (VIP)      = 100.000
Tổng = 170.000
SC01 còn 18 ghế
```

#### 💡 Ví dụ bị từ chối

```text
Nhân viên đặt A2 và A1 ở SC01 (A1 đang bị V1 giữ)
→ Từ chối CẢ lần đặt, A2 vẫn trống, không tạo vé nào
```

### 🔍 3.3. Tra cứu vé

| Bước | Hành động |
|:---:|:---|
| 1 | Nhân viên nhập **mã vé** |
| 2 | Hệ thống tìm vé trong danh sách |
| 3 | Hiển thị: người đặt, mã Booking, suất chiếu, ghế, **đơn giá lúc đặt**, trạng thái |

> [!NOTE]
> Vé đã hủy vẫn được giữ lại để tra cứu và hiển thị trạng thái `CANCELLED`.

### ❌ 3.4. Hủy vé

| Bước | Hành động | Kết quả |
|:---:|:---|:---|
| 1 | Nhập **mã vé** cần hủy | Kiểm tra vé tồn tại |
| 2 | Kiểm tra trạng thái | Chỉ hủy được khi `ACTIVE` |
| 3 | Nhân viên **xác nhận hủy** | Không xác nhận thì giữ nguyên |
| 4 | Vé chuyển sang `CANCELLED` | Chỉ hủy ghế trên vé đó |
| 5 | Giải phóng ghế **đúng suất** | Các vé khác cùng Booking giữ nguyên |
| 6 | Hoàn tiền mô phỏng **100% đơn giá đã lưu trên vé** | Không tính lại theo bảng giá hiện tại |

> [!WARNING]
> Hủy lần hai bị từ chối. Hủy một vé cũ không được giải phóng ghế của vé mới đã đặt lại cùng ghế đó.

```text
Hủy V1 (A1, SC01) → xác nhận
V1 = CANCELLED, hoàn 70.000
SC01 còn 19 ghế
Tổng gốc 170.000 | Tổng hoàn 70.000 | Còn lại 100.000
```

### 💾 3.5. Lưu và nạp lại dữ liệu

```text
Khởi động → Có file dữ liệu?  → Có: nạp lại toàn bộ
                              → Không: dùng dữ liệu mẫu (SC01, SC02)
Thoát     → Ghi toàn bộ suất, ghế, Booking, Ticket, trạng thái hủy ra file
```

> [!IMPORTANT]
> Sau khi lưu rồi mở lại, mọi trạng thái phải khôi phục đầy đủ: vé nào đã hủy, ghế nào đã đặt, đơn giá lúc đặt và các số tiền. Mã Booking và mã vé mới **không được trùng** với mã đã có.

## 📏 Quy tắc nghiệp vụ

Các quy tắc dưới đây được chia thành 5 nhóm. Mọi chức năng của chương trình đều phải tuân theo.

| Nhóm | Nội dung |
|:---|:---|
| 💺 **Ghế và giá vé** | Mã ghế, loại ghế, bảng giá |
| 🎞️ **Suất chiếu** | Trạng thái ghế theo từng suất |
| 🎟️ **Đặt vé** | Điều kiện hợp lệ, xác nhận, mã không trùng |
| ❌ **Hủy vé** | Điều kiện hủy, hoàn tiền mô phỏng |
| 💾 **Lưu trữ và tiền tệ** | Nạp/ghi file, kiểu dữ liệu tiền |

### 💺 4.1. Ghế và giá vé

| Loại ghế | Hàng | Mã ghế | Đơn giá |
|:---:|:---:|:---:|---:|
| 🟦 **Standard** | A, B, C | A1–C5 (15 ghế) | **70.000 đ** |
| 🟨 **VIP** | D | D1–D5 (5 ghế) | **100.000 đ** |

- Mã ghế có dạng **hàng + số**, từ **A1 đến D5** (4 hàng × 5 ghế = 20 ghế).
- Nhập chữ thường (ví dụ `a1`) vẫn được và được **chuẩn hóa thành chữ hoa**.
- Giá chỉ phụ thuộc loại ghế, **không** có thuế, phụ phí hay giảm giá.

### 🎞️ 4.2. Trạng thái ghế theo suất

> [!IMPORTANT]
> Trạng thái ghế được xác định theo cặp **mã suất + mã ghế**, không lưu cờ `booked` dùng chung cho cả phòng.

| Trường hợp | Kết quả |
|:---|:---|
| A1 ở **SC01** đã đặt | A1 ở **SC02** vẫn đặt được |
| Có đúng 1 vé `ACTIVE` giữ ghế trong suất | Ghế hiển thị **Đã đặt** |
| Không có vé `ACTIVE` nào giữ ghế trong suất | Ghế hiển thị **Trống** |

### 🎟️ 4.3. Quy tắc đặt vé

| # | Quy tắc |
|:---:|:---|
| 1 | Danh sách ghế phải có **ít nhất 1 ghế**. |
| 2 | Không được có mã ghế **lặp** trong cùng một lần đặt. |
| 3 | Mã ghế phải **nằm trong sơ đồ** (A1–D5). |
| 4 | Không được chọn ghế đang bị vé `ACTIVE` giữ trong suất đó. |
| 5 | Tên người đặt **không rỗng** sau khi `trim()`. |
| 6 | Phải **kiểm tra toàn bộ** danh sách trước khi tạo bất kỳ vé nào. |
| 7 | Nhân viên **không xác nhận** thì không tạo Booking, không tạo vé, không giữ chỗ. |
| 8 | Mã Booking và mã vé **không được trùng**, kể cả sau khi nạp file hoặc khi vé cũ đã hủy. |
| 9 | Mỗi vé lưu **đơn giá tại lúc đặt**; tra cứu, thống kê và hủy đều dùng giá đã lưu. |

> [!WARNING]
> Chỉ cần một ghế sai (không tồn tại, bị lặp hoặc đã có người giữ) thì **từ chối cả lần đặt**, không tạo vé một phần.

### ❌ 4.4. Quy tắc hủy vé

| # | Quy tắc |
|:---:|:---|
| 1 | Chỉ hủy được vé có `status = ACTIVE`. |
| 2 | Hủy lần hai bị **từ chối**. |
| 3 | Hủy theo mã vé chỉ hủy **ghế trên vé đó**; các vé khác trong cùng Booking giữ nguyên. |
| 4 | Hoàn tiền mô phỏng **100% đơn giá đã lưu trên vé**, không tính lại theo bảng giá hiện tại. |
| 5 | Vé đã hủy vẫn được **giữ lại** để tra cứu với trạng thái `CANCELLED`. |
| 6 | Hủy một vé cũ **không** giải phóng ghế của vé mới đã đặt lại cùng ghế đó. |

#### 💡 Ví dụ

```text
V1 = A1, SC01, 70.000 → đã hủy (CANCELLED)
Đặt lại A1 ở SC01 → tạo V4 (ACTIVE)
Thử hủy V1 lần nữa → TỪ CHỐI, A1 của V4 vẫn giữ nguyên
```

### 💾 4.5. Lưu trữ và tiền tệ

| # | Quy tắc |
|:---:|:---|
| 1 | Lần chạy đầu chưa có file thì dùng **dữ liệu mẫu**; nếu đã có dữ liệu thì **không** nạp mẫu đè lên. |
| 2 | File dữ liệu **hỏng** phải báo lỗi rõ ràng, **không** thay bằng dữ liệu rỗng rồi ghi đè file cũ. |
| 3 | Sau khi lưu rồi mở lại, mọi trạng thái phải **khôi phục đầy đủ**. |
| 4 | Tiền tính bằng kiểu **`long`**, đơn vị là **đồng**. |

> [!NOTE]
> Nhập sai (mã suất, mã vé không tồn tại; tên chỉ có khoảng trắng; chọn menu sai) thì chương trình **báo lỗi, không đổi dữ liệu và menu vẫn dùng tiếp**.

## 🗺️ Use Case Diagram

Hệ thống đặt vé xem phim có **1 actor chính** và **1 actor phụ**. Các use case được mô tả theo từng actor.

### 🎭 5.1. Actor

```text
Nhân viên quầy vé   (actor chính)
File lưu trữ        (actor phụ)
```

### 🧑‍💼 5.2. Use Case của Nhân viên quầy vé

```text
Nhân viên quầy vé
 ├── Tra cứu suất chiếu
 ├── Xem sơ đồ ghế
 ├── Đặt vé
 ├── Tra cứu vé
 ├── Hủy vé
 ├── Xem thống kê suất chiếu
 └── Lưu và nạp dữ liệu
```

`Đặt vé` có:

```text
Đặt vé
 │
 ├── <<include>> Xem sơ đồ ghế
 ├── <<include>> Kiểm tra toàn bộ danh sách ghế
 ├── <<include>> Xác nhận giá
 └── <<include>> Tạo Booking + Ticket
```

`Hủy vé` có:

```text
Hủy vé
 │
 ├── <<include>> Tra cứu vé
 ├── <<include>> Xác nhận hủy
 └── <<include>> Giải phóng ghế và hoàn tiền mô phỏng
```

> [!NOTE]
> Quan hệ `<<include>>` nghĩa là use case gốc **luôn** thực hiện use case được include.

---

### 💾 5.3. Use Case của File lưu trữ

```text
File lưu trữ
 ├── Cung cấp dữ liệu khi khởi động
 └── Nhận dữ liệu khi thoát
```

`Lưu và nạp dữ liệu` có:

```text
Lưu và nạp dữ liệu
 │
 ├── <<include>> Nạp dữ liệu mẫu (khi chưa có file)
 └── <<include>> Ghi toàn bộ dữ liệu ra file
```

---

### 📋 5.4. Tổng hợp Use Case

| Mã | Use Case | Actor | Mô tả ngắn |
|:---:|:---|:---|:---|
| UC01 | 🎞️ Tra cứu suất chiếu | Nhân viên | Xem mã suất, tên phim, giờ chiếu, số ghế trống |
| UC02 | 💺 Xem sơ đồ ghế | Nhân viên | Xem ghế trống / đã đặt của một suất |
| UC03 | 🎟️ Đặt vé | Nhân viên | Nhập tên và danh sách ghế, xác nhận giá, tạo vé |
| UC04 | 🔍 Tra cứu vé | Nhân viên | Tìm vé theo mã vé |
| UC05 | ❌ Hủy vé | Nhân viên | Hủy vé còn hiệu lực, hoàn tiền mô phỏng 100% |
| UC06 | 📊 Xem thống kê | Nhân viên | Vé còn hiệu lực, ghế trống, tiền gốc, tiền hoàn, còn lại |
| UC07 | 💾 Lưu và nạp dữ liệu | Nhân viên, File lưu trữ | Ghi file khi thoát, nạp lại khi khởi động |

### 📝 5.5. Đặc tả nhanh Use Case chính

#### 🎟️ Đặt vé

| Mục | Nội dung |
|:---|:---|
| **Điều kiện đầu** | Suất chiếu tồn tại và còn ghế trống |
| **Luồng chính** | Chọn suất → xem ghế → nhập tên + ghế → kiểm tra → xác nhận giá → tạo vé |
| **Ngoại lệ** | Ghế sai / lặp / đã đặt → từ chối **cả lần đặt**; không xác nhận → không tạo vé |
| **Điều kiện sau** | Tạo Booking và các Ticket `ACTIVE`; ghế chuyển sang **Đã đặt** trong đúng suất |

#### ❌ Hủy vé

| Mục | Nội dung |
|:---|:---|
| **Điều kiện đầu** | Vé tồn tại và `status = ACTIVE` |
| **Luồng chính** | Nhập mã vé → kiểm tra → xác nhận hủy → đổi trạng thái → hoàn tiền |
| **Ngoại lệ** | Mã vé không tồn tại hoặc vé đã hủy → từ chối, không đổi dữ liệu |
| **Điều kiện sau** | Vé `CANCELLED`, ghế được giải phóng đúng suất, hoàn 100% đơn giá đã lưu |
## 🗂️ Use Case Diagram dạng tổng quát

```text
                         HỆ THỐNG ĐẶT VÉ XEM PHIM
┌────────────────────────────────────────────────────────────────────────┐
│                                                                        │
│ Nhân viên quầy vé                                                      │
│  │                                                                     │
│  ├────────── (Tra cứu suất chiếu)                                      │
│  ├────────── (Xem sơ đồ ghế)                                           │
│  ├────────── (Đặt vé)                                                  │
│  │               │                                                     │
│  │               ├── <<include>> Kiểm tra toàn bộ danh sách ghế        │
│  │               ├── <<include>> Xác nhận giá                          │
│  │               └── <<include>> Tạo Booking + Ticket                  │
│  ├────────── (Tra cứu vé)                                              │
│  ├────────── (Hủy vé)                                                  │
│  │               │                                                     │
│  │               ├── <<include>> Xác nhận hủy                          │
│  │               └── <<include>> Giải phóng ghế + hoàn tiền mô phỏng   │
│  ├────────── (Xem thống kê suất chiếu)                                 │
│  └────────── (Lưu và nạp dữ liệu)                                      │
│                  │                                                     │
│                  ├── <<include>> Nạp dữ liệu mẫu (khi chưa có file)    │
│                  └── <<include>> Ghi dữ liệu ra file                   │
│                                                                        │
│ File lưu trữ                                                           │
│  │                                                                     │
│  ├────────── (Cung cấp dữ liệu khi khởi động)                          │
│  └────────── (Nhận dữ liệu khi thoát)                                  │
│                                                                        │
└────────────────────────────────────────────────────────────────────────┘
```

> [!NOTE]
> Khung ngoài là **ranh giới hệ thống**. Các actor nằm trong khung để dễ nhìn tổng thể. Mỗi `( )` là một use case, `<<include>>` là bước mà use case đó luôn thực hiện.

### 🔗 Tóm tắt quan hệ

| Use Case | Include | Ghi chú |
|:---|:---|:---|
| 🎟️ **Đặt vé** | Kiểm tra toàn bộ danh sách ghế, Xác nhận giá, Tạo Booking + Ticket | Kiểm tra xong toàn bộ mới tạo vé |
| ❌ **Hủy vé** | Xác nhận hủy, Giải phóng ghế + hoàn tiền mô phỏng | Chỉ hủy được vé `ACTIVE` |
| 💾 **Lưu và nạp dữ liệu** | Nạp dữ liệu mẫu, Ghi dữ liệu ra file | Có dữ liệu thì không nạp mẫu đè lên |
## 🧩 Class Diagram

Hệ thống gồm **10 lớp**, chia thành 4 nhóm: mô hình dữ liệu, nghiệp vụ, lưu trữ và giao diện.

---

### 💺 6.1. Seat

```text
Seat  «abstract»
---------------------------------------------
- seatId : String
- row : char
- number : int
---------------------------------------------
+ calculatePrice() : long   {abstract}
+ getSeatId() : String
+ getRow() : char
+ getNumber() : int
```

> [!NOTE]
> `Seat` là lớp trừu tượng. Giá được tính ở hai lớp con bằng cách **override** `calculatePrice()`.

---

### 🟦 6.2. StandardSeat

```text
StandardSeat  extends Seat
---------------------------------------------
(kế thừa toàn bộ thuộc tính của Seat)
---------------------------------------------
+ calculatePrice() : long      → 70.000
```

---

### 🟨 6.3. VipSeat

```text
VipSeat  extends Seat
---------------------------------------------
(kế thừa toàn bộ thuộc tính của Seat)
---------------------------------------------
+ calculatePrice() : long      → 100.000
```

> [!IMPORTANT]
> Khi báo giá, chương trình duyệt danh sách theo kiểu `Seat` và gọi `calculatePrice()`, **không** dùng `if/switch` theo loại ghế.

---

### 🎞️ 6.4. Showtime

```text
Showtime
---------------------------------------------
- showtimeId : String
- movieTitle : String
- startTime : String
- seats : List<Seat>
---------------------------------------------
+ getShowtimeId() : String
+ getMovieTitle() : String
+ getStartTime() : String
+ findSeat(seatId : String) : Seat
```

---

### 🧾 6.5. Booking

```text
Booking
---------------------------------------------
- bookingId : String
- customerName : String
- showtimeId : String
- tickets : List<Ticket>
---------------------------------------------
+ addTicket(ticket : Ticket) : void
+ getTotalAmount() : long
+ getTickets() : List<Ticket>
```

---

### 🎟️ 6.6. Ticket

```text
Ticket
---------------------------------------------
- ticketId : String
- bookingId : String
- showtimeId : String
- seatId : String
- priceAtBooking : long
- status : TicketStatus
---------------------------------------------
+ cancel() : void
+ isActive() : boolean
+ getRefundAmount() : long
+ getters ...
```

> [!NOTE]
> `priceAtBooking` là đơn giá **tại lúc đặt**. Tra cứu, thống kê và hoàn tiền đều dùng giá này, không tính lại theo bảng giá hiện tại.

---

### 🔖 6.7. TicketStatus

```text
TicketStatus  «enumeration»
---------------------------------------------
ACTIVE
CANCELLED
```

---

### ⚙️ 6.8. BookingService

```text
BookingService
---------------------------------------------
- showtimes : List<Showtime>
- bookings : List<Booking>
- tickets : List<Ticket>
---------------------------------------------
+ isSeatBooked(showtimeId, seatId) : boolean
+ validateSeats(showtimeId, seatIds) : void
+ calculateTotal(showtimeId, seatIds) : long
+ createBooking(name, showtimeId, seatIds) : Booking
+ findTicket(ticketId : String) : Ticket
+ cancelTicket(ticketId : String) : long
+ getStatistics(showtimeId : String) : Statistics
```

> [!IMPORTANT]
> Mọi quy tắc nghiệp vụ (kiểm tra ghế, tạo vé, hủy vé, thống kê) nằm ở `BookingService`, **không** đặt trong `Menu`.

---

### 💾 6.9. DataStore

```text
DataStore
---------------------------------------------
- filePath : String
---------------------------------------------
+ save(service : BookingService) : void
+ load() : BookingService
```

---

### 🖥️ 6.10. Menu

```text
Menu
---------------------------------------------
- service : BookingService
- scanner : Scanner
---------------------------------------------
+ run() : void
+ showMenu() : void
```

> [!NOTE]
> `Menu` chỉ nhận dữ liệu nhập và hiển thị kết quả, không chứa nghiệp vụ.
## 🔗 Quan hệ giữa các Class

Mục này mô tả cách các lớp ở mục Class Diagram liên kết với nhau.

### 🧬 7.1. Kế thừa (Inheritance)

```text
Seat  «abstract»
 │
 ├── StandardSeat   (hàng A, B, C - 70.000 đ)
 └── VipSeat        (hàng D - 100.000 đ)
```

| Lớp cha | Lớp con | Ý nghĩa |
|:---|:---|:---|
| `Seat` | `StandardSeat`, `VipSeat` | Hai lớp con **override** `calculatePrice()` để trả về giá riêng |

> [!IMPORTANT]
> Đây là chỗ áp dụng **đa hình**: mã nguồn duyệt `List<Seat>` rồi gọi `calculatePrice()`, mỗi đối tượng tự trả giá đúng loại ghế.

---

### 🧩 7.2. Composition và Aggregation

```text
Booking  ◆───────── 1..*  Ticket
Showtime ◇───────── 20    Seat
```

| Quan hệ | Loại | Giải thích |
|:---|:---:|:---|
| `Booking` → `Ticket` | **Composition** (1 – 1..*) | Một lần đặt chứa một hoặc nhiều vé; vé không tồn tại độc lập ngoài Booking |
| `Showtime` → `Seat` | **Aggregation** (1 – 20) | Mỗi suất liên kết với sơ đồ 20 ghế của phòng |

---

### 🔀 7.3. Association (liên kết)

```text
Ticket ───────► TicketStatus     (vé có trạng thái ACTIVE hoặc CANCELLED)
Ticket ───────► Seat             (vé gắn với một ghế, qua seatId)
Ticket ───────► Showtime         (vé gắn với một suất, qua showtimeId)
Booking ──────► Showtime         (một lần đặt thuộc một suất, qua showtimeId)
```

| Từ | Đến | Cách liên kết |
|:---|:---|:---|
| `Ticket` | `TicketStatus` | Thuộc tính `status` |
| `Ticket` | `Seat` | Thuộc tính `seatId` |
| `Ticket` | `Showtime` | Thuộc tính `showtimeId` |
| `Booking` | `Showtime` | Thuộc tính `showtimeId` |

> [!NOTE]
> Một vé là **một ghế của một suất**. Trạng thái ghế được suy ra từ các vé `ACTIVE`, **không** lưu cờ `booked` dùng chung trong phòng.

---

### ⚙️ 7.4. Dependency (phụ thuộc)

```text
Menu ─ ─ ─ ─ ─► BookingService
BookingService ─ ─ ─► Showtime
BookingService ─ ─ ─► Booking
BookingService ─ ─ ─► Ticket
BookingService ─ ─ ─► DataStore
DataStore ─ ─ ─ ─ ─► BookingService
```

| Lớp | Phụ thuộc vào | Mục đích |
|:---|:---|:---|
| `Menu` | `BookingService` | Gọi chức năng nghiệp vụ, chỉ nhập và hiển thị |
| `BookingService` | `Showtime`, `Booking`, `Ticket` | Quản lý và xử lý dữ liệu |
| `BookingService` | `DataStore` | Lưu và nạp dữ liệu |
| `DataStore` | `BookingService` | Đọc và ghi trạng thái hiện tại |

---

### 🗺️ 7.5. Sơ đồ quan hệ tổng quát

```text
                       Menu
                         │  dùng
                         ▼
                  BookingService ─ ─ ─ ─ ─ ─ ─► DataStore
                         │
        ┌────────────────┼──────────────────┐
        ▼                ▼                  ▼
    Showtime          Booking             Ticket
        ◇                ◆                  │
        │ 20             │ 1..*             ├──► TicketStatus
        ▼                ▼                  ├──► Seat
       Seat            Ticket               └──► Showtime
        ▲
   ┌────┴────┐
StandardSeat  VipSeat
```

### 📋 7.6. Tóm tắt các quan hệ

| Loại quan hệ | Ký hiệu | Các cặp lớp |
|:---|:---:|:---|
| Kế thừa | `▲` | `Seat` ← `StandardSeat`, `VipSeat` |
| Composition | `◆` | `Booking` — `Ticket` |
| Aggregation | `◇` | `Showtime` — `Seat` |
| Association | `──►` | `Ticket` → `TicketStatus`, `Seat`, `Showtime`; `Booking` → `Showtime` |
| Dependency | `- - ►` | `Menu` → `BookingService`; `BookingService` ↔ `DataStore` |