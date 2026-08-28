import React, { useState, useEffect } from 'react';

function App() {
  // 1. Quản lý trạng thái (States)
  const [products, setProducts] = useState([]); // Danh sách hàng hóa từ Oracle
  const [category, setCategory] = useState('FOOD'); // Loại hàng: FOOD, ELECTRIC, CERAMIC
  const [loading, setLoading] = useState(false);

  // 2. Tự động lấy danh sách hàng hóa khi mở trang
  useEffect(() => {
    fetchProducts();
  }, []);

  // --- HÀM 1: LẤY DANH SÁCH TỪ BACKEND (GET) ---
  const fetchProducts = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/hang-hoa');
      if (!response.ok) throw new Error("Không thể lấy dữ liệu");
      const data = await response.json();
      setProducts(data); // Cập nhật danh sách vào giao diện
    } catch (error) {
      console.error("Lỗi kết nối Backend:", error);
    }
  };

  // --- HÀM 2: THÊM MỚI HÀNG HÓA (POST) ---
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    // Lấy dữ liệu từ các ô nhập
    const formData = new FormData(e.target);
    const productData = Object.fromEntries(formData.entries());

    // Xác định đúng Endpoint API dựa trên loại hàng đã chọn
    let endpoint = 'thuc-pham';
    if (category === 'ELECTRIC') endpoint = 'dien-may';
    if (category === 'CERAMIC') endpoint = 'sanh-su';

    try {
      const response = await fetch(`http://localhost:8080/api/hang-hoa/${endpoint}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(productData),
      });

      if (response.ok) {
        alert("Thêm thành công vào Oracle Database!");
        e.target.reset(); // Xóa sạch các ô nhập sau khi thêm
        fetchProducts(); // Tải lại danh sách mới nhất
      } else {
        const errorMsg = await response.text();
        alert("Lỗi nghiệp vụ: " + errorMsg); // Hiển thị lỗi trùng mã, đơn giá âm...
      }
    } catch (error) {
      alert("Lỗi kết nối đến Server Spring Boot!");
    } finally {
      setLoading(false);
    }
  };

  // 3. Giao diện (UI)
  return (
    <div style={styles.container}>
      <header style={styles.header}>
        <h1 style={{ margin: 0 }}>HỆ THỐNG QUẢN LÝ SIÊU THỊ</h1>
      </header>

      {/* --- FORM NHẬP LIỆU --- */}
      <section style={styles.formSection}>
        <h3 style={{ marginTop: 0 }}>Nhập thông tin hàng hóa</h3>
        <form onSubmit={handleSubmit} style={styles.formGrid}>
          <div>
            <label style={styles.label}>Loại hàng hóa:</label>
            <select 
              value={category} 
              onChange={(e) => setCategory(e.target.value)}
              style={styles.input}
            >
              <option value="FOOD">Hàng Thực Phẩm (VAT 5%)</option>
              <option value="ELECTRIC">Hàng Điện Máy (VAT 10%)</option>
              <option value="CERAMIC">Hàng Sành Sứ (VAT 10%)</option>
            </select>
          </div>

          <div>
            <label style={styles.label}>Mã hàng (Duy nhất):</label>
            <input name="maHang" required style={styles.input} placeholder="VD: TP001" />
          </div>

          <div>
            <label style={styles.label}>Tên hàng hóa:</label>
            <input name="tenHang" required style={styles.input} placeholder="Tên sản phẩm..." />
          </div>

          <div style={{ display: 'flex', gap: '10px' }}>
            <div style={{ flex: 1 }}>
              <label style={styles.label}>Số lượng:</label>
              <input type="number" name="soLuongTon" defaultValue="0" style={styles.input} />
            </div>
            <div style={{ flex: 1 }}>
              <label style={styles.label}>Đơn giá (VNĐ):</label>
              <input type="number" name="donGia" defaultValue="0" style={styles.input} />
            </div>
          </div>

          {/* --- CÁC TRƯỜNG ĐẶC THÙ THEO LOẠI --- */}
          <div style={styles.specialFields}>
            {category === 'FOOD' && (
              <div style={styles.formGrid}>
                <div>
                  <label style={styles.label}>Ngày sản xuất:</label>
                  <input type="date" name="ngaySanXuat" style={styles.input} title="Ngày sản xuất" />
                  </div>
                  <div>
                  <label style={styles.label}>Ngày hết hạn:</label>
                  <input type="date" name="ngayHetHan" style={styles.input} title="Ngày hết hạn" />
                  </div>
                <input name="nhaCungCap" placeholder="Nhà cung cấp" style={styles.input} />
              </div>
            )}

            {category === 'ELECTRIC' && (
              <div style={styles.formGrid}>
                <input type="number" name="thoiGianBaoHanh" placeholder="Bảo hành (tháng)" style={styles.input} />
                <input type="number" step="0.1" name="congSuat" placeholder="Công suất (KW)" style={styles.input} />
              </div>
            )}

            {category === 'CERAMIC' && (
              <div style={styles.formGrid}>
                <input name="nhaSanXuat" placeholder="Nhà sản xuất" style={styles.input} />
                <input type="date" name="ngayNhapKho" style={styles.input} title="Ngày nhập kho" />
              </div>
            )}
          </div>

          <button 
            type="submit" 
            disabled={loading}
            style={loading ? styles.btnDisabled : styles.btnSubmit}
          >
            {loading ? 'Đang xử lý...' : 'LƯU VÀO DATABASE'}
          </button>
        </form>
      </section>

      {/* --- BẢNG DANH SÁCH --- */}
      <section>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h3>Danh sách hàng hóa hiện có</h3>
          <button onClick={fetchProducts} style={styles.btnRefresh}>Làm mới danh sách</button>
        </div>
        
        <table style={styles.table}>
          <thead>
            <tr style={styles.tableHeader}>
              <th style={styles.th}>Mã Hàng</th>
              <th style={styles.th}>Tên Hàng</th>
              <th style={styles.th}>Số Lượng</th>
              <th style={styles.th}>Đơn Giá</th>
              <th style={styles.th}>Thuế VAT</th>
              <th style={styles.th}>Đánh Giá</th>
              <th style={styles.th}>Thông Tin Chi Tiết</th>
            </tr>
          </thead>
          <tbody>
            {products.length === 0 ? (
              <tr><td colSpan="7" style={{ textAlign: 'center', padding: '20px' }}>Chưa có hàng hóa nào trong kho.</td></tr>
            ) : (
              products.map((p) => (
                <tr key={p.maHang} style={styles.tr}>
                  <td style={styles.td}><b>{p.maHang}</b></td>
                  <td style={styles.td}>{p.tenHang}</td>
                  <td style={styles.td}>{p.soLuongTon}</td>
                  <td style={styles.td}>{p.donGia.toLocaleString()}đ</td>
                  <td style={{ ...styles.td, color: '#e74c3c', fontWeight: 'bold' }}>{p.tienVat.toLocaleString()}đ</td>
                  <td style={styles.td}>
                    <span style={{ 
                      ...styles.badge, 
                      backgroundColor: 
                        p.danhGia === 'Khó bán' ? '#e74c3c' : 
                        p.danhGia === 'Bán được' ? '#2ecc71' : 
                        p.danhGia === 'Bán chậm' ? '#f1c40f' : '#95a5a6'
                    }}>
                      {p.danhGia}
                    </span>
                  </td>
                  <td style={{ ...styles.td, fontSize: '12px', color: '#7f8c8d' }}>
                    {p.loaiHang === 'FOOD' && `HSD: ${p.ngayHetHan} | NCC: ${p.nhaCungCap}`}
                    {p.loaiHang === 'ELECTRIC' && `BH: ${p.thoiGianBaoHanh}th | CS: ${p.congSuat}KW`}
                    {p.loaiHang === 'CERAMIC' && `NSX: ${p.nhaSanXuat} | Nhập: ${p.ngayNhapKho}`}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </section>
    </div>
  );
}

// 4. CSS-in-JS (Style cho ứng dụng)
const styles = {
  container: { maxWidth: '1100px', margin: '20px auto', padding: '20px', fontFamily: 'Arial, sans-serif' },
  header: { textAlign: 'center', marginBottom: '30px', borderBottom: '2px solid #3498db', paddingBottom: '10px' },
  formSection: { backgroundColor: '#ecf0f1', padding: '20px', borderRadius: '10px', marginBottom: '30px' },
  formGrid: { display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' },
  label: { display: 'block', marginBottom: '5px', fontWeight: 'bold', fontSize: '14px' },
  input: { width: '100%', padding: '10px', borderRadius: '5px', border: '1px solid #bdc3c7', boxSizing: 'border-box' },
  specialFields: { gridColumn: 'span 2', padding: '15px', backgroundColor: '#fff', borderRadius: '5px', border: '1px dashed #3498db' },
  btnSubmit: { gridColumn: 'span 2', padding: '12px', backgroundColor: '#27ae60', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer', fontWeight: 'bold' },
  btnDisabled: { gridColumn: 'span 2', padding: '12px', backgroundColor: '#95a5a6', color: 'white', border: 'none', borderRadius: '5px', cursor: 'not-allowed' },
  btnRefresh: { padding: '8px 15px', backgroundColor: '#3498db', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' },
  table: { width: '100%', borderCollapse: 'collapse', marginTop: '10px', boxShadow: '0 0 20px rgba(0,0,0,0.1)' },
  tableHeader: { backgroundColor: '#34495e', color: 'white' },
  th: { padding: '12px', textAlign: 'left' },
  td: { padding: '12px', borderBottom: '1px solid #ddd' },
  tr: { transition: 'background 0.3s', ':hover': { backgroundColor: '#f5f5f5' } },
  badge: { padding: '5px 10px', borderRadius: '20px', color: 'white', fontSize: '11px', fontWeight: 'bold', display: 'inline-block' }
};

export default App;