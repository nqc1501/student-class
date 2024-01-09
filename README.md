# team-project-n2_4

Chủ đề:	Thuê phòng học online
## I.	Sử dụng mô hình REST service:
Quy trình nghiệp vụ: Đặt phòng thí nghiệm online bao gồm: 
-	Hiển thị phòng cần tìm
-	Kiểm tra phòng còn trống theo lịch đặt
-	Tính giá
-	Xác nhận phòng đặt 
-	Thông báo trạng thái 
 
 ![341000502_980567936276149_4910510700184471652_n](https://user-images.githubusercontent.com/93857625/232883953-7756474a-8966-4a38-a15b-0f7bad8774de.png)


## II.	Phân tích và Mô hình hóa dịch vụ
### 1.	Phân tách quy trình thành các hành động chi tiết

 -	Bắt đầu gửi thông tin phòng cần tìm
 -	Nhận thông tin tìm kiếm phòng của sinh viên
 -	Kiểm tra phòng trong csdl với thông tin tìm kiếm của sinh viên
 -	Không có phòng như yêu cầu của sinh viên, quay lại bước 1
 -	Nếu có phòng, nhận lịch đặt của sinh viên
 -	Nếu lịch đặt đã bị trùng với sinh viên khác, kết thúc
 -	Nếu có phòng trống theo lịch đặt, tính giá 
 -	Xác minh từ chối thuê thủ công
 -	Gửi thông báo từ chối và kết thúc chương trình
 -	Xác nhận chấp nhận thủ công
 -	Thông báo sinh viên đặt thuê phòng thành công
 -	Cho phép sinh viên thuê phòng
 -	Cập nhật lại dữ liệu trong CSDL
 -	Kết thúc quy trình
### 2.	Gạch bỏ các bước không phù hợp
 **Không phải tất cả các các logic quy trình đều phù hợp để thực hiện mô hình REST service, các hành động không phù hợp bị gạch bỏ là:**
 -	Xác minh từ chối thuê thủ công
 -	Xác nhận chấp nhận thủ công
 -	Cho phép sinh viên thuê phòng
### 3.	Xác định ứng viên dịch vụ thực thể

 **Xem xét các hành động còn lại từ bước, xác định và phân loại những hành động được coi là bất khả tri (Được in đậm):**
 -	Bắt đầu gửi thông tin phòng cần tìm
 -	**Nhận thông tin tìm kiếm phòng của sinh viên**
 -	Kiểm tra phòng trong csdl với thông tin tìm kiếm của sinh viên
 -	Không có phòng như yêu cầu của sinh viên, quay lại bước 1
 -	Nếu có phòng, nhận lịch đặt của sinh viên
 -	Nếu lịch đặt đã bị trùng với sinh viên khác, kết thúc
 -	Nếu có phòng trống theo lịch đặt, tính giá 
 -	**Gửi thông báo từ chối và kết thúc chương trình**
 -	**Gửi thông báo sinh viên đặt thuê phòng thành công**
 -	Cập nhật lại dữ liệu trong CSDL
 -	Kết thúc quy trình

 **Các ứng viên dịch vụ thực thể được xác định:**
 
 -	Ứng viên **Room**
  ```bash
  Để lấy thông tin của phòng gồm tên phòng, trạng thái,… thiết lập ứng viên Get Detail
  ```
  ```bash
  Để cập nhật trạng thái của phòng sau khi có sinh viên đặt thiết lập ứng viên Update Status
  ```
  
  ![Room_phan3](https://user-images.githubusercontent.com/93857625/232885846-a8ec5815-5d1c-4292-a3f7-735e8e910a5e.png)

 -	Ứng viên **Booking**
  ```bash
  Để lấy thông tin đặt phòng thiết lập ứng viên Get Detail
  ```
  
  ![Booking_phan3](https://user-images.githubusercontent.com/93857625/232885899-25679319-f222-4404-9339-8b7f8c417495.png)
 
### 4.	Xác định logic cụ thể của cả quá trình

 **Các bước không bất khả tri:**
 -	Bắt đầu gửi thông tin phòng cần tìm
 -	Kiểm tra phòng trong csdl với thông tin tìm kiếm của sinh viên
 -	Không có phòng như yêu cầu của sinh viên, quay lại bước 1
 -	Nếu có phòng, nhận lịch đặt của sinh viên
 -	Nếu lịch đặt đã bị trùng với sinh viên khác, kết thúc
 -	Nếu có phòng trống theo lịch đặt, tính giá 
 -	Cập nhật lại dữ liệu trong CSDL
 -	Kết thúc quy trình
 -	Ta chọn “Bắt đầu gửi thông tin phòng cần tìm” là ứng viên năng lực dịch vụ cho ứng viên dịch vụ “Đặt phòng online”


 **Ứng viên Room Booking:**

  ![xác định logic cụ thể của quy trình](https://user-images.githubusercontent.com/93857625/232886011-e7dc923a-6e2d-4e51-89a1-38051321119c.png)
  
   - Khả năng “Start” sẽ được gọi bởi một chương trình phần mềm riêng biệt, chương trình này sẽ hoạt động như một bộ khởi tạo tích hợp

### 5.	Xác định tài nguyên
 -	Room /room/
 -	Roomconditions /roomconditions/
 -	Booking /booking/
 
 ### 6.	Xác định các ứng viên tổ hợp dịch vụ 
 
 ![các ứng viên tổ hợp dịch vụ](https://github.com/jnp2018/team-project-n2_4/assets/93857625/f94e9daf-d898-4eee-9e35-4acd4c9cfa93)
 
 ### 7.	Xác định các ứng viên dịch vụ tiện ích
 -	Các hành động gửi thông báo từ chối hay chấp nhận được hợp thành một ứng viên khả năng dịch vụ chung:
 
 ![image](https://user-images.githubusercontent.com/93857625/232891439-3fbc7f4b-3eb3-40c1-8552-724d2f2bb729.png)
 
 ### 8.	Xác định các ứng viên vi dịch vụ
 -	Để hỗ trợ tách rời xử lý cho hành động “quản lý phòng học”, bao gồm xem thông tin về phòng học, tình trạng phòng học, lịch trống và các yêu cầu đặc biệt khác, đề xuất một ứng viên vi dịch vụ là Gateway:
 
 ![ứng viên vi dịch vụ - phân tích (2)](https://github.com/jnp2018/team-project-n2_4/assets/93857625/5138f042-15c0-4b3d-aefe-d095395bbb0b)
 
 ### 9.	Sửa đổi các ứng viên tổ hợp dịch vụ

![sửa đổi ứng viên tổ hợp dịch vụ](https://github.com/jnp2018/team-project-n2_4/assets/93857625/56109ba4-28ee-4e46-b64b-f010420cc9bd)

 ## III.	Thiết kế
 
### 1.	Liên kết các dịch vụ với tài nguyên tương ứng
 -	RoomBooking service

 ![image](https://user-images.githubusercontent.com/93857625/232891101-effb577e-387e-4af4-ab24-0931a2b634fb.png)

 -	Room service

 ![image](https://user-images.githubusercontent.com/93857625/232891197-3e035b53-11e4-49b8-a6c6-15ac4cd5ba80.png)

 -	Booking service
 
 ![image](https://user-images.githubusercontent.com/93857625/232891236-0af3695e-48c9-47bd-9e34-80f98c594208.png)
### 2. Thiết kế ứng viên dịch vụ tiện ích

![thiết kế - dịch vụ tiện ích](https://user-images.githubusercontent.com/93857625/234455121-cf959ef8-b9cd-430d-a407-b2d68fb5e781.png)

### 3.	Thiết kế ứng viên vi dịch vụ
 
 ![ứng viên vi dịch vụ](https://github.com/jnp2018/team-project-n2_4/assets/93857625/3967d6fa-946b-4572-9b69-c589ce4b8fe0)

------------------------------------------------------------------
## Công nghệ được sử dụng:

| Tech            | name                 | Version    |  Reason                        |
|:-------         |:------:              |-------:    |   -------:                     |
|  Platform       |  JDK                 |   8        | Base platform                  |
|  Language       |  Java                |  8         | Using Spring Framework         |
|  Framwork       |  Spring Framework    |  3.0.5     | Implement Microservice         |
|                 |  Spring Boot         |  3.0.5     | Implement Microservice         |
|   Build tool    |  Maven               |  3.6.3     |                                |
|   API Gateway   |  Spring cloud        |  2.0.2     | Build a Gateway to             |
|                 |                      |            |loadbalance and fillter request |
|                 |  Gateway             |  Costume   |  fillter request               |
|   Redis         |  Spring Redis        |  2.0.8     |  Save user token               |
| Eureka          |  Eureka Client-Server|  2.0.2     | Registry service               |



- Luồng hoạt động của dự án: Đầu tiên client sẽ đi qua api gate way để vào hệ thống. Gateway tự động nhận biết các service đã đăng ký với eureka server để chuyển hướng và gọi đến service client.
- Cấu hình máy chủ vận hành
![image](https://user-images.githubusercontent.com/86352137/234345168-a26590cd-62c5-401c-b0f1-fe42d82fbf50.png)
- Hiện tại dự án đang sử dụng các module booking-service, userservice, core, eureka, gateway để đăng nhập và đặt lịch sử dụng phòng học, thêm học sinh dựa trên danh sách cho trước.
- Đăng ký các service lên eureka
~~~.properties
# server
server.port=9081
# info
info.eureka.host=localhost
info.eureka.port=8761
eureka.client.serviceUrl.defaultZone=http://${info.eureka.host}:${info.eureka.port}/eureka/
# eureka

# application
spring.application.name=gateway
~~~

- Gateway làm nhiệm vụ chia tải, và định tuyến người dùng đến đúng các service bằng service-name thay vì sử dụng port. Gate way còn có nhiệm vụ load balancing để sử dụng cho các ứng dụng có mức tải cao. Định hướng proxy đến các máy chủ khác, chặn IP xấu.
- Lọc các request dựa trên header bao gồm các lỗi CORS hay các request đến từ máy chủ khác dựa theo IP:
~~~Java
    private void resp(RequestContext ctx, String msg, int code) {
        ctx.getResponse().setCharacterEncoding("UTF-8");
        ctx.setResponseBody(JsonUtil.toJSONString(Result.error(code, msg)));
        ctx.addOriginResponseHeader("Access-Control-Allow-Origin", "*");
    }
~~~
