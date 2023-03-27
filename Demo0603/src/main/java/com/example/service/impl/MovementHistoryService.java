package com.example.service.impl;

import com.example.dto.MovementHistoryDTO;
import com.example.entity.MovementHistory;
import com.example.repository.IDetailsRepository;
import com.example.repository.IMovementHistoryRepository;
import com.example.service.IMovementHistoryService;
import com.example.util.DateUtil;
import com.example.util.UserDetailsUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class MovementHistoryService implements IMovementHistoryService {

    @Autowired
    private IDetailsRepository iDetailsRepository;
    @Autowired
    private IMovementHistoryRepository iMovementHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<MovementHistoryDTO> getMovementHistoryByVehicleId(int vehicleId)  {

        List<MovementHistory> movementHistoryList = iMovementHistoryRepository.getByUsernameAndVehicleId(UserDetailsUtils.UserDetails().getUsername(), vehicleId);
        // convert entities --> dtos
        List<MovementHistoryDTO> movementHistoryDTO = modelMapper.map(movementHistoryList, new TypeToken<List<MovementHistoryDTO>>() {
        }.getType());
        for (MovementHistory movementHistory : movementHistoryList) {
            // tốc độ trung bình
            float avgSpeed = iDetailsRepository.getAVGByMovementHistoryId(movementHistory.getId());

            // số lần di chuyển
            int pauseCount = iDetailsRepository.getPauseCountByMovementHistoryId(movementHistory.getId());

            // giờ bắt đầu
            LocalDateTime startDate = movementHistory.getStartDate();
            String startTimeStr = String.format("%02d:%02d:%02d", startDate.getHour(), startDate.getMinute(), startDate.getSecond());

            // ngày đi
            String startDateS = movementHistory.getStartDate().toLocalDate().toString();

            // Tổng thời gian di chuyển
            int second = 0;
            List<String> localTimes = iDetailsRepository.getListTimeByMovementHistoryId(movementHistory.getId());
            for (String totalTime : localTimes) {
                int sum = DateUtil.second(totalTime);
                second += sum;
            }
            String totalTimeStr = DateUtil.timeStr(second);


            // giờ kết thúc
            int[] ints = new int[pauseCount];
            List<String> endTimes = iDetailsRepository.getTimeByMovementHistoryId(movementHistory.getId());
            for (String endTime : endTimes) {
                int sum = DateUtil.second(endTime);
                for (int i = 0; i < pauseCount; ) {
                    if (ints[i] != 0) {
                        i++;
                        continue;
                    }
                    ints[i] = sum;
                    break;
                }
            }
            int max = ints[0];
            for (int num : ints) {
                if (max < num)
                    max = num;
            }

            String endTimeStr = DateUtil.timeStr(max);

            for (MovementHistoryDTO movementHistoryDTO1 : movementHistoryDTO) {
                if (movementHistoryDTO1.getAvgSpeed() == 0) {
                    movementHistoryDTO1.setAvgSpeed(avgSpeed);
                    movementHistoryDTO1.setPauseCount(pauseCount);
                    movementHistoryDTO1.setStartTime(startTimeStr);
                    movementHistoryDTO1.setStartDate(startDateS);
                    movementHistoryDTO1.setTotalTravelTime(totalTimeStr);
                    movementHistoryDTO1.setEndTime(endTimeStr);
                    break;
                }
            }
        }
        return movementHistoryDTO;
    }

}