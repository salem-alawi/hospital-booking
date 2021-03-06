package com.finalprojectaden.hospitalbooking.controller.admin;

import com.finalprojectaden.hospitalbooking.dto.admin.hospital.CreateAndUpdateHospitalDto;
import com.finalprojectaden.hospitalbooking.model.Doctor;
import com.finalprojectaden.hospitalbooking.model.Hospital;
import com.finalprojectaden.hospitalbooking.model.HospitalSection;
import com.finalprojectaden.hospitalbooking.service.DoctorService;
import com.finalprojectaden.hospitalbooking.service.HospitalSectionService;
import com.finalprojectaden.hospitalbooking.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class HospitalControllerAdmin extends AdminBaseController {


    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private HospitalSectionService hospitalSectionService;

    @GetMapping("/hospitals")
    public ResponseEntity findAllHospitalAdmin(Pageable pageable) {
       Page<Hospital> hospitalPage=this.hospitalService.findAllHospital(pageable);

        return new ResponseEntity(hospitalPage, HttpStatus.OK);
    }

    @PostMapping("/hospitals")
    public ResponseEntity createNewHospitalAdmin(@RequestBody CreateAndUpdateHospitalDto createAndUpdateHospitalDto) {

        Hospital hospital = this.hospitalService.createNewHospital(createAndUpdateHospitalDto);

        return new ResponseEntity(hospital, HttpStatus.OK);
    }

    @GetMapping("/hospitals/{id}")
    public ResponseEntity findOneHospital(@PathVariable("id") UUID hospitalId) throws Exception {

        Hospital hospital = this.hospitalService.findOneById(hospitalId);

        return new ResponseEntity(hospital, HttpStatus.OK);

    }

    @PutMapping("/hospitals/{id}")
    public ResponseEntity updateHospitalById(@PathVariable("id") UUID hospitalId,
                                             @RequestBody CreateAndUpdateHospitalDto createAndUpdateHospitalDto) throws Exception {

        this.hospitalService.updateHospital(hospitalId, createAndUpdateHospitalDto);

        return new ResponseEntity(HttpStatus.OK);

    }


    @DeleteMapping("/hospitals/{id}")
    public ResponseEntity disableHospital(@PathVariable("id") UUID hospitalId) throws Exception {

        this.hospitalService.disableHospital(hospitalId);
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/hospitals/{id}/sections")
    public ResponseEntity findAllHospitalSection(@PathVariable("id") UUID hospitalId) throws Exception {
        Hospital hospital = this.hospitalService.findOneById(hospitalId);

        List<HospitalSection> hospitalSectionList = hospitalSectionService.findAllByHospitalId(hospitalId);

        return new ResponseEntity(hospitalSectionList, HttpStatus.OK);

    }

    @PostMapping("/hospitals/{id}/sections/{sectionId}")
    public ResponseEntity addSectionToHospital(@PathVariable("id")UUID hospitalId,@PathVariable("sectionId")UUID sectionId) throws Exception {

        this.hospitalService.addSectionToHospital(hospitalId,sectionId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/hospitals/{id}/doctors")
    public ResponseEntity findAllHospitalDoctors(@PathVariable("id") UUID hospitalId) throws Exception {
        Hospital hospital = this.hospitalService.findOneById(hospitalId);

        List<Doctor> doctorList = doctorService.findAllByHospitalId(hospitalId);
        return new ResponseEntity(doctorList, HttpStatus.OK);
    }

    @PostMapping("/hospitals/{id}/images")
    public ResponseEntity updateHospitalsImage(@PathVariable("id")UUID hospitalId,@RequestBody List<String> images) throws Exception {

        Hospital hospital= this.hospitalService.findOneById(hospitalId);

        hospital.getHospitalStaticConfig().setImages(images);
        this.hospitalService.save(hospital);
        return new ResponseEntity(HttpStatus.OK);



    }

}
