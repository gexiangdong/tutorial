package cn.devmgr.tutorial.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Address {
  private static final Logger logger = LoggerFactory.getLogger(Address.class);

  private String postCode;
  private String detailAddress;

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public String getDetailAddress() {
    return detailAddress;
  }

  public void setDetailAddress(String detailAddress) {
    this.detailAddress = detailAddress;
  }
}
