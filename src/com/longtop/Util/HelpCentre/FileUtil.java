package com.longtop.Util.HelpCentre;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.apache.log4j.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);

    /**
     * 解压ZIP文件
     * @param queryCondition 参数
     * @param agentId	银行编号
     * @param zipPath	ZIP文件路径 /wls10/…/BankReceipt
     * @param zipName	ZIP文件名
     * @return
     * @throws Exception
     */
    public static String unZip(String queryCondition,String agentId,String zipPath,String zipName) throws Exception{
        long start = System.currentTimeMillis();
        String SuccessFilg="E9999|解压失败";
        File filePath = new File(zipPath+ File.separator+zipName);
        File floderPath = new File(zipPath+File.separator+queryCondition);
        //判断是否含有条件层级的文件夹，如果没有，需要创建
        if(!floderPath.exists())
            floderPath.mkdirs();
        //判断是否存在压缩文件
        if(filePath.exists()){
            ZipFile zipFile = null;
            try {
                zipFile=new ZipFile(filePath);
                Enumeration<? extends ZipEntry> entries=zipFile.entries();
                while(entries.hasMoreElements()){
                    ZipEntry entry=(ZipEntry)entries.nextElement();
                    if(agentId.equals("103") && entry.getName().equals("result.txt"))
                        continue;
                    logger.info("解压"+entry.getName());
                    // 如果是文件夹，就创建个文件夹
                    if(entry.isDirectory()){
                        String dirPath=zipPath+ File.separator + queryCondition+File.separator+entry.getName();
                        File dir=new File(dirPath);
                        dir.mkdirs();
                    }else{
                        // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                        File targetFile=new File(zipPath + File.separator + queryCondition + File.separator + entry.getName());
                        // 保证这个文件的父文件夹必须要存在
                        if(!targetFile.getParentFile().exists())
                            targetFile.getParentFile().mkdirs();
                        targetFile.createNewFile();
                        //将压缩文件的内容写入到这个文件中
                        InputStream is =zipFile.getInputStream(entry);
                        FileOutputStream fos=new FileOutputStream(targetFile);
                        int len;
                        byte[] buf=new byte[2048];
                        while((len=is.read(buf))>-1){
                            fos.write(buf, 0, len);
                        }
                        fos.close();
                        is.close();
                    }
                }
                long end=System.currentTimeMillis();
                logger.info("解压完成，耗时：" + (end - start)/1000 +" s");
                SuccessFilg="M0001|处理成功";
            } catch (Exception e) {
                throw e;
            }finally{
                if(zipFile != null){
                    try {
                        zipFile.close();
                    } catch (IOException e) {
                        throw e;
                    }
                }
            }
        }else{
            logger.info("E9999|未查询到ZIP文件");
            SuccessFilg="E9999|未查询到ZIP文件";
        }
        return SuccessFilg;
    }

    /**
     * 压缩文件为ZIP文件
     * @param filePath 待压缩文件路径
     * @param zipPath  压缩后文件路径
     * @return
     */
    public static String goZip(String filePath,String zipPath,boolean KeepDirStructure)throws Exception{
        long start =System.currentTimeMillis();
        String  SuccessFilg= "E9999|压缩失败";
        ZipOutputStream zipOutPut=null;
        try {
            FileOutputStream fileOutPutStram=new FileOutputStream(zipPath);
            zipOutPut=new ZipOutputStream(fileOutPutStram);
            File zipFilePath=new File(filePath);
            List list=new ArrayList();
            //递归压缩
            compress(zipFilePath,zipOutPut,zipFilePath.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            logger.info("压缩完成，耗时：" + (end - start) +" ms");
            SuccessFilg="M0001|处理成功";
        } catch (Exception e) {
            throw e;
        } finally{
            if(zipOutPut!=null){
                try{
                    zipOutPut.close();
                }catch(IOException e){
                    throw e;
                }
            }
        }
        return SuccessFilg;
    }

    /**
     * 循环压缩方法
     * @param zipFilePath 待压缩文件路径
     * @param zipOutPut	zip输出流
     * @param name	压缩后的名字
     * @param KeepDirStructure 是否保留原目录结构
     * @throws Exception
     */
    public static void compress(File zipFilePath,ZipOutputStream zipOutPut,String name,boolean KeepDirStructure) throws Exception{
        byte[] buf=new byte[2048];
        if(zipFilePath.isFile()){
            //向zip输出流中添加一个zip实体，构造器中name为zip实体文件的名字
            zipOutPut.putNextEntry(new ZipEntry(name));
            //cope文件到zip输出流中
            int len;
            FileInputStream in=new FileInputStream(zipFilePath);
            while((len=in.read(buf))!=-1){
                zipOutPut.write(buf, 0, len);
            }
            zipOutPut.closeEntry();
            in.close();
        }else{
            File[] listFiles=zipFilePath.listFiles();
            if(listFiles==null ||listFiles.length==0){
                if(KeepDirStructure){
                    //对空文件的处理
                    zipOutPut.putNextEntry(new ZipEntry(zipFilePath.getName()+File.separator));
                    zipOutPut.closeEntry();
                }
            }else{
                for(File file:listFiles){
                    if(KeepDirStructure){
                        compress(file, zipOutPut, zipFilePath.getName() + File.separator + file.getName(),KeepDirStructure);
                    }else{
                        compress(file, zipOutPut, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }

    /**
     * 删除sftp上的文件
     * @param directory 目录
     * @param deleteFile 待删除的文件
     * @param channelSftp
     */
    public static void deleteSftpFile(String directory, String deleteFile, ChannelSftp channelSftp)throws SftpException {
        try {
            channelSftp.cd(directory);
            channelSftp.rm(deleteFile);
        } catch (SftpException e) {
            throw e;
        }
    }

    /**
     * 删除本地文件
     * @param file
     */
    public static void deleteFile(File file)throws Exception{
        if(file.isDirectory()){
            String[] children=file.list();
            for(int i=0;i<children.length;i++){
                deleteFile(new File(file,children[i]));
            }
        }
        file.delete();
    }
}
