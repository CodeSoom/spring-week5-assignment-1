import axios from 'axios';

export const fetchProducts = async () => {
  const { data } = await axios.get('http://localhost:8080/products');
  return data;
};

export const fetchProduct = async (id) => {
  const { data } = await axios.get(`http://localhost:8080/products/${id}`);
  return data;
};

export const postProduct = async (product) => {
  try {
    const { data } = await axios
      .post('http://localhost:8080/products', product);
    return data;
  } catch (err) {
    if (err.response?.status === 400) {
      throw new Error('잘못된 요청입니다. 파라미터를 확인해 주세요');
    }

    throw err;
  }
};

export const deleteProduct = async (id) => {
  await axios.delete(`http://localhost:8080/products/${id}`);
};

export const putProduct = async (product) => {
  try {
    const { data } = await axios
      .patch(`http://localhost:8080/products/${product.id}`, product);
    return data;
  } catch (err) {
    if (err.response?.status === 400) {
      throw new Error('잘못된 요청입니다. 파라미터를 확인해 주세요');
    }

    throw err;
  }
};
