import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'  // これを追加
const app = createApp(App)  // アプリケーションインスタンスを作成してappに格納
app.use(router)             // appにrouterを適用
app.mount('#app')           // 最後にアプリを#appにマウント