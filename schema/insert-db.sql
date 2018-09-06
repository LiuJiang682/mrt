INSERT INTO template_config VALUES (1, 'MRT', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector:mandatory,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,DG4:au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,SG4:au.gov.vic.ecodev.mrt.template.processor.sg4.Sg4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector', 'jiang.liu@ecodev.vic.gov.au', 'au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder');
INSERT INTO template_config VALUES (2, 'VGPHYDRO', 'LOC:au.gov.vic.ecodev.template.processor.custom.vgp.hydro.location.meta.VgpHydroLocationMetaProcessor:au.gov.vic.ecodev.template.file.custom.vgp.hydro.FileNameTemplateFileSelector,SAMP:au.gov.vic.ecodev.template.processor.custom.vgp.hydro.samples.meta.VgpHydroSamplesMetaProcessor:au.gov.vic.ecodev.template.file.custom.vgp.hydro.FileNameTemplateFileSelector,ANAL:au.gov.vic.ecodev.template.processor.custom.vgp.hydro.samples.analysis.VgpHydroSamplesAnalysisProcessor:au.gov.vic.ecodev.template.file.custom.vgp.hydro.FileNameTemplateFileSelector,OBS:au.gov.vic.ecodev.template.processor.custom.vgp.hydro.observations.VgpHydroObservationsProcessor:au.gov.vic.ecodev.template.file.custom.vgp.hydro.FileNameTemplateFileSelector', 'jiang.liu@ecodev.vic.gov.au','au.gov.vic.ecodev.template.mail.custom.vgp.hydro.VgpHydroEmailBodyBuilder');

INSERT INTO file_error_log(id, batch_id, error_msg, CREATED_TIME) values (1, 1, 'Line number 6 : H0106 must be a number!', systimestamp);

INSERT INTO template_updater_config VALUES (1, 'Sl4Template', 'au.gov.vic.ecodev.mrt.template.processor.updater.sl4.Sl4TemplateUpdater');
INSERT INTO template_updater_config VALUES (2, 'Ds4Template', 'au.gov.vic.ecodev.mrt.template.processor.updater.ds4.Ds4TemplateUpdater');
INSERT INTO template_updater_config VALUES (3, 'Dl4Template', 'au.gov.vic.ecodev.mrt.template.processor.updater.dl4.Dl4TemplateUpdater');
INSERT INTO template_updater_config VALUES (4, 'Dg4Template', 'au.gov.vic.ecodev.mrt.template.processor.updater.dg4.Dg4TemplateUpdater');
INSERT INTO template_updater_config VALUES (5, 'Sg4Template', 'au.gov.vic.ecodev.mrt.template.processor.updater.sg4.Sg4TemplateUpdater');
INSERT INTO template_updater_config VALUES (6, 'VgpHydroLocMetaTemplate', 'au.gov.vic.ecodev.template.processor.updater.custom.vgp.hydro.VgpHydroLocationMetaTemplateUpdater');
INSERT INTO template_updater_config VALUES (7, 'VgpHydroSamplesMetaTemplate', 'au.gov.vic.ecodev.template.processor.updater.custom.vgp.hydro.VgpHydroSamplesMetaTemplateUpdater');
INSERT INTO template_updater_config VALUES (8, 'VgpHydroSamplesAnalysisTemplate', 'au.gov.vic.ecodev.template.processor.updater.custom.vgp.hydro.VgpHydroSamplesAnalysisTemplateUpdater');
INSERT INTO template_updater_config VALUES (9, 'VgpHydroObservationsTemplate', 'au.gov.vic.ecodev.template.processor.updater.custom.vgp.hydro.VgpHydroObservationsTemplateUpdater');

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1, 'SL4', 'MANDATORY.VALIDATE.FIELDS', 'H0002,H0005,H0100,H0200,H0201,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0503,H0530,H0531,H0532,H0533,H1000,D');

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
    VALUES (16, 'SG4', 'MANDATORY.VALIDATE.FIELDS', 'H0002,H0005,H0100,H0202,H0203,H0501,H0502,H0503,H0530,H0531,H0600,H0601,H0602,H0700,H0701,H0702,H0800,H0801,H0802,H1000,H1001,H1002,H1003,H1006,D');
    
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
    VALUES (1078, 'DG4', 'H0801', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0801Validator'); 
    
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
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1098, 'SL4', 'H0100', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0100Validator'); 
    
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
    VALUES (1099, 'SG4', 'H0100', 'au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0100Validator'); 
    
INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, FIELD_VALUE) 
    VALUES (1, 1, 'SL4', 'Au', 1, '0.01');
    
 INSERT INTO TEMPLATE_DISPLAY_PROPERTIES(ID, TEMPLATE, DISPLAY_PROPERTIES, HEADER_FIELDS, TEMPLATE_RETRIEVER) 
    values (1, 'MRT', '{"SL4":[{"LOC_SITE":"SITE_ID,EASTING,NORTHING,LATITUDE,LONGITUDE,FILE_NAME,ROW_NUMBER,ISSUE_COLUMN_INDEX"},{"DH_BOREHOLE":"FILE_NAME,ROW_NUMBER,DRILL_TYPE,DEPTH,ELEVATION_KB,AZIMUTH_MAG-SQL:SELECT a.FILE_NAME, a.ROW_NUMBER, b.DRILL_TYPE, a.DEPTH, a.ELEVATION_KB, a.AZIMUTH_MAG FROM DH_BOREHOLE a, DH_DRILLING_DETAILS b where a.DILLING_DETAILS_ID = b.ID AND a.LOADER_ID = ?"}],"DS4":[{"DH_DOWNHOLE":"HOLE_ID,FILE_NAME,ROW_NUMBER,SURVEYED_DEPTH,AZIMUTH_MAG,DIP"}],"DL4":[{"DH_LITHOLOGY":"HOLE_ID,FILE_NAME,ROW_NUMBER,DEPTH_FROM"}],"DG4":[{"DH_GEOCHEMISTRY":"HOLE_ID,SAMPLE_ID,FILE_NAME,ROW_NUMBER,SAMPLE_FROM,SAMPLE_TO,DRILL_CODE"}],"SG4":[{"DH_SURFACE_GEOCHEMISTRY":"SAMPLE_ID,FILE_NAME,ROW_NUMBER,EASTING,NORTHING,SAMPLE_TYPE,ISSUE_COLUMN_INDEX"}]}', '{"SL4":"H1000-false,H1001-true,H1004-true","DS4":"H1000-false,H1001-true,H1004-true","DL4":"H1000-false,H1001-true,H1004-true","DG4":"H1000-false,H1001-true,H1002-true,H1003-true,H1004-true,H1005-true,H1006-true,H1007-true","SG4":"H1000-false,H1001-true,H1002-true,H1003-true,H1004-true,H1005-true,H1006-true,H1007-true"}', '{"SL4":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever","DS4":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever","DL4":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever","DG4":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever","SG4":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever"}');       
    
