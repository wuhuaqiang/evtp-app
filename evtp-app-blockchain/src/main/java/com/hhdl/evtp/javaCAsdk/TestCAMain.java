package com.hhdl.evtp.javaCAsdk;

/**
 * Created by 夕 on 2018/10/22 19:07
 */

public class TestCAMain {

    static String orgName ="Org1MSP" ;
    static String mspId="Org1MSP";
    static String calocation="http://10.168.1.139:7054";
    static String adminName="admin";
    static String adminPasswd="adminpw";
    static String newUserName="liming";
    static String newUserPasswd="123456";
    static String affiliation="org2.department1";
    static  String caName = "ca0";//.org1.blockchain.comca0

    public static void main(String[] args) throws Exception {


        System.out.println(FabricCA.registerUser(orgName,mspId,calocation,caName,adminName,adminPasswd,newUserName,newUserPasswd,affiliation));

        System.out.println(FabricCA.EnrollUser(orgName,mspId,caName,calocation,newUserName,newUserPasswd));

        System.out.println(FabricCA.getCertAndKeyStore(orgName,mspId,caName,calocation,newUserName,newUserPasswd));

        FabricCA.RevokeUser(caName,calocation,newUserName,orgName,mspId,newUserPasswd);

    }




}
