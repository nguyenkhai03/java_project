# Website đặt phòng - VinHome Resorts
## Giới Thiệu
Dự án này là một ứng dụng web được thiết kế cho "VinHome Resorts" để quản lý và hiển thị các loại hình lưu trú khác nhau. Người dùng có thể xem các phòng có sẵn, đặt phòng, và xem các mô tả chi tiết bao gồm tiện nghi và giá cả.
## Tính Năng
- **Trang Chủ**: Truy cập nhanh vào tất cả các mục bao gồm danh sách phòng
- **Danh Sách Phòng**: Liệt kê động tất cả các phòng có sẵn với chi tiết như loại phòng, giá, tiện nghi và số người tối đa.
- **Hệ Thống Đặt Phòng**: Người dùng có thể đặt phòng thông qua giao diện thân thiện, kiểm tra các ngày nhập và ràng buộc về sức chứa.
- **Thiết Kế Đáp Ứng**: Trang web hoàn toàn đáp ứng và cung cấp trải nghiệm xem tối ưu trên nhiều thiết bị.
- **Tìm Kiếm và Lọc**: Người dùng có thể tìm kiếm phòng và lọc chúng dựa trên các danh mục như căn hộ, biệt thự, và penthouse.
- ** Admin **: Thêm sửa xóa quản lý phòng 
## Công Nghệ Sử Dụng
- **Spring Boot**: Dùng để tạo máy chủ phía sau và xử lý logic ứng dụng.
- **Thymeleaf**: Máy phát template phía máy chủ để render các trang web.
- **Bootstrap & CSS**: Dùng để thiết kế kiểu và xây dựng thiết kế đáp ứng.
- **JavaScript**: Dùng cho kịch bản phía khách hàng

## Thiết Lập và Cài Đặt

1. **Clone Kho Lưu Trữ**
   git clone https://github.com/nguyenkhai03/java_project
   cd java_project

2. **Cấu Hình Properties**
   Cập nhật `src/main/resources/application.properties` với cấu hình cơ sở dữ liệu và máy chủ của bạn.

3. **Xây Dựng Dự Án**
   mvn clean install

**Chạy Ứng Dụng**
   mvn spring-boot:run

   Nếu bạn đang sử dụng IDE như IntelliJ IDEA hoặc Eclipse,netbean, bạn có thể chạy ứng dụng bằng cách thực thi lớp chính trực tiếp.

5. **Truy Cập Ứng Dụng**
   Mở trình duyệt web của bạn và truy cập `http://localhost:8081` để xem ứng dụng.
