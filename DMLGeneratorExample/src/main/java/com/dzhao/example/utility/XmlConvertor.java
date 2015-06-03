package com.dzhao.example.utility;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringWriter;

public class XmlConvertor {

    private XmlConvertor(){}

    public static <T> String toXML(Object obj, Class<T> clazz) throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext ctx = JAXBContext.newInstance(clazz);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.marshal(obj, sw);
        return sw.toString();
    }

    public static <T> T fromXML(InputStream is, Class<T> clazz) throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        return (T)unmarshaller.unmarshal(is);
    }
}
