package com.sdut.community.scheduleTask;

import com.sdut.community.mapper.BlogMapper;
import com.sdut.community.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@EnableScheduling
public class VisitsScheduleTask {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private BlogMapper blogMapper;

    //添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(fixedRate = 1000*60*60)
    private void updateVisits(){

        //获取所有的bid集合
        List<Integer> bids = blogMapper.getAllBids();
        for (Integer bid:bids){
            int count =0;
            if(redisUtils.get(bid+"-visits")!=null){
                count = Integer.parseInt((String) redisUtils.get(bid+"-visits"));
                //更改mysql中的visits
                blogMapper.updateBlogVisits(count,bid);
            }

        }


    }

}
