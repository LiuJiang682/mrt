--INSERT INTO template_config VALUES (1, 'mrt', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4,DL4,DG4');
--INSERT INTO template_config VALUES (1, 'mrt', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor');
--INSERT INTO template_config VALUES (1, 'MRT', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor,DG4:au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor');
--INSERT INTO template_config VALUES (1, 'MRT', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor,DG4:au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor,SG4:au.gov.vic.ecodev.mrt.template.processor.sg4.Sg4TemplateProcessor');
INSERT INTO template_config VALUES (1, 'MRT', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector:mandatory,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,DG4:au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,SG4:au.gov.vic.ecodev.mrt.template.processor.sg4.Sg4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector', 'jiang.liu@ecodev.vic.gov.au', 'au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder');
INSERT INTO template_config VALUES (2, 'VGPHYDRO', 'LOC:au.gov.vic.ecodev.template.processor.custom.vgp.hydro.VgpHydroLocationMetaProcessor:au.gov.vic.ecodev.template.file.custom.vgp.hydro.FileNameTemplateFileSelector', 'jiang.liu@ecodev.vic.gov.au','au.gov.vic.ecodev.template.mail.custom.vgp.hydro.VgpHydroEmailBodyBuilder');

INSERT INTO template_updater_config VALUES (1, 'Sl4Template', 'au.gov.vic.ecodev.mrt.template.processor.updater.sl4.Sl4TemplateUpdater');
INSERT INTO template_updater_config VALUES (2, 'Ds4Template', 'au.gov.vic.ecodev.mrt.template.processor.updater.ds4.Ds4TemplateUpdater');
INSERT INTO template_updater_config VALUES (3, 'Dl4Template', 'au.gov.vic.ecodev.mrt.template.processor.updater.dl4.Dl4TemplateUpdater');
INSERT INTO template_updater_config VALUES (4, 'Dg4Template', 'au.gov.vic.ecodev.mrt.template.processor.updater.dg4.Dg4TemplateUpdater');

INSERT INTO file_error_log(id, batch_id, SEVERITY, error_msg, CREATED_TIME) values (1, 1, 'ERROR', 'Line number 6 : H0106 must be a number!', systimestamp);

INSERT INTO DH_DRILLING_DETAILS(ID, FILE_NAME, DRILL_TYPE, DRILL_COMPANY, DRILL_DESCRIPTION) values (1, 'myTest.txt', 'DD', 'Drill Faster Pty Ltd', 'Diamond drilling');

INSERT INTO lOC_SITE(LOADER_ID, SITE_ID, GSV_SITE_ID, ROW_NUMBER, PARISH, PROSPECT, AMG_ZONE, EASTING, NORTHING, LATITUDE, LONGITUDE, LOCN_ACC, LOCN_DATUM_CD, ELEVATION_GL, ELEV_ACC, ELEV_DATUM_CD, COORD_SYSTEM, VERTICAL_DATUM, NUM_DATA_RECORDS, ISSUE_COLUMN_INDEX, FILE_NAME) 
    values (1, 'KPDD001', 0, '1', 'N/A', 'Kryptonite', 55, 392200, 6589600, 0, 0, 0, 'GDA94', 0, 0, 'AHD', null, null, -1, -1, 'myTest.txt');
    
INSERT INTO lOC_SITE(LOADER_ID, SITE_ID, GSV_SITE_ID, ROW_NUMBER, PARISH, PROSPECT, AMG_ZONE, EASTING, NORTHING, LATITUDE, LONGITUDE, LOCN_ACC, LOCN_DATUM_CD, ELEVATION_GL, ELEV_ACC, ELEV_DATUM_CD, COORD_SYSTEM, VERTICAL_DATUM, NUM_DATA_RECORDS, ISSUE_COLUMN_INDEX, FILE_NAME) 
    values (12, 'KPDD001', 0, '2', 'N/A', 'Kryptonite', 55, 392200, 6589600, 0, 0, 0, 'GDA94', 0, 0, 'AHD', null, null, -1, -1, 'myTest.txt');    
    
INSERT INTO lOC_SITE(LOADER_ID, SITE_ID, GSV_SITE_ID, ROW_NUMBER, PARISH, PROSPECT, AMG_ZONE, EASTING, NORTHING, LATITUDE, LONGITUDE, LOCN_ACC, LOCN_DATUM_CD, ELEVATION_GL, ELEV_ACC, ELEV_DATUM_CD, COORD_SYSTEM, VERTICAL_DATUM, NUM_DATA_RECORDS, ISSUE_COLUMN_INDEX, FILE_NAME) 
    values (9999, 'KPDD001', 0, '2', 'N/A', 'Kryptonite', 55, 392200, 6589600, 0, 0, 0, 'GDA94', 0, 0, 'AHD', null, null, -1, -1, 'myTest.txt');        
    
INSERT INTO lOC_SITE(LOADER_ID, SITE_ID, GSV_SITE_ID, ROW_NUMBER, PARISH, PROSPECT, AMG_ZONE, EASTING, NORTHING, LATITUDE, LONGITUDE, LOCN_ACC, LOCN_DATUM_CD, ELEVATION_GL, ELEV_ACC, ELEV_DATUM_CD, COORD_SYSTEM, VERTICAL_DATUM, NUM_DATA_RECORDS, ISSUE_COLUMN_INDEX, FILE_NAME) 
    values (12, 'KPDD002', 0, '3', 'N/A', 'Kryptonite', 55, 392200, 6589600, 0, 0, 0, 'GDA94', 0, 0, 'AHD', null, null, -1, -1, 'myTest.txt');        
    
INSERT INTO DH_BOREHOLE(LOADER_ID, HOLE_ID, ROW_NUMBER, BH_AUTHORITY_CD, BH_REGULATION_CD, DILLING_DETAILS_ID, DRILLING_START_DT, DRILLING_COMPLETION_DT, DEPTH, ELEVATION_KB, AZIMUTH_MAG, BH_CONFIDENTIAL_FLG, DEPTH_UOM, FILE_NAME) 
    values (1, 'KPDD001', '1', 'U', 'N/A', 1, sysdate, sysdate, 210, 320, null, 'Y', 'MTR', 'myTest.txt');    
    
INSERT INTO DH_BOREHOLE(LOADER_ID, HOLE_ID, ROW_NUMBER, BH_AUTHORITY_CD, BH_REGULATION_CD, DILLING_DETAILS_ID, DRILLING_START_DT, DRILLING_COMPLETION_DT, DEPTH, ELEVATION_KB, AZIMUTH_MAG, BH_CONFIDENTIAL_FLG, DEPTH_UOM, FILE_NAME) 
    values (12, 'KPDD001', '2', 'U', 'N/A', 1, sysdate, sysdate, 210, 320, null, 'Y', 'MTR', 'myTest.txt'); 

INSERT INTO DH_BOREHOLE(LOADER_ID, HOLE_ID, ROW_NUMBER, BH_AUTHORITY_CD, BH_REGULATION_CD, DILLING_DETAILS_ID, DRILLING_START_DT, DRILLING_COMPLETION_DT, DEPTH, ELEVATION_KB, AZIMUTH_MAG, BH_CONFIDENTIAL_FLG, DEPTH_UOM, FILE_NAME) 
    values (12, 'KPDD002', '3', 'U', 'N/A', 1, sysdate, sysdate, 210, 320, null, 'Y', 'MTR', 'myTest.txt'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1, 'SL4', 'MANDATORY.VALIDATE.FIELDS', 'H0002,H0005,H0200,H0201,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0503,H0530,H0531,H0532,H0533,H1000,D');

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (2, 'SL4', 'AZIMUTHMAG.PRECISION', '6');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (3, 'SL4', 'DIP.PRECISION', '6');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (4, 'DS4', 'AZIMUTHMAG.PRECISION', '6');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (5, 'DS4', 'DIP.PRECISION', '6');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (6, 'DS4', 'MANDATORY.VALIDATE.FIELDS', 'H0002,H0005,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0503,H0530,H0531,H0534,H0535,H1000,D');

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (7, 'DS4', 'au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.DownHoleHoleIdNotInBoreHoleSearcher.INIT.FIELDS', 'jdbcTemplate,key'); 
                       
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (8, 'DS4', 'au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.DownHoleSurveyedDepthGtTotalDepthSearcher.INIT.FIELDS', 'jdbcTemplate,key');     

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (9, 'DL4', 'MANDATORY.VALIDATE.FIELDS', 'H0002,H0005,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0530,H0531,H1000,D');    

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (10, 'DL4', 'au.gov.vic.ecodev.mrt.template.processor.context.properties.dl4.LithologyHoleIdNotInBoreHoleSearcher.INIT.FIELDS', 'jdbcTemplate,key');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (11, 'DL4', 'au.gov.vic.ecodev.mrt.template.processor.context.properties.dl4.LithologyDepthFromGtTotalDepthSearcher.INIT.FIELDS', 'jdbcTemplate,key');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (12, 'DG4', 'MANDATORY.VALIDATE.FIELDS', 'H0002,H0005,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0530,H0531,H0600,H0601,H0602,H0700,H0701,H0702,H0800,H0801,H0802,H1000,H1001,H1002,H1003,H1006,D'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (13, 'DG4', 'au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4.GeoChemistryHoleIdNotInBoreHoleSearcher.INIT.FIELDS', 'jdbcTemplate,key');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (14, 'DG4', 'au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4.GeoChemistryFromGtTotalDepthSearcher.INIT.FIELDS', 'jdbcTemplate,key');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (15, 'DG4', 'au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4.GeoChemistryToGtTotalDepthSearcher.INIT.FIELDS', 'jdbcTemplate,key'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (16, 'SG4', 'MANDATORY.VALIDATE.FIELDS', 'H0002,H0005,H0202,H0203,H0501,H0502,H0503,H0530,H0531,H0600,H0601,H0602,H0700,H0701,H0702,H0800,H0801,H0802,H1000,H1001,H1002,H1003,H1006,D');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (17, 'DG4', 'H1001.MANDATORY.FIELDS.HEADER', 'H,He,Li,Be,B,C,N,O,F,Ne,Na,Mg,Al,Si,P,S,Cl,Ar,K,Ca,Sc,Ti,V,Cr,Mn,Fe,Co,Ni,Cu,Zn,Ga,Ge,As,Se,Br,Kr,Rb,Sr,Y,Zr,Nb,Mo,Tc,Ru,Rh,Pd,Ag,Cd,In,Sn,Sb,Te,I,Xe,Cs,Ba,La,Ce,Pr,Nd,Pm,Sm,Eu,Gd,Tb,Dy,Ho,Er,Tm,Yb,Lu,Hf,Ta,W,Re,Os,Ir,Pt,Au,Hg,Tl,Pb,Bi,Po,At,Rn,Fr,Ra,Ac,Th,Pa,U,Np,Pu,Am,Cm,Bk,Cf,Es,Fm,Md,No,Lr,Rf,Db,Sg,Bh,Hs,Mt,Ds,Rg,Cn,Nh,Fl,Mc,Lv,Ts,Og');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (18, 'DG4', 'H1002.MANDATORY.FIELDS.HEADER', 'H,He,Li,Be,B,C,N,O,F,Ne,Na,Mg,Al,Si,P,S,Cl,Ar,K,Ca,Sc,Ti,V,Cr,Mn,Fe,Co,Ni,Cu,Zn,Ga,Ge,As,Se,Br,Kr,Rb,Sr,Y,Zr,Nb,Mo,Tc,Ru,Rh,Pd,Ag,Cd,In,Sn,Sb,Te,I,Xe,Cs,Ba,La,Ce,Pr,Nd,Pm,Sm,Eu,Gd,Tb,Dy,Ho,Er,Tm,Yb,Lu,Hf,Ta,W,Re,Os,Ir,Pt,Au,Hg,Tl,Pb,Bi,Po,At,Rn,Fr,Ra,Ac,Th,Pa,U,Np,Pu,Am,Cm,Bk,Cf,Es,Fm,Md,No,Lr,Rf,Db,Sg,Bh,Hs,Mt,Ds,Rg,Cn,Nh,Fl,Mc,Lv,Ts,Og');    

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (19, 'DG4', 'H1003.MANDATORY.FIELDS.HEADER', 'H,He,Li,Be,B,C,N,O,F,Ne,Na,Mg,Al,Si,P,S,Cl,Ar,K,Ca,Sc,Ti,V,Cr,Mn,Fe,Co,Ni,Cu,Zn,Ga,Ge,As,Se,Br,Kr,Rb,Sr,Y,Zr,Nb,Mo,Tc,Ru,Rh,Pd,Ag,Cd,In,Sn,Sb,Te,I,Xe,Cs,Ba,La,Ce,Pr,Nd,Pm,Sm,Eu,Gd,Tb,Dy,Ho,Er,Tm,Yb,Lu,Hf,Ta,W,Re,Os,Ir,Pt,Au,Hg,Tl,Pb,Bi,Po,At,Rn,Fr,Ra,Ac,Th,Pa,U,Np,Pu,Am,Cm,Bk,Cf,Es,Fm,Md,No,Lr,Rf,Db,Sg,Bh,Hs,Mt,Ds,Rg,Cn,Nh,Fl,Mc,Lv,Ts,Og');    

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (20, 'SG4', 'H1001.MANDATORY.FIELDS.HEADER', 'H,He,Li,Be,B,C,N,O,F,Ne,Na,Mg,Al,Si,P,S,Cl,Ar,K,Ca,Sc,Ti,V,Cr,Mn,Fe,Co,Ni,Cu,Zn,Ga,Ge,As,Se,Br,Kr,Rb,Sr,Y,Zr,Nb,Mo,Tc,Ru,Rh,Pd,Ag,Cd,In,Sn,Sb,Te,I,Xe,Cs,Ba,La,Ce,Pr,Nd,Pm,Sm,Eu,Gd,Tb,Dy,Ho,Er,Tm,Yb,Lu,Hf,Ta,W,Re,Os,Ir,Pt,Au,Hg,Tl,Pb,Bi,Po,At,Rn,Fr,Ra,Ac,Th,Pa,U,Np,Pu,Am,Cm,Bk,Cf,Es,Fm,Md,No,Lr,Rf,Db,Sg,Bh,Hs,Mt,Ds,Rg,Cn,Nh,Fl,Mc,Lv,Ts,Og');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (21, 'SG4', 'H1002.MANDATORY.FIELDS.HEADER', 'H,He,Li,Be,B,C,N,O,F,Ne,Na,Mg,Al,Si,P,S,Cl,Ar,K,Ca,Sc,Ti,V,Cr,Mn,Fe,Co,Ni,Cu,Zn,Ga,Ge,As,Se,Br,Kr,Rb,Sr,Y,Zr,Nb,Mo,Tc,Ru,Rh,Pd,Ag,Cd,In,Sn,Sb,Te,I,Xe,Cs,Ba,La,Ce,Pr,Nd,Pm,Sm,Eu,Gd,Tb,Dy,Ho,Er,Tm,Yb,Lu,Hf,Ta,W,Re,Os,Ir,Pt,Au,Hg,Tl,Pb,Bi,Po,At,Rn,Fr,Ra,Ac,Th,Pa,U,Np,Pu,Am,Cm,Bk,Cf,Es,Fm,Md,No,Lr,Rf,Db,Sg,Bh,Hs,Mt,Ds,Rg,Cn,Nh,Fl,Mc,Lv,Ts,Og');    

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (22, 'SG4', 'H1003.MANDATORY.FIELDS.HEADER', 'H,He,Li,Be,B,C,N,O,F,Ne,Na,Mg,Al,Si,P,S,Cl,Ar,K,Ca,Sc,Ti,V,Cr,Mn,Fe,Co,Ni,Cu,Zn,Ga,Ge,As,Se,Br,Kr,Rb,Sr,Y,Zr,Nb,Mo,Tc,Ru,Rh,Pd,Ag,Cd,In,Sn,Sb,Te,I,Xe,Cs,Ba,La,Ce,Pr,Nd,Pm,Sm,Eu,Gd,Tb,Dy,Ho,Er,Tm,Yb,Lu,Hf,Ta,W,Re,Os,Ir,Pt,Au,Hg,Tl,Pb,Bi,Po,At,Rn,Fr,Ra,Ac,Th,Pa,U,Np,Pu,Am,Cm,Bk,Cf,Es,Fm,Md,No,Lr,Rf,Db,Sg,Bh,Hs,Mt,Ds,Rg,Cn,Nh,Fl,Mc,Lv,Ts,Og');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1000, 'DS4', 'H0002', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0002Validator'); 

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1001, 'DS4', 'H0005', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0005Validator'); 

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1002, 'DS4', 'H0202', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4.H0202Validator');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1003, 'DS4', 'H0203', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0203Validator');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1004, 'DS4', 'H0400', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0400Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1005, 'DS4', 'H0401', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0401Validator');    

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1006, 'DS4', 'H0402', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0402Validator');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1007, 'DS4', 'H0501', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0501Validator');   
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1008, 'DS4', 'H0502', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0502Validator');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1009, 'DS4', 'H0503', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0503Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1010, 'DS4', 'H0530', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0530Validator');     
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1011, 'DS4', 'H0531', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0531Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1012, 'DS4', 'H0534', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4.H0534Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1013, 'DS4', 'H0535', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4.H0535Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1014, 'DS4', 'H1000', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4.H1000Validator');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1015, 'DS4', 'D', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4.DValidator');   
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1016, 'SL4', 'H0002', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0002Validator'); 

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1017, 'SL4', 'H0005', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0005Validator'); 

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1018, 'SL4', 'H0202', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.H0202Validator');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1019, 'SL4', 'H0203', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0203Validator');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1020, 'SL4', 'H0400', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0400Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1021, 'SL4', 'H0401', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0401Validator');    

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1022, 'SL4', 'H0402', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0402Validator');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1023, 'SL4', 'H0501', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0501Validator');   
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1024, 'SL4', 'H0502', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0502Validator');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1025, 'SL4', 'H0503', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0503Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1026, 'SL4', 'H0530', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0530Validator');     
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1027, 'SL4', 'H0531', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0531Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1028, 'SL4', 'H0532', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.H0532Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1029, 'SL4', 'H0533', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.H0533Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1030, 'SL4', 'H1000', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.H1000Validator');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1031, 'SL4', 'D', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.DValidator');       

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1032, 'SL4', 'H0200', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.H0200Validator');     
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1033, 'SL4', 'H0201', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.H0201Validator');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1034, 'DL4', 'H0002', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0002Validator'); 

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1035, 'DL4', 'H0005', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0005Validator');   

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1036, 'DL4', 'H0202', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4.H0202Validator');

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1037, 'DL4', 'H0203', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0203Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1038, 'DL4', 'H0400', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0400Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1039, 'DL4', 'H0401', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0401Validator');    

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1040, 'DL4', 'H0402', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0402Validator');
 
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1041, 'DL4', 'H0501', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0501Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1042, 'DL4', 'H0502', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0502Validator');
 
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1043, 'DL4', 'H0530', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0530Validator'); 

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1044, 'DL4', 'H0531', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0531Validator');     

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1045, 'DL4', 'H1000', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4.H1000Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1046, 'DL4', 'D', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4.DValidator');  

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1047, 'DG4', 'H0002', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0002Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1048, 'DG4', 'H0005', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0005Validator');     
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1049, 'DG4', 'H0202', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H0202Validator'); 

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1050, 'DG4', 'H0400', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0400Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1051, 'DG4', 'H0401', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0401Validator');    

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1052, 'DG4', 'H0402', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0402Validator');
 
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1053, 'DG4', 'H0501', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0501Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1054, 'DG4', 'H0502', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0502Validator');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1055, 'DG4', 'H0530', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0530Validator'); 
  
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1056, 'DG4', 'H0531', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0531Validator'); 

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1057, 'DG4', 'H1000', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H1000Validator');
 
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1058, 'DG4', 'D', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.DValidator');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1059, 'DG4', 'H0203', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0203Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1060, 'SG4', 'H0002', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0002Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1061, 'SG4', 'H0005', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0005Validator');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1062, 'SG4', 'H0202', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H0202Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1063, 'SG4', 'H0203', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0203Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1064, 'SG4', 'H0501', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0501Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1065, 'SG4', 'H0502', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0502Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1066, 'SG4', 'H0503', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0503Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1067, 'SG4', 'H0530', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0530Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1068, 'SG4', 'H0531', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0531Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1069, 'SG4', 'H1000', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H1000Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1070, 'SG4', 'D', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.DValidator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1071, 'DG4', 'H0600', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0600Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1072, 'DG4', 'H0601', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0601Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1073, 'DG4', 'H0602', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0602Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1074, 'DG4', 'H0700', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0700Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1075, 'DG4', 'H0701', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0701Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1076, 'DG4', 'H0702', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0702Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1077, 'DG4', 'H0800', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0800Validator');     
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1079, 'DG4', 'H0802', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0802Validator');    
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1080, 'DG4', 'H1001', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H1001Validator');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1081, 'DG4', 'H1002', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H1002Validator');   
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1082, 'DG4', 'H1003', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H1003Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1083, 'DG4', 'H1006', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H1006Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1084, 'SG4', 'H0600', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0600Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1085, 'SG4', 'H0601', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0601Validator');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1086, 'SG4', 'H0602', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0602Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1087, 'SG4', 'H0700', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0700Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1088, 'SG4', 'H0701', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0701Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1089, 'SG4', 'H0702', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0702Validator');
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1090, 'SG4', 'H0800', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0800Validator');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1091, 'SG4', 'H0801', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0801Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1092, 'SG4', 'H0802', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0802Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1093, 'SG4', 'H1001', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H1001Validator');  
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1094, 'SG4', 'H1002', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H1002Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1095, 'SG4', 'H1003', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H1003Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1096, 'SG4', 'H1006', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H1006Validator');  
    
    
INSERT INTO DH_DOWNHOLE(ID, LOADER_ID, HOLE_ID, FILE_NAME, ROW_NUMBER, SURVEYED_DEPTH, AZIMUTH_MAG, DIP, AZIMUTH_TRUE)
    VALUES (1, 1, 'KPDD001', 'myTest.txt', '1', 30, 180, 60, NULL);
    
