document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("keyword");
    const searchButton = document.getElementById("searchButton");
    const cityItems = document.querySelectorAll(".all-cities div, .highlighted-cities .city");

    // Xử lý sự kiện khi click vào nút tìm kiếm
    searchButton.addEventListener("click", function () {
        searchRooms(searchInput.value);
    });

    // Xử lý sự kiện khi nhấn Enter trong ô input
    searchInput.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            searchRooms(searchInput.value);
        }
    });

    // Xử lý khi chọn tỉnh/thành phố từ danh sách
    cityItems.forEach(city => {
        city.addEventListener("click", function () {
            let cityName = city.innerText.trim(); // Lấy tên tỉnh/thành phố
            searchRooms(cityName);
        });
    });

    // Hàm thực hiện chuyển hướng tìm kiếm
    function searchRooms(keyword) {
        if (keyword.trim() !== "") {
            window.location.href = "/search?keyword=" + encodeURIComponent(keyword);
        }
    }
});
