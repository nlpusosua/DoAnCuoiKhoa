document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.querySelector('.search-input');
    const searchButton = document.querySelector('.search-button');
    const dropdownSearch = document.querySelector('.dropdown-search');
    const filterButtons = document.querySelectorAll('.filter-button');

    // Show dropdown on search input click
    searchInput.addEventListener('click', function() {
        dropdownSearch.style.display = 'block';
    });

    // Hide dropdown on search button click
    searchButton.addEventListener('click', function() {
        dropdownSearch.style.display = 'none';
    });

    // bảng filter
const dropdownContent = {
    "phong-tro": `
        <div class="filter-dropdown-content">
            <div>Phòng trọ, nhà trọ</div>
            <div>Nhà thuê nguyên căn</div>
            <div>Cho thuê căn hộ</div>
            <div>Cho thuê căn hộ mini</div>
            <div>Cho thuê căn hộ dịch vụ</div>
            <div>Tìm người ở ghép</div>
            <div>Cho thuê mặt bằng</div>
        </div>
    `,
    "muc-gia": `
        <div class="filter-dropdown-content">
            <div>0 - 1 triệu</div>
            <div>1 - 3 triệu</div>
            <div>3 - 5 triệu</div>
            <div>5 - 10 triệu</div>
            <div>10 triệu trở lên</div>
        </div>
    `,
    "dien-tich": `
        <div class="filter-dropdown-content">
            <div>0 - 20 m²</div>
            <div>20 - 50 m²</div>
            <div>50 - 100 m²</div>
            <div>100 m² trở lên</div>
        </div>
    `
};

filterButtons.forEach(button => {
    button.addEventListener('click', function(event) {
        event.stopPropagation();
        const filterType = button.getAttribute('data-filter');
        let dropdown = button.nextElementSibling;

        if (dropdown && dropdown.classList.contains('filter-dropdown')) {
            dropdown.remove(); // Ẩn dropdown nếu đã hiển thị
            button.querySelector('i').classList.remove('fa-chevron-up');
            button.querySelector('i').classList.add('fa-chevron-down');
        } else {
            // Xóa tất cả dropdowns hiện tại và đặt lại biểu tượng
            document.querySelectorAll('.filter-dropdown').forEach(dropdown => dropdown.remove());
            document.querySelectorAll('.filter-button i').forEach(icon => {
                icon.classList.remove('fa-chevron-up');
                icon.classList.add('fa-chevron-down');
            });

            // Tạo mới dropdown và thiết lập chiều rộng
            dropdown = document.createElement('div');
            dropdown.className = 'filter-dropdown';
            dropdown.innerHTML = dropdownContent[filterType] || '<div>No data yet</div>';
            
            // Đặt chiều rộng của dropdown bằng với chiều rộng của nút
            dropdown.style.width = `${button.offsetWidth}px`;

            // Thêm dropdown vào DOM
            button.parentNode.insertBefore(dropdown, button.nextSibling);
            dropdown.style.display = 'block'; // Hiển thị dropdown

            // Xoay biểu tượng
            button.querySelector('i').classList.remove('fa-chevron-down');
            button.querySelector('i').classList.add('fa-chevron-up');
        }
    });
});


// xoay
document.addEventListener('click', function() {
    document.querySelectorAll('.filter-dropdown').forEach(dropdown => dropdown.remove());
    document.querySelectorAll('.filter-button i').forEach(icon => {
        icon.classList.remove('fa-chevron-up');
        icon.classList.add('fa-chevron-down');
    });
});

    // Close dropdown when clicking outside
    document.addEventListener('click', function(event) {
        if (!event.target.closest('.search-box') && !event.target.closest('.filter-button')) {
            dropdownSearch.style.display = 'none';
            document.querySelectorAll('.filter-dropdown').forEach(dropdown => dropdown.remove());
        }
    });
});