INSERT INTO TEMPLATE_DISPLAY_PROPERTIES(ID, TEMPLATE, DISPLAY_PROPERTIES, HEADER_FIELDS, TEMPLATE_RETRIEVER) 
    values (2, 'VGPHYDRO', '{"SAMP":[{"SAMP_METADATA":"SITE_ID,SAMPLE_ID,IGSN,FILE_NAME,ROW_NUMBER,CORE_ID,LAB_CODE,TYPE,PREP_CODE,SAMP_DATE,SAMP_TOP,SAMP_BOTTOM,STAND_WATER_LVL,PUMPING_DEPTH,REFERENCE,SAMP_AREA_DESC"}],"ANAL":[{"SAMP_ANALYSIS":"SAMPLE_ID,IGSN,FILE_NAME,ROW_NUMBER,LAB_SAMP_NO,ANAL_DATE,PARAM,UOM,RESULT,ANAL_METH,LOR"}],"OBS":[{"OBSERVATIONS":"SITE_ID,SAMPLE_ID,IGSN,FILE_NAME,ROW_NUMBER,OCCUR_TIME,PARAM,DEPTH_FROM,DEPTH_TO,RESULT,OBSERVER,TYPE"}]}', '{}', '{"LOC":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.vgphydro.VgpHydroTemplateDataRetriever","SAMP":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.vgphydro.VgpHydroTemplateDataRetriever","ANAL":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.vgphydro.VgpHydroTemplateDataRetriever","OBS":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.vgphydro.VgpHydroTemplateDataRetriever"}');       
        
commit;