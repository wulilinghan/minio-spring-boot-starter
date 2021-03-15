package top.b0x0.xml.demo.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import top.b0x0.xml.demo.domain.XmlResponse;

/**
 * @author musui
 */
public class ParseStringTest {
    public final static String RESP_XML = "" +
            "<?xml version=\"1.0\" encoding=\"Gbk\"?>" +
            "<Gzelink>" +
            "<Info>" +
            "<Trx_Code>311001</Trx_Code>" +
            "<Version>03</Version>" +
            "<Data_Type>2</Data_Type>" +
            "<Req_Sn>1615775170196</Req_Sn>" +
            "<Ret_Code>0000</Ret_Code>" +
            "<Err_Msg>processingCompletedOrReceivedSuccessfully</Err_Msg>" +
            "<Signed_Msg>537D080946C979Ff0B102D5D779F8406Afc8Dac460362Ca9F657Bfa2D20Ed142957B3A259Dc14F6412259Fa472Ac146A00178E2A7Ff6493F5E1235Bc952D68A3C0De0520A97C18Cda5320Ae50C54ee2974a9C2B0E84389Fe251F2B55093Cb1C400489C6B21F71293B50Bcf1Ed025762C1A6C7B9394f2Dedadd78D1C9Ee37B823</Signed_Msg>" +
            "</Info>" +
            "<Body>" +
            "<Ret_Details>" +
            "<Ret_Detail>" +
            "<Trans_Status>0000</Trans_Status>" +
            "<Trans_Info>processingCompletedOrReceivedSuccessfully</Trans_Info>" +
            "<Sign_Mode>0</Sign_Mode>" +
            "<Protocol_No>105202103150903345849</Protocol_No>" +
            "<Merchant_Id>001053110000001</Merchant_Id>" +
            "<Bank_Code>105</Bank_Code>" +
            "<Account_No>6227008888888998800</Account_No>" +
            "<Account_Name>wangTianpeng</Account_Name>" +
            "<Account_Type>00</Account_Type>" +
            "<Id_Type>0</Id_Type>" +
            "<Id>6301011945****271X</Id>" +
            "<Tel>136****0009</Tel>" +
            "<Business_Code>14902</Business_Code>" +
            "<Account_Prop>0</Account_Prop>" +
            "<Lgl_Rep_Nm></Lgl_Rep_Nm>" +
            "<Lgl_Rep_Id_Tp></Lgl_Rep_Id_Tp>" +
            "<Lgl_Rep_Id_No></Lgl_Rep_Id_No>" +
            "<Disable_Date>20220315</Disable_Date>" +
            "<Limit_Period_Unit>01</Limit_Period_Unit>" +
            "<Max_Cnt_Limit>50</Max_Cnt_Limit>" +
            "<Single_Limit>30000</Single_Limit>" +
            "</Ret_Detail>" +
            "</Ret_Details>" +
            "</Body>" +
            "</Gzelink>";

    public static void main(String[] args) throws JsonProcessingException {
        XmlMapper xmlMapper = XmlMapper.builder()
                //反序列化时，若实体类没有对应的属性，是否抛出JsonMappingException异常，false忽略掉
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                //序列化是否绕根元素，true，则以类名为根元素
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                //忽略空属性
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                //XML标签名:使用骆驼命名的属性名，
                .propertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE)
                //设置转换模式
                .enable(MapperFeature.USE_STD_BEAN_NAMING)
                .build();


        xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);
        XmlResponse resp = xmlMapper.readValue(RESP_XML, XmlResponse.class);
        System.out.println("XML to JavaBean " + resp);

    }
}
