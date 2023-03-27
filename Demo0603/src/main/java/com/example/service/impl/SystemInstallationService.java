package com.example.service.impl;

import com.example.dto.ChangeSystemInstallationDTO;
import com.example.dto.UserDTO;
import com.example.entity.SystemInstallation;
import com.example.repository.ISystemInstallationRepository;
import com.example.service.ISystemInstallationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SystemInstallationService implements ISystemInstallationService {

    @Autowired
    private ISystemInstallationRepository iSystemInstallationRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ChangeSystemInstallationDTO getSystemInstallationById(int id) {
        SystemInstallation systemInstallation = iSystemInstallationRepository.findById(id).get();
        ChangeSystemInstallationDTO map = modelMapper.map(systemInstallation, ChangeSystemInstallationDTO.class);
        return map;
    }

    @Override
    public ChangeSystemInstallationDTO changeSystemInstallation(int id, ChangeSystemInstallationDTO dto) {

        SystemInstallation systemInstallation = iSystemInstallationRepository.findById(id).get();
        systemInstallation.setBanner(dto.getBanner());
        systemInstallation.setLogo(dto.getLogo());
        SystemInstallation systemInstallation1 = iSystemInstallationRepository.save(systemInstallation);
        ChangeSystemInstallationDTO map = modelMapper.map(systemInstallation1, ChangeSystemInstallationDTO.class);
        return map;
    }
}