INSERT INTO DH_LITHOLOGY(ID, LOADER_ID, HOLE_ID, FILE_NAME, ROW_NUMBER, DEPTH_FROM)
    VALUES (1, 1, 'KPDD001', 'myTest.txt', '1', 3);    
    
INSERT INTO DH_GEOCHEMISTRY(ID, LOADER_ID, HOLE_ID, SAMPLE_ID, FILE_NAME, ROW_NUMBER, SAMPLE_FROM, SAMPLE_TO, DRILL_CODE)
    VALUES (1, 1, 'KPDD001', 'ABC123', 'myTest.txt', '1', 0, 1, 'DD');
    
INSERT INTO DH_SURFACE_GEOCHEMISTRY(ID, LOADER_ID, SAMPLE_ID, FILE_NAME, ROW_NUMBER, EASTING, NORTHING, SAMPLE_TYPE, AMG_ZONE, ISSUE_COLUMN_INDEX)
    VALUES (1, 1, 'KPDD001', 'myTest.txt', '1', 0, 1, 'DD', 54, -1);    
    
INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE) 
    VALUES (1, 1, 'myTest.txt', 'SL4', 'Au', 1, 0, '0.01');    
    
INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE) 
    VALUES (2, 1, 'myTest.txt', 'SL4', 'H1000', 0, 0, 'Hole_id,NAT_Grid_ID,Easting_MGA,Northing_MGA,Elevation,Total Hole Depth,Drill Code,Nat_Survey_Method,Precollar_Depth,Dip,Azimuth_MAG,Lease_ID,Prospect,Date_Started,Date_Completed,Company,Comments');   

INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE) 
    VALUES (3, 12, 'myTest.txt', 'SL4', 'H1000', 0, 0, 'Hole_id,NAT_Grid_ID,Easting_MGA,Northing_MGA,Elevation,Total Hole Depth,Drill Code,Nat_Survey_Method,Precollar_Depth,Dip,Azimuth_MAG,Lease_ID,Prospect,Date_Started,Date_Completed,Company,Comments');  

INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE) 
    VALUES (4, 100, 'myTest.txt', 'SL4', 'H1000', 0, 0, 'Hole_id,NAT_Grid_ID,Easting_MGA,Northing_MGA,Elevation,Total Hole Depth,Drill Code,Nat_Survey_Method,Precollar_Depth,Dip,Azimuth_MAG,Lease_ID,Prospect,Date_Started,Date_Completed,Company,Comments');  
    
INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE) 
    VALUES (5, 1, 'myTest.txt', 'DS4', 'H1000', 0, 0, 'Hole_id');  
    
INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE) 
    VALUES (6, 12, 'myTest.txt', 'SL4', 'Au', 1, 0, '0.01');    
    
INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE) 
    VALUES (7, 12, 'myTest.txt', 'SL4', 'Au', 2, 0, '0.01');     
    
INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE) 
    VALUES (8, 100, 'myTest.txt', 'DS4', 'H1000', 0, 0, 'Hole_id,Sureryed_Depth,Azimuth_Mag,Dip,Azimuth_True');  
    
INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE) 
    VALUES (9, 100, 'myTest.txt', 'DL4', 'H1000', 0, 0, 'Hole_id,Depth_from'); 
    
INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE) 
    VALUES (10, 100, 'myTest.txt', 'DG4', 'H1000', 0, 0, 'Hole_id,Sample_id,Sample_from,Sample_to,Drill_code');  
    
INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE) 
    VALUES (11, 100, 'myTest.txt', 'SG4', 'H1000', 0, 0, 'Sample_id,Easting,Northing,Sample_type');    
    
INSERT INTO SESSION_HEADER(ID, TEMPLATE, FILE_NAME, PROCESS_DATE, TENEMENT, TENEMENT_HOLDER, REPORTING_DATE, PROJECT_NAME, STATUS, COMMENTS, EMAIL_SENT, APPROVED, REJECTED, CREATED) 
    values (100, 'MRT', 'MRT_EL123.zip', sysdate, 'abc', 'def', sysdate, 'ghi', 'running', '', 'N', 0, 0, sysdate);   
    
INSERT INTO SESSION_HEADER(ID, TEMPLATE, FILE_NAME, PROCESS_DATE, TENEMENT, TENEMENT_HOLDER, REPORTING_DATE, PROJECT_NAME, STATUS, COMMENTS, EMAIL_SENT, APPROVED, REJECTED, CREATED) 
    values (200, 'MRT', 'MRT_EL123.zip', sysdate, 'abc', 'def', sysdate, 'ghi', 'running', '', 'N', 0, 1, sysdate);   
   
