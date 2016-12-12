package com.omniprimeinc.audit.platform.autojobs.services.utils;

/**
 *调用手机归属查询接口http://sj.apidata.cn/，返回json格式信息
 * <p>
 * 字段名	    字段类型	    字段说明<p>
 * status	    Integer	    接口请求状态：0=请求失败，1=请求成功<p>
 * data	    object	    返回的结果数据对象<p>
 * message    String	    返回的消息，请求失败时返回失败原因<p>
 * <p>
 * data   节点字段说明：
 * <p>
 * 字段名	    字段类型	    字段说明<p>
 * prefix	    Integer	    手机号码号段。<p>
 * mobile	    String	    查询的手机号码<p>
 * province	    String	    归属地省份名称。<p>
 * city	        String	    归属地城市名称<p>
 * isp	        String	    归属的运营商名称<p>
 * code	        Integer	    归属地行政编号<p>
 * zipcode	    Integer	    归属地邮政编码<p>
 * types	    String	    手机号码卡信息<p>
 *
 * @author shichp
 * Created by shichp on 2016/12/10.
 */

public class MobileApiResponse {
    Integer status;
    String message;
    MobileApiResponseData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MobileApiResponseData getData() {
        return data;
    }

    public void setData(MobileApiResponseData data) {
        this.data = data;
    }

    public class MobileApiResponseData {
        Integer prefix;
        String mobile;
        String province;
        String city;
        String isp;
        Integer code;
        Integer zipcode;
        String types;

        public Integer getPrefix() {
            return prefix;
        }

        public void setPrefix(Integer prefix) {
            this.prefix = prefix;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getIsp() {
            return isp;
        }

        public void setIsp(String isp) {
            this.isp = isp;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Integer getZipcode() {
            return zipcode;
        }

        public void setZipcode(Integer zipcode) {
            this.zipcode = zipcode;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }
    }
}
