package com.bupt.covid.controller;

import com.bupt.covid.pojo.BluetoothInfo;
import com.bupt.covid.service.BluetoothInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BluetoothInfoAPI {
    @Autowired
    BluetoothInfoService bluetoothInfoService;

//    @GetMapping("/api/Bluetooth/getBluetoothInfo")
//    public int getBluetoothInfo() throws Exception{
//        return 4;
//    }

    /**
     * 插入多条蓝牙扫描信息
     * @param bluetoothInfoList
     * @return
     */
    @PostMapping("/api/Bluetooth/postBluetoothInfoList")
    public int postBluetoothInfo(@RequestBody List<BluetoothInfo> bluetoothInfoList){
        int result = 1;
        for(BluetoothInfo bluetoothInfo : bluetoothInfoList) {
            result = bluetoothInfoService.insertOneBluetoothInfo(bluetoothInfo.getUserid(),
                    bluetoothInfo.getTime_stamp(), bluetoothInfo.getMy_identifier(),
                    bluetoothInfo.getTarget_identifier(), bluetoothInfo.getDistance(),
                    bluetoothInfo.getDuration(), bluetoothInfo.getDate());
        }
        return result;
    }

//    @PostMapping("/api/Bluetooth/postOneBluetoothInfo")
//    public int postOneBluetoothInfo(
//            @RequestParam("userid") Integer userid,
//            @RequestParam("time_stamp") Integer time_stamp,
//            @RequestParam("my_identifier") String my_identifier,
//            @RequestParam("target_identifier") String target_identifier,
//            @RequestParam("distance") Float distance,
//            @RequestParam("duration") Integer duration,
//            @RequestParam("date") String date) throws Exception{
//        int result = 1;
//        result = bluetoothInfoService.insertOneBluetoothInfo(userid,time_stamp,
//                my_identifier, target_identifier, distance, duration, date);
//        return result;
//    }

}
