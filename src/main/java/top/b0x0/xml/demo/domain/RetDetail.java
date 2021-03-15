package top.b0x0.xml.demo.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * @author musui
 */
@Data
public class RetDetail {

    @JacksonXmlProperty(localName = "Trans_Status")
    private String transStatus;
    @JacksonXmlProperty(localName = "Trans_Info")
    private String transInfo;
    @JacksonXmlProperty(localName = "Sign_Mode")
    private String signMode;
    @JacksonXmlProperty(localName = "Protocol_No")
    private String protocolNo;
    @JacksonXmlProperty(localName = "Merchant_Id")
    private String merchantId;
    @JacksonXmlProperty(localName = "Bank_Code")
    private String bankCode;
    @JacksonXmlProperty(localName = "Account_No")
    private String accountNo;
    @JacksonXmlProperty(localName = "Account_Name")
    private String accountName;
    @JacksonXmlProperty(localName = "Account_Type")
    private String accountType;
    @JacksonXmlProperty(localName = "Id_Type")
    private String idType;
    @JacksonXmlProperty(localName = "Id")
    private String id;
    @JacksonXmlProperty(localName = "Tel")
    private String tel;
    @JacksonXmlProperty(localName = "Business_Code")
    private String businessCode;
    @JacksonXmlProperty(localName = "Account_Prop")
    private String accountProp;
    @JacksonXmlProperty(localName = "Lgl_Rep_Nm")
    private String lglRepNm;
    @JacksonXmlProperty(localName = "Lgl_Rep_Id_Tp")
    private String lglRepIdTp;
    @JacksonXmlProperty(localName = "Lgl_Rep_Id_No")
    private String lglRepIdNo;
    @JacksonXmlProperty(localName = "Disable_Date")
    private String disableDate;
    @JacksonXmlProperty(localName = "Limit_Period_Unit")
    private String limitPeriodUnit;
    @JacksonXmlProperty(localName = "Max_Cnt_Limit")
    private String maxCntLimit;
    @JacksonXmlProperty(localName = "Single_Limit")
    private String singleLimit;

}