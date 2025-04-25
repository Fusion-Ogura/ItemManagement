<script setup>
import { createRouter, createWebHistory } from 'vue-router';
import ItemList from './components/ItemList.vue';
import ItemDetail from './components/ItemDetail.vue';

const routes = [
  { path: '/', component: ItemList }, // 商品一覧
  { path: '/item/:id', component: ItemDetail, props: true }, // 商品詳細
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});
</script>

<template>
  <main>
    <router-view />  <!-- ここにルーティングされたコンポーネントが表示される -->
  </main>
</template>