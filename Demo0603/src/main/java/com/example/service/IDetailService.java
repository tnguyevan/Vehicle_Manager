package com.example.service;

import com.example.dto.DetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;


public interface IDetailService {

    Page<DetailDTO> getListDetailByMovementHistoryId(Pageable pageable, int movementHistoryId);


}
