FCOUNTRY.SRVPGM: FCOUNTRY.BND COU300.MODULE COU301.MODULE
FCOUNTRY.SRVPGM: TEXT = Functions Country
FCUSTOMER.SRVPGM: FCUSTOMER.BND CUS300.MODULE CUS301.MODULE
FCUSTOMER.SRVPGM: TEXT = Functions Customer
FFAMILLY.SRVPGM: FFAMILLY.BND FAM300.MODULE FAM301.MODULE
FFAMILLY.SRVPGM: TEXT = Functions Family
FPROVIDER.SRVPGM: FPROVIDER.BND PRO300.MODULE
FPROVIDER.SRVPGM: TEXT = Functions Provider
TXT.SRVPGM: TXT.BND TXT001.MODULE 
XML.SRVPGM: XML.BND XML001.MODULE TXT.SRVPGM
FARTICLE.SRVPGM: FARTICLE.BND ART300.MODULE ART301.MODULE FFAMILLY.SRVPGM
FARTICLE.SRVPGM: TEXT = Functions Article
FPARAMETER.SRVPGM: FPARAMETER.BND PAR300.MODULE
FPARAMETER.SRVPGM: TEXT = Functions Parameter
LOG.SRVPGM: LOG.BND LOG300.MODULE
LOG.SRVPGM: TEXT = Functions Log

FVAT.SRVPGM: fvat.bnd VAT300.MODULE
FVAT.SRVPGM: TEXT = Functions VAT
FVAT.SRVPGM: private TEXT = Functions VAT

PARSEUTL.SRVPGM: PARSEUTL.BND COU101.MODULE
