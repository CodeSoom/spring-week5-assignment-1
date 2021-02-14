import configureStore from 'redux-mock-store';

import { getDefaultMiddleware } from '@reduxjs/toolkit';

import {
  setProducts, loadProducts, registerProduct, success, clearForm, setProduct,
  loadProduct, removeProduct, setMode, updateProduct, fail,
} from './slice';

import { productsFixture } from '../fixtures/products';

import { postProduct, putProduct } from '../api/products';

const mockStore = configureStore(getDefaultMiddleware());

jest.mock('../api/products');

describe('slice', () => {
  let store;

  beforeEach(() => {
    store = mockStore(() => ({
      product: productsFixture[0],
    }));
  });

  describe('loadProducts', () => {
    it('runs setProducts', async () => {
      await store.dispatch(loadProducts());

      const actions = store.getActions();

      expect(setProducts.match(actions[0])).toBe(true);
    });
  });

  describe('loadProduct', () => {
    const id = 1;

    it('runs setProduct', async () => {
      await store.dispatch(loadProduct(id));

      const actions = store.getActions();

      expect(setProduct.match(actions[0])).toBe(true);
    });
  });

  describe('registerProducts', () => {
    context('when successfully registered', () => {
      beforeEach(() => {
        postProduct.mockResolvedValue(productsFixture[0]);
      });

      it('runs success', async () => {
        await store.dispatch(registerProduct());

        const actions = store.getActions();

        expect(clearForm.match(actions[0])).toBe(true);
        expect(success.match(actions[1])).toBe(true);
      });
    });

    context('when fail', () => {
      beforeEach(() => {
        postProduct.mockRejectedValue(new Error('Some error'));
      });

      it('runs fail', async () => {
        await store.dispatch(registerProduct());

        const actions = store.getActions();

        expect(fail.match(actions[0])).toBe(true);
      });
    });
  });

  describe('deleteProduct', () => {
    const id = 1;

    it('runs setProducts', async () => {
      await store.dispatch(removeProduct(id));

      const actions = store.getActions();

      expect(setProducts.match(actions[0])).toBe(true);
    });
  });

  describe('updateProduct', () => {
    context('when successfully updated', () => {
      beforeEach(() => {
        putProduct.mockResolvedValue(productsFixture[0]);
      });

      it('runs setProduct', async () => {
        await store.dispatch(updateProduct());

        const actions = store.getActions();

        expect(setProduct.match(actions[0])).toBe(true);
        expect(setMode.match(actions[1])).toBe(true);
      });
    });

    context('when fail', () => {
      beforeEach(() => {
        putProduct.mockRejectedValue(new Error('Some error'));
      });

      it('runs fail', async () => {
        await store.dispatch(updateProduct());

        const actions = store.getActions();

        expect(fail.match(actions[0])).toBe(true);
      });
    });
  });
});
