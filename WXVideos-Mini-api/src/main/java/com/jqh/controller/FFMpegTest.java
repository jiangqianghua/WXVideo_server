package com.jqh.controller;

import java.util.ArrayList;
import java.util.List;

public class FFMpegTest {

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
//        ProcessBuilder process = new ProcessBuilder(command);
//        process.start();
        
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
