package com.sist.web.vo;

import lombok.Data;

/*
 *  RCP_SEQ          NOT NULL NUMBER(10)    
	RCP_NM                    VARCHAR2(200) 
	RCP_WAY2                  VARCHAR2(100) 
	RCP_PAT2                  VARCHAR2(100) 
	INFO_WGT                  VARCHAR2(50)  
	INFO_ENG                  NUMBER(10,2)  
	INFO_CAR                  NUMBER(10,2)  
	INFO_PRO                  NUMBER(10,2)  
	INFO_FAT                  NUMBER(10,2)  
	INFO_NA                   NUMBER(10,2)  
	HASH_TAG                  VARCHAR2(200) 
	ATT_FILE_NO_MAIN          VARCHAR2(500) 
	ATT_FILE_NO_MK            VARCHAR2(500) 
	RCP_PARTS_DTLS            CLOB          
	RCP_NA_TIP                CLOB          
	USER_ID                   VARCHAR2(20)  
	HIT                       NUMBER   
 */

@Data
public class RecipeVO {
	private int rcp_seq;
    private String rcp_nm;
    private String rcp_way2;
    private String rcp_pat2;
    private String info_wgt;
    private double info_eng;
    private double info_car;
    private double info_pro;
    private double info_fat;
    private double info_na;
    private String hash_tag;
    private String att_file_no_main;
    private String att_file_no_mk;
    private String rcp_parts_dtls;
    private String rcp_na_tip;
    private String user_id;
}
