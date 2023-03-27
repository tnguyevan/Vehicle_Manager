package com.example.service.impl;

import com.example.dto.DetailDTO;
import com.example.dto.ListUserDTO;
import com.example.entity.Details;
import com.example.repository.IDetailsRepository;
import com.example.service.IDetailService;
import com.example.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class DetailService implements IDetailService {

    @Autowired
    private IDetailsRepository iDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Page<DetailDTO> getListDetailByMovementHistoryId(Pageable pageable,  int movementHistoryId) {
        Page<Details> detailsPage = iDetailsRepository.getByMovementHistoryId(pageable, movementHistoryId);
        // convert entities --> dtos
        List<DetailDTO> detailDTOS = modelMapper.map(detailsPage.getContent(), new TypeToken<List<DetailDTO>>() {
        }.getType());

        for (Details details : detailsPage) {

            // giờ bắt đầu
            LocalDateTime startDate = details.getStartTime();
            String startTimeStr = String.format("%02d:%02d:%02d", startDate.getHour(), startDate.getMinute(), startDate.getSecond());

            // ngày đi
            String startDateStr = details.getStartTime().toLocalDate().toString();

            // Tổng thời gian di chuyển

            String localTimes = iDetailsRepository.getTimeById(details.getId());
            int sum = DateUtil.second(localTimes);
            int second = sum;

            String totalTimeStr = DateUtil.timeStr(second);


            // giờ kết thúc
            LocalDateTime endTime = details.getEndTime();

            String endTimeStr = String.format("%02d:%02d:%02d", endTime.getHour(), endTime.getMinute(), endTime.getSecond());

            for (DetailDTO detailDTO : detailDTOS) {
                if (detailDTO.getTotalTravelTime() == null) {
                    detailDTO.setStartTime(startTimeStr);
                    detailDTO.setStartDate(startDateStr);
                    detailDTO.setTotalTravelTime(totalTimeStr);
                    detailDTO.setEndTime(endTimeStr);
                    break;
                }
            }
        }
        Page<DetailDTO> dtoPages = new PageImpl<>(detailDTOS, pageable, detailsPage.getTotalElements());

        return dtoPages;
    }
}