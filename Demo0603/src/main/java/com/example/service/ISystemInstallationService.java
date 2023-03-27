package com.example.service;

import com.example.dto.ChangeSystemInstallationDTO;
import com.example.entity.SystemInstallation;

public interface ISystemInstallationService {


    ChangeSystemInstallationDTO getSystemInstallationById(int id);


    ChangeSystemInstallationDTO changeSystemInstallation(int id, ChangeSystemInstallationDTO dto);

}
