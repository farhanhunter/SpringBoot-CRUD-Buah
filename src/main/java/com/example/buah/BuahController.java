package com.example.buah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/buah")
public class BuahController {

    @Autowired
    private BuahRepository buahRepository;

//    REST API Endpoints:
//    Mengambil semua buah (GET):
    @GetMapping
    public ResponseEntity<List<Buah>> viewBuahPage() {
        List<Buah> buahList = buahRepository.findAll();
        return ResponseEntity.ok(buahList);
    }

//    Mengambil satu buah berdasarkan ID (GET):
    @GetMapping("/{id}")
    public ResponseEntity<Buah> getById(@PathVariable("id") Long id) {
        Buah buah = buahRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invalid Buah Id:" + id));
        return ResponseEntity.ok(buah);
    }

//    Menambah buah baru (POST):
    @PostMapping("/")
    public ResponseEntity<Buah> saveBuah(@RequestBody Buah buah) {
        buahRepository.save(buah);
        return ResponseEntity.ok(buah);
    }

//    Memperbarui buah (PUT):
    @PutMapping("/{id}")
    public ResponseEntity<Buah> updateById(@PathVariable("id") Long id, @RequestBody Buah buah) {
        Buah buah1 = buahRepository.findById(id).get();
        buah1.setNama(buah.getNama());
        Buah updatedBuah = buahRepository.save(buah1);
        return ResponseEntity.ok(updatedBuah);
    }

//    Menghapus buah (DELETE):
    @DeleteMapping("/{id}")
    public ResponseEntity<Buah> deleteBuah(@PathVariable("id") Long id) {
        Buah buah = buahRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invalid Buah Id:" + id));
        buahRepository.delete(buah);
        return ResponseEntity.ok(buah);
    }

//    Web View Endpoints:
//    Menampilkan halaman daftar buah:
    @GetMapping("/list")
    public String listPage(Model model) {
        List<Buah> buahList = buahRepository.findAll();
        model.addAttribute("buahList", buahList);
        return "buah-list"; //template buah-list.html
    }

//    Menampilkan form untuk menambah buah:
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("buah", new Buah());
        model.addAttribute("formTitle", "Tambah Buah");
        return "buah-form"; //template buah-form.html
    }

//    Menampilkan form untuk mengedit buah:
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id) {
        return "edit-form"; //template buah-form.html
    }

//    Exception Handler:
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error"; // template error.html
    }
}