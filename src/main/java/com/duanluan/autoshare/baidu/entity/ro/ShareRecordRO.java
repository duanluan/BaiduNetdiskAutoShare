package com.duanluan.autoshare.baidu.entity.ro;

import com.duanluan.autoshare.baidu.entity.ShareRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 分享记录 响应结果对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShareRecordRO extends BaseRO implements Serializable {

  private static final long serialVersionUID = 4623783315800290777L;

  private List<ShareRecord> list;
}
