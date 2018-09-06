INSERT INTO template_config VALUES (1, 'MRT', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector:mandatory,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,DG4:au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,SG4:au.gov.vic.ecodev.mrt.template.processor.sg4.Sg4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector', 'jiang.liu@ecodev.vic.gov.au', 'au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder');
INSERT INTO template_config VALUES (2, 'VGPHYDRO', 'LOC:au.gov.vic.ecodev.template.processor.custom.vgp.hydro.location.meta.VgpHydroLocationMetaProcessor:au.gov.vic.ecodev.template.file.custom.vgp.hydro.FileNameTemplateFileSelector,SAMP:au.gov.vic.ecodev.template.processor.custom.vgp.hydro.samples.meta.VgpHydroSamplesMetaProcessor:au.gov.vic.ecodev.template.file.custom.vgp.hydro.FileNameTemplateFileSelector,ANAL:au.gov.vic.ecodev.template.processor.custom.vgp.hydro.samples.analysis.VgpHydroSamplesAnalysisProcessor:au.gov.vic.ecodev.template.file.custom.vgp.hydro.FileNameTemplateFileSelector,OBS:au.gov.vic.ecodev.template.processor.custom.vgp.hydro.observations.VgpHydroObservationsProcessor:au.gov.vic.ecodev.template.file.custom.vgp.hydro.FileNameTemplateFileSelector', 'jiang.liu@ecodev.vic.gov.au','au.gov.vic.ecodev.template.mail.custom.vgp.hydro.VgpHydroEmailBodyBuilder');
--INSERT INTO template_config VALUES (1, 'MRT', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor,DG4:au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor,SG4:au.gov.vic.ecodev.mrt.template.processor.sg4.Sg4TemplateProcessor');
--INSERT INTO template_config VALUES (1, 'MRT', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor,DG4:au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor');
--INSERT INTO template_config VALUES (1, 'MRT', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor');
--INSERT INTO template_config VALUES (1, 'MRT', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor');
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
    
--INSERT INTO TEMPLATE_CONTEXT_PROPERTIES(ID, TEMPLATE_NAME, PROPERTY_NAME, PROPERTY_VALUE) 
--    VALUES (1097, 'VGPHYDRO', 'MANDATORY.VALIDATE.FIELDS', 'H0202');    
    
INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, FIELD_VALUE) 
    VALUES (1, 1, 'SL4', 'Au', 1, '0.01');
    
 INSERT INTO TEMPLATE_DISPLAY_PROPERTIES(ID, TEMPLATE, DISPLAY_PROPERTIES, HEADER_FIELDS, TEMPLATE_RETRIEVER) 
    values (1, 'MRT', '{"SL4":[{"LOC_SITE":"SITE_ID,EASTING,NORTHING,LATITUDE,LONGITUDE,FILE_NAME,ROW_NUMBER,ISSUE_COLUMN_INDEX"},{"DH_BOREHOLE":"FILE_NAME,ROW_NUMBER,DRILL_TYPE,DEPTH,ELEVATION_KB,AZIMUTH_MAG-SQL:SELECT a.FILE_NAME, a.ROW_NUMBER, b.DRILL_TYPE, a.DEPTH, a.ELEVATION_KB, a.AZIMUTH_MAG FROM DH_BOREHOLE a, DH_DRILLING_DETAILS b where a.DILLING_DETAILS_ID = b.ID AND a.LOADER_ID = ?"}],"DS4":[{"DH_DOWNHOLE":"HOLE_ID,FILE_NAME,ROW_NUMBER,SURVEYED_DEPTH,AZIMUTH_MAG,DIP"}],"DL4":[{"DH_LITHOLOGY":"HOLE_ID,FILE_NAME,ROW_NUMBER,DEPTH_FROM"}],"DG4":[{"DH_GEOCHEMISTRY":"HOLE_ID,SAMPLE_ID,FILE_NAME,ROW_NUMBER,SAMPLE_FROM,SAMPLE_TO,DRILL_CODE"}],"SG4":[{"DH_SURFACE_GEOCHEMISTRY":"SAMPLE_ID,FILE_NAME,ROW_NUMBER,EASTING,NORTHING,SAMPLE_TYPE,ISSUE_COLUMN_INDEX"}]}', '{"SL4":"H1000-false,H1001-true,H1004-true","DS4":"H1000-false,H1001-true,H1004-true","DL4":"H1000-false,H1001-true,H1004-true","DG4":"H1000-false,H1001-true,H1002-true,H1003-true,H1004-true,H1005-true,H1006-true,H1007-true","SG4":"H1000-false,H1001-true,H1002-true,H1003-true,H1004-true,H1005-true,H1006-true,H1007-true"}', '{"SL4":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever","DS4":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever","DL4":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever","DG4":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever","SG4":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever"}');       
    
INSERT INTO TEMPLATE_DISPLAY_PROPERTIES(ID, TEMPLATE, DISPLAY_PROPERTIES, HEADER_FIELDS, TEMPLATE_RETRIEVER) 
    values (2, 'VGPHYDRO', '{"SAMP":[{"SAMP_METADATA":"SITE_ID,SAMPLE_ID,IGSN,FILE_NAME,ROW_NUMBER,CORE_ID,LAB_CODE,TYPE,PREP_CODE,SAMP_DATE,SAMP_TOP,SAMP_BOTTOM,STAND_WATER_LVL,PUMPING_DEPTH,REFERENCE,SAMP_AREA_DESC"}],"ANAL":[{"SAMP_ANALYSIS":"SAMPLE_ID,IGSN,FILE_NAME,ROW_NUMBER,LAB_SAMP_NO,ANAL_DATE,PARAM,UOM,RESULT,ANAL_METH,LOR"}],"OBS":[{"OBSERVATIONS":"SITE_ID,SAMPLE_ID,IGSN,FILE_NAME,ROW_NUMBER,OCCUR_TIME,PARAM,DEPTH_FROM,DEPTH_TO,RESULT,OBSERVER,TYPE"}]}', '{}', '{"LOC":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.vgphydro.VgpHydroTemplateDataRetriever","SAMP":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.vgphydro.VgpHydroTemplateDataRetriever","ANAL":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.vgphydro.VgpHydroTemplateDataRetriever","OBS":"au.gov.vic.ecodev.mrt.rest.service.template.retriever.vgphydro.VgpHydroTemplateDataRetriever"}');       
        
commit;

SELECT * FROM SESSION_HEADER s WHERE s.APPROVED = 0 AND s.REJECTED = 0 ORDER BY s.CREATED desc;

SELECT DISTINCT HOLE_ID FROM DH_DOWNHOLE WHERE LOADER_ID = '1803841892007519023' AND HOLE_ID NOT IN (
select 
    hole_id
    --count(hole_id) as counter
from 
    DH_BOREHOLE 
where 
    LOADER_ID = '1803841892007519023' 
and 
    HOLE_ID in (
        select 
            DISTINCT hole_id 
        from 
            DH_DOWNHOLE 
        where 
            LOADER_ID = '1803841892007519023'
    )
    );
--GROUP BY HOLE_ID);
--having count(hole_id) = 0;

SELECT DISTINCT HOLE_ID FROM DH_LITHOLOGY WHERE LOADER_ID = '1803842161746995807' AND HOLE_ID NOT IN (
SELECT HOLE_ID FROM DH_BOREHOLE WHERE LOADER_ID = '1803842161746995807' AND HOLE_ID IN (
SELECT DISTINCT HOLE_ID FROM DH_LITHOLOGY WHERE LOADER_ID = '1803842161746995807'));

SELECT * FROM DH_BOREHOLE WHERE LOADER_ID =1062717439815094695;

SELECT DISTINCT HOLE_ID FROM DH_DOWNHOLE WHERE LOADER_ID = '1803841892007519023';
SELECT DH.HOLE_ID FROM DH_DOWNHOLE DH, DH_BOREHOLE BH WHERE DH.LOADER_ID = BH.LOADER_ID AND BH.HOLE_ID IN (SELECT DISTINCT HOLE_ID FROM DH_DOWNHOLE WHERE DH_DOWNHOLE.LOADER_ID = '1803841892007519023');

select 
            DISTINCT hole_id
        from 
            DH_DOWNHOLE 
        where 
            LOADER_ID = '1803841892007519023';
            
select dh.HOLE_ID from dh_downhole dh, DH_BOREHOLE bh where dh.HOLE_ID = bh.HOLE_ID and dh.LOADER_ID = bh.LOADER_ID and dh.surveyed_depth > bh.depth and dh.LOADER_ID = '1803841891946810863';            
            
SELECT l.HOLE_ID FROM DH_LITHOLOGY l, DH_BOREHOLE bh WHERE l.HOLE_ID = bh.HOLE_ID AND l.LOADER_ID = bh.LOADER_ID AND l.DEPTH_FROM > bh.DEPTH AND l.LOADER_ID = '1803842161746995807';

SELECT DISTINCT HOLE_ID FROM DH_GEOCHEMISTRY WHERE LOADER_ID = 1803842161064055245 AND HOLE_ID NOT IN (
SELECT HOLE_ID FROM DH_BOREHOLE WHERE LOADER_ID = 1803842161064055245 AND HOLE_ID IN (
SELECT DISTINCT HOLE_ID FROM DH_GEOCHEMISTRY WHERE LOADER_ID = 1803842161064055245));

SELECT gc.HOLE_ID FROM DH_GEOCHEMISTRY gc, DH_BOREHOLE bh WHERE gc.HOLE_ID = bh.HOLE_ID AND gc.LOADER_ID = bh.LOADER_ID and gc.SAMPLE_FROM > bh.depth and gc.LOADER_ID = '1803842161058953061';

SELECT gc.HOLE_ID FROM DH_GEOCHEMISTRY gc, DH_BOREHOLE bh WHERE gc.HOLE_ID = bh.HOLE_ID AND gc.LOADER_ID = bh.LOADER_ID and gc.SAMPLE_TO > bh.depth and gc.LOADER_ID = '1803842161058953061';

update DH_DOWNHOLE SET HOLE_ID = 'STD002' WHERE ID = 1803841891946810880;            

INSERT INTO file_error_log(id, batch_id, SEVERITY, error_msg, CREATED_TIME) values (5, 1, 'WARNING', 'Line number 6 : H0106 must be a number!', systimestamp);

update TEMPLATE_CONTEXT_PROPERTIES set PROPERTY_VALUE = 'H0002,H0005,H0100,H0200,H0201,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0503,H0530,H0531,H0532,H0533,H1000,D'
    where id = 1;
update TEMPLATE_CONTEXT_PROPERTIES set PROPERTY_VALUE = 'H0002,H0005,H0100,H0202,H0203,H0501,H0502,H0503,H0530,H0531,H0600,H0601,H0602,H0700,H0701,H0702,H0800,H0801,H0802,H1000,H1001,H1002,H1003,H1006,D'
    where id = 16;
select error_msg, SEVERITY from file_error_log where batch_id = 1 and SEVERITY = 'ERROR' order by CREATED_TIME asc;

select * from DH_LITHOLOGY;

select class_names from template_config where template_name = 'MRT';

select * from FILE_ERROR_LOG where BATCH_ID = 1803841891063730792;

select * from FILE_ERROR_LOG ORDER BY CREATED_TIME desc;

SELECT * FROM FILE_ERROR_LOG WHERE ERROR_MSG LIKE '%tenement%';

select * from FILE_ERROR_LOG WHERE SEVERITY = 'ERROR';

select * from SESSION_HEADER;

select ID, TEMPLATE from SESSION_HEADER WHERE REJECTED = '1';

SELECT * FROM TEMPLATE_UPDATER_CONFIG;

select * from loc_site;

select * from DH_BOREHOLE;

select * from DH_BOREHOLE where loader_id = '180384216985034425';

SELECT * FROM DH_DRILLING_DETAILS;

SELECT b.DRILL_TYPE FROM DH_BOREHOLE a, DH_DRILLING_DETAILS b where a.DILLING_DETAILS_ID = b.ID;

SELECT b.DRILL_TYPE, a.DEPTH, a.ELEVATION_KB, a.AZIMUTH_MAG FROM DH_BOREHOLE a, DH_DRILLING_DETAILS b where a.DILLING_DETAILS_ID = b.ID AND a.LOADER_ID = 1803841891858440842;

SELECT * FROM TEMPLATE_CONTEXT_PROPERTIES order by id asc;

select * from template_config;

select * from TEMPLATE_CONTEXT_PROPERTIES where TEMPLATE_NAME = 'DS4' and PROPERTY_NAME = 'au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.DownHoleHoleIdNotInBoreHoleSearcher.INIT.FIELDS';

select * from DH_GEOCHEMISTRY;

select * from DH_OPTIONAL_FIELDS WHERE TEMPLATE_NAME = 'DG4' AND TEMPLATE_HEADER LIKE 'Au%';

SELECT * FROM DH_OPTIONAL_FIELDS WHERE TEMPLATE_NAME = 'SL4';

SELECT * FROM DH_OPTIONAL_FIELDS WHERE ROW_NUMBER = 'H1001';

select * from DH_OPTIONAL_FIELDS where loader_id = '1803842161062742566';
select * from DH_OPTIONAL_FIELDS where loader_id = '180384216170827247' and ROW_NUMBER = 'H1001';

select * from DH_OPTIONAL_FIELDS where loader_id = '1062717439233584289' and TEMPLATE_NAME = 'SG4' and ROW_NUMBER = 'H1000';

select * from DH_OPTIONAL_FIELDS where loader_id = '180384216169993114' and ROW_NUMBER = 'H1000';

select * from DH_OPTIONAL_FIELDS where loader_id = 7819569682267858911 and ROW_NUMBER ='3';

select * from DH_OPTIONAL_FIELDS where loader_id = 7819569682095465078 and  '3' > ROW_NUMEBER;

select * from DH_OPTIONAL_FIELDS where loader_id = 7819569682095465078 and ROW_NUMBER < '3';

select distinct row_number from DH_OPTIONAL_FIELDS;

SELECT FILE_NAME, FIELD_VALUE FROM DH_OPTIONAL_FIELDS WHERE LOADER_ID = 7819569682267858911 AND TEMPLATE_NAME = 'SL4' AND TEMPLATE_HEADER = 'H1000' AND ROW_NUMBER = '0' AND COLUMN_NUMBER = 0;

select SITE_ID,EASTING,NORTHING,LATITUDE,LONGITUDE from LOC_SITE;

select SITE_ID,EASTING,NORTHING,LATITUDE,LONGITUDE from LOC_SITE where LOADER_ID = 1803842162120443363;

SELECT LOADER_ID, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER FROM DH_OPTIONAL_FIELDS WHERE LOADER_ID = '180384216652833769' AND TEMPLATE_NAME = 'SL4';

SELECT * FROM DH_OPTIONAL_FIELDS WHERE ROW_NUMBER = 'H1000';

SELECT * FROM SESSION_HEADER s, LOC_SITE l, DH_BOREHOLE b WHERE s.ID = l.LOADER_ID AND s.ID = b.LOADER_ID;

SELECT * FROM SESSION_HEADER s where s.APPROVED = 1 order by created desc;

select * from SESSION_HEADER s where s.REJECTED = 1 ORDER BY CREATED DESC;

SELECT * FROM SESSION_HEADER s where s.id = 1062717439833960773;

SELECT FIELD_VALUE FROM DH_OPTIONAL_FIELDS WHERE LOADER_ID = 180384216736376001 AND TEMPLATE_NAME = 'MRT' AND ROW_NUMBER = 'H1000' AND TEMPLATE_HEADER = 'H1000';

SELECT FIELD_VALUE FROM DH_OPTIONAL_FIELDS WHERE LOADER_ID = 1803842162120443363 AND TEMPLATE_NAME = 'SL4' AND ROW_NUMBER = 'H1000' AND TEMPLATE_HEADER = 'H1000';

SELECT * FROM DH_OPTIONAL_FIELDS WHERE LOADER_ID = 1062717439231372045 AND TEMPLATE_NAME = 'DL4' AND ROW_NUMBER = 'H1000' AND TEMPLATE_HEADER = 'H1000';

select * from DH_OPTIONAL_FIELDS WHERE ROW_NUMBER LIKE 'H10%';

SELECT * FROM SESSION_HEADER sh order by sh.CREATED desc;
SELECT * FROM SESSION_HEADER sh, DH_OPTIONAL_FIELDS of WHERE sh.ID = of.LOADER_ID AND of.ROW_NUMBER LIKE 'H100%';


SELECT * FROM DH_SURFACE_GEOCHEMISTRY WHERE LOADER_ID = 1803842161862807455;

SELECT * FROM DH_LITHOLOGY WHERE LOADER_ID = 1062717439235905366 AND FILE_NAME = 'CA_VICDL4_VEIN2018C.txt';

SELECT * FROM DH_OPTIONAL_FIELDS WHERE LOADER_ID = 1062717439235905366 AND FILE_NAME = 'CA_VICDL4_VEIN2018C.txt';

select * from DH_OPTIONAL_FIELDS where file_name = 'DUOX_VICDS4_SURV2018A.txt' and TEMPLATE_NAME = 'DS4';
UPDATE SESSION_HEADER set APPROVED = 1 WHERE ID in (180384216736364780, 180384216736364786 );

select * from DH_MANDATORY_HEADERS where loader_id = 7819569682116306016 and template_name = 'SL4';

SELECT * FROM SAMP_METADATA WHERE LOADER_ID = 7819569681699061723;
SELECT SITE_ID,SAMPLE_ID,IGSN,CORE_ID,LAB_CODE,TYPE,PREP_CODE,SAMP_DATE,SAMP_TOP,SAMP_BOTTOM,STAND_WATER_LVL,PUMPING_DEPTH,REFERENCE,SAMP_AREA_DESC FROM SAMP_METADATA WHERE LOADER_ID = 7819569682023568110;

SELECT DISTINCT FILE_NAME FROM SAMP_METADATA WHERE LOADER_ID = 7819569681699061723;

SELECT COUNT(ID) FROM SESSION_HEADER WHERE REJECTED = '1';

desc samp_metadata;

delete loc_site where site_id = 'STD001';

delete TEMPLATE_CONFIG;

delete TEMPLATE_CONFIG where id = 2;

delete FILE_ERROR_LOG;

delete from TEMPLATE_CONTEXT_PROPERTIES where ID = 7;
delete from TEMPLATE_CONTEXT_PROPERTIES where ID = 16;

delete TEMPLATE_UPDATER_CONFIG;

delete TEMPLATE_UPDATER_CONFIG where ID = 6;

delete loc_site;
delete DH_BOREHOLE;

select * from TEMPLATE_DISPLAY_PROPERTIES;
DELETE TEMPLATE_DISPLAY_PROPERTIES;

DELETE TEMPLATE_DISPLAY_PROPERTIES WHERE ID = 2;

CREATE TABLE template_updater_config (
  id         NUMBER(25,0) NOT NULL PRIMARY KEY,
  template_name VARCHAR2(250),
  class_names  VARCHAR2(1000)
);

INSERT INTO template_updater_config VALUES (1, 'Sl4Template', 'au.gov.vic.ecodev.mrt.template.processor.sl4.updater.Sl4TemplateUpdater');

INSERT INTO template_config VALUES (1, 'MRT', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor');
INSERT INTO template_config VALUES (1, 'MRT', 'SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor');

drop table LOC_SITE;

CREATE TABLE "TLOADER"."LOC_SITE" 
   (	"LOADER_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE, 
	"SITE_ID" VARCHAR2(250 BYTE) DEFAULT null NOT NULL ENABLE, 
	"GSV_SITE_ID" NUMBER(5,0) DEFAULT null, 
    "ROW_NUMBER"  VARCHAR2(10 BYTE) DEFAULT null NOT NULL ENABLE,
	"PARISH" VARCHAR2(250 BYTE) DEFAULT ' ' NOT NULL ENABLE, 
	"PROSPECT" VARCHAR2(250 BYTE) DEFAULT 'UNK' NOT NULL ENABLE, 
	"AMG_ZONE" NUMBER(5,0) DEFAULT 0 NOT NULL ENABLE, 
	"EASTING" NUMBER DEFAULT 0 NOT NULL ENABLE, 
	"NORTHING" NUMBER DEFAULT 0 NOT NULL ENABLE, 
	"LATITUDE" NUMBER DEFAULT 0, 
	"LONGITUDE" NUMBER DEFAULT 0, 
	"LOCN_ACC" NUMBER DEFAULT 0, 
	"LOCN_DATUM_CD" VARCHAR2(5 BYTE) DEFAULT '0' NOT NULL ENABLE, 
	"ELEVATION_GL" NUMBER DEFAULT null, 
	"ELEV_ACC" NUMBER DEFAULT null, 
	"ELEV_DATUM_CD" VARCHAR2(5 BYTE) DEFAULT 'UNK' NOT NULL ENABLE, 
	"COORD_SYSTEM" VARCHAR2(20 BYTE), 
	"VERTICAL_DATUM" VARCHAR2(25 BYTE), 
	"NUM_DATA_RECORDS" NUMBER(5,0) NOT NULL ENABLE, 
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ISSUE_COLUMN_INDEX" NUMBER(5,0) null,
    "UWI" VARCHAR2(250 BYTE) DEFAULT NULL,
    "LOCN_NAME" VARCHAR2(250 BYTE) DEFAULT NULL,
    "LOCN_DESC" VARCHAR2(250 BYTE) DEFAULT NULL,
    "STATE" VARCHAR2(25 BYTE) DEFAULT NULL,
    "BORE_DIAMETER" NUMBER DEFAULT 0,
    "TD" NUMBER DEFAULT 0,
    "TVD" NUMBER DEFAULT 0,
    "KB" NUMBER DEFAULT 0,
    "DEPTH_DATUM" VARCHAR2(5 BYTE) DEFAULT NULL,
	 CONSTRAINT "LOADERID_SITEID_UNIQUE" UNIQUE ("LOADER_ID", "SITE_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;



DROP TABLE DH_BOREHOLE;

CREATE TABLE "TLOADER"."DH_BOREHOLE" 
   (	
    "LOADER_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE,
    "HOLE_ID" VARCHAR2(250 BYTE) DEFAULT NULL NOT NULL ENABLE, 
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ROW_NUMBER"  VARCHAR2(10 BYTE) DEFAULT null NOT NULL ENABLE,
	"BH_AUTHORITY_CD" VARCHAR2(1 BYTE) DEFAULT 'U' NOT NULL ENABLE, 
	"BH_REGULATION_CD" VARCHAR2(5 BYTE) DEFAULT 'UNK' NOT NULL ENABLE, 
	"DILLING_DETAILS_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE, 
	"DRILLING_START_DT" DATE DEFAULT '01-JAN-1770' NOT NULL ENABLE, 
	"DRILLING_COMPLETION_DT" DATE DEFAULT '01-JAN-1770' NOT NULL ENABLE, 
	"DEPTH" NUMBER DEFAULT NULL, 
	"ELEVATION_KB" NUMBER DEFAULT NULL, 
    "AZIMUTH_MAG" NUMBER DEFAULT NULL,
	"BH_CONFIDENTIAL_FLG" VARCHAR2(1 BYTE) DEFAULT 'Y' NOT NULL ENABLE, 
	"DEPTH_UOM" VARCHAR2(10 BYTE) DEFAULT 'MTR' NOT NULL ENABLE, 
	"AZIMUTH" VARCHAR2(20 BYTE), 
	"LOCAL_NAME" VARCHAR2(225 BYTE), 
	"AZIMUTH_UOM" VARCHAR2(20 BYTE), 
	"DIP" VARCHAR2(20 BYTE), 
	"DIP_UOM" VARCHAR2(20 BYTE), 
	  CONSTRAINT "LOADERID_HOLEID_UNIQUE" UNIQUE ("LOADER_ID", "HOLE_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
 

   --COMMENT ON COLUMN "TLOADER"."DH_BOREHOLE"."SITE_ID" IS 'ID from LOC_SITE RECORD';
 
   COMMENT ON COLUMN "TLOADER"."DH_BOREHOLE"."DILLING_DETAILS_ID" IS 'Identifier from Drilling Details table';
 
   COMMENT ON COLUMN "TLOADER"."DH_BOREHOLE"."DEPTH" IS 'Total depth in ';
 
   COMMENT ON COLUMN "TLOADER"."DH_BOREHOLE"."LOCAL_NAME" IS 'Bore local name Company Hole ID';
   
   DROP TABLE "TLOADER"."TEMPLATE_CONTEXT_PROPERTIES";
   
   CREATE TABLE "TLOADER"."TEMPLATE_CONTEXT_PROPERTIES" 
   (	
    "ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE,
    "TEMPLATE_NAME" VARCHAR2(250 BYTE) DEFAULT NULL NOT NULL ENABLE, 
	"PROPERTY_NAME" VARCHAR2(250 BYTE) DEFAULT NULL NOT NULL ENABLE, 
	"PROPERTY_VALUE" VARCHAR2(1000 BYTE) DEFAULT NULL NOT NULL ENABLE, 
      CONSTRAINT "ID_PK" PRIMARY KEY ("ID"),
	  CONSTRAINT "TEMPNAME_PROPNAME_UNIQUE" UNIQUE ("TEMPLATE_NAME", "PROPERTY_NAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
  
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES VALUES (1, 'SL4', 'MANDATORY.VALIDATE.FIELDS', 'H0002,H0005,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0503,H0530,H0531,H0532,H0533,H1000,D');

INSERT INTO TEMPLATE_CONTEXT_PROPERTIES VALUES (2, 'SL4', 'AZIMUTHMAG.PRECISION', '6');
INSERT INTO TEMPLATE_CONTEXT_PROPERTIES VALUES (3, 'SL4', 'DIP.PRECISION', '6');

DROP TABLE "TLOADER"."FILE_ERROR_LOG";

CREATE TABLE "TLOADER"."FILE_ERROR_LOG" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
	"BATCH_ID" NUMBER(25,0) NOT NULL ENABLE, 
    "SEVERITY" VARCHAR2(10 BYTE) DEFAULT 'ERROR' NOT NULL ENABLE,
	"ERROR_MSG" VARCHAR2(4000 BYTE), 
    "CREATED_TIME" TIMESTAMP NOT NULL ENABLE,
	 CONSTRAINT "FILE_ERROR_LOG_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
  
DROP TABLE "TLOADER"."DH_DOWNHOLE";

CREATE TABLE "TLOADER"."DH_DOWNHOLE" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
	"LOADER_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE,
    "HOLE_ID" VARCHAR2(250 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ROW_NUMBER"  VARCHAR2(10 BYTE) DEFAULT null NOT NULL ENABLE,
    "SURVEYED_DEPTH" NUMBER DEFAULT NULL NOT NULL ENABLE,
	"AZIMUTH_MAG" NUMBER DEFAULT NULL,
    "DIP" NUMBER DEFAULT NULL NOT NULL ENABLE,
    "AZIMUTH_TRUE" NUMBER DEFAULT NULL,
	 CONSTRAINT "DOWN_HOLE_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
  
DROP TABLE "TLOADER"."DH_LITHOLOGY";

CREATE TABLE "TLOADER"."DH_LITHOLOGY" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
	"LOADER_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE,
    "HOLE_ID" VARCHAR2(250 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ROW_NUMBER"  VARCHAR2(10 BYTE) DEFAULT null NOT NULL ENABLE,
    "DEPTH_FROM" NUMBER DEFAULT NULL NOT NULL ENABLE,
	 CONSTRAINT "LITHOLOGY_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
  
DROP TABLE "TLOADER"."DH_GEOCHEMISTRY";

CREATE TABLE "TLOADER"."DH_GEOCHEMISTRY" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
	"LOADER_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE,
    "HOLE_ID" VARCHAR2(250 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "SAMPLE_ID" VARCHAR2(250 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ROW_NUMBER"  VARCHAR2(10 BYTE) DEFAULT null NOT NULL ENABLE,
    "SAMPLE_FROM" NUMBER DEFAULT NULL NOT NULL ENABLE,
    "SAMPLE_TO" NUMBER DEFAULT NULL NOT NULL ENABLE,
    "DRILL_CODE" VARCHAR2(10 BYTE) DEFAULT NULL,
	 CONSTRAINT "GEOCHEMISTRY_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;  
  
  DROP TABLE "TLOADER"."DH_SURFACE_GEOCHEMISTRY";
  
  CREATE TABLE "TLOADER"."DH_SURFACE_GEOCHEMISTRY" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
	"LOADER_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE,
    "SAMPLE_ID" VARCHAR2(250 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ROW_NUMBER"  VARCHAR2(10 BYTE) DEFAULT null NOT NULL ENABLE,
    "EASTING" NUMBER DEFAULT 0 NOT NULL ENABLE, 
	"NORTHING" NUMBER DEFAULT 0 NOT NULL ENABLE, 
    "AMG_ZONE" NUMBER(5,0) DEFAULT 0 NOT NULL ENABLE,
    "SAMPLE_TYPE" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ISSUE_COLUMN_INDEX" NUMBER(5,0) null,
	 CONSTRAINT "SURFACE_GEOCHEMISTRY_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ; 
  
  DROP TABLE "TLOADER"."DH_OPTIONAL_FIELDS";
  
  CREATE TABLE "TLOADER"."DH_OPTIONAL_FIELDS" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
	"LOADER_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE,
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "TEMPLATE_NAME" VARCHAR2(100 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "TEMPLATE_HEADER" VARCHAR2(100 BYTE) DEFAULT NULL NOT NULL ENABLE,
	"ROW_NUMBER"  NUMBER DEFAULT NULL NOT NULL ENABLE, 
    "COLUMN_NUMBER" NUMBER DEFAULT NULL NOT NULL ENABLE,
    "FIELD_VALUE" VARCHAR2(4000 BYTE) DEFAULT NULL,
	 CONSTRAINT "DH_OPTIONAL_FIELDS_PK" PRIMARY KEY ("ID"),
     CONSTRAINT "OP_FLDS_UNIQUE" UNIQUE ("LOADER_ID", "FILE_NAME", "TEMPLATE_NAME", "TEMPLATE_HEADER", "ROW_NUMBER", "COLUMN_NUMBER")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ; 
  
  DROP TABLE "TLOADER"."SESSION_HEADER";
   
  CREATE TABLE "TLOADER"."SESSION_HEADER" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
    "TEMPLATE" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
    "FILE_NAME" VARCHAR2(250 BYTE) NOT NULL ENABLE,
	"PROCESS_DATE" DATE NOT NULL ENABLE, 
	"TENEMENT" VARCHAR2(250 BYTE), 
	"TENEMENT_HOLDER" VARCHAR2(250 BYTE), 
	"REPORTING_DATE" DATE NOT NULL ENABLE, 
	"PROJECT_NAME" VARCHAR2(250 BYTE), 
	"STATUS" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
	"COMMENTS" VARCHAR2(1000 BYTE), 
	"EMAIL_SENT" CHAR(1 BYTE) DEFAULT 'N', 
    "APPROVED" NUMBER(1) DEFAULT 0 NOT NULL ENABLE CHECK ( "APPROVED" IN (0,1)),
    "REJECTED" NUMBER(1) DEFAULT 0 NOT NULL ENABLE CHECK( "REJECTED" IN (0,1)),
    "CREATED" TIMESTAMP DEFAULT SYSDATE NOT NULL ENABLE,
	 CONSTRAINT "SESSION_HEADER_PK" PRIMARY KEY ("ID")
     --, CONSTRAINT "AP_RJ_MT_EXCLUD" CHECK (NOT ("APROVALED" = 1 AND "REJECTED" = 1 ))
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
  
  ALTER TABLE "TLOADER"."SESSION_HEADER"
  ADD CONSTRAINT "APR_REJ_MT_EXCLUD" CHECK (NOT (("APPROVED" = 1) AND ("REJECTED" = 1)));
 

   COMMENT ON COLUMN "TLOADER"."SESSION_HEADER"."ID" IS 'IDENTIFIER for a single Template group  Load Session ';
   
    COMMENT ON COLUMN "TLOADER"."SESSION_HEADER"."TEMPLATE" IS 'Name of the overall template, e.g. MRT ';
    
    COMMENT ON COLUMN "TLOADER"."SESSION_HEADER"."FILE_NAME" IS 'Name of the upload file, e.g. MRT_EL123.zip';
 
   COMMENT ON COLUMN "TLOADER"."SESSION_HEADER"."PROCESS_DATE" IS 'DATE process was run ';
 
   COMMENT ON COLUMN "TLOADER"."SESSION_HEADER"."REPORTING_DATE" IS 'MAINLY FOR MRT can be set to process date for internal Templates';
 
   COMMENT ON COLUMN "TLOADER"."SESSION_HEADER"."PROJECT_NAME" IS 'Name of associated Project';
 
   COMMENT ON COLUMN "TLOADER"."SESSION_HEADER"."STATUS" IS 'Status of process';
 
   COMMENT ON COLUMN "TLOADER"."SESSION_HEADER"."EMAIL_SENT" IS 'Has processed emaild owner';
   
   COMMENT ON COLUMN "TLOADER"."SESSION_HEADER"."APPROVED" IS 'Session has been approved';
   
   COMMENT ON COLUMN "TLOADER"."SESSION_HEADER"."REJECTED" IS 'Session has been rejected';
   
   COMMENT ON COLUMN "TLOADER"."SESSION_HEADER"."CREATED" IS 'Session created time';
   
   DROP TABLE "TLOADER"."TEMPLATE_DISPLAY_PROPERTIES";
   
  CREATE TABLE "TLOADER"."TEMPLATE_DISPLAY_PROPERTIES" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
    "TEMPLATE" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"DISPLAY_PROPERTIES"  VARCHAR2(4000 BYTE),
    "HEADER_FIELDS" VARCHAR2(4000 BYTE),
    "TEMPLATE_RETRIEVER" VARCHAR2(4000 BYTE),
	 CONSTRAINT "TEMPLATE_DISPLAY_PROP_PK" PRIMARY KEY ("ID"),
      CONSTRAINT "TEMPLATE_UNIQUE" UNIQUE ("TEMPLATE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
 

   COMMENT ON COLUMN "TLOADER"."TEMPLATE_DISPLAY_PROPERTIES"."ID" IS 'IDENTIFIER for a single Template group  Load Session ';
   
    COMMENT ON COLUMN "TLOADER"."TEMPLATE_DISPLAY_PROPERTIES"."TEMPLATE" IS 'Name of the overall template, e.g. MRT ';
 
   COMMENT ON COLUMN "TLOADER"."TEMPLATE_DISPLAY_PROPERTIES"."HEADER_FIELDS" IS 'Template header fields ';
   
    COMMENT ON COLUMN "TLOADER"."TEMPLATE_DISPLAY_PROPERTIES"."DISPLAY_PROPERTIES" IS 'Template display fields ';
   
    DROP TABLE "TLOADER"."TEMPLATE_CONFIG";
   
  CREATE TABLE "TLOADER"."TEMPLATE_CONFIG" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
	"TEMPLATE_NAME" VARCHAR2(250 BYTE), 
	"CLASS_NAMES" VARCHAR2(1000 BYTE), 
    "OWNER_EMAILS" VARCHAR2(1000 BYTE),
    "EMAILS_BUILDER" VARCHAR2(1000 BYTE),
	 CONSTRAINT "TEMPLATE_CONFIG_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
 

   COMMENT ON COLUMN "TLOADER"."TEMPLATE_CONFIG"."ID" IS 'IDENTIFIER for a single Template group  Load Session ';
   
    COMMENT ON COLUMN "TLOADER"."TEMPLATE_CONFIG"."TEMPLATE_NAME" IS 'Name of the overall template, e.g. MRT ';
 
   COMMENT ON COLUMN "TLOADER"."TEMPLATE_CONFIG"."CLASS_NAMES" IS 'Template classes, e.g. SL4, DL4 ';
   
   COMMENT ON COLUMN "TLOADER"."TEMPLATE_CONFIG"."OWNER_EMAILS" IS 'Template owners email, e.g. SL4, DL4 ';
   
   COMMENT ON COLUMN "TLOADER"."TEMPLATE_CONFIG"."EMAILS_BUILDER" IS 'Template custom email builder class name';
   
DROP TABLE "TLOADER"."DH_DRILLING_DETAILS";

CREATE TABLE "TLOADER"."DH_DRILLING_DETAILS" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
	"DRILL_TYPE" VARCHAR2(50 BYTE), 
	"DRILL_DESCRIPTION" VARCHAR2(250 BYTE), 
	"DRILL_COMPANY" VARCHAR2(250 BYTE), 
	 CONSTRAINT "DRILLING_DETAILS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
 

   COMMENT ON COLUMN "TLOADER"."DH_DRILLING_DETAILS"."ID" IS 'Unique Identifier';
   
   COMMENT ON COLUMN "TLOADER"."DH_DRILLING_DETAILS"."FILE_NAME" IS 'File name of data come from';
 
   COMMENT ON COLUMN "TLOADER"."DH_DRILLING_DETAILS"."DRILL_TYPE" IS 'Code Used for Drill Type';
 
   COMMENT ON COLUMN "TLOADER"."DH_DRILLING_DETAILS"."DRILL_DESCRIPTION" IS 'Description of Drill Type';
 
   COMMENT ON COLUMN "TLOADER"."DH_DRILLING_DETAILS"."DRILL_COMPANY" IS 'Drilling company name ';   
   
DROP TABLE "TLOADER"."DH_MANDATORY_HEADERS";

CREATE TABLE "TLOADER"."DH_MANDATORY_HEADERS"
(
    "ID" NUMBER(25,0) NOT NULL ENABLE, 
    "LOADER_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE, 
    "TEMPLATE_NAME" VARCHAR2(250 BYTE) DEFAULT NULL NOT NULL ENABLE, 
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ROW_NUMBER"  VARCHAR2(10 BYTE) DEFAULT 0 NOT NULL ENABLE, 
    "COLUMN_HEADER" VARCHAR2(100 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "FIELD_VALUE" VARCHAR2(50 BYTE) DEFAULT NULL,
    CONSTRAINT "MD_HDRS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
   
 DROP TABLE "TLOADER"."SAMP_METADATA";
   
  CREATE TABLE "TLOADER"."SAMP_METADATA" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
	"LOADER_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE, 
    "SITE_ID" NUMBER(15,0) DEFAULT null NOT NULL ENABLE,
    "SAMPLE_ID" NUMBER(15,0) DEFAULT NULL NOT NULL ENABLE,
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ROW_NUMBER"  VARCHAR2(10 BYTE) DEFAULT 0 NOT NULL ENABLE,
    "CORE_ID" NUMBER(15,0) DEFAULT NULL,
    "LAB_CODE" VARCHAR2(25 BYTE) DEFAULT NULL,
    "TYPE" VARCHAR2(100 BYTE) DEFAULT NULL,
    "PREP_CODE" VARCHAR2(100 BYTE) DEFAULT NULL,
    "SAMP_DATE" TIMESTAMP DEFAULT NULL,
    "IGSN" VARCHAR2(25 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "SAMP_TOP" NUMBER NOT NULL ENABLE,
    "SAMP_BOTTOM" NUMBER NOT NULL ENABLE,
    "STAND_WATER_LVL" NUMBER NOT NULL ENABLE,
    "PUMPING_DEPTH" NUMBER NOT NULL ENABLE,
    "REFERENCE" VARCHAR2(100 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "SAMP_AREA_DESC" VARCHAR2(1000 BYTE) DEFAULT NULL,
	 CONSTRAINT "SAMP_METD_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
  
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."ID" IS 'IDENTIFIER for a single sample meta data';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."LOADER_ID" IS 'IDENTIFIER for a single data loading session';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."SITE_ID" IS 'This is the borehole/well ID or if other XXX';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."SAMPLE_ID" IS 'This is the Unique within GSV number';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."CORE_ID" IS 'This is the Core Seq No';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."LAB_CODE" IS 'This is the lab used for the analysis.  E.g. ALS';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."TYPE" IS 'e.g. cutting, core, water, stygofauna, air etc';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."PREP_CODE" IS 'This is how the sample was prepared  prior to analysis.  E.g. grinding';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."SAMP_DATE" IS 'Date of sampling';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."IGSN" IS 'International Geo Sample Number.';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."SAMP_TOP" IS 'This is the "area" within the sample was taken.  It could be the core top or it could be the screen top for a groundwater sample.';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."SAMP_BOTTOM" IS 'This is the "area" within the sample was taken.  It could be the core bottom or it could be the screen bottom for a groundwater sample.';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."STAND_WATER_LVL" IS 'Used for collection of groundwate samples.';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."PUMPING_DEPTH" IS 'Used for collection of groundwate samples.';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."REFERENCE" IS 'This is the addition of a reference to other tabular information.';
COMMENT ON COLUMN "TLOADER"."SAMP_METADATA"."SAMP_AREA_DESC" IS 'Formation/ Aquifer name e.g. Belfast Formation.';

DROP TABLE "TLOADER"."SAMP_ANALYSIS";
   
  CREATE TABLE "TLOADER"."SAMP_ANALYSIS" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
	"LOADER_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE, 
    "SAMPLE_ID" VARCHAR2(250 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ROW_NUMBER"  VARCHAR2(10 BYTE) DEFAULT 0 NOT NULL ENABLE,
    "IGSN" VARCHAR2(100 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "LAB_SAMP_NO" VARCHAR2(100 BYTE) DEFAULT NULL,
    "ANAL_DATE" TIMESTAMP DEFAULT NULL,
    "PARAM" VARCHAR2(100 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "UOM" VARCHAR2(25 BYTE) DEFAULT NULL,
    "RESULT" VARCHAR2(25 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ANAL_METH" VARCHAR2(50 BYTE) DEFAULT NULL,
    "LOR" VARCHAR2(10 BYTE) DEFAULT NULL,
	 CONSTRAINT "SAMP_ANAL_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
 
COMMENT ON COLUMN "TLOADER"."SAMP_ANALYSIS"."ID" IS 'IDENTIFIER for a single analyse data';
COMMENT ON COLUMN "TLOADER"."SAMP_ANALYSIS"."LOADER_ID" IS 'IDENTIFIER for a single data loading session';
COMMENT ON COLUMN "TLOADER"."SAMP_ANALYSIS"."SAMPLE_ID" IS 'This is the Unique within GSV number';
COMMENT ON COLUMN "TLOADER"."SAMP_ANALYSIS"."IGSN" IS 'International Geo Sample Number.';
COMMENT ON COLUMN "TLOADER"."SAMP_ANALYSIS"."LAB_SAMP_NO" IS 'An additional and diffierent sample ID.';
COMMENT ON COLUMN "TLOADER"."SAMP_ANALYSIS"."ANAL_DATE" IS 'The date the sample has been analysed.';
COMMENT ON COLUMN "TLOADER"."SAMP_ANALYSIS"."PARAM" IS 'This is what is being analysed.';
COMMENT ON COLUMN "TLOADER"."SAMP_ANALYSIS"."UOM" IS 'Unit Of Measure.';
COMMENT ON COLUMN "TLOADER"."SAMP_ANALYSIS"."RESULT" IS 'The result of anaylse.';
COMMENT ON COLUMN "TLOADER"."SAMP_ANALYSIS"."ANAL_METH" IS 'This is the method of analysis used by lab.';
COMMENT ON COLUMN "TLOADER"."SAMP_ANALYSIS"."LOR" IS 'Limit Of Reporting.';

 DROP TABLE "TLOADER"."OBSERVATIONS";
   
  CREATE TABLE "TLOADER"."OBSERVATIONS" 
   (	"ID" NUMBER(25,0) NOT NULL ENABLE, 
	"LOADER_ID" NUMBER(25,0) DEFAULT NULL NOT NULL ENABLE, 
    "SITE_ID" VARCHAR2(250 BYTE) DEFAULT null NOT NULL ENABLE,
    "SAMPLE_ID" VARCHAR2(250 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "FILE_NAME" VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "ROW_NUMBER"  VARCHAR2(10 BYTE) DEFAULT 0 NOT NULL ENABLE,
    "IGSN" VARCHAR2(100 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "OCCUR_TIME" TIMESTAMP DEFAULT NULL,
    "PARAM" VARCHAR2(100 BYTE) DEFAULT NULL NOT NULL ENABLE,
    "DEPTH_FROM" NUMBER DEFAULT NULL,
    "DEPTH_TO" NUMBER DEFAULT NULL,
    "RESULT" VARCHAR2(1000 BYTE) DEFAULT NULL,
    "OBSERVER" VARCHAR2(50 BYTE) DEFAULT NULL,
    "TYPE" VARCHAR2(100 BYTE) DEFAULT NULL,
	 CONSTRAINT "OBS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "GEDIS" ;
  
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."ID" IS 'IDENTIFIER for a single sample meta data';
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."LOADER_ID" IS 'IDENTIFIER for a single data loading session';
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."SITE_ID" IS 'This is the borehole/well ID or if other XXX';
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."SAMPLE_ID" IS 'This is the Unique within GSV number';
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."IGSN" IS 'International Geo Sample Number.';
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."OCCUR_TIME" IS 'This is the date sampled.';
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."PARAM" IS 'This is what is being measured or observed.';
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."DEPTH_FROM" IS 'This is sample depth from.';
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."DEPTH_TO" IS 'This is sample depth to.';
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."RESULT" IS 'This is what has been observed.';
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."OBSERVER" IS 'This is observe actor.';
  COMMENT ON COLUMN "TLOADER"."OBSERVATIONS"."TYPE" IS 'What general item are you measuring or observing.';