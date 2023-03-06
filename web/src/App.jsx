import { Route, Routes, Navigate } from 'react-router-dom';

import ProductsPage from './pages/ProductsPage';
import ProductRegisterPage from './pages/ProductRegisterPage';
import ProductDetailPage from './pages/ProductDetailPage';

export default function App() {
  return (
    <>
      <header>
        <h1>고양이 장난감 가게</h1>
      </header>
      <Routes>
        <Route exact path="/products" element={<ProductsPage />} />
        <Route path="/products/product" element={<ProductRegisterPage />} />
        <Route path="/products/:id" element={<ProductDetailPage />} />
        <Route path="*" element={<Navigate to="/products" />} />
      </Routes>
    </>
  );
}
