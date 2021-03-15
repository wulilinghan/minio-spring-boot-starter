package top.b0x0.xml.demo.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * @author TANG
 * @date 2021-03-15
 */
@Data
public class XmlInfo {

    @JacksonXmlProperty(localName = "Trx_Code")
    private String trxCode;
    @JacksonXmlProperty(localName = "Version")
    private String version;
    @JacksonXmlProperty(localName = "Data_Type")
    private String dataType;
    @JacksonXmlProperty(localName = "Req_Sn")
    private String reqSn;
    @JacksonXmlProperty(localName = "Ret_Code")
    private String retCode;
    @JacksonXmlProperty(localName = "Err_Msg")
    private String errMsg;
    @JacksonXmlProperty(localName = "Signed_Msg")
    private String signedMsg;
}
