# FlightMate

FlightMate 是一個以 Jetpack Compose 即 MVVM 架構實作的 Android App，提供航班查詢與即時匯率換算功能。

## Features

### 航班資訊查詢
- 顯示航班編號、航空公司、時間與狀態
- 支援篩選功能：依航班狀態與航空公司代碼多重篩選
- 篩選 UI 採用 Material3 FilterChip + Drawer 組合
- 空資料或無匹配篩選條件時會顯示適當提示

### 自動刷新
- 自動每 10 秒刷新一次航班資料
- 若請求失敗，自動刷新會停止，需使用者手動重試
- 支援手動「重新整理」會重啟刷新計時器（避免多重 coroutine）

### 匯率轉換
- 使用者可輸入金額與選擇基礎幣別（預設 USD）
- 顯示常用幣別（預設六種）匯率與轉換後金額
- 支援即時輸入與轉換

### 錯誤處理
- 使用 sealed class `AppException` 統一錯誤管理
- 視不同錯誤顯示相對應錯誤畫面（網路錯誤／API 錯誤／未知錯誤）

### 深色主題支援
- 支援 Material 3 的深色與亮色主題切換
- 可手動設定是否啟用動態色彩，預設為啟動（dynamicColor = true）

### 螢幕轉向支援
螢幕旋轉時畫面重建，不會遺失資料或閃爍

## 技術堆棧
- Kotlin + Jetpack Compose：現代聲明式 UI 開發
- ViewModel + StateFlow：UI 狀態管理
- Retrofit + Moshi：API 請求與 JSON 解析
- Hilt：依賴注入
- Coil：非同步圖片載入
- Material3 + Compose Theme：符合 MaterialDesign 的一致性 UI 設計
- JUnit + Turbine：單元測試與 Flow 驗證

## API Key 管理

使用 `local.properties` 存放私密金鑰，並透過 Gradle 注入。

## TODO

- [ ] 加入航班即時狀態更新動畫
- [ ] 導入 CI/CD workflow
