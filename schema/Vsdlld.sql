select * from FR_FRAMEWORK_AREA_POLYGON WHERE STATE = 'VIC';
select * from FR_FRAMEWORK_AREA_POLYGON where FEATURE_TYPE_CODE = 'mainland' and STATE = 'VIC';
SELECT f.SHAPE as geometry from FR_FRAMEWORK_AREA_POLYGON f where f.FEATURE_TYPE_CODE = 'mainland' and f.STATE = 'VIC';
SELECT f.SHAPE, t.x easting, t.y northing 
    from FR_FRAMEWORK_AREA_POLYGON f,
        table(MDSYS.SDO_UTIL.GETVERTICES(f.SHAPE)) t
    where f.FEATURE_TYPE_CODE = 'mainland' and f.STATE = 'VIC';
    
SELECT t.x longitude, t.y latitude from table(MDSYS.SDO_UTIL.GETVERTICES( MDSYS.SDO_GEOMETRY(
        2001, 
        4283,
        MDSYS.SDO_POINT_TYPE(586271.68119835, 5806917.9683928, NULL),
        NULL,
        NULL))) t;    
    
SELECT f.SHAPE.Get_Dims() FROM FR_FRAMEWORK_AREA_POLYGON f WHERE f.NAME = 'LAWRENCE ISLAND';
SELECT f.SHAPE.Get_Dims() FROM FR_FRAMEWORK_AREA_POLYGON f WHERE f.FEATURE_TYPE_CODE = 'mainland' and STATE = 'VIC';
SELECT f.SHAPE.Get_GType() FROM FR_FRAMEWORK_AREA_POLYGON f WHERE f.FEATURE_TYPE_CODE = 'mainland' and STATE = 'VIC';

select DISTINCT type from minten;

select * from minten where type = 'EL';

SELECT * FROM FR_FRAMEWORK_AREA_POLYGON f 
    WHERE MDSYS.SDO_CONTAINS(f.SHAPE,
       MDSYS.SDO_GEOMETRY(
        2001, 
        28354,
        MDSYS.SDO_POINT_TYPE(586271.68119835, 5806917.9683928, NULL),
        NULL,
        NULL)
    ) = 'TRUE';
    
    SELECT * FROM FR_FRAMEWORK_AREA_POLYGON f 
    WHERE MDSYS.SDO_CONTAINS(f.SHAPE,
       MDSYS.SDO_GEOMETRY(
        2001, 
        28355,
        MDSYS.SDO_POINT_TYPE(586271.68119835, 5806917.9683928, NULL),
        NULL,
        NULL)
    ) = 'TRUE';
    
    SELECT * FROM FR_FRAMEWORK_AREA_POLYGON f 
    WHERE MDSYS.SDO_CONTAINS(f.SHAPE,
       MDSYS.SDO_GEOMETRY(
        2001, 
        28355,
        MDSYS.SDO_POINT_TYPE(699758.47554411, 5912594.31684648, NULL),
        NULL,
        NULL)
    ) = 'TRUE'
    and
        f.STATE = 'VIC'
    ;
    
SELECT * FROM FR_FRAMEWORK_AREA_POLYGON f 
    WHERE MDSYS.SDO_CONTAINS(f.SHAPE,
        MDSYS.SDO_GEOMETRY(
            2001, 
            4283,
            MDSYS.SDO_POINT_TYPE(143.5544, -35.33781, NULL),
            NULL,
            NULL)
        ) = 'TRUE'
    and
        f.STATE = 'VIC'
    ;    
    
 SELECT * FROM FR_FRAMEWORK_AREA_POLYGON f 
    WHERE 
        f.STATE = 'VIC'
    and 
        MDSYS.SDO_CONTAINS(f.SHAPE,
            MDSYS.SDO_GEOMETRY(
                2001, 
                4283,
                MDSYS.SDO_POINT_TYPE(143.5544, -35.33781, NULL),
                NULL,
                NULL)
            ) = 'TRUE';       
    
SELECT * FROM FR_FRAMEWORK_AREA_POLYGON f 
    WHERE MDSYS.SDO_CONTAINS((SELECT p.SHAPE FROM FR_FRAMEWORK_AREA_POLYGON p WHERE p.STATE = 'VIC'),
        MDSYS.SDO_GEOMETRY(
            2001, 
            4283,
            MDSYS.SDO_POINT_TYPE(143.5544, -35.33781, NULL),
            NULL,
            NULL)
        ) = 'TRUE';        
    
