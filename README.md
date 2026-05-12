# FastMart - E-Commerce Android App

## Project Overview

FastMart is a mobile e-commerce application built for Android. It allows users to browse, search, and purchase products. The app has two main user types:
- **Buyers**: Can browse products, add to cart, make purchases, and view order history
- **Sellers**: Can add new products, view their orders, and manage inventory

This project demonstrates real-time database synchronization using Firebase, local data storage with SQLite, and modern Android development practices.

---

## Requirements Implemented

1. **Real-Time Database Synchronization**
   - All product data fetched dynamically from Firebase Realtime Database
   - Newly added products by sellers appear immediately on buyer's home screen
   - No need to restart app to see updates
   - Uses ValueEventListener for continuous database monitoring

2. **Cart Management with SQLite**
   - Products stored locally in SQLite database
   - Quantity management with +/- buttons
   - Update operations using SQLite UPDATE queries
   - Delete operations via three-dot menu
   - Real-time total price calculation

3. **Products Added From Multiple Sources**
   - From Product Description Page (Buy Now button)
   - From Favorites Page (cart icon)
   - All synced with SQLite

4. **Checkout System**
   - Fetch all cart items from SQLite
   - Prepare detailed order summary
   - Send SMS confirmation to emulator device
   - Store order details in Firebase
   - Clear cart after successful checkout

5. **Order History**
   - Buyers can view their past orders
   - Sellers can view orders from customers
   - Order details include: products, quantities, prices, timestamps

---

## Tech Stack

- **Java** - Main programming language
- **Android SDK** - Android development (API Level 28+)
- **Firebase Realtime Database** - Cloud database for products and orders
- **Firebase Authentication** - User authentication
- **SQLite** - Local database for cart and favorites
- **RecyclerView** - Display lists and grids
- **SharedPreferences** - User session management
- **Android SMS Manager** - Send order confirmation SMS
- **Gradle** - Build automation

---

## Dependencies

```gradle
dependencies {
    // Android Core
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    
    // Firebase
    implementation 'com.google.firebase:firebase-database:20.2.4'
    implementation 'com.google.firebase:firebase-auth:22.1.2'
    implementation 'com.google.firebase:firebase-storage:20.2.1'
    
    // Material Design
    implementation 'com.google.android.material:material:1.9.0'
    
    // RecyclerView
    implementation 'androidx.recyclerview:recyclerview:1.3.0'
    
    // Glide (Image Loading)
    implementation 'com.github.bumptech.glide:glide:4.15.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.15.1'
}
```

---

## How to Run the Project

### Prerequisites:
- Android Studio (Latest version recommended)
- Android SDK (API 28 or higher)
- Java JDK (Version 11 or higher)

### Setup Steps:

**Step 1: Open the Project**
```bash
File → Open → Select the Assignment3 folder
```

**Step 2: Configure Firebase**
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or use existing
3. Add Android app to your Firebase project
4. Download `google-services.json`
5. Place `google-services.json` in `app/` folder
6. Enable Realtime Database and Authentication in Firebase Console

**Step 3: Set Up Android Emulator**
```bash
Tools → AVD Manager → Create Virtual Device
Select device → Select Android 12 or higher → Click Finish
```

**Step 4: Build and Run**
```bash
# In Android Studio:
Click "Run" button (Shift + F10)
Select your emulator device
App will install and launch
```

---

## Project Structure

```
Assignment3/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/assignment3/
│   │       │   ├── data/
│   │       │   │   ├── model/          (Data models)
│   │       │   │   ├── database/       (SQLite helpers)
│   │       │   │   └── utils/          (Firebase initializer)
│   │       │   └── view/
│   │       │       ├── adapter/        (RecyclerView adapters)
│   │       │       └── *Activity.java  (Activity screens)
│   │       ├── res/
│   │       │   ├── drawable/           (Images)
│   │       │   ├── layout/             (XML layouts)
│   │       │   └── values/             (Strings, colors, styles)
│   │       └── AndroidManifest.xml
│   ├── build.gradle
│   └── google-services.json
├── build.gradle
└── settings.gradle
```

---

## Database Structure

### Firebase Realtime Database:
```
/Products
  /P0001
    - id: "P0001"
    - name: "Product Name"
    - category: "Category"
    - price: "$99.99"
    - description: "..."
    - sellerId: "seller1"
    - imageRes: 123456
    - isDeal: true

/Orders
  /buyer123
    /order_timestamp
      - buyerId: "buyer123"
      - orderDate: "2026-05-12"
      - totalPrice: "$949.97"
      - items: [...]
```

### SQLite Database (Local):
```
CartDatabase:
  cart_items
    - id, productId, productName, price, quantity, imageRes

FavoritesDatabase:
  favorites
    - id, productId, productName, price, imageRes
```


