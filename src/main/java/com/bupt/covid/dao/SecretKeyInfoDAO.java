package com.bupt.covid.dao;

import com.bupt.covid.pojo.SecretKeyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SecretKeyInfoDAO extends JpaRepository<SecretKeyInfo, Integer> {
    List<SecretKeyInfo> findAllByUserid(Integer userid);

    /**
     * 获得从 date到现在的 userid的每日跟踪秘钥
     * @param userid
     * @param date
     * @return
     */
    @Query(value = "select * from secretkeys_info where userid =?1 and (date > ?2)",nativeQuery = true)
    List<SecretKeyInfo> getByUseridAndDate(Integer userid, String date);
}
