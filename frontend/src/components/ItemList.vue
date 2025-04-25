<template>
  <div class="p-4">
    <h1 class="text-2xl font-bold mb-4">商品一覧</h1>
    <!-- 検索入力 -->
    <div class="mb-4">
      <input v-model="searchKeyword" type="text" placeholder="商品名で検索" class="p-2 border rounded" />
      <button @click="searchItems" class="px-4 py-2 ml-2 bg-blue-500 text-white rounded">検索</button>
    </div>

    <div v-if="items.length === 0">データがありません。</div>

    <table v-else class="table-auto w-full border-collapse border border-gray-300">
      <thead>
        <tr>
          <th class="border p-2" @click = "sort('id')">
            ID
            <span v-if="sortField === 'id'">
              <span v-if="sortOrder === 'asc'">▲</span>
              <span v-if="sortOrder === 'desc'">▼</span>
            </span></th>
          <th class="border p-2" @click = "sort('name')">
            名前
            <span v-if="sortField === 'name'">
              <span v-if="sortOrder === 'asc'">▲</span>
              <span v-if="sortOrder === 'desc'">▼</span>
            </span></th>
          <th class="border p-2" @click = "sort('price')">価格
            <span v-if="sortField === 'price'">
              <span v-if="sortOrder === 'asc'">▲</span>
              <span v-if="sortOrder === 'desc'">▼</span>
            </span>
          </th>
          <th class="border p-2" @click = "sort('quantity')">在庫数
            <span v-if="sortField === 'quantity'">
              <span v-if="sortOrder === 'asc'">▲</span>
              <span v-if="sortOrder === 'desc'">▼</span>
            </span>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in items" :key="item.id">
          <td class="border p-2"><router-link :to="`/item/${item.id}`" class="border p-2">{{ item.id }}</router-link></td>
          <td class="border p-2">{{ item.name }}</td>
          <td class="border p-2">{{ formatPrice(item.price) }}</td>
          <td class="border p-2">{{ item.stock.quantity }}</td>
        </tr>
      </tbody>
    </table>

    <!-- ページ送りボタン -->
    <div class="mt-4 flex justify-center items-center space-x-4">
      <button @click="prevPage" :disabled="currentPage === 0"
              class="px-4 py-2 bg-gray-200 rounded disabled:opacity-50">前のページ</button>
      <span>ページ {{ currentPage + 1 }} / {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage >= totalPages - 1"
              class="px-4 py-2 bg-gray-200 rounded disabled:opacity-50">次のページ</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const items = ref([]);
const currentPage = ref(0);
const totalPages = ref(1);
const searchKeyword = ref(''); // 検索キーワード

// ソート状態（フィールド名、順序）
const sortField = ref('id');  // デフォルトソートフィールド
const sortOrder = ref('asc'); // デフォルトソート順

function fetchItems() {
  const sortQuery = `${sortField.value},${sortOrder.value}`;
  const keywordQuery = searchKeyword.value ? `&keyword=${encodeURIComponent(searchKeyword.value)}` : '';  // 検索キーワードをクエリに追加
  axios.get(`http://localhost:8080/api/items?page=${currentPage.value}&size=10&sort=${sortQuery}${keywordQuery}`,{
  headers: {
    'Cache-Control': 'no-cache',  // キャッシュを無効にするヘッダー)
  }})
    .then(response => {
      items.value = response.data.content;
      totalPages.value = response.data.totalPages;
    })
    .catch(error => {
      console.error(error);
    });
}
// ソート順の切り替え
function sort(field) {
  if (sortField.value === field) {
    // 同じフィールドをクリックした場合、昇順<->降順の切り替え
    sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc';
  } else {
    // 新しいフィールドをクリックした場合、昇順に設定
    sortField.value = field;
    sortOrder.value = 'asc';
  }
  fetchItems(); // ソート後、データを再取得
}
function nextPage() {
  if (currentPage.value < totalPages.value - 1) {
    currentPage.value++;
    fetchItems();
  }
}

function prevPage() {
  if (currentPage.value > 0) {
    currentPage.value--;
    fetchItems();
  }
}
function searchItems() {
  currentPage.value = 0; // 検索後、ページは最初に戻す
  fetchItems();
}
onMounted(() => {
  fetchItems();
});
// 価格を桁区切りのカンマを付けて表示する
function formatPrice(price) {
  return price.toLocaleString();
}
</script>

<style scoped>
/* ソート対象の列にカーソルを合わせたときにポインターが表示される */
th {
  cursor: pointer;
}
.table-auto {
  border-collapse: collapse;
  width: 100%;
  table-layout: fixed;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

th {
  background-color: #f4f4f4;
  font-weight: bold;
}

td {
  background-color: #fff;
}

th, td {
  min-width: 80px;
}

th.sortable:hover {
  background-color: #eee;
}

td.number {
  text-align: right;
}
/* 偶数列に色をつける */
tr:nth-child(even) td {
  background-color: #f9f9f9;
}
</style>
