package com.bupt.covid.utils;


import com.bupt.covid.pojo.BluetoothInfo;
import com.bupt.covid.pojo.SecretKeyInfo;
import com.bupt.covid.pojo.User;
import com.bupt.covid.service.BluetoothInfoService;
import com.bupt.covid.service.SecretKeyInfoService;
import com.bupt.covid.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlgUtil {
    @Autowired
    UserServiceImpl userInfoService;

    @Autowired
    BluetoothInfoService bluetoothInfoService;

    @Autowired
    SecretKeyInfoService secretKeyInfoService;

    /**
     * 更新全部的用户的感蓝牙染率和蓝牙感染风险等级
     * @return
     * @throws ParseException
     */
    public int updateAllInfectionRateAndRiskLevel() throws Exception {
        List<User> userInfoListDesc = userInfoService.getUserInfoListDesc();
        for(User userInfo : userInfoListDesc){
            float infectionRate;
            int riskLevel;
            //判断是否是确诊用户
            if(userInfo.getStatus() == 1){
                infectionRate = 1;
                riskLevel = 10;
            }else {
                infectionRate = calculateOneInfectionRate(userInfo);
                riskLevel = (int) (infectionRate * 10);
            }
            userInfo.setBluetoothInfectionRate(infectionRate);
            userInfo.setBluetoothRiskLevel(riskLevel);
            userInfoService.updateOneUserInfo(userInfo);
        }
        return 0;
    }

    /**
     * 计算一个用户的蓝牙感染率
     * @param userInfo
     * @return
     * @throws ParseException
     */
    public float calculateOneInfectionRate(User userInfo) throws Exception {
        int userid = userInfo.getId();
        int riskLevel = userInfo.getBluetoothRiskLevel();
        float newInfectionRate = 0;
        String todayDateString = Utility.getDateString(0);

        //用来存储每个目标用户对主用户的感染概率
        List<Double> targetInfectionProbList = new ArrayList<>();

        //获得主用户的14天内的蓝牙扫描信息列表
        List<BluetoothInfo> BTInfoList = bluetoothInfoService.getBluetoothInfoListByUserid(userid);

        //获得目标用户信息列表（感染风险等级比主用户高的用户）
        List<User> targetUserInfoList = userInfoService.getUserInfoListByRiskLevel(riskLevel);
        User user0 = new User();
        user0.setId(0);
        user0.setBluetoothInfectionRate(1);
        targetUserInfoList.add(user0);

        //对目标用户列表中每一项（一个目标用户）遍历
        for(User targetUserInfo : targetUserInfoList){

            //用来记录目标用户对主用户的每次接触计算出的感染概率
            List<Double> infectionProbList = new ArrayList<>();

            //获得目标用户的14天内的每日跟踪秘钥
            List<SecretKeyInfo> SKInfoList = secretKeyInfoService.
                    getSecretKeyInfoListByUserid(targetUserInfo.getId());

            //将每日跟踪秘钥列表按与今天相隔的天数，分为14组
            ArrayList<SecretKeyInfo>[] SKInfoArrayList = new ArrayList[14];
            for(int i = 0; i < 14; i++)
                SKInfoArrayList[i] = new ArrayList<SecretKeyInfo>();
            for(SecretKeyInfo SKInfo : SKInfoList){
                int n = Utility.getDaysBetween(SKInfo.getDate(), todayDateString);
                SKInfoArrayList[n].add(SKInfo);
            }

            //将蓝牙扫描信息表按与今天相隔的天数，分为14组
            ArrayList<BluetoothInfo>[] BTInfoArrayList = new ArrayList[14];
            for(int i = 0; i < 14; i++)
                BTInfoArrayList[i] = new  ArrayList<BluetoothInfo>();
            for(BluetoothInfo IdInfo : BTInfoList){
                int n = Utility.getDaysBetween(IdInfo.getDate(), todayDateString);
                BTInfoArrayList[n].add(IdInfo);
            }

            //将每日跟踪秘钥和蓝牙扫描信息分为14天的，每天一轮匹配，即14轮匹配
            for(int i = 0; i < 14; i++ ){
                //判断在此轮匹配中，每日跟踪秘钥和蓝牙扫描信息是否都存在，
                //若都存在则继续下一步
                if(SKInfoArrayList[i]!=null && SKInfoArrayList[i].size() > 0 &&
                        BTInfoArrayList[i]!=null && BTInfoArrayList[i].size() > 0){
                    //在此轮匹配中，对于每一个每日跟踪秘钥都生成一个匿名标识符列表
                    //每一个匿名标识符列表大小是144
                    for(int x = 0; x < SKInfoArrayList[i].size(); x++){
                        String[] rpijs = this.getRpijByDtki(SKInfoArrayList[i].get(x).getSecretkey());
                        //将生成的匿名标识符列表与蓝牙扫描信息列表里的目标标识符进行匹配
                        for(int j = 0; j < 144; j++){
                            for(int n = 0; n < BTInfoArrayList[i].size(); n++){
                                //若有匹配成功的记录，则
                                if(BTInfoArrayList[i].get(n).getTarget_identifier().equals(rpijs[j])){
                                    float T = ((float)BTInfoArrayList[i].get(n).getDuration())/ 60 / 60;
                                    float D = BTInfoArrayList[i].get(n).getDistance();
                                    double infectionProb = calculateOnceInfectionProb(T, D);
                                    infectionProbList.add(infectionProb);
                                }
                            }
                        }
                    }
                }
            }
            //计算此目标用户对主用户的总感染概率，并存入List
            double d = calculateMulInfectionProb(infectionProbList);
            double targetInfectionProb = d * targetUserInfo.getBluetoothInfectionRate();
            targetInfectionProbList.add(targetInfectionProb);
        }

        //根据每个目标用户对主用户的感染概率，计算主用户最终的新的感染概率
        newInfectionRate = (float) calculateMulInfectionProb(targetInfectionProbList);

        return newInfectionRate;
    }

    /**
     * 根据接触时长、接触距离，计算一个用户对另一个用户的感染概率
     * @param T
     * @param D
     * @return
     */
    public double calculateOnceInfectionProb(float T, float D){
        int I = 1;
        int q = 48;
        double p = (double) 0.3;
        double Q = (2.8 * 6 * (Math.pow(D, 2))) / 2;
        double infectionProb = 1 - Math.pow(Math.E,
                ((-(I * q * p * T)) / Q));
        return infectionProb;
    }

    /**
     * 根据连续接触的每次的感染概率，计算导致的总的感染概率
     * @param infectionProbList
     * @return
     */
    public double calculateMulInfectionProb(List<Double> infectionProbList){
        double temp = 1;
        for(double P : infectionProbList){
            temp = temp * (1 - P);
        }
        return 1 - temp;
    }



    /**
     * 返回64位16进制数（可选SHA-256）
     * @param algorithm
     * @param content
     * @return
     * @throws Exception
     */
    public static String digest(String algorithm, byte[] content) throws Exception {
        MessageDigest instance = MessageDigest.getInstance(algorithm);
        instance.update(content);
        //当所有数据已被更新,调用digest()方法完成哈希计算,返回字节数组
        byte[] digest = instance.digest();
        //System.out.println("算法=" + algorithm + ",摘要=" + DatatypeConverter.printHexBinary(digest));
        return DatatypeConverter.printHexBinary(digest);
    }

    /**
     * 根据每日跟踪秘钥获得匿名标识符表
     * @param dtki
     * @return
     * @throws Exception
     */
    public static String[] getRpijByDtki(String dtki) throws Exception {
        String[] Rpijs = new String[144];
        String temp = "";
        for (int i = 0; i < 144; i++) {
            temp = dtki + i;
            Rpijs[i] = digest("SHA-256", temp.getBytes()).substring(0, 16);
        }
        return Rpijs;
    }

}
