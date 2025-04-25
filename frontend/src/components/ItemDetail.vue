<template>
    <div class="p-4">
      <h1 class="text-2xl font-bold mb-4">商品詳細</h1>
      
      <!-- 商品がまだロードされていない場合のメッセージ -->
      <div v-if="item === null">商品データがありません。</div>
  
      <!-- 商品詳細情報 -->
      <div v-else>
        <div><strong>商品ID:</strong> {{ item.id }}</div>
        <div><strong>商品名:</strong> {{ item.name }}</div>
        <div><strong>価格:</strong> ¥{{ item.price.toLocaleString() }}</div>
        <div><strong>在庫数:</strong> {{ item.stock.quantity }}</div>
  
        <!-- 編集ボタン -->
        <button @click="toggleEdit" class="mt-4 px-4 py-2 bg-blue-500 text-white rounded">
          編集
        </button>
  
        <!-- 編集フォーム -->
        <div v-if="isEditing" class="mt-4">
          <div>
            <label for="name" class="block">商品名</label>
            <input type="text" v-model="editItem.name" id="name" class="border p-2 w-full" />
          </div>
          <div class="mt-2">
            <label for="price" class="block">価格</label>
            <input type="number" v-model="editItem.price" id="price" class="border p-2 w-full" />
          </div>
          <div class="mt-2">
            <label for="quantity" class="block">在庫数</label>
            <input type="number" v-model="editItem.stock.quantity" id="quantity" class="border p-2 w-full" />
          </div>
  
          <!-- 保存ボタン -->
          <button @click="saveItem" class="mt-4 px-4 py-2 bg-green-500 text-white rounded">
            保存
          </button>
        </div>
        <router-link to="/" class="btn">一覧に戻る</router-link>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import { useRoute } from 'vue-router';
  
  const route = useRoute();
  const item = ref(null);  // 商品詳細
  const isEditing = ref(false);  // 編集モードかどうか
  const editItem = ref({});  // 編集用の一時データ
  
  // 商品詳細の取得
  function fetchItemDetail() {
    const itemId = route.params.id;  // URLパラメータからIDを取得
    axios.get(`http://localhost:8080/api/items/${itemId}`)
      .then(response => {
        item.value = response.data;
        editItem.value = { ...response.data };  // 編集用のデータをセット
      })
      .catch(error => {
        console.error(error);
      });
  }
  
  // 編集モードを切り替え
  function toggleEdit() {
    isEditing.value = !isEditing.value;
  }
  
  // 商品情報を更新する
  function saveItem() {
    const itemId = route.params.id;
    axios.put(`http://localhost:8080/api/items/${itemId}`, editItem.value)
      .then(() => {
        item.value = { ...editItem.value };  // 更新したデータを反映
        isEditing.value = false;  // 編集モードを終了
      })
      .catch(error => {
        console.error(error);
      });
  }
  
  // コンポーネントがマウントされたときに商品データを取得
  onMounted(() => {
    fetchItemDetail();
  });
  </script>
  <style>
.btn {
  display: inline-block;
  padding: 0.1rem 0.5rem; /* 縦 横 */
  background-color: #3490dc;
  color: white;
  border-radius: 2px;
  text-decoration: none;
  font-size: 0.875rem; /* 文字サイズも調整するならここ */
}
.btn:hover {
  background-color: #2779bd;
}

  </style>
  