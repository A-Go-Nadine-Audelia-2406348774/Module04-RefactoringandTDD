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


