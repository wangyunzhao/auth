package com.example.auth.provider.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nianzhong on 2019/8/27.
 */
public class BaseAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)

    private Integer id;

    @ApiModelProperty(hidden = true)

    private String merchantUserId;


    private String username;


    private String password;

    @ApiModelProperty(hidden = true)

    private String hszPassword;


    private String email;


    private String wechat;


    private String remark;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(hidden = true)
    private Date updateTime;


    private String name;


    private String addName;


    private String phone;


    private boolean channel;


    private boolean dev;


    private String type;

    private String shortName;


    private String fullName;


    private String cardId;


    private String scale;


    private String city;


    private String address;


    private String contract;


    private String contractPhone;


    private String qq;


    private String bank;


    private String bankName;


    private String bankAccountName;


    private String bankAccount;


    private String bankContract;


    private String bankContractPhone;


    private String appKey;

    private String onlineChannel;

    private String proportionTime;


    private String proportionProp;


    private String chargeType;


    private int deviceNum;


    private int channelNum;

    private int appNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHszPassword() {
        return hszPassword;
    }

    public void setHszPassword(String hszPassword) {
        this.hszPassword = hszPassword;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isChannel() {
        return channel;
    }

    public void setChannel(boolean channel) {
        this.channel = channel;
    }

    public boolean isDev() {
        return dev;
    }

    public void setDev(boolean dev) {
        this.dev = dev;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getContractPhone() {
        return contractPhone;
    }

    public void setContractPhone(String contractPhone) {
        this.contractPhone = contractPhone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankContract() {
        return bankContract;
    }

    public void setBankContract(String bankContract) {
        this.bankContract = bankContract;
    }

    public String getBankContractPhone() {
        return bankContractPhone;
    }

    public void setBankContractPhone(String bankContractPhone) {
        this.bankContractPhone = bankContractPhone;
    }

    public String getProportionTime() {
        return proportionTime;
    }

    public void setProportionTime(String proportionTime) {
        this.proportionTime = proportionTime;
    }

    public String getProportionProp() {
        return proportionProp;
    }

    public void setProportionProp(String proportionProp) {
        this.proportionProp = proportionProp;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public int getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(int deviceNum) {
        this.deviceNum = deviceNum;
    }

    public int getAppNum() {
        return appNum;
    }

    public void setAppNum(int appNum) {
        this.appNum = appNum;
    }

    public String getOnlineChannel() {
        return onlineChannel;
    }

    public void setOnlineChannel(String onlineChannel) {
        this.onlineChannel = onlineChannel;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public int getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(int channelNum) {
        this.channelNum = channelNum;
    }
}
