package org.revo.charity.Controller;

import org.revo.charity.Domain.Phone;
import org.revo.charity.Service.PhoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/phones")
public class PhoneController {
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping
    public ResponseEntity<List<Phone>> findAll() {
        return ResponseEntity.ok(phoneService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Phone>> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(phoneService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<Phone> save(@RequestBody Phone phone) {
        return ResponseEntity.ok(phoneService.save(phone));
    }
}