INSERT INTO SESSION_HEADER(ID, TEMPLATE, FILE_NAME, PROCESS_DATE, TENEMENT, TENEMENT_HOLDER, REPORTING_DATE, PROJECT_NAME, STATUS, COMMENTS, EMAIL_SENT, APPROVED, REJECTED, CREATED) 
    values (201, 'MRT', 'MRT_EL123.zip', sysdate, 'abc', 'def', sysdate, 'ghi', 'running', '', 'N', 0, 1, sysdate);   
    
INSERT INTO SESSION_HEADER(ID, TEMPLATE, FILE_NAME, PROCESS_DATE, TENEMENT, TENEMENT_HOLDER, REPORTING_DATE, PROJECT_NAME, STATUS, COMMENTS, EMAIL_SENT, APPROVED, REJECTED, CREATED) 
    values (99991, 'MRT', 'MRT_EL123.zip', sysdate, 'abc', 'def', sysdate, 'ghi', 'running', '', 'N', 0, 1, sysdate);   
        
INSERT INTO TEMPLATE_DISPLAY_PROPERTIES(ID, TEMPLATE, DISPLAY_PROPERTIES) 
    values (100, 'MRT', '{"SL4":[{"LOC_SITE":"SITE_ID,EASTING,NORTHING,LATITUDE,LONGITUDE"},{"DH_BOREHOLE":"DRILL_TYPE,DEPTH,ELEVATION_KB,AZIMUTH_MAG-SQL:SELECT b.DRILL_TYPE, a.DEPTH, a.ELEVATION_KB, a.AZIMUTH_MAG FROM DH_BOREHOLE a, DH_DRILLING_DETAILS b where a.DILLING_DETAILS_ID = b.ID AND a.LOADER_ID = ?"}],"DS4":[{"DH_DOWNHOLE":"SURVEYED_DEPTH,AZIMUTH_MAG,DIP"}]}')    