SELECT * FROM (SELECT * FROM FR_FRAMEWORK_AREA_POLYGON p WHERE p.STATE = 'VIC') f
    WHERE MDSYS.SDO_CONTAINS(f.SHAPE,
         MDSYS.SDO_GEOMETRY(
            2001, 
            4283,
            MDSYS.SDO_POINT_TYPE(143.5544, -35.33781, NULL),
            NULL,
            NULL)
    ) = 'TRUE';
        
    
SELECT count(*) FROM FR_FRAMEWORK_AREA_POLYGON f 
    WHERE MDSYS.SDO_CONTAINS(f.SHAPE,
       MDSYS.SDO_GEOMETRY(
        2001, 
        4283,
        MDSYS.SDO_POINT_TYPE(143.5544, -35.33781, NULL),
        NULL,
        NULL)
    ) = 'TRUE';       
    
SELECT t.x longitude, t.y latitude FROM TABLE(MDSYS.SDO_UTIL.GETVERTICES(SDO_GEOMETRY(
        2001, 
        4283,
        SDO_POINT_TYPE(586271.68119835, 5806917.9683928, NULL),
        NULL,
        NULL)
        )) t;
    
SELECT * FROM FR_FRAMEWORK_AREA_POLYGON f 
    WHERE MDSYS.SDO_inside(
       SDO_GEOMETRY(
        2001, 
        4283,
        SDO_POINT_TYPE(586271.68119835, 5806917.9683928, NULL),
        NULL,
        NULL),
        f.SHAPE
    ) = 'TRUE';    
    
SELECT * FROM FR_FRAMEWORK_AREA_POLYGON f 
    WHERE MDSYS.SDO_CONTAINS( (SELECT p.SHAPE FROM FR_FRAMEWORK_AREA_POLYGON p WHERE p.FEATURE_TYPE_CODE = 'mainland' and p.STATE = 'VIC'),
       SDO_GEOMETRY(
        2001, 
        4283,
        SDO_POINT_TYPE(586271.68119835, 5806917.9683928, NULL),
        NULL,
        NULL)
    ) = 'TRUE';    
    
SELECT * FROM FR_FRAMEWORK_AREA_POLYGON f
    WHERE MDSYS.SDO_RELATE(f.SHAPE, 
        MDSYS.SDO_GEOMETRY(
        2001, 
        4283,
        MDSYS.SDO_POINT_TYPE(586271.68119835, 5806917.9683928, NULL),
        NULL,
        NULL),
        'mask=CONTAINS'
    ) = 'TRUE';
    
SELECT f.STATE, 
    MDSYS.SDO_GEOM.RELATE(f.SHAPE, 'DETERMINE', MDSYS.SDO_GEOMETRY(
        2001, 
        4283,
        MDSYS.SDO_POINT_TYPE( 586271.68119835, 5806917.9683928,  NULL),
        NULL,
        NULL),
        0.001) rela
FROM 
    FR_FRAMEWORK_AREA_POLYGON f
WHERE
    f.STATE = 'VIC' 
AND
    f.FEATURE_TYPE_CODE = 'mainland';
    
SELECT f.STATE, 
    MDSYS.SDO_GEOM.RELATE(f.SHAPE, 'DETERMINE', MDSYS.SDO_GEOMETRY(
        2001, 
        4283,
        MDSYS.SDO_POINT_TYPE(143.5544, -35.33781,  NULL),
        NULL,
        NULL),
        1) rela
FROM 
    FR_FRAMEWORK_AREA_POLYGON f
WHERE
    f.STATE = 'VIC' 
AND
    f.FEATURE_TYPE_CODE = 'mainland';    
    
SELECT * FROM FR_FRAMEWORK_AREA_POLYGON f 
    WHERE 
        f.shape.SDO_POINT.X = 586271.68119835;
        
SELECT SDO_GEOM.VALIDATE_GEOMETRY_WITH_CONTEXT(f.SHAPE, 0.05) FROM FR_FRAMEWORK_AREA_POLYGON f WHERE f.FEATURE_TYPE_CODE = 'mainland' and STATE = 'VIC';
        
SELECT * FROM all_sdo_geom_metadata;    

SELECT * FROM all_sdo_geom_metadata WHERE OWNER = 'MINERALS' AND TABLE_NAME LIKE 'FR_FRA%';

