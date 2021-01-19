package com.longtop.Util.HelpCentre;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.longtop.alsb.dao.BankGSBHDao;
import com.longtop.alsb.dao.HibernateContext;
import com.longtop.alsb.entity.BnkGsbh;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.zip.ZipFile;

public class SFTPUtil {
    private static Logger logger = Logger.getLogger(SFTPUtil.class);

    public static String Line="_";

    public static Session getSession(String username, String host, int port) throws JSchException {
        JSch jsch = new JSch();
        jsch.getSession(username, host, port);
        Session sshSession = jsch.getSession(username, host, port);
        return sshSession;
    }

    /**
     * 获取连接
     * @param username
     * @param password
     * @param host
     * @return
     * @throws JSchException
     */
    public static ChannelSftp getConnect(Session sshSession, String username, String password, String host) throws JSchException {
        ChannelSftp channelSftp = null;
        logger.info("================创建会话");
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshConfig.put("kex", "diffie-hellman-group1-sha1");//openssh太高需要换算法（openSSH7.4及以后）
        sshSession.setConfig(sshConfig);
        sshSession.connect(3000);
        logger.info("================创建连接");
        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        logger.info("================开放通道");
        channelSftp = (ChannelSftp) channel;
        logger.info("================连接到 " + host);
        return channelSftp;
    }


    /**
     * 关闭连接
     */
    public static void disconnect(ChannelSftp channelSftp, Session sshSession)throws Exception {
        if (channelSftp != null) {
            if (channelSftp.isConnected()) {
                channelSftp.disconnect();
                logger.info("SFTP已经关闭");
            }
        }
        if (sshSession != null) {
            if (sshSession.isConnected()) {
                sshSession.disconnect();
                logger.info("已经关闭sshSession");
            }
        }
    }

