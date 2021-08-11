package com.duanluan.autoshare.baidu.util;

import com.alibaba.fastjson.JSON;
import com.duanluan.autoshare.baidu.config.BaiduConfig;
import com.duanluan.autoshare.baidu.constants.ErrnoConstant;
import com.duanluan.autoshare.baidu.constants.HeaderConstant;
import com.duanluan.autoshare.baidu.entity.ShareRecord;
import com.duanluan.autoshare.baidu.entity.ro.*;
import com.ejlchina.okhttps.FastjsonMsgConvertor;
import com.ejlchina.okhttps.HTTP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 百度网盘工具类
 */
@Slf4j
@Component
public class BaiduNetdiskUtils {

  private BaiduNetdiskUtils() {
  }

  @Autowired
  private BaiduConfig baiduConfig;

  private static final String HOST = "pan.baidu.com";
  /**
   * 基础 URL
   */
  private static final String BASE_URL = "https://" + HOST;
  /**
   * 用户代理
   */
  private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36";
  /**
   * 请求对象
   */
  private static final HTTP HTTP_OBJ = HTTP.builder().baseUrl(BASE_URL).addMsgConvertor(new FastjsonMsgConvertor()).build();
  /**
   * Cookie
   */
  private static String cookie = "BIDUPSID=38FA060A427C9C0A04EE6D2B51460A07; PSTM=1620736448; __yjs_duid=1_ba620814a98f28f2a8bb6b26dbdc6de71620742800774; csrfToken=dARoK5oGj5GVqCtm54VVLRRJ; PANWEB=1; secu=1; BDSFRCVID=pNPOJeCT5G0Y_-Ted8r0bQDx1WOoTx5TTPjcTR5qJ04BtyCVgBR4EG0PtfN48G-bmaKgogKKL2OTHm_F_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF=tJk8_DPbJK-3fP36q4cBb-4WhmT22-us2grR2hcH0b61EnR_bTjobJ-93MrZ0fjPWDTiaKJjBMb1DbRMjUJUjMuF-fRKbfcpWDTm_q5TtUJMeCnTDMRh-l04XNbyKMnitIv9-pPKWhQrh459XP68bTkA5bjZKxtq3mkjbPbDfn028DKuDjtBD5JWjGRabK6aKC5bL6rJabC3eRu9XU6q2bDeQN3ke4Qa5CnDaUntLlbWhf3OjtTv0l0vWtv4WbbvLT7johRTWqR4epvqXxonDh83KJ5nQfodHCOO04jO5hvv8KoO3M7VBUKmDloOW-TB5bbPLUQF5l8-sq0x0bOte-bQXH_EJ5_ffnFH_C8Qb-3bKRopMtOhq4tehHRjWqO9WDTm-I5TtUTrKfbNb57KD-DuQhADLMRyQT-t-Ubx2JOxVC5ly60MLR0-LnjM3qDJ3mkjbPbDBD-WsT6PhtQvbP4syPRrJfRnWIjdKfA-b4ncjRcTehoM3xI8LNj405OTbIFO0KJzJCcjqR8ZDTK5DToP; BDSFRCVID_BFESS=pNPOJeCT5G0Y_-Ted8r0bQDx1WOoTx5TTPjcTR5qJ04BtyCVgBR4EG0PtfN48G-bmaKgogKKL2OTHm_F_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF_BFESS=tJk8_DPbJK-3fP36q4cBb-4WhmT22-us2grR2hcH0b61EnR_bTjobJ-93MrZ0fjPWDTiaKJjBMb1DbRMjUJUjMuF-fRKbfcpWDTm_q5TtUJMeCnTDMRh-l04XNbyKMnitIv9-pPKWhQrh459XP68bTkA5bjZKxtq3mkjbPbDfn028DKuDjtBD5JWjGRabK6aKC5bL6rJabC3eRu9XU6q2bDeQN3ke4Qa5CnDaUntLlbWhf3OjtTv0l0vWtv4WbbvLT7johRTWqR4epvqXxonDh83KJ5nQfodHCOO04jO5hvv8KoO3M7VBUKmDloOW-TB5bbPLUQF5l8-sq0x0bOte-bQXH_EJ5_ffnFH_C8Qb-3bKRopMtOhq4tehHRjWqO9WDTm-I5TtUTrKfbNb57KD-DuQhADLMRyQT-t-Ubx2JOxVC5ly60MLR0-LnjM3qDJ3mkjbPbDBD-WsT6PhtQvbP4syPRrJfRnWIjdKfA-b4ncjRcTehoM3xI8LNj405OTbIFO0KJzJCcjqR8ZDTK5DToP; BDUSS=VHYlRKR093M0RaVk5rd2dvUDJJQ1E4Z35jOUQ1ZVpXYUw4R0FtS2s0T0pqUmRoSVFBQUFBJCQAAAAAAAAAAAEAAACudR9klOCBeQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIkA8GCJAPBgZX; BDUSS_BFESS=VHYlRKR093M0RaVk5rd2dvUDJJQ1E4Z35jOUQ1ZVpXYUw4R0FtS2s0T0pqUmRoSVFBQUFBJCQAAAAAAAAAAAEAAACudR9klOCBeQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIkA8GCJAPBgZX; STOKEN=11dcff004b121208667340848d09f9066fe0ece25543c5f9ae1134dce8f7ad6d; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDCLND=hCRinD0JYNSuvv0MVThXQJi%2FnaVTgiwZ; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1626167363,1626625313,1627016290,1627016526; Hm_lvt_fa0277816200010a74ab7d2895df481b=1627026387; Hm_lpvt_fa0277816200010a74ab7d2895df481b=1627026387; BAIDUID=3B5179A5740581B1FE67D47DE0263901:FG=1; H_WISE_SIDS=107320_110085_127969_164869_167970_175759_176399_176553_176677_177094_177371_177414_177550_178139_178329_178529_178623_179345_179402_179441_180119_180328_180407_180433_180436_180513_180641_180699_180750_180755_180757_180822_180890_180936_181107_181118_181219_181253_181260_181326_181401_181430_181433_181446_181585_181610_181631_181664_181670_181799_181940_182024_182066_182073_182076_182134_182273_182331_182366_182412; H_PS_PSSID=34304_34099_33966_31253_34279_33848_34073_34282_34111_26350_34215; BAIDUID_BFESS=3B5179A5740581B1FE67D47DE0263901:FG=1; delPer=0; PSINO=6; Hm_lpvt_7a3960b6f067eb0085b7f96ff5e660b0=1627113330; PANPSC=7249143043707563066%3AKkwrx6t0uHDzB70GPiWUKHlibiKzYuqjnZmfDpLowebAo6sidHFppgZuq6kOUHWCXt5%2FapUws1U86C122tNHOtrMDPdxxOv706AvTy5qeHhhBaVJALTttmM0MRXtmcWVBlOY6dvHjZN2TQaGfItAk1vKX4DMNpv6HhgzMjI7O4rZ81l%2BOzvAFl6rs7RhyW0Et2o1MmJi5hZ9bfFV5UX2VQ%3D%3D";

