/*
 * Copyright (C) 2016 AriaLyy(https://github.com/AriaLyy/Aria)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arialyy.aria.core.common;

import android.text.TextUtils;
import com.arialyy.aria.core.FtpUrlEntity;
import com.arialyy.aria.core.ProtocolType;
import com.arialyy.aria.core.processor.IFtpUploadInterceptor;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CheckUtil;

/**
 * Created by laoyuyu on 2018/3/9.
 */
public class FtpOption extends BaseOption {

  private String charSet, userName, password, account;
  private boolean isNeedLogin = false;
  private FtpUrlEntity urlEntity;
  private String protocol, keyAlias, storePass, storePath;
  private boolean isImplicit = true;
  private IFtpUploadInterceptor uploadInterceptor;

  public FtpOption() {
    super();
  }

  public FtpOption charSet(String charSet) {
    if (TextUtils.isEmpty(charSet)) {
      throw new NullPointerException("字符编码为空");
    }
    this.charSet = charSet;
    return this;
  }

  public FtpOption login(String userName, String password) {
    return login(userName, password, null);
  }

  public FtpOption login(String userName, String password, String account) {
    if (TextUtils.isEmpty(userName)) {
      ALog.e(TAG, "用户名不能为null");
      return this;
    } else if (TextUtils.isEmpty(password)) {
      ALog.e(TAG, "密码不能为null");
      return this;
    }
    this.userName = userName;
    this.password = password;
    this.account = account;
    isNeedLogin = true;
    return this;
  }

  /**
   * 设置协议类型
   *
   * @param protocol {@link ProtocolType}
   */
  public FtpOption setProtocol(@ProtocolType String protocol) {
    if (TextUtils.isEmpty(protocol)) {
      ALog.e(TAG, "设置协议失败，协议信息为空");
      return this;
    }
    this.protocol = protocol;
    return this;
  }

  /**
   * 设置证书别名
   *
   * @param keyAlias 别名
   */
  public FtpOption setAlias(String keyAlias) {
    if (TextUtils.isEmpty(keyAlias)) {
      ALog.e(TAG, "设置证书别名失败，证书别名为空");
      return this;
    }
    this.keyAlias = keyAlias;
    return this;
  }

  /**
   * 设置证书密码
   *
   * @param storePass 私钥密码
   */
  public FtpOption setStorePass(String storePass) {
    if (TextUtils.isEmpty(storePass)) {
      ALog.e(TAG, "设置证书密码失败，证书密码为空");
      return this;
    }
    this.storePass = storePass;
    return this;
  }

  /**
   * 设置证书路径
   *
   * @param storePath 证书路径
   */
  public FtpOption setStorePath(String storePath) {
    if (TextUtils.isEmpty(storePath)) {
      ALog.e(TAG, "设置证书路径失败，证书路径为空");
      return this;
    }
    this.storePath = storePath;
    return this;
  }

  /**
   * 设置安全模式，默认true
   *
   * @param isImplicit true 隐式，false 显式
   */
  public FtpOption setImplicit(boolean isImplicit) {
    this.isImplicit = isImplicit;
    return this;
  }

  /**
   * FTP文件上传拦截器，如果远端已有同名文件，可使用该拦截器控制覆盖文件或修改该文件上传到服务器端端文件名
   */
  public FtpOption setUploadInterceptor(IFtpUploadInterceptor uploadInterceptor) {
    if (uploadInterceptor == null) {
      throw new NullPointerException("ftp拦截器为空");
    }
    CheckUtil.checkMemberClass(uploadInterceptor.getClass());
    this.uploadInterceptor = uploadInterceptor;
    return this;
  }

  public void setUrlEntity(FtpUrlEntity urlEntity) {
    this.urlEntity = urlEntity;
    urlEntity.needLogin = isNeedLogin;
    urlEntity.user = userName;
    urlEntity.password = password;
    urlEntity.account = account;
    if (!TextUtils.isEmpty(storePath)) {
      urlEntity.isFtps = true;
      urlEntity.protocol = protocol;
      urlEntity.keyAlias = keyAlias;
      urlEntity.storePass = storePass;
      urlEntity.storePath = storePath;
      urlEntity.isImplicit = isImplicit;
    }
  }
}
