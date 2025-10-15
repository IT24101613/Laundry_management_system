package com.janiya.reviews_app_springboot.service;

import com.janiya.reviews_app_springboot.model.Complaint;
import com.janiya.reviews_app_springboot.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Optional<Complaint> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }

    public Complaint saveComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }

    public Complaint resolveComplaint(Long id) {
        Optional<Complaint> complaint = complaintRepository.findById(id);
        if (complaint.isPresent()) {
            Complaint resolvedComplaint = complaint.get();
            resolvedComplaint.setResolved(true);
            return complaintRepository.save(resolvedComplaint);
        }
        return null;
    }
}