Nama = Go Nadine Audelia
NPM = 2406348774

REFLECTION
1. Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule,chapter “Evaluating Your Testing Objectives”), whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time  you make more tests.
Setelah mengikuti alur TDD dalam pengerjaan modul Order ini, saya merasa alur ini sangat berguna. Sesuai dengan prinsip Percival, TDD membantu saya dalam beberapa hal yaitu dengan menulis tes terlebih dahulu, saya harus memikirkan bagaimana struktur constructor dan method pada Order dan OrderRepository sebelum benar-benar mengimplementasikannya. Selain itu, saat melakukan refactor, saya merasa aman karena sudah ada unit test yang menjaga logika tersebut. Untuk ke depannya, saya merasa perlu lebih teliti dalam memisahkan cases di tahap awal agar tidak ada skenario yang terlewat saat proses implementasi berlangsung.

2. You have created unit tests in Tutorial. Now reflect whether your tests have successfully followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you create more tests.
Secara keseluruhan, unit test yang telah dibuat untuk Order sudah mengikuti prinsip F.I.R.S.T.Berikut adalah alasannya:
Fast: Test berjalan sangat cepat karena menggunakan Mockito untuk melakukan mocking pada OrderRepository. 
Independent: Setiap method test bersifat independen. Penggunaan @BeforeEach memastikan setiap test dimulai dengan data orders yang baru dan bersih, sehingga hasil satu test tidak memengaruhi test lainnya.
Repeatable: Test ini dapat dijalankan di lingkungan mana pun dengan hasil yang konsisten karena tidak bergantung pada faktor eksternal seperti jaringan atau waktu sistem.
Self-Validating: Test menggunakan assertions seperti assertEquals, assertNull, dan assertThrows. Jika tes lulus, berarti logika benar.
Thorough/Timely: Test mencakup happy path dan unhappy path. Test juga dibuat tepat waktu sesuai dengan kaidah TDD.
Untuk langkah selanjutnya, saya akan terus konsisten menerapkan mocking untuk menjaga kecepatan dan memastikan cakupan tes mencakup lebih banyak variasi.


## REFLEKSI BONUS 2
Nama = Neal Guarddin\
NPM = 2406348282
### 1. Apa pendapat saya tentang kode pasangan saya? Apakah masih ada aspek yang kurang?

Secara umum, kode pasangan saya sudah berjalan dengan baik dan fitur utama seperti order dan payment sudah berhasil diimplementasikan. Struktur project juga sudah cukup jelas karena dipisahkan ke dalam layer controller, service, repository, dan model. Namun, setelah saya review, masih ada beberapa aspek maintainability yang bisa ditingkatkan.

Pada `OrderController.java`, saya menemukan bahwa controller masih memegang terlalu banyak tanggung jawab. Method `createOrderPost()` tidak hanya menangani request dari user, tetapi juga membuat dummy product, membuat object `Order`, menghasilkan UUID, menentukan timestamp, lalu menampilkan riwayat order. Ini membuat controller menjadi terlalu gemuk dan kurang fokus pada tugas utamanya sebagai penghubung antara request dan service.

Pada `PaymentServiceImpl.java`, saya menemukan penggunaan string literal secara langsung untuk status pembayaran, seperti `"SUCCESS"` dan `"REJECTED"`. Cara ini membuat kode lebih rentan terhadap typo dan lebih sulit dirawat apabila di masa depan terdapat perubahan status atau penambahan aturan baru. Selain itu, dependency pada kedua class tersebut sebelumnya masih menggunakan field injection, sehingga dependency kurang eksplisit dan desain class menjadi kurang rapi.

Jadi, menurut saya kode pasangan saya sudah fungsional, tetapi masih ada aspek pemisahan tanggung jawab, kejelasan dependency, dan keterbacaan logika bisnis yang perlu diperbaiki agar maintainability-nya lebih baik.

### 2. Apa yang saya lakukan untuk berkontribusi pada kode pasangan saya?

Kontribusi saya berfokus pada refactoring untuk meningkatkan maintainability tanpa mengubah behavior utama dari program. Saya melakukan review terhadap kode pasangan saya, lalu memilih dua file yang paling layak direfactor, yaitu `OrderController.java` dan `PaymentServiceImpl.java`.

