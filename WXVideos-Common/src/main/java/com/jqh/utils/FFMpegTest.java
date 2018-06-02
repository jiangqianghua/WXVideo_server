package com.jqh.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FFMpegTest {

    // 合并音视频
    //ffmpeg -i video.mp4 -i bgm1.mp3 -t 7 -y bgmvideo.mp4
    private String ffmpegExe ;
    public FFMpegTest(String ffmpegExe){
        this.ffmpegExe = ffmpegExe ;
    }
    public void convertor(String input,String outPut) throws Exception{
        // ffmpeg -i input.mp4 output.avi
        List<String> command = new ArrayList<>();
        command.add(ffmpegExe);
        command.add("-i");
        command.add(input);
        command.add(outPut);
        for(String c:command){
            System.out.print(c);
        }
        System.out.println();
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null){

        }
        if(br != null){
            br.close();
        }
        if(inputStreamReader != null){
            inputStreamReader.close();
        }

        if(errorStream != null){
            errorStream.close();
        }

        System.out.println("convertor over!");
    }
    public static void main(String[] args) {
        FFMpegTest ffMpegTest = new FFMpegTest("/usr/local/Cellar/ffmpeg/4.0/bin/ffmpeg");
        try {
            ffMpegTest.convertor(
                    "/Users/jiangqianghua/Downloads/apache-tomcat-7.0.75/webapps/wxvideos/18060277D28X28ZC/video/tmp_922a668d4a302815072b7d91ff705027.mp4",
                    "/Users/jiangqianghua/Downloads/apache-tomcat-7.0.75/webapps/wxvideos/18060277D28X28ZC/video/out.avi"
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
