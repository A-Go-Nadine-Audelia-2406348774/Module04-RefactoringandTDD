Nama = Go Nadine Audelia
NPM = 2406348774

REFLECTION MODULE 2

1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them. 

Saya sempat mengalami isu peringatan keamanan kotlin:S6474 karena proyek menggunakan library eksternal tanpa verifikasi integritas, yang menyebabkan Security Hotspots pada SonarCloud. Awalnya cara saya membenarkan adalah dengan membuat verification-metadata.xml, tapi ternyata ada cara lain yang lebih praktis sehingga saya menghapus file verification-metadata.xml. Cara yang akhirnya saya pakai adalah pergi langsung ke web SonarCloud dan di bagian Security Hotspot saya ubah untuk menandainya menjadi Reviewed-Safe. Lebih baik pakai cara ini karena saya cukup yakin library yang saya gunakan itu terpercaya.
Saya juga sempat mengalami masalah kegagalan di unit test dan yang saya lakukan untuk membenarkan isu itu adalah dengan memperbaiki method yang menjadi penyebab masalah dan menambahkan unit test agar bisa mencapai 100% coverage.

2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!
Menurut saya, implementasi saat ini udah memenuhi Continuous Integration karena setiap kali ada perubahan kode yang di-push ke repositori GitHub, GitHub actions langsung menjalankan proses build,  unit test, dan mengecek quality code melalui SonarCloud. Selain itu, implementasi juga telah memenuhi kriteria Continuous Deployment melalui integrasi dengan PaaS Koyeb yang menggunakan metode pull based approach. Setiap perubahan yang lolos tahap CI dan dimerge ke branch main akan otomatis dirilis ke lingkungan produksi.


REFLECTION MODULE 3

1. Explain what principles you apply to your project!
a. Single Responsibility Principle
Saya menerapkan Single Responsibility Principle/SRP dengan cara memisahkan CarController dan ProductController ke dalam file yang berbeda. Sebelumnya kedua controller ini berada dalam satu file yang berarti satu kelas memiliki lebih dari satu alasan untuk menerapkan perubahan kode jika ada.
b. Liskov Substitution Principle 
Saya menghapus inheritance pada CarController karena CarController tidak benar-benar membutuhkan fungsionalitas dari ProductController. Jika dibiarkan maka dapat menyebabkan perilaku yang tidak diharapkan ketika metode induk diubah.
c. Dependency Inversion Principle
Saya membuat Interface untuk service dan repository. Controller sekarang bergantung pada interface dan bukan pada implementasi/Impl.

2. Explain the advantages of applying SOLID principles to your project with examples
a. Single Responsibility Principle
Keuntungan SRP adalah jika ada perubahan pada logika bisnis car misalnya perubahan skema ID, saya hanya perlu mengubah file terkait car tanpa risiko merusak fitur product.
b. Liskov Substitution Principle 
Dengan memastikan class tidak melakukan inheritance yang salah maka kode menjadi lebih stabil. Menghapus inheritance yang tidak perlu mencegah hal yang tidak diinginkan ketika ada perubahan pada kelas lain.
c. Dependency Inversion Principle 
Penerapan DIP dapat dengan mudah mengganti implementasi penyimpanan data. Contohnya adalah jika ingin mengganti penyimpanan data yang menggunakan ArrayList menjadi database nyata, maka cukup membuat kelas implementasi baru yang mengikuti Interface yang sama tanpa perlu mengubah kode di sisi Controller.

3. Explain the disadvantages of not applying SOLID principles to your project with examples.
a. Single Responsibility Principle
Perubahan di satu bagian kode dapat menyebabkan kerusakan di bagian lain yang tidak terkait secara logis karena tanggung jawab class lebih dari satu.
b. Liskov Substitution Principle
Kode menjadi sangat sulit untuk diubah. Misalnya, ketika CarController masih extends ProductController maka perubahan kecil pada mapping URL di product dapat merusak routing pada car karena adanya ketergantungan yang tidak perlu.
c. Dependency Inversion Principle 
Kekurangannya adalah modul tingkat tinggi menjadi sangat bergantung pada modul tingkat rendah. Contohnya adalah jika tidak menggunakan interface untuk service maka setiap kali ada perubahan pada parameter metode di CarServiceImpl, controller akan langsung mengalami kegagalan compilation karena bergantung pada implementasi itu.