    /**
     * 上传单个文件
     *
     * @param sftpPath
     *            ：远程保存目录
     * @param sftpFileName
     *            ：保存文件名
     * @param localPath
     *            ：本地上传目录(以路径符号结束)
     * @param localFileName
     *            ：上传的文件名
     * @return
     */
    public static void uploadFile(String sftpPath, String sftpFileName,
                                  String localPath, String localFileName,ChannelSftp channelSftp) throws Exception{
        FileInputStream fileInputStream = null;
        try {
            createDir(sftpPath,channelSftp);
            File file = new File(localPath + File.separator+ localFileName);
            fileInputStream = new FileInputStream(file);
            channelSftp.put(fileInputStream, sftpFileName);
        } catch (Exception e) {
            throw e;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * 下载单个文件
     * @param sftpPath
     * @param sftpFile
     * @param localPath
     * @param channelSftp
     * @return
     * @throws Exception
     */
    public static void downloadFile(String sftpPath, String sftpFile,String localPath, ChannelSftp channelSftp) throws Exception{
        FileOutputStream fileOutputStream = null;
        try {
            channelSftp.cd(sftpPath);
            File file = new File(localPath);
            File zipFile = new File(sftpPath + File.separator + sftpFile);
            fileOutputStream = new FileOutputStream(file);
            if (!zipFile.exists()) channelSftp.get(sftpFile, fileOutputStream);
        } catch (Exception e) {
            throw e;
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * 创建目录
     *
     * @param createpath
     * @return
     * @throws Exception
     */
    public static boolean createDir(String createpath,ChannelSftp channelSftp) throws Exception {
        boolean createDirSign=false;
        try {
            if (isDirExist(createpath,channelSftp)) {
                channelSftp.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString(),channelSftp)) {
                    channelSftp.cd(filePath.toString());
                } else {
                    // 建立目录
                    channelSftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    channelSftp.cd(filePath.toString());
                }

            }
            channelSftp.cd(createpath);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 判断目录是否存在
     *
     * @param directory
     * @return
     */
    public static boolean isDirExist(String directory,ChannelSftp channelSftp) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = channelSftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }


    /**
     * SFTP文件发送方法
     * @param version/agentid
     * @param sftpPath sftp服务器路径
     * @param localPath 本地路径
     * @param fileName	文件名
     * @param targetPath 目标地址（无任何意义）
     * @return
     */
    public static String sftpSendFile(HibernateContext hiContext, String version, String sftpPath, String localPath, String fileName, String targetPath) {
        String successFlag="M0001|处理成功";
        JSch jsch = new JSch();
        ChannelSftp channelSftp=null;
        ChannelSftp targetChannelSftp=null;
        Session targetSshSession=null;
        Session sshSession=null;
        try {
            List list = new BankGSBHDao().checkVersion(hiContext, version);
            if(StringUtil.isEmpty(targetPath)){//下载sftp服务器压缩文件到OSB共享盘
                if(list!=null || list.size()>0){
                    String host= ((BnkGsbh)list.get(0)).getfSftpIp();
                    String sftpPort=((BnkGsbh)list.get(0)).getfSftpPost();
                    String usernameBase64=((BnkGsbh)list.get(0)).getfUser();
                    String username=new String(new BASE64Decoder().decodeBuffer(usernameBase64), "UTF-8");
                    String passwordBase64=((BnkGsbh)list.get(0)).getfPassword();
                    String password=new String(new BASE64Decoder().decodeBuffer(passwordBase64), "UTF-8");
                    int port=Integer.parseInt(sftpPort);
                    sshSession = getSession(username, host, port);
                    channelSftp=getConnect(sshSession,username, password, host);
                    //工行处理后的文件，会根据fileName的名字为母文件夹
                    if(version.equals("102")){
                        Vector vector=channelSftp.ls(sftpPath);
                        Iterator iterator=vector.iterator();
                        String dateFolder=fileName.replaceAll(".zip", "");
                        String localFolderPath=localPath+File.separator+dateFolder;
                        File localDatePath=new File(localFolderPath);
                        if(!localDatePath.exists()){//防止误删
                            localDatePath.mkdir();
                            while(iterator.hasNext()){
                                LsEntry entry=(LsEntry) iterator.next();
                                if(entry.getFilename().equals(".") || entry.getFilename().equals("..")) continue;
                                String iteratorNamePath=sftpPath+"/"+entry.getFilename().toString();
                                if(isDirExist(iteratorNamePath,channelSftp)){
                                    File localFloderPath=new File(localFolderPath+"/"+entry.getFilename().toString());
                                    if(!localFloderPath.exists()) localFloderPath.mkdir();
                                    Vector vectorFile=channelSftp.ls(iteratorNamePath);
                                    Iterator iteratorFile=vectorFile.iterator();
                                    int i=0;
                                    while(iteratorFile.hasNext()){
                                        LsEntry entryFile=(LsEntry) iteratorFile.next();
                                        if(entryFile.getFilename().equals(".") || entryFile.getFilename().equals("..")) continue;
                                        i++;
                                        downloadFile(iteratorNamePath, entryFile.getFilename().toString(),
                                                localFolderPath+File.separator+entry.getFilename().toString()+File.separator+entryFile.getFilename().toString(), channelSftp);
                                        channelSftp.cd(iteratorNamePath);
                                        channelSftp.rm(entryFile.getFilename().toString());
                                    }
                                    String acctZipName=dateFolder+Line+localFloderPath.getName()+Line+i+".zip";
                                    String acctZipPath=localFolderPath+File.separator+acctZipName;
                                    FileUtil.goZip(localFloderPath.toString(), acctZipPath, false);
                                    FileUtil.deleteFile(localFloderPath);
                                    localFloderPath.mkdirs();
                                    File acctZipPathFile=new File(acctZipPath);
                                    acctZipPathFile.renameTo(new File(localFloderPath+File.separator+acctZipName));
                                }
                            }
                        }
                        disconnect(channelSftp,sshSession);
                        //fileName中包含日期，或根据这个日期去遍历查找该日期文件
                    }else if(version.equals("103")){
                        downloadFile(sftpPath, fileName,localPath+File.separator+fileName, channelSftp);
                        disconnect(channelSftp,sshSession);
                        String dateFolder=fileName.split("_")[1];
                        //避免上次处理zip文件异常的问题
                        String localFolderPath=localPath+File.separator+dateFolder;
                        File localFloder=new File(localFolderPath);
                        if(localFloder.exists()) FileUtil.deleteFile(localFloder);

                        FileUtil.unZip(dateFolder, version, localPath, fileName);
                        FileUtil.deleteFile(new File(localPath+File.separator+fileName));
                        File[] listFile=localFloder.listFiles();
                        for(File file:listFile){
                            File[] acctListFile=file.listFiles();
                            int acctListCount=acctListFile.length;
                            String acctZipName=dateFolder+Line+file.getName()+Line+acctListCount+".zip";
                            String acctZipPath=localFolderPath+File.separator+acctZipName;
                            FileUtil.goZip(file.toString(), acctZipPath, false);
                            FileUtil.deleteFile(file);
                            file.mkdirs();
                            File acctZipPathFile=new File(acctZipPath);
                            acctZipPathFile.renameTo(new File(file+File.separator+acctZipName));
                        }
                    }else if(version.equals("104")){
                        Vector vector=channelSftp.ls(sftpPath);
                        Iterator iterator=vector.iterator();
                        String dateFolder=fileName.replaceAll(".zip", "");
                        String localFolderPath=localPath+File.separator+dateFolder;
                        File localDatePath=new File(localFolderPath);
                        if(localDatePath.exists()) FileUtil.deleteFile(localDatePath);
                        localDatePath.mkdir();
                        while(iterator.hasNext()){
                            LsEntry entry=(LsEntry) iterator.next();
                            if(entry.getFilename().equals(".") || entry.getFilename().equals("..")) continue;
                            downloadFile(sftpPath, entry.getFilename(),localFolderPath+File.separator+entry.getFilename(), channelSftp);
                            File initZipFile = new File(localFolderPath+File.separator+entry.getFilename());
                            ZipFile zipFile=new ZipFile(initZipFile);
                            int fileNum=zipFile.size();
                            zipFile.close();
                            int beginIndex=entry.getFilename().indexOf("-");
                            int lastIndex=entry.getFilename().indexOf(".");
                            String acctIdName=entry.getFilename().substring(beginIndex+1,lastIndex);
                            String zipFileName=dateFolder+Line+acctIdName+Line+fileNum+".zip";
                            File newZipFile=new File(localFolderPath+File.separator+acctIdName+File.separator+zipFileName);
                            new File(localFolderPath+File.separator+acctIdName).mkdirs();
                            if(initZipFile.renameTo(newZipFile)) channelSftp.rm(entry.getFilename().toString());
                        }
                        disconnect(channelSftp,sshSession);
                    }else if(version.equals("105")){
                        Vector vector=channelSftp.ls(sftpPath);
                        Iterator iterator=vector.iterator();
                        while(iterator.hasNext()){
                            LsEntry entry=(LsEntry) iterator.next();
                            if(entry.getFilename().equals(".") || entry.getFilename().equals("..")) continue;
                            downloadFile(sftpPath, entry.getFilename(),localPath+File.separator+entry.getFilename(), channelSftp);
                        }
                    }else if(version.equals("301")){
                        downloadFile(sftpPath, fileName,localPath+File.separator+fileName, channelSftp);
                        String dateFolder=fileName.split("_")[1].replaceAll(".zip", "");
                        disconnect(channelSftp,sshSession);
                        //避免上次处理zip文件异常的问题
                        String dateFloder=localPath+File.separator+dateFolder;
                        File localFloder=new File(dateFloder);
                        if(localFloder.exists()) FileUtil.deleteFile(localFloder);

                        FileUtil.unZip(dateFolder, version, localPath, fileName);
                        FileUtil.deleteFile(new File(localPath+File.separator+fileName));
                        File[] listFile=localFloder.listFiles();
                        for(File file:listFile){
                            int dateInt=file.getName().indexOf(Line);
                            ZipFile zipFile=new ZipFile(dateFloder+File.separator+file.getName());
                            int fileNum=zipFile.size();
                            zipFile.close();
                            File acctidFloder=new File(dateFloder+File.separator+file.getName().substring(0, dateInt));
                            acctidFloder.mkdirs();
                            File newLoadZipFile=new File(acctidFloder+File.separator+
                                    dateFolder+Line+file.getName().substring(0, dateInt)+Line+fileNum+".zip");
                            if(!file.renameTo(newLoadZipFile)) return successFlag="E9999|交行电子回单修改文件名失败";
                        }
                    }else{
                        logger.info("============== 没有version为"+version+"的sftp维护信息！");
                        successFlag="E9999|没有version为"+version+"的sftp维护信息";
                    }
                }else{//给久其发送电子回单
                    successFlag="E9999|没有version为"+version+"的sftp维护信息";
                }
            }else{
                if(list!=null || list.size()>0){
                    String host= ((BnkGsbh)list.get(0)).getfSftpIp();
                    String sftpPort=((BnkGsbh)list.get(0)).getfSftpPost();
                    String usernameBase64=((BnkGsbh)list.get(0)).getfUser();
                    String username=new String(new BASE64Decoder().decodeBuffer(usernameBase64), "UTF-8");
                    String passwordBase64=((BnkGsbh)list.get(0)).getfPassword();
                    String password=new String(new BASE64Decoder().decodeBuffer(passwordBase64), "UTF-8");
                    int port=Integer.parseInt(sftpPort);
                    sshSession = getSession(username, host, port);
                    channelSftp=getConnect(sshSession,username, password, host);
                    uploadFile(sftpPath, fileName,localPath, fileName,channelSftp); // 上传文件
                    disconnect(channelSftp,sshSession);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            successFlag="E9999|失败"+e.getMessage();
        } finally {
            try{
                disconnect(channelSftp,sshSession);
                disconnect(targetChannelSftp,targetSshSession);
                logger.info("================关闭所有连接");
            }catch(Exception e){
                e.printStackTrace();
                successFlag="E9999|失败"+e.getMessage();
            }
        }
        logger.info("sftpSendFile运行最终结果："+successFlag);
        return successFlag;
    }
}
