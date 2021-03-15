package top.b0x0.xml.demo.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * @author TANG
 * @date 2021-03-15
 */
@Data
//@JacksonXmlRootElement(localName = "Gzelink") // 注释这句话也能用
public class XmlResponse {

    @JacksonXmlProperty(localName = "Info")
    private XmlInfo xmlInfo;

    @JacksonXmlProperty(localName = "Body")
    private XmlBody xmlBody;

}
