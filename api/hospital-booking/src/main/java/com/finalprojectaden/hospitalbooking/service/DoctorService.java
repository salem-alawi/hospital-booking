package com.finalprojectaden.hospitalbooking.service;

import com.finalprojectaden.hospitalbooking.dto.admin.doctors.CreateNewDoctor;
import com.finalprojectaden.hospitalbooking.dto.admin.doctors.UpdateDoctor;
import com.finalprojectaden.hospitalbooking.model.Doctor;
import com.finalprojectaden.hospitalbooking.model.Hospital;
import com.finalprojectaden.hospitalbooking.model.HospitalSection;
import com.finalprojectaden.hospitalbooking.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSectionService hospitalSectionService;

    public Page<Doctor> findAllDoctor(Pageable pageable) {
        return this.doctorRepository.findAll(pageable);
    }

    public Doctor updateOneById(Doctor doctor) throws Exception {

//        Doctor doctor = this.doctorRepository.findById(doctorId).orElseThrow(() -> new Exception("can't find doctor"));
//        HospitalSection hospitalSection = this.hospitalSectionService.findOneById(updateDoctor.getSectionId());
//        doctor.update(updateDoctor, hospitalSection);
        return this.doctorRepository.save(doctor);
    }


    public List<Doctor> findAllByHospitalId(UUID hospitalId) {

        return this.doctorRepository.findAllByHospitalId(hospitalId.toString());
    }

    public Doctor createNewDoctor(CreateNewDoctor createNewDoctor) throws Exception {

//        Hospital hospital = this.hospitalService.findOneById(createNewDoctor.getHospitalId());
        HospitalSection hospitalSection = this.hospitalSectionService.findOneById(createNewDoctor.getHospitalSectionId());

        Doctor doctor = new Doctor(createNewDoctor, hospitalSection.getHospital(), hospitalSection);
        return this.doctorRepository.save(doctor);

    }

    public Long findCountByHospitalISectionId(UUID sectionId) {

        return this.doctorRepository.findCountByHospitalSectionId(sectionId.toString());

    }

    public void setDoctorHospitalNullByHospitalSectionId(UUID hospitalSectionId) {

        List<Doctor> doctorList = this.doctorRepository.findAllByHospitalSectionId(hospitalSectionId.toString());

        doctorList.forEach((doctor -> {
            doctor.setHospitalSectionId(null);
        }));
        doctorRepository.saveAll(doctorList);

    }

    public void deleteOneDoctorById(UUID doctorId) throws Exception {

        Doctor doctor= this.doctorRepository.findById(doctorId).orElseThrow(()-> new Exception("can't find doctor"));
        this.doctorRepository.delete(doctor);
    }
}
