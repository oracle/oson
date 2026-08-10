/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.binary;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public final class OsonConstants {
  
  /** Magic = 0xff 4a 5a  Version = xx, be */
  static final int MAGIC = 0xff4a5a00;
  
  /** Magic as a byte array */
  public static final byte[] MAGIC_BYTES = new byte[] { -1, 74, 90 };
  
  static final int MAGIC_VERSION1 = MAGIC | 1;
  static final int MAGIC_VERSION3 = MAGIC | 3; 
  
  static final int JZNOCT3_FLDNM2_SZ_UB2 = 0x01_00;
  
  /** No field ID arrays */
  //static final int JZNOCT2_STREAM_ENCODED = 0x0001; // error if set
  /** Does the image use relative offsets (see bug 34124943) */
  static final int JZNOCT2_REL_OFFSET = 0x0001; 
   
  static final int JZNOCT2_INLINE_LEAF = 0x0002; // always on, error if not set
  
  static final int JZNOCT2_SLEN_IN_PCODE = 0x0004; // always on, error if not set
  
  static final int JZNOCT2_TOT_DISFNM_UB4 = 0x0008;
  
  /** Share simple values true/false/null/1/0/""/{}/[]  */
  static final int JZNOCT2_SHR_SIMP_NODES = 0x0020;
  
  /** Share last seen value for each fid */
  static final int JZNOCT2_SHR_NODES = 0x0040;
  
  /** Entire document is a single scalar */
  static final int JZNOCT2_J_SCALAR = 0x0010;
  
  /** First significant byte of hash id (ub1) is stored in hash-id-array. */
  static final int JZNOCT_HID_USEUB1    = 0x0100;
  
  /** 
   * First two significant bytes of hash id (ub2) is stored in hash-id-array.
   * If neither JZNOCT_HID_USEUB1 nor JZNOCT_HID_USEUB2 is set, then by 
   * default the full ub4 hash id is stored in hash-id-array.        
   */
  static final int JZNOCT_HID_USEUB2 = 0x0200; 

  /**
   * total distinct number of field names exceed 255, so we need to 
   * use ub2 to  store number of distinct field names.  By default, ub1 
   * is used to store number of distinct field names.  The array entry
   * size for the array of field-ids for an object is ub2 if this
   *  flag JZNOCT_TOT_DISFNM_UB2 is on.
   */
  static final int JZNOCT_TOT_DISFNM_UB2 = 0x0400;

  /** 
   * Total field-name-string heap segment size uses ub4 to store. By default,
   * ub2 is used to store the size of field-name-string heap. */
  static final int JZNOCT_FLDNM_SZ_UB4 = 0x0800; 
  
  /**           
   * Total  tree-segment  size uses ub4 to store. By default, 
   * ub2 is used to store the size of tree-segment. 
   */
  static final int JZNOCT_TREE_SZ_UB4 = 0x1000;

  /**
   * tiny node stats
   */
  static final int JZNOCT_TINY_NODE_STAT = 0x2000;

  /** Object field ids within each object is NOT sorted by field id */
  public static final int JZNOCT_FID_NO_SORT  = 0x8000; 
  
  /** In update header.  If set, use ub2 for forwarding addresses (else ub4) */
  static final int JZNOCTUPDHDR_OVFLW_SEG_UB2 = 0x0100;
  
  static final int JZNOCT_UPD_OVFLW = 0x75; // 0111 0101
  
  static final int JZNOCT_UPD2_FWA = 0x76; // 0111 0110
  
  static final int JZNOCT_UPD4_FWA = 0x77; // 0111 0111
  
  static final int JZNOCT_UPD_XSZ_RES = 0x78; // 0111 0111
  
  static final int JZNOCT_UPD_OBJ_REF_BITMASK = 0x83; // 1000 0011 
  
  public static final int OPCODE_OFFSET_SIZE_BIT = 0x20; // 0010 0000
  
  public static final int OPCODE_CHILD_SIZE_BITS = 0x18; // 0001 1000
  
  public static final int OPCODE_CHILD_NO_SORT_BIT = 0x04; // 0000 0100
  
  public static final int JZNOCT_OBJECT_TYP = 0x80; // 1000 0000
  
  public static final int JZNOCT_ARRAY_TYP  = 0xC0; // 1100 0000

  /** <3> 1,1,0,0,0,0 JSON null value */
  static final int JZNOCT_JNULL_C  = 0x30;
   
  /** <4> 1,1,0,0,0,1 JSON boolean true */
  static final int JZNOCT_JBOOLT_C = 0x31;
  
  /** <5> 1,1,0,0,1,0 JSON boolean false */
  static final int JZNOCT_JBOOLF_C = 0x32;

  /** <6> 1,1,0,0,1,1 JSON string whose len >=32 && < 256 so that 1 byte for str len is ok */
  static final int JZNOCT_JSUB1L_C = 0x33;

  /** <7> 1,1,0,1,0,0 JSON number which is encoded using ORA DTYNUM whose len > 16 && <= 22 */
  static final int JZNOCT_JORA_DTYNUM_C = 0x34;

  /** <7.1> 1,1,1,0,1,0,0 BSON DEC  number which is encoded using ORA DTYNUM whose len > 16 && <= 22 */
  static final int JZNOCT_JORA_DTYNUM_DEC_C = 0x74;
  
  /** <7.d> 0,1,1,1,1,1,0,1 timestamp 7 */
  static final int JZNOCT_JDTYSTAMP7_C = 0x7d;
  
  /** <7.c> 0,1,1,1,1,1,0,0 timestamp tz 13 */
  static final int JZNOCT_JDTYSTAMP_TZ_C = 0x7c;
  
  /** <7.e> id */
  static final int JZNOCT_JDTYGENID_C = 0x7e;
  
  /** <7.f> float */
  static final int JZNOCT_JDTYFLT_C = 0x7f; 
  
  /** <8> 1,1,0,1,0,1 JSON number which is encoded using string whose len <= 256 so that 1 byte for str len is ok */
  static final int JZNOCT_JSNUM_C  = 0x35;

  /** <9> 1,1,0,1,1,0 JSON number which is encoded using ORA DTYBDOUBLE with len=8 */
  static final int JZNOCT_JDTYDB_C = 0x36;

  /** <10> 1,1,1,0,0,0 JSON string value with ub2 len */
  static final int JZNOCT_JSUB2L_C = 0x37;

  /** <11> 1,1,1,0,0,1 JSON string value with ub4 len */
  static final int JZNOCT_JSUB4L_C = 0x38;

  /** <12> 1,1,1,0,1,0 ORA_TIMESTAMP 11 byte representing JSON string representing timestamp */
  static final int JZNOCT_JDTYSTAMP_C = 0x39;

  /** <13>  2 byte len binary */
  static final int JZNOCT_JBINUB2L_C = 0x3a;
  
  /** <14> 4 byte len binary */
  static final int JZNOCT_JBINUB4L_C = 0x3b;
  
  /** <15> date */
  static final int JZNOCT_JDTYDATE_C = 0x3c;
  
  /** <16> year month */
  static final int JZNOCT_JDTYYM_C = 0x3d;
  
  /** <17> day second */
  static final int JZNOCT_JDTYDS_C = 0x3e;
  
  /** Indicates a 2-byte op-code */
  static final int JZNOCT_JEXT = 0x7b;
  
  /** 0x7b01 - Vector, 4 bytes length, vector bytes */
  static final int JZNOCT_JVECTOR = (JZNOCT_JEXT << 8) | 0x01;
  
  public static final int UB1_MAXSZ = 1 << 8;
  
  public static final int UB2_MAXSZ = 1 << 16;
 
  /** The largest key size for the first dictionary */
  public static int MAX_SMALL_KEY_LENGTH = 0xff;
  
  /** The largest key size for the first dictionary */
  public static int MAX_BIG_KEY_LENGTH = 0xff_ff;
  
  /** Threshold to sort fids of object.  Copied from C implementation */
  static final int JZNOCT_BIN_SRCH_TRIG_LIMIT = 10;
  
  static final int JZNOCT_STR_OK_5BITS = 0x1f; 
  
  /** should be 0xf, bug on c side */
  static final int JZNOCT_ORANUM_OK_4BITS = 0x8;

  /**
   * For a json object opcdoe, if the 7th signifiant bit of the
   * opcode is on, then the field id children array of the object is
   * referened by other object nodes which have identical field id
   * arrays as of this json object 
   */
  static final int JZNOCT_OBJ_FID_REFERRED = 0x02;
  
  /** Tiny node threshold (< than this) */
  static final int JZNOCT_UPD_UB4_FWA_SZ = 5;
    
  public static int MASK_SB4 = 0x40;
  public static boolean isSB4(int op) {
    return (op & 0xF8) == MASK_SB4; // case <1.1> 0100 0xxx
  }
  
  public static int MASK_SB8 = 0x50;
  public static boolean isSB8(int op) {
    return (op & 0xF0) == MASK_SB8; // case <1.2> 0101 xxxx  
  }
  
  public static int MASK_ORANUM_16 = 0x20;
  public static boolean isOraNum16(int op) {
    return (op & 0xF0) == MASK_ORANUM_16; // case <2>   0010 xxxx
  }
  
  public static int MASK_DEC_16 = 0x60;  
  public static boolean isDec_16(int op) {
    return (op & 0xF0) == MASK_DEC_16; // case <2.1> 0110 xxxx
  }
}
