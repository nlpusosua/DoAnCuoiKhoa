document.addEventListener('DOMContentLoaded', function() {
// Thêm CSS cho Toast notification1
    const toastCSS = `
    .toast-container {
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 9999;
    }
    .toast {
        background-color: #333;
        color: white;
        padding: 15px 25px;
        border-radius: 5px;
        margin-bottom: 10px;
        opacity: 0;
        transition: opacity 0.3s, transform 0.3s;
        transform: translateY(-20px);
        box-shadow: 0 2px 10px rgba(0,0,0,0.2);
    }
    .toast.success {
        background-color: #4CAF50;
    }
    .toast.warning {
        background-color: #ff9800;
    }
    .toast.error {
        background-color: #f44336;
    }
    .toast.show {
        opacity: 1;
        transform: translateY(0);
    }
`;
// Thêm CSS vào head
    const styleElement = document.createElement('style');
    styleElement.textContent = toastCSS;
    document.head.appendChild(styleElement);
// Tạo container cho toast
    const toastContainer = document.createElement('div');
    toastContainer.className = 'toast-container';
    document.body.appendChild(toastContainer);
// Hàm hiển thị toast thay cho alert
    function showToast(message, type = 'success') {
        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        toast.textContent = message;
        toastContainer.appendChild(toast);
        // Hiển thị toast
        setTimeout(() => {
            toast.classList.add('show');
        }, 10);
        // Tự động ẩn toast sau 3 giây
        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => {
                toastContainer.removeChild(toast);
            }, 300);
        }, 3000);
    }


// Lắng nghe sự kiện và kiểm tra trạng thái yêu thích khi trang tải xong
    const favoriteIcons = document.querySelectorAll('.icon-box-news-foru-index');
// Đảm bảo tất cả icons ban đầu đều là regular heart
    favoriteIcons.forEach(icon => {
        icon.classList.remove('fa-solid');
        icon.classList.add('fa-regular');
        icon.style.color = ''; // Màu mặc định
    });
// Kiểm tra phòng nào đã được lưu để đặt icon phù hợp
    function checkFavoritedRooms() {
        // Tạo mảng chứa tất cả ID phòng cần kiểm tra
        const roomIds = Array.from(favoriteIcons)
            .map(icon => icon.getAttribute('data-room-id'))
            .filter(id => id && !id.includes('${') && id.trim() !== ''); // Lọc ra các ID không hợp lệ
        if (roomIds.length === 0) return;
        // Gọi API kiểm tra từng phòng
        roomIds.forEach(roomId => {
            axios.get('/api/favorites/check', {
                params: { roomId: roomId }
            })
                .then(function(response) {
                    if (response.data.success && response.data.favorited) {
                        // Tìm icon tương ứng và cập nhật trạng thái
                        const iconElement = document.querySelector(`.icon-box-news-foru-index[data-room-id="${roomId}"]`);
                        if (iconElement) {

                            iconElement.classList.remove('fa-regular');
                            iconElement.classList.add('fa-solid');
                            iconElement.style.color = 'red'; // Thêm màu đỏ cho tim

                            iconElement.setAttribute('data-favorited', 'true');
                        }
                    }
                })
                .catch(function(error) {
                    console.error("Error checking favorite status:", error);
                });
        });
    }
// Gọi hàm kiểm tra khi trang tải xong
    checkFavoritedRooms();
// Lắng nghe sự kiện click vào icon favorite
    favoriteIcons.forEach(icon => {
        icon.addEventListener('click', function(event) {
            event.preventDefault(); // Ngăn chặn hành động mặc định của thẻ <a>
            const roomId = this.getAttribute('data-room-id');
            // Kiểm tra ID hợp lệ
            if (!roomId || roomId.includes('${') || roomId.trim() === '') {
                showToast("ID phòng không hợp lệ, vui lòng thử lại", "error");
                return;
            }
            // Kiểm tra xem đã được yêu thích chưa
            const isFavorited = this.getAttribute('data-favorited') === 'true';

            if (isFavorited) {
                removeFavorite(roomId, this);
            } else {
                saveFavorite(roomId, this);
            }
        });
    });

    function saveFavorite(roomId, iconElement) {
        // Hiển thị thông báo đang xử lý (tùy chọn)
        console.log("Saving favorite for room ID:", roomId);
        axios.post('/api/favorites/add', null, {
            params: {
                roomId: roomId
            }
        })
            .then(function(response) {
                console.log("Server response:", response.data);
                if (response.data.success) {
                    // Hiển thị thông báo
                    showToast(response.data.message, "success");
                    // Cập nhật icon

                    iconElement.classList.remove('fa-regular');
                    iconElement.classList.add('fa-solid');
                    iconElement.style.color = 'red'; // Đặt màu đỏ cho tim đã yêu thích

                    iconElement.setAttribute('data-favorited', 'true');
                } else {
                    // Hiển thị thông báo lỗi
                    showToast(response.data.message, "error");
                }
            })
            .catch(function(error) {
                console.error("Error saving favorite:", error);
                console.error("Error response:", error.response?.data);
                if (error.response) {
                    const data = error.response.data;
                    if (data.requireLogin) {
                        // Hiển thị thông báo yêu cầu đăng nhập
                        showToast(data.message, "warning");
                        // Tùy chọn: Chuyển hướng đến trang đăng nhập
                        // window.location.href = '/login';
                    } else {
                        showToast(data.message || 'Có lỗi xảy ra, vui lòng thử lại', "error");
                    }
                } else {
                    showToast('Có lỗi xảy ra, vui lòng thử lại', "error");
                }
            });
    }

    function removeFavorite(roomId, iconElement) {
        axios.delete('/api/favorites/remove', {
            params: {
                roomId: roomId
            }
        })
            .then(function(response) {
                console.log("Server response:", response.data);
                if (response.data.success) {
                    // Hiển thị thông báo
                    showToast(response.data.message, "success");
                    // Cập nhật icon trở lại trạng thái chưa yêu thích

                    iconElement.classList.remove('fa-solid');
                    iconElement.classList.add('fa-regular');
                    iconElement.style.color = ''; // Xóa màu đỏ

                    iconElement.setAttribute('data-favorited', 'false');
                } else {
                    // Hiển thị thông báo lỗi
                    showToast(response.data.message, "error");
                }
            })
            .catch(function(error) {
                console.error("Error removing favorite:", error);
                console.error("Error response:", error.response?.data);
                if (error.response) {
                    const data = error.response.data;
                    if (data.requireLogin) {
                        // Hiển thị thông báo yêu cầu đăng nhập
                        showToast(data.message, "warning");
                    } else {
                        showToast(data.message || 'Có lỗi xảy ra, vui lòng thử lại', "error");
                    }
                } else {
                    showToast('Có lỗi xảy ra, vui lòng thử lại', "error");
                }
            });
    }
});