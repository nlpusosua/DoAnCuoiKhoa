// Lấy các input
const oldPasswordEl = document.getElementById('old-pass');
const newPasswordEl = document.getElementById('new-pass');
const confirmPasswordEl = document.getElementById('confirm-pass');
const passwordForm = document.querySelector('.form-user-password');

passwordForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    if (newPasswordEl.value.trim() !== confirmPasswordEl.value.trim()) {
        toastr.error("Hai mật khẩu mới không trùng nhau");
        return;
    }

    // Kiểm tra nếu currentUser vẫn null
    if (!currentUser || !currentUser.id) {
        toastr.error("Không tìm thấy thông tin người dùng!");
        return;
    }

    const data = {
        id: currentUser.id,
        oldPassword: oldPasswordEl.value,
        newPassword: newPasswordEl.value,
    };

    if (!$('.form-user-password').valid()) {
        return;
    }
    try {
        const response = await axios.put("/api/user/update/password", data);
        toastr.success("Đổi mật khẩu thành công!");
    } catch (error) {
        console.log(error);
        toastr.error(error.response.data.message);
    }
});

// Khởi tạo validate
$('.form-user-password').validate({
    rules: {
        oPassword: {
            required: true,
        },
        nPassword: {
            required: true,
        },
        confirmPassword: {
            required: true,
            equalTo: "#new-pass"
        }
    },
    messages: {
        oPassword: {
            required: "Không được để trống"
        },
        nPassword: {
            required: "Không được để trống"
        },
        confirmPassword: {
            required: "Không được để trống",
            equalTo: "Mật khẩu nhập lại không khớp"
        },
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.mb-3').append(error);
    },
    highlight: function (element) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function (element) {
        $(element).removeClass('is-invalid');
    }
});