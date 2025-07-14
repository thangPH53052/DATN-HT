window.onload = function () {
    khoiTao();

    // Gợi ý từ khóa tìm kiếm
    var tags = ["Samsung", "iPhone", "Huawei", "Oppo", "Mobi"];
    for (var t of tags) addTags(t, "/index.html?search=" + t, true);

    phanTich_URL_chiTietSanPham();
}

// Khi không tìm thấy sản phẩm
function khongTimThaySanPham() {
    document.getElementById('productNotFound').style.display = 'block';
    document.getElementsByClassName('chitietSanpham')[0].style.display = 'none';
}

// Lấy ID từ URL và fetch dữ liệu từ API
async function phanTich_URL_chiTietSanPham() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get("id");

    if (!id) return khongTimThaySanPham();

    try {
        const res = await fetch(`/san-pham-chi-tiet/api/${id}`);
        if (!res.ok) throw new Error("Không tìm thấy sản phẩm");
        const sanPham = await res.json();

        // Set thông tin trang
        document.title = sanPham.ten + ' - Chi tiết sản phẩm';
        document.getElementById("productName").textContent = sanPham.ten;

        // Cập nhật hình ảnh
        let imgUrl = (sanPham.hinhAnhUrls && sanPham.hinhAnhUrls.length)
            ? `/uploads/images/${sanPham.hinhAnhUrls[0]}`
            : '/img/default.jpg';
        document.getElementById("mainImage").src = imgUrl;

        // Cập nhật giá và số lượng
        document.getElementById("productPrice").innerHTML = `<strong>Giá:</strong> ${sanPham.giaBan.toLocaleString('vi-VN')}₫`;
        document.getElementById("productQuantity").innerHTML = `<strong>Số lượng còn lại:</strong> ${sanPham.soLuongTon}`;

        // Nút thêm vào giỏ
        document.getElementById("btnAddToCart").onclick = () => themVaoGioHang(sanPham.id);

        // Hiện phần chi tiết
        document.querySelector(".chitietSanpham").style.display = 'block';

    } catch (err) {
        console.error(err);
        khongTimThaySanPham();
    }
}

// Đóng mở xem hình
function opencertain() {
    document.getElementById("overlaycertainimg")?.style?.transform = "scale(1)";
}
function closecertain() {
    document.getElementById("overlaycertainimg")?.style?.transform = "scale(0)";
}
