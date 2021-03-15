package top.b0x0.xml.demo.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * @author musui
 */
@Data
public class RetDetails {

    @JacksonXmlProperty(localName = "Ret_Detail")
    private RetDetail retDetail;
}