  /**
   * 检查状态码
   *
   * @param objRO    响应结果对象
   * @param errorMsg 错误消息
   * @param <T>      响应结果对象类型
   * @return 状态码是否表示成功
   */
  private static <T extends BaseRO> boolean checkErrno(T objRO, String errorMsg) {
    Integer errno = null;
    if (objRO == null || (errno = objRO.getErrno()) == null || errno != 0) {
      if (errno != null) {
        if (ErrnoConstant.LOGIN_FAILURE.equals(errno)) {
          log.error("无效登录或 bdstoken 错误！");
        }
      } else if (StringUtils.isNotBlank(errorMsg)) {
        log.error(errorMsg + (objRO != null ? objRO.getShowMsg() : ""));
      }
      return false;
    }
    return true;
  }

  /**
   * 登录状态是否有误
   *
   * @param cookieStr Cookie
   * @return 登录状态是否有误，正常时，重新赋值当前类的静态 Cookie
   */
  public boolean loginStatus(String cookieStr) {
    LoginStatusRO loginStatusRO = HTTP_OBJ.sync("/api/loginStatus" +
      "?clienttype=" + baiduConfig.getClienttype() +
      "&app_id=" + baiduConfig.getAppId() +
      "&web=" + baiduConfig.getWeb())
      // 添加请求头
      .addHeader(HeaderConstant.ACCEPT, "application/json, text/plain, */*")
      // .addHeader(HeaderConstant.ACCEPT_ENCODING, "gzip, deflate, br")
      .addHeader(HeaderConstant.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7")
      .addHeader(HeaderConstant.CONNECTION, "keep-alive")
      .addHeader(HeaderConstant.COOKIE, cookieStr)
      .addHeader(HeaderConstant.HOST, HOST)
      // .addHeader(HeaderConstant.REFERER, BASE_URL + "/disk/main?errmsg=Auth+Login+Sucess&errno=0&ssnerror=0&")
      .addHeader(HeaderConstant.REFERER, BASE_URL + "/disk/main")
      .addHeader(HeaderConstant.SEC_FETCH_DEST, "empty")
      .addHeader(HeaderConstant.SEC_FETCH_MODE, "cors")
      .addHeader(HeaderConstant.SEC_FETCH_SITE, "same-origin")
      .addHeader(HeaderConstant.USER_AGENT, USER_AGENT)
      .addHeader(HeaderConstant.X_KL_AJAX_REQUEST, "Ajax_Request")
      .addHeader(HeaderConstant.X_REQUESTED_WITH, "XMLHttpRequest")
      .get().getBody().toBean(LoginStatusRO.class);
    // 登录状态正常时，重新赋值静态 Cookie
    if (checkErrno(loginStatusRO, null)) {
      if (!cookie.equals(cookieStr)) {
        cookie = cookieStr;
      }
      return true;
    }
    return false;
  }

  /**
   * 分享记录
   *
   * @param page 第几页，从 1 开始
   * @param num  每页数量，默认 100
   * @return 分享链接列表
   */
  public List<ShareRecord> shareRecord(int page, Integer num) {
    ShareRecordRO shareRecordRO = HTTP_OBJ.sync("/share/record" +
      "?channel=" + baiduConfig.getChannel() +
      "&clienttype=" + baiduConfig.getClienttype() +
      "&app_id=" + baiduConfig.getAppId() +
      "&num=" + (num != null && num > 0 ? num.toString() : 100) +
      "&page=" + page +
      "&web=" + baiduConfig.getWeb() +
      "&order=ctime&desc=1")
      // 添加请求头
      .addHeader(HeaderConstant.ACCEPT, "application/json, text/plain, */*")
      // .addHeader(HeaderConstant.ACCEPT_ENCODING, "gzip, deflate, br")
      .addHeader(HeaderConstant.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7")
      .addHeader(HeaderConstant.CONNECTION, "keep-alive")
      .addHeader(HeaderConstant.COOKIE, cookie)
      .addHeader(HeaderConstant.HOST, HOST)
      // .addHeader(HeaderConstant.REFERER, BASE_URL + "/disk/main?errmsg=Auth+Login+Sucess&errno=0&ssnerror=0&")
      .addHeader(HeaderConstant.REFERER, BASE_URL + "/disk/main")
      .addHeader(HeaderConstant.SEC_FETCH_DEST, "empty")
      .addHeader(HeaderConstant.SEC_FETCH_MODE, "cors")
      .addHeader(HeaderConstant.SEC_FETCH_SITE, "same-origin")
      .addHeader(HeaderConstant.USER_AGENT, USER_AGENT)
      .addHeader(HeaderConstant.X_KL_AJAX_REQUEST, "Ajax_Request")
      .addHeader(HeaderConstant.X_REQUESTED_WITH, "XMLHttpRequest")
      .get().getBody().toBean(ShareRecordRO.class);
    if (checkErrno(shareRecordRO, "获取分享记录失败！")) {
      return shareRecordRO.getList();
    }
    return null;
  }

  /**
   * 分享
   *
   * @param bdstoken
   * @param logid
   * @param pwd      密码
   * @param fsIds    分享的文件 ID 数组
   * @return 分享链接响应结果
   */
  public ShareSetRO shareSet(String bdstoken, String logid, String pwd, List<Long> fsIds) {
    Map<String, Object> paramMap = new HashMap<>(5);
    paramMap.put("channel_list", new String[]{});
    paramMap.put("period", 0);
    paramMap.put("pwd", pwd);
    paramMap.put("schannel", 4);
    paramMap.put("fid_list", fsIds);

    ShareSetRO shareSetRO = HTTP_OBJ.sync("/share/set" +
      "?channel=" + baiduConfig.getChannel() +
      "&clienttype=" + baiduConfig.getClienttype() +
      "&web=" + baiduConfig.getWeb() +
      "&channel=" + baiduConfig.getChannel() +
      "&web=" + baiduConfig.getWeb() +
      "&app_id=" + baiduConfig.getAppId() +
      "&bdstoken=" + bdstoken +
      "&logid=" + logid +
      "&clienttype=" + baiduConfig.getClienttype())
      .addBodyPara(paramMap)
      // 添加请求头
      .addHeader(HeaderConstant.ACCEPT, "*/*")
      // .addHeader(HeaderConstant.ACCEPT_ENCODING, "gzip, deflate, br")
      .addHeader(HeaderConstant.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7")
      .addHeader(HeaderConstant.CONNECTION, "keep-alive")
      // .addHeader(HeaderConstant.CONTENT_LENGTH, String.valueOf(JSON.toJSONString(paramMap).length()))
      .addHeader(HeaderConstant.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
      .addHeader(HeaderConstant.COOKIE, cookie)
      .addHeader(HeaderConstant.HOST, HOST)
      .addHeader(HeaderConstant.REFERER, BASE_URL + "/disk/home?_at_=" + System.currentTimeMillis())
      .addHeader(HeaderConstant.SEC_FETCH_DEST, "empty")
      .addHeader(HeaderConstant.SEC_FETCH_MODE, "cors")
      .addHeader(HeaderConstant.SEC_FETCH_SITE, "same-origin")
      .addHeader(HeaderConstant.USER_AGENT, USER_AGENT)
      .addHeader(HeaderConstant.X_KL_AJAX_REQUEST, "Ajax_Request")
      .addHeader(HeaderConstant.X_REQUESTED_WITH, "XMLHttpRequest")
      .post().getBody().toBean(ShareSetRO.class);
    if (checkErrno(shareSetRO, "分享失败！")) {
      return shareSetRO;
    }
    return null;
  }

  /**
   * 取消分享
   *
   * @param bdstoken
   * @param shareidList 取消分享的分享 ID 数组
   * @return 是否取消分享成功
   */
  public boolean shareCancel(String bdstoken, List<String> shareidList) {
    Map<String, Object> paramMap = new HashMap<>(1);
    paramMap.put("shareid_list", shareidList);

    ShareCancelRO shareCancelRO = HTTP_OBJ.sync("/share/cancel" +
      "?channel=" + baiduConfig.getChannel() +
      "&bdstoken=" + bdstoken +
      "&clienttype=" + baiduConfig.getClienttype() +
      "&app_id=" + baiduConfig.getAppId() +
      "&web=" + baiduConfig.getWeb())
      .addBodyPara(paramMap)
      // 添加请求头
      .addHeader(HeaderConstant.ACCEPT, "application/json, text/plain, */*")
      // .addHeader(HeaderConstant.ACCEPT_ENCODING, "gzip, deflate, br")
      .addHeader(HeaderConstant.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7")
      .addHeader(HeaderConstant.CONNECTION, "keep-alive")
      .addHeader(HeaderConstant.CONTENT_LENGTH, "22")
      // .addHeader(HeaderConstant.CONTENT_LENGTH, String.valueOf(JSON.toJSONString(paramMap).length()))
      .addHeader(HeaderConstant.CONTENT_TYPE, "application/x-www-form-urlencoded")
      .addHeader(HeaderConstant.COOKIE, cookie)
      .addHeader(HeaderConstant.HOST, HOST)
      .addHeader(HeaderConstant.ORIGIN, BASE_URL)
      .addHeader(HeaderConstant.REFERER, BASE_URL + "/disk/main")
      .addHeader(HeaderConstant.SEC_FETCH_DEST, "empty")
      .addHeader(HeaderConstant.SEC_FETCH_MODE, "cors")
      .addHeader(HeaderConstant.SEC_FETCH_SITE, "same-origin")
      .addHeader(HeaderConstant.USER_AGENT, USER_AGENT)
      .addHeader(HeaderConstant.X_REQUESTED_WITH, "XMLHttpRequest")
      .post().getBody().toBean(ShareCancelRO.class);
    return checkErrno(shareCancelRO, "取消分享失败！");
  }

  public static void main(String[] args) {
    Map<String, Object> map = new HashMap<>();
    ArrayList<String> list = new ArrayList<>();
    list.add("123");
    map.put("a", list);
    map.put("b", 123);
    System.out.println(JSON.toJSONString(map));
    System.out.println("%5B%224021848677%22%5D".length());
  }

}
