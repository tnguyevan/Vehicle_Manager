package com.example.service.impl;

import com.example.dto.ListVehicleDTO;
import com.example.dto.VehicleDTO;
import com.example.entity.*;
import com.example.form.VehicleFormCreate;
import com.example.repository.IDetailsRepository;
import com.example.repository.IMovementHistoryRepository;
import com.example.repository.IUserRepository;
import com.example.repository.IVehicleRepository;
import com.example.service.IVehicleService;
import com.example.util.DateUtil;
import com.example.util.UserDetailsUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
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
public class VehicleService implements IVehicleService {

    @Autowired
    private IVehicleRepository iVehicleRepository;

    @Autowired
    private IDetailsRepository iDetailsRepository;
    @Autowired
    private IMovementHistoryRepository iMovementHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<VehicleDTO> getListVehicleByUsername() {
        List<Vehicle> vehicleList = iVehicleRepository.findByUsernameAndStartDate(UserDetailsUtils.UserDetails().getUsername(), DateUtil.todayStr());
        // convert entities --> dtos
        List<VehicleDTO> dtos = modelMapper.map(vehicleList, new TypeToken<List<VehicleDTO>>() {
        }.getType());

        for (Vehicle vehicle : vehicleList) {
            MovementHistory movementHistory = iMovementHistoryRepository.getByVehicleIdAndDate(vehicle.getId(), DateUtil.todayStr());

            // số lần di chuyển
            int pauseCount = iDetailsRepository.getPauseCountByMovementHistoryId(movementHistory.getId());

            // giờ bắt đầu
            LocalDateTime startDate = movementHistory.getStartDate();
            String startTimeStr = String.format("%02d:%02d:%02d", startDate.getHour(), startDate.getMinute(), startDate.getSecond());

            // Tổng thời gian di chuyển
            int second = 0;
            List<String> localTimes = iDetailsRepository.getListTimeByMovementHistoryId(movementHistory.getId());
            for (String totalTime : localTimes) {
                int sum = DateUtil.second(totalTime);
                second += sum;
            }
            String totalTimeS = DateUtil.timeStr(second);

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

            for (VehicleDTO vehicleDTO : dtos) {
                if (vehicleDTO.getPauseCount() == 0) {
                    vehicleDTO.setPauseCount(pauseCount);
                    vehicleDTO.setStartTime(startTimeStr);
                    vehicleDTO.setTotalTravelTime(totalTimeS);
                    vehicleDTO.setEndTime(endTimeStr);
                    break;
                }
            }
        }
        return dtos;
    }

    @Override
    public ListVehicleDTO createVehicle(VehicleFormCreate vehicleFormCreate) {

        User user = userRepository.findByUsername(UserDetailsUtils.UserDetails().getUsername());

        TypeMap<VehicleFormCreate, Vehicle> typeMap = modelMapper.getTypeMap(VehicleFormCreate.class, Vehicle.class);
        if (typeMap == null) { // if not already added
            // skip field
            modelMapper.addMappings(new PropertyMap<VehicleFormCreate, Vehicle>() {
                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        }

        vehicleFormCreate.setUserId(user.getId());

        Vehicle vehicle = modelMapper.map(vehicleFormCreate, Vehicle.class);

        vehicle.setStatus(VehicleStatus.ACTIVE);

        Vehicle save = iVehicleRepository.save(vehicle);

        ListVehicleDTO vehicleDTO = modelMapper.map(save, ListVehicleDTO.class);
        vehicleDTO.setUserName(user.getName());
        vehicleDTO.setUserPhoneNumber(user.getPhoneNumber());
        vehicleDTO.setUserAddress(user.getAddress());

        return vehicleDTO;
    }
}