select t.sdo.sdo_point.x as x
     , t.sdo.sdo_point.y as y
  from (select sdo_cs.transform( 
                       sdo_geometry( 2001
                                ,28354
                                 -- , 32754  
--                                   , SDO_POINT_TYPE(586271.68119835,5806917.9683928,NULL)
                                   , SDO_POINT_TYPE(630500, 5845150,NULL)
                                   , null
                                   , null)
                             , 4283     --Longitude / Latitude (WGS 84)  SRID  
                            ) as sdo
        from dual
      ) t;
      
      
 SELECT * FROM MINTEN;  
 
 SELECT * FROM MINTEN m
 WHERE MDSYS.SDO_CONTAINS(m.SHAPE,
       MDSYS.SDO_GEOMETRY(
        2001, 
        4283,
        MDSYS.SDO_POINT_TYPE(143.5544, -35.33781, NULL),
        NULL,
        NULL)
    ) = 'TRUE';  
    
    SELECT * FROM MINTEN m
 WHERE MDSYS.SDO_CONTAINS(m.SHAPE,
       MDSYS.SDO_GEOMETRY(
        2001, 
        4283,
        MDSYS.SDO_POINT_TYPE( 148.4499982, -37.6999972, NULL),
        NULL,
        NULL)
    ) = 'TRUE';  
    
    SELECT * FROM MINTEN m
 WHERE MDSYS.SDO_CONTAINS(m.SHAPE,
       MDSYS.SDO_GEOMETRY(
        2001, 
        28354,
        MDSYS.SDO_POINT_TYPE(732156.87610377, 6086499.6270333, NULL),
        NULL,
        NULL)
    ) = 'TRUE'; 
    
    SELECT * FROM MINTEN m
 WHERE MDSYS.SDO_CONTAINS(m.SHAPE,
       MDSYS.SDO_GEOMETRY(
        2001, 
        28355,
        MDSYS.SDO_POINT_TYPE(587607.98439159, 5810236.29552759, NULL),
        NULL,
        NULL)
    ) = 'TRUE';
    
     SELECT * FROM MINTEN m
 WHERE MDSYS.SDO_CONTAINS(m.SHAPE,
       MDSYS.SDO_GEOMETRY(
        2001, 
        20354,
        MDSYS.SDO_POINT_TYPE(732156.87610377, 6086499.6270333, NULL),
        NULL,
        NULL)
    ) = 'TRUE';
    
     SELECT * FROM MINTEN m
 WHERE MDSYS.SDO_CONTAINS(m.SHAPE,
       MDSYS.SDO_GEOMETRY(
        2001, 
        28355,
        --28354,
        --32754,
       -- 20354,
        MDSYS.SDO_POINT_TYPE(630500, 5845150, NULL),
        NULL,
        NULL)
    ) = 'TRUE';
    
SELECT COUNT(*) FROM MINTEN m
    WHERE 
        m.TNO = 'EL5478'
    AND
        MDSYS.SDO_CONTAINS(m.SHAPE,
        MDSYS.SDO_GEOMETRY(
        2001, 
        28355,
        MDSYS.SDO_POINT_TYPE(630500, 5845150, NULL),
        NULL,
        NULL)
    ) = 'TRUE';   
    
    SELECT COUNT(*) FROM MINTEN m
    WHERE 
        m.TNO = 'EL803'
    AND
        MDSYS.SDO_CONTAINS(m.SHAPE,
        MDSYS.SDO_GEOMETRY(
        2001, 
        4283,
        MDSYS.SDO_POINT_TYPE(143.5544, -35.33781, NULL),
        NULL,
        NULL)
    ) = 'TRUE';   
    
     SELECT COUNT(*) FROM MINTEN m
    WHERE 
        m.TNO = 'EL5478'
    AND
        MDSYS.SDO_CONTAINS(m.SHAPE,
        MDSYS.SDO_GEOMETRY(
        2001, 
        4283,
        MDSYS.SDO_POINT_TYPE(148.47698629581745,  -37.53140210243021, NULL),
        NULL,
        NULL)
    ) = 'TRUE';   
    
 SELECT * FROM MINTEN m
 WHERE
    MDSYS.SDO_FILTER(
        m.shape,
        SDO_GEOMETRY(
            2001, 32754, NULL,
            SDO_ELEM_INFO_ARRAY(1,2,1),
            SDO_ORDINATE_ARRAY(630500, 5845150, NULL)
        )
    ) = 'TRUE';