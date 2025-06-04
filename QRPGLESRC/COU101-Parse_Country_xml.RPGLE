**FREE
ctl-opt nomain option(*srcstmt:*nodebugio); 


Dcl-Ds progStatus psds;
  xmlElements  Int(20) Pos(372);
End-Ds;

/copy parseutl
//=============================================
// XML INTO
//=============================================x``
Dcl-Proc ParseCountryIntoDS export;
  Dcl-Pi ParseCountryIntoDS likeds(COUNTRYDS);
    XmlPath char(100) options(*varsize);
   End-Pi;
//=============================================
dcl-s xml_doc_path char(256) inz('');
dcl-s trimmed_xml_doc_path char(256);
dcl-s xml_doc_len int(10);
dcl-ds returnedDS likeds(COUNTRYDS);
    trimmed_xml_doc_path = %trim(xml_doc_path);
    xml_doc_len = %len(trimmed_xml_doc_path);
    xml-into returnedDS %xml('/home/apinto1/builds/doc/country.xml': 'case=any doc=file path=countrydetials/country allowextra=yes allowmissing=yes'); 
   return returnedDS;
End-Proc;
//=============================================
//XML SAX
//==============================================
Dcl-Proc ParseCountryIntoSAX  export;
  Dcl-Pi ParseCountryIntoSAX likeds(COUNTRYDS);
    XmlPath char(100) options(*varsize);
   End-Pi;
//=============================================
dcl-s xml_doc_path char(256) inz('');
dcl-s trimmed_xml_doc_path char(256);
dcl-s xml_doc_len int(10);
dcl-ds returnedDS likeds(COUNTRYDS);
    trimmed_xml_doc_path = %trim(xml_doc_path);
    xml_doc_len = %len(trimmed_xml_doc_path);
    xml-into returnedDS %xml('/home/apinto1/builds/doc/country.xml': 'case=any doc=file path=countrydetials/country allowextra=yes allowmissing=yes'); 
    return returnedDS;
End-Proc;
//=============================================
// XML DATA INTO
//==============================================
Dcl-Proc ParseCountryIntoData export;
  Dcl-Pi ParseCountryIntoData likeds(COUNTRYDS);
    XmlPath char(100) options(*varsize);
   End-Pi;
//=============================================
dcl-s xml_doc_path char(256) inz('');
dcl-s trimmed_xml_doc_path char(256);
dcl-s xml_doc_len int(10);
dcl-ds returnedDS likeds(COUNTRYDS);
    trimmed_xml_doc_path = %trim(xml_doc_path);
    xml_doc_len = %len(trimmed_xml_doc_path);
    xml-into returnedDS %xml('/home/apinto1/builds/doc/country.xml': 'case=any doc=file path=countrydetials/country allowextra=yes allowmissing=yes'); 
    return returnedDS;
End-Proc;

//=============================================


    