Pada `OrderController.java`, saya merapikan tanggung jawab controller dengan mengekstrak logika pembuatan order ke helper method, serta mengekstrak logika untuk menampilkan history order ke method terpisah. Saya juga mengganti field injection menjadi constructor injection agar dependency lebih jelas.

Pada `PaymentServiceImpl.java`, saya merapikan logika perubahan status pembayaran dengan menghilangkan magic strings melalui konstanta, memisahkan validasi status ke method khusus, dan memisahkan mapping status payment ke status order ke method terpisah. Saya juga mengganti field injection menjadi constructor injection.

Dengan kontribusi ini, kode menjadi lebih mudah dibaca, lebih mudah dirawat, dan lebih jelas alur tanggung jawabnya.

### 3. Code smell apa yang saya temukan pada kode pasangan saya?

Saya menemukan beberapa code smell utama pada dua file yang saya refactor.

#### a. Mixed Responsibility / Large Controller pada `OrderController.java`
Method `createOrderPost()` memiliki terlalu banyak tanggung jawab sekaligus. Method tersebut menerima request, membuat dummy product, membuat object `Order`, menghasilkan ID, menentukan waktu order, menyimpan order, lalu juga menyiapkan tampilan history. Ini merupakan code smell karena controller seharusnya lebih fokus pada koordinasi request-response, bukan menampung terlalu banyak detail logika.

#### b. Field Injection
Baik pada `OrderController.java` maupun `PaymentServiceImpl.java`, dependency awalnya di-inject menggunakan `@Autowired` pada field. Ini termasuk code smell karena dependency menjadi tersembunyi, tidak eksplisit, dan kurang baik untuk maintainability maupun testing.

#### c. Magic Strings pada `PaymentServiceImpl.java`
Di `PaymentServiceImpl.java`, status seperti `"SUCCESS"` dan `"REJECTED"` ditulis langsung di dalam logika program. Ini adalah code smell karena raw string seperti ini mudah menimbulkan typo, sulit dilacak, dan tidak fleksibel jika nanti ada perubahan.

#### d. Conditional Logic yang Kurang Terstruktur pada `PaymentServiceImpl.java`
Logika validasi status dan pengubahan status order sebelumnya masih ditulis langsung di dalam method `setStatus()` dengan bentuk `if-else`. Walaupun masih bekerja, struktur seperti ini kurang rapi dan membuat method menjadi kurang fokus.

### 4. Langkah refactoring apa yang saya sarankan dan saya lakukan untuk memperbaiki code smell tersebut?

Saya melakukan beberapa langkah refactoring berikut.

#### a. Mengubah field injection menjadi constructor injection
Pada `OrderController.java` dan `PaymentServiceImpl.java`, saya mengganti dependency injection dari field injection menjadi constructor injection. Dengan cara ini, dependency menjadi lebih eksplisit, object menjadi lebih mudah dipahami, dan class lebih mudah diuji.

#### b. Mengekstrak logika pembuatan order pada `OrderController.java`
Saya memecah method `createOrderPost()` dengan memindahkan logika pembuatan dummy order ke helper method `buildDummyOrder()` dan `createDummyProducts()`. Saya juga memindahkan logika untuk menampilkan history order ke method `renderOrderHistory()`. Refactoring ini membuat method utama menjadi lebih pendek, lebih fokus, dan lebih mudah dibaca.

#### c. Menghilangkan magic strings pada `PaymentServiceImpl.java`
Saya mengganti string literal status pembayaran menjadi konstanta, seperti `PAYMENT_STATUS_SUCCESS` dan `PAYMENT_STATUS_REJECTED`. Refactoring ini membuat kode lebih aman dan lebih mudah dipelihara.

#### d. Memisahkan validasi status dan mapping status order
Pada `PaymentServiceImpl.java`, saya memecah logika di dalam `setStatus()` menjadi beberapa bagian kecil, yaitu `validateStatus()` untuk memvalidasi input status dan `resolveOrderStatus()` untuk menentukan status order yang sesuai. Dengan begitu, method `setStatus()` menjadi lebih jelas, lebih rapi, dan tanggung jawab tiap bagian lebih terpisah.

Secara keseluruhan, refactoring yang saya lakukan berfokus pada peningkatan maintainability, readability, dan separation of concerns, tanpa mengubah tujuan utama dari fitur yang sudah ada.