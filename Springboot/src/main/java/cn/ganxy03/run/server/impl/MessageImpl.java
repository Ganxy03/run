package cn.ganxy03.run.server.impl;

import cn.ganxy03.run.Tencent.TencentMapConstant;
import cn.ganxy03.run.entity.MessageBroad;
import cn.ganxy03.run.entity.MessageReply;
import cn.ganxy03.run.repository.MessageBroadRepository;
import cn.ganxy03.run.repository.MessageReplyRepository;
import cn.ganxy03.run.server.MessageServer;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static cn.ganxy03.run.Tencent.TencentMapConstant.IP_LOCATION_API;

@Service
public class MessageImpl implements MessageServer {
    private MessageBroadRepository messageBroadRepository;
    private MessageReplyRepository messageReplyRepository;

    public MessageImpl(MessageBroadRepository messageBroadRepository,MessageReplyRepository messageReplyRepository) {
        this.messageBroadRepository = messageBroadRepository;
        this.messageReplyRepository = messageReplyRepository;
    }

    public void PostMessage(String content) {
        MessageBroad messageBroad = new MessageBroad();
        messageBroad.setIp(RecordIP());
        messageBroad.setTime(LocalDateTime.now());
        messageBroad.setContent(content);
        messageBroad.setStatus(0);
        messageBroadRepository.save(messageBroad);
    }

    public void ReplyMessage(Integer goal, String content) {
        MessageReply messageReply = new MessageReply();
        messageReply.setIp(RecordIP());
        messageReply.setTime(LocalDateTime.now());
        messageReply.setGoal(goal);
        messageReply.setContent(content);
        messageReply.setStatus(0);
        messageReplyRepository.save(messageReply);
    }

    public List<MessageBroad> GetAllBroads() {
        return messageBroadRepository.findAll();
    }

    public List<MessageReply> GetAllReplies() {
        return messageReplyRepository.findAll();
    }

    public String RecordIP() {
        JSONObject requestData = new JSONObject();
        requestData.put("key", TencentMapConstant.KEY);
        String signature = cn.ganxy03.run.Tencent.TencentMapUtil.generateSignaturePost(IP_LOCATION_API, requestData);
        String finalRequest = cn.ganxy03.run.Tencent.TencentMapConstant.HOST + IP_LOCATION_API + "?key=" + cn.ganxy03.run.Tencent.TencentMapConstant.KEY + "&sig=" + signature;
        System.out.println(finalRequest);

        RestTemplate restTemplate = new RestTemplate();
//        System.out.println(restTemplate.postForObject(finalRequest, requestData, TencentMapResult.class));
        cn.ganxy03.run.Tencent.TencentMapResult tencentMapResult = restTemplate.postForObject(finalRequest, requestData, cn.ganxy03.run.Tencent.TencentMapResult.class);
//        System.out.println(tencentMapResult);

        Map<String, Object> resultMap = (Map<String, Object>) tencentMapResult.getResult();
//        if (resultMap != null) {
//            String ip = (String) resultMap.get("ip");
//            Map<String, Double> location = (Map<String, Double>) resultMap.get("location");
//            Map<String, String> adInfo = (Map<String, String>) resultMap.get("ad_info");
//
//            // 获取result中的具体值
//            Double latitude = location.get("lat");
//            Double longitude = location.get("lng");
//            String nation = adInfo.get("nation");
//            String province = adInfo.get("province");
//            String city = adInfo.get("city");
//            String district = adInfo.get("district");
//
//            return ip;
////            UserLogin userLogin = new UserLogin();
////            userLogin.setIp(ip);
////            userLogin.setPhone(phone);
////            userLogin.setLatitude(latitude);
////            userLogin.setLongitude(longitude);
////            userLogin.setNation(nation);
////            userLogin.setProvince(province);
////            userLogin.setCity(city);
////            userLogin.setDistrict(district);
////            userLogin.setTime(LocalDateTime.now());
////            userLoginRepository.save(userLogin);
//
//        }
        String ip = (String) resultMap.get("ip");
        return ip;
